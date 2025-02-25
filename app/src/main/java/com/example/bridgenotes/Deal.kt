data class Deal(
    val id: Long,
    val tournamentId: Long,
    val dealNumber: String,
    val opponents: String,
    val contract: String,
    val declarer: String,
    val result: String,
    val score: String,
    val notes: String
) 