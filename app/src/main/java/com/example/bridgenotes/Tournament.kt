package com.example.bridgenotes

data class Tournament(
    val id: String,
    val name: String,
    val date: java.time.LocalDateTime,
    val resultsLink: String,
    val pairOrTeam: String,
    val note: String
)