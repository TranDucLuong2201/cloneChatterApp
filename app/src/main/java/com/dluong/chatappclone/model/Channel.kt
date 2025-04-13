package com.dluong.chatappclone.model

data class Channel(
    val id: String = "",
    val name: String,
    val createAt: Long = System.currentTimeMillis()
)
