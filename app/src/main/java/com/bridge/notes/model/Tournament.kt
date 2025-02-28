package com.bridge.notes.model

data class Tournament(
    val id: Long = 0L,
    val name: String,
    val date: java.time.LocalDateTime,
    val resultsLink: String,
    val pairOrTeam: String,
    val note: String,
    val deals: List<Deal> = emptyList()
)