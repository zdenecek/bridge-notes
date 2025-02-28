package com.example.bridgenotes

import Deal

data class Tournament(
    val id: Long = 0L,
    val name: String,
    val date: java.time.LocalDateTime,
    val resultsLink: String,
    val pairOrTeam: String,
    val note: String,
    val deals: List<Deal> = emptyList()
)