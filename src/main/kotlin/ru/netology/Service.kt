package ru.netology

object Service {
    private var users: MutableList<User> = mutableListOf(
        User(0, "me"),
        User(1, "mum"),
        User(2, "dad"),
        User(3, "brother"),
        User(4, "sister"),
        User(5, "husband"),
        User(6, "friend")
    )
    private var chats: MutableList<Chat> = mutableListOf()
    private var messages: MutableList<Message> = mutableListOf()

    fun sendMessage(userId: Int = 0, companionName: String, text: String): Int {
        val companionId: Int = users.asSequence()
            .filter { user -> user.name == companionName }
            .take(1)
            .joinToString { it.userId.toString() }.toInt()

        val chatListWithThisCompanion: List<Chat> = chats.filter { chat -> chat.companionId == companionId }
        return if (chatListWithThisCompanion.isNotEmpty()) {
            addMessage(userId, companionId, chatListWithThisCompanion.last().chatId, text)
        } else {
            val newChatId = addChat(userId, companionId)
            addMessage(userId, companionId, newChatId, text)
        }
    }

    private fun addChat(userId: Int, companionId: Int): Int {
        val newChat = Chat(userId, companionId, getNewChatId())
        chats.add(newChat)
        return newChat.chatId
    }

    private fun addMessage(userId: Int, companionId: Int, chatId: Int, text: String, unread: Boolean = false): Int {
        val newMessage = Message(userId, companionId, chatId, getNewMessageId(), text, unread)
        messages.add(newMessage)
        return newMessage.messageId
    }

    private fun getNewChatId(): Int {
        return if (chats.isEmpty()) 1
        else {
            val lastChatId = chats.last().chatId
            lastChatId + 1
        }
    }

    private fun getNewMessageId(): Int {
        return if (messages.isEmpty()) 1
        else {
            val lastMessageId = messages.last().messageId
            lastMessageId + 1
        }
    }

    fun deleteMessage(messageId: Int) {
        val chatId: Int = messages.asSequence()
            .filter { message -> message.messageId == messageId }
            .take(1)
            .joinToString { it.chatId.toString() }.toInt()

        val messageListInChat: List<Message> =
            messages.filter { message -> message.chatId == chatId }
        if (messageListInChat.size == 1) {
            chats.removeIf(fun(chat: Chat) = chat.chatId == chatId)
        }
        messages.removeIf(fun(message: Message) = message.messageId == messageId)
    }

    fun deleteChat(chatId: Int): Boolean {
        val chatList: List<Chat> = chats.filter { chat -> chat.chatId == chatId }
        return if (chatList.isEmpty()) {
            println("Chat is not found")
            false
        } else {
            chats.removeIf(fun(chat: Chat) = chat.chatId == chatId)
            messages.removeIf(fun(message: Message) = message.chatId == chatId)
            true
        }
    }

    fun getChats(): Boolean {
        var done = false
        for (chat in chats) {
            val companionName = users.asSequence()
                .filter { user -> user.userId == chat.companionId }
                .take(1)
                .joinToString { it.name }
                .ifEmpty { "Chat is empty" }

            val messagesList: List<Message> =
                messages.filter { message -> message.chatId == chat.chatId && message.unread }

            println("Chat # ${chat.chatId} with $companionName. Count of unread messages = ${messagesList.size}")
            done = true
        }
        return done
    }

    fun getMessages(chatId: Int, lastMessageId: Int, countOfMessages: Int) {
        val messagesStr: String = messages.asSequence()
            .filter { message -> message.chatId == chatId && message.messageId >= lastMessageId }
            .take(countOfMessages)
            .joinToString(separator = "\n") { it.text }
            .ifEmpty { "Could not found some messages" }

        println(messagesStr)
    }

    fun getIncomingMessage(userName: String, companionId: Int = 0, text: String): Int {
        val userId = users.asSequence()
            .filter { user -> user.name == userName }
            .take(1)
            .joinToString { it.userId.toString() }.toInt()

        val chatListWithThisCompanion: List<Chat> = chats.filter { chat -> chat.companionId == userId }
        return if (chatListWithThisCompanion.isNotEmpty()) {
            addMessage(userId, companionId, chatListWithThisCompanion.last().chatId, text, true)
        } else {
            val newChatId = addChat(userId, companionId)
            addMessage(userId, companionId, newChatId, text, true)
        }
    }

    fun getUnreadChatsCount(): Int {
        val messageUnreadList: List<Message> = messages.filter { message -> message.unread }
        var countOfUnreadChats = 0

        for (message in messageUnreadList) {
            val chatList: List<Chat> = chats.filter { chat -> chat.chatId == message.chatId }
            countOfUnreadChats += chatList.size
        }
        return countOfUnreadChats
    }
}