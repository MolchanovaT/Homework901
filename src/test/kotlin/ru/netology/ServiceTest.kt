package ru.netology

import org.junit.Test

import org.junit.Assert.*

class ServiceTest {

    @Test
    fun shouldNotSendMessageNotFoundCompanion() {
        val result: Int = Service.sendMessage(
                0,
            "best friend",
            "Text"
        )
        assertEquals(result, 0)
    }

    @Test
    fun shouldSendMessage() {
        val result: Int = Service.sendMessage(
            0,
            "mum",
            "Text"
        )
        assertEquals(result, 2)
    }

    @Test
    fun shouldNotDeleteMessage() {
        val result: Boolean = Service.deleteMessage(15555)

        assertFalse(result)
    }

    @Test
    fun shouldDeleteChat() {
        Service.sendMessage(
            0,
            "mum",
            "Text"
        )
        val result: Boolean = Service.deleteMessage(1)

        assertTrue(result)
    }

    @Test
    fun shouldNotGetChats() {
        val result: Boolean = Service.getChats()

        assertFalse(result)
    }

    @Test
    fun shouldGetChats() {
        Service.sendMessage(0, "mum", "text")
        val result: Boolean = Service.getChats()

        assertTrue(result)
    }

    @Test
    fun shouldNotGetMessages() {
        val result: Boolean = Service.getMessages(1555, 1,1)

        assertFalse(result)
    }

    @Test
    fun shouldGetMessages() {
        Service.sendMessage(0, "mum", "text")
        val result: Boolean = Service.getMessages(1, 1,1)

        assertTrue(result)
    }

    @Test
    fun shouldNotGetIncomingMessage() {
        val result: Int = Service.getIncomingMessage("best friend", 0,"text")

        assertEquals(result, 0)
    }

    @Test
    fun shouldGetIncomingMessage() {
        val result: Int = Service.getIncomingMessage("mum", 0,"text")

        assertEquals(result, 2)
    }

    @Test
    fun shouldNotGetUnreadChatsCount() {
        Service.deleteChat(1)
        Service.deleteChat(2)
        Service.deleteChat(3)
        val result: Int = Service.getUnreadChatsCount()

        assertEquals(result, 0)
    }

    @Test
    fun shouldGetUnreadChatsCount() {
        Service.getIncomingMessage(
            "mum",
            0,
            "Text"
        )
        val result: Int = Service.getUnreadChatsCount()

        assertNotEquals(result, 0)
    }
}