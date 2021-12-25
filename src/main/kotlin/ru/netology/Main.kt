package ru.netology

fun main() {
    println("Add same messages")
    messageTo("mum", "Hi, mum! How are you?")
    messageTo("husband", "Hello, honey! Miss you so much)))))))))")
    messageTo("brother", "Hi, my little bunny)")
    messageTo("mum", "How are you feeling today?")
    messageTo("mum", "Is everything ok?")
    println()

    println("Add same incoming messages")
    messageFrom("mum", "Hi, sweetie!")
    messageFrom("husband", "Hello, my lovely! Miss you as well)))))))))")
    messageFrom("brother", "Hi, my little sis)")
    messageFrom("mum", "I feel myself very good today, everything is good!")
    println()

    println("Print some messages")
    Service.getMessages(1, 1, 5)
    println()

    Service.getChats()
    println()

    println("Delete some messages")
    deleteMessageById(4)
    deleteMessageById(3)
    println()

    Service.getChats()
    println()

    println("Delete some chats")
    deleteChatById(1)
    println()

    Service.getChats()
    println()

    println("Get count of unread chats")
    println("Count of unread chats: ${Service.getUnreadChatsCount()}")
    println()
}

fun messageTo(companionName: String, text: String) {
    val res = Service.sendMessage(userId = 0, companionName, text)
    if (res != 0) println("Message successfully send. Message id = $res")
    else println("Message could not be sent. Message id = $res")
}

fun messageFrom(userName: String, text: String) {
    val res = Service.getIncomingMessage(userName, companionId = 0, text)
    if (res != 0) println("Message successfully send. Message id = $res")
    else println("Message could not be sent. Message id = $res")
}

fun deleteMessageById(messageId: Int) {
    Service.deleteMessage(messageId)
    println("Message $messageId successfully deleted")
}

fun deleteChatById(chatId: Int) {
    if (Service.deleteChat(chatId)) println("Chat $chatId successfully deleted")
    else println("Chat could not be deleted")
}