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
        val companionsList: List<User> = users.filter { user -> user.name == companionName }
        if (companionsList.isEmpty()) {
            println("Companion is not found")
            return 0
        }
        val companionId = companionsList.last().userId

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

    fun deleteMessage(messageId: Int): Boolean {
        val messageList: List<Message> = messages.filter { message -> message.messageId == messageId }
        return if (messageList.isEmpty()) {
            println("Message is not found")
            false
        } else {
            val messageListInChat: List<Message> =
                messages.filter { message -> message.chatId == messageList.last().chatId }
            if (messageListInChat.size == 1) {
                chats.removeIf(fun(chat: Chat) = chat.chatId == messageList.last().chatId)
            }
            messages.removeIf(fun(message: Message) = message.messageId == messageId)
            true
        }
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
            val companionsList: List<User> = users.filter { user -> user.userId == chat.companionId }

            val messagesList: List<Message> =
                messages.filter { message -> message.chatId == chat.chatId && message.unread }

            if (companionsList.isNotEmpty()) {
                val companionName = companionsList.last().name
                println("Chat # ${chat.chatId} with $companionName. Count of unread messages = ${messagesList.size}")
                done = true
            }
        }
        return done
    }

    fun getMessages(chatId: Int, lastMessageId: Int, countOfMessages: Int): Boolean {
        val messageList: List<Message> = messages.filter { message -> message.chatId == chatId }
        if (messageList.isEmpty()) {
            println("Could not found some messages")
            return false
        } else {
            val messageListAfterLastMessageId: List<Message> =
                messageList.filter { message -> message.messageId >= lastMessageId }
            return if (messageListAfterLastMessageId.isEmpty()) {
                println("Could not found some messages")
                false
            } else {
                var countOfPrintedMessages = 0
                for (message in messageListAfterLastMessageId) {
                    message.unread = false
                    countOfPrintedMessages++
                    println(message.text)
                    if (countOfPrintedMessages >= countOfMessages) break
                }
                true
            }
        }
    }

    fun getIncomingMessage(userName: String, companionId: Int = 0, text: String): Int {
        val usersList: List<User> = users.filter { user -> user.name == userName }
        if (usersList.isEmpty()) {
            println("User is not found")
            return 0
        }
        val userId = usersList.last().userId


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