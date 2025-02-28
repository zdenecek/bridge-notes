package com.bridge.notes.model

data class Deal(
    val id: Long = 0L,
    val tournamentId: Long = 0L,
    val dealNumber: String,
    val opponents: String,
    val contract: String,
    val declarer: String,
    val result: String,
    val score: String,
    val notes: String
) 