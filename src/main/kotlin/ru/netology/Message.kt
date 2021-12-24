package ru.netology

data class Message(
    val userId: Int,
    val companionId: Int,
    val chatId: Int,
    var messageId: Int,
    val text: String,
    var unread: Boolean = false
)
