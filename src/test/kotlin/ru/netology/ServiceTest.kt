package ru.netology

import org.junit.Test

import org.junit.Assert.*

class ServiceTest {

    @Test
    fun shouldNotGetChats() {
        val result: Boolean = Service.getChats()

        assertFalse(result)
    }

    @Test
    fun shouldGetIncomingMessage() {
        val result: Int = Service.getIncomingMessage("mum", 0, "text")

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