package com.bridge.notes.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.notes.persistence.database.DataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TournamentListViewModel(
    private val repository: DataRepository
) : ViewModel() {

    val tournaments = repository.getAllTournaments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertDemoData() {
        viewModelScope.launch {
            for (tournament in createDemoData()) {
                repository.createTournament(tournament)
            }
        }
    }

    private fun createDemoData(): List<Tournament> {
        return listOf(
            Tournament(
                name = "Velká cena Prahy - 1. kolo",
                date = LocalDateTime.of(2024, 12, 11, 12, 0),
                resultsLink = "http://results.bridgepraha.cz/vcp2024_1",
                pairOrTeam = "Tomis - Kaštovský",
                note = "První kolo série Velká cena Prahy",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Svoboda - Kopecký",
                        contract = "3NT",
                        declarer = "E",
                        result = "+1",
                        score = "-430",
                        notes = "Zdeněk to pokazil, musí vrátit ve třetím štychu piku."
                    ),
                    Deal(
                        dealNumber = "2",
                        opponents = "Medlín - Martynek",
                        contract = "4♠",
                        declarer = "S",
                        result = "=",
                        score = "+420",
                        notes = "Správná obrana by porazila závazek."
                    )
                )
            ),
            Tournament(
                name = "VC Neratovic",
                date = LocalDateTime.of(2024, 3, 20, 17, 0),
                resultsLink = "http://results.bridgepraha.cz/vcn2024",
                pairOrTeam = "Tomis - Kaštovský",
                note = "Večerní turnaj v Neratovicích",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Nulíček - Vozábal",
                        contract = "6♥",
                        declarer = "W",
                        result = "-1",
                        score = "+100",
                        notes = "Dobrá obrana porazila slem."
                    )
                )
            ),
            Tournament(
                name = "Mezinárodní Turnaj Mosty",
                date = LocalDateTime.of(2024, 5, 15, 14, 30),
                resultsLink = "http://results.bridgepraha.cz/mtm2024",
                pairOrTeam = "Novák - Černý",
                note = "Úvodní kolo mezinárodního turnaje",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Novák - Černý",
                        contract = "4NT",
                        declarer = "N",
                        result = "0",
                        score = "+50",
                        notes = "Vyvážená hra s minimy chyb."
                    ),
                    Deal(
                        dealNumber = "2",
                        opponents = "Marek - Dvořák",
                        contract = "3♠",
                        declarer = "E",
                        result = "+2",
                        score = "-100",
                        notes = "Překvapivé zvraty v průběhu hry."
                    ),
                    Deal(
                        dealNumber = "3",
                        opponents = "Horák - Bartoš",
                        contract = "5♦",
                        declarer = "S",
                        result = "=",
                        score = "+120",
                        notes = "Skvělá obrana proti protivníkovi."
                    )
                )
            ),
            Tournament(
                name = "Bridgemasters Cup",
                date = LocalDateTime.of(2024, 6, 10, 18, 45),
                resultsLink = "http://results.bridgepraha.cz/bmc2024",
                pairOrTeam = "Kaštovský - Novotný",
                note = "Předkolo Bridgemasters Cup",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Petr - Adam",
                        contract = "3♣",
                        declarer = "W",
                        result = "+1",
                        score = "-150",
                        notes = "Výborná obrana vedla k drobnému skóre."
                    ),
                    Deal(
                        dealNumber = "2",
                        opponents = "Sedláček - Fiala",
                        contract = "4♠",
                        declarer = "N",
                        result = "0",
                        score = "+200",
                        notes = "Vyrovnaná hra s malými chybami."
                    )
                )
            ),
            Tournament(
                name = "Zimní Bridge Festival",
                date = LocalDateTime.of(2024, 2, 28, 16, 0),
                resultsLink = "http://results.bridgepraha.cz/zbf2024",
                pairOrTeam = "Martynek - Vít",
                note = "Speciální zimní edice",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Krejčí - Bílý",
                        contract = "4NT",
                        declarer = "S",
                        result = "+3",
                        score = "-250",
                        notes = "Rychlé rozhodnutí vedlo k úspěchu."
                    )
                )
            ),
            Tournament(
                name = "Letní Open Bridge",
                date = LocalDateTime.of(2024, 7, 12, 13, 15),
                resultsLink = "http://results.bridgepraha.cz/lob2024",
                pairOrTeam = "Dvořák - Svoboda",
                note = "Letní turnaj s otevřenou registrací",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Růžička - Zelenka",
                        contract = "3NT",
                        declarer = "E",
                        result = "0",
                        score = "+80",
                        notes = "Vyrovnaný průběh hry."
                    ),
                    Deal(
                        dealNumber = "2",
                        opponents = "Marek - Urban",
                        contract = "5♣",
                        declarer = "W",
                        result = "-2",
                        score = "-220",
                        notes = "Těžký odboj při kontraktu."
                    )
                )
            ),
            Tournament(
                name = "Regionální Mistrovství",
                date = LocalDateTime.of(2024, 8, 5, 19, 0),
                resultsLink = "http://results.bridgepraha.cz/rm2024",
                pairOrTeam = "Horák - Čermák",
                note = "Regionální turnaj pro špičkové páry",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Kovář - Svoboda",
                        contract = "4♥",
                        declarer = "N",
                        result = "+1",
                        score = "+150",
                        notes = "Dobře zahraná ruka s přesným odhadem."
                    ),
                    Deal(
                        dealNumber = "2",
                        opponents = "Václav - Beneš",
                        contract = "3♠",
                        declarer = "S",
                        result = "=",
                        score = "+110",
                        notes = "Vyrovnaný průběh s dobrým nastavením obrany."
                    )
                )
            ),
            Tournament(
                name = "Český Bridge Masters",
                date = LocalDateTime.of(2024, 9, 9, 20, 30),
                resultsLink = "http://results.bridgepraha.cz/cbm2024",
                pairOrTeam = "Maly - Veselý",
                note = "Nejprestižnější turnaj v České republice",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Novotný - Marek",
                        contract = "2NT",
                        declarer = "W",
                        result = "+1",
                        score = "-100",
                        notes = "Dobré čtení hry a skvělá obrana."
                    ),
                    Deal(
                        dealNumber = "2",
                        opponents = "Černá - Vít",
                        contract = "4♣",
                        declarer = "E",
                        result = "+2",
                        score = "-300",
                        notes = "Výborná hra vedla k úspěšnému výsledku."
                    ),
                    Deal(
                        dealNumber = "3",
                        opponents = "Krejčí - Urban",
                        contract = "3NT",
                        declarer = "N",
                        result = "=",
                        score = "+90",
                        notes = "Vyrovnaná hra s minimálními chybami."
                    )
                )
            ),
            Tournament(
                name = "Pražská Bridge Liga",
                date = LocalDateTime.of(2024, 10, 3, 18, 0),
                resultsLink = "http://results.bridgepraha.cz/pbl2024",
                pairOrTeam = "Vít - Marek",
                note = "Liga v hlavním městě",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Svoboda - Dvořák",
                        contract = "4♠",
                        declarer = "S",
                        result = "+1",
                        score = "-180",
                        notes = "Pevná hra s minimální chybou."
                    ),
                    Deal(
                        dealNumber = "2",
                        opponents = "Novotný - Horák",
                        contract = "3♦",
                        declarer = "E",
                        result = "=",
                        score = "+100",
                        notes = "Vyrovnaná hra s dobře nastavenou obranou."
                    )
                )
            ),
            Tournament(
                name = "Velká cena Prahy - Finále",
                date = LocalDateTime.of(2024, 12, 15, 15, 0),
                resultsLink = "http://results.bridgepraha.cz/vcp2024_final",
                pairOrTeam = "Tomis - Kaštovský",
                note = "Finále série Velká cena Prahy se 16 dealy",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Horák - Novotný",
                        contract = "3NT",
                        declarer = "E",
                        result = "+1",
                        score = "-250",
                        notes = "Silný úvodní kontrakt."
                    ),
                    Deal(
                        dealNumber = "2",
                        opponents = "Svoboda - Dvořák",
                        contract = "4♠",
                        declarer = "S",
                        result = "0",
                        score = "+300",
                        notes = "Dobrá rovnováha v obou směrech."
                    ),
                    Deal(
                        dealNumber = "3",
                        opponents = "Kaštovský - Veselý",
                        contract = "4♥",
                        declarer = "W",
                        result = "+2",
                        score = "-400",
                        notes = "Výborná obrana vedla k úspěchu."
                    ),
                    Deal(
                        dealNumber = "4",
                        opponents = "Marek - Černý",
                        contract = "5♦",
                        declarer = "N",
                        result = "=",
                        score = "+150",
                        notes = "Vyrovnaný průběh s minimy chyb."
                    ),
                    Deal(
                        dealNumber = "5",
                        opponents = "Krejčí - Bartoš",
                        contract = "3♣",
                        declarer = "E",
                        result = "-1",
                        score = "+80",
                        notes = "Rychlý odboj a chytrá obrana."
                    ),
                    Deal(
                        dealNumber = "6",
                        opponents = "Horák - Veselý",
                        contract = "4NT",
                        declarer = "S",
                        result = "+1",
                        score = "-220",
                        notes = "Pevná hra s drobným deficitem."
                    ),
                    Deal(
                        dealNumber = "7",
                        opponents = "Novotný - Dvořák",
                        contract = "3NT",
                        declarer = "W",
                        result = "=",
                        score = "+130",
                        notes = "Vyrovnaná hra s dobře načasovanými tahy."
                    ),
                    Deal(
                        dealNumber = "8",
                        opponents = "Martynek - Urban",
                        contract = "4♣",
                        declarer = "N",
                        result = "+2",
                        score = "-350",
                        notes = "Agresivní hra s vysokým rizikem."
                    ),
                    Deal(
                        dealNumber = "9",
                        opponents = "Petr - Adam",
                        contract = "5♠",
                        declarer = "E",
                        result = "0",
                        score = "+200",
                        notes = "Vyvážený kontrakt a stabilní průběh."
                    ),
                    Deal(
                        dealNumber = "10",
                        opponents = "Růžička - Zelenka",
                        contract = "3♥",
                        declarer = "S",
                        result = "+1",
                        score = "-180",
                        notes = "Dobrý výběr kontraktu za nejisté situace."
                    ),
                    Deal(
                        dealNumber = "11",
                        opponents = "Maly - Čermák",
                        contract = "4♦",
                        declarer = "W",
                        result = "=",
                        score = "+90",
                        notes = "Pečlivá hra a silná obrana."
                    ),
                    Deal(
                        dealNumber = "12",
                        opponents = "Horák - Kaštovský",
                        contract = "3♣",
                        declarer = "N",
                        result = "-2",
                        score = "+110",
                        notes = "Riskantní volba kontraktu se vyplatila."
                    ),
                    Deal(
                        dealNumber = "13",
                        opponents = "Svoboda - Urban",
                        contract = "4NT",
                        declarer = "E",
                        result = "+1",
                        score = "-300",
                        notes = "Výborné čtení karet během hry."
                    ),
                    Deal(
                        dealNumber = "14",
                        opponents = "Marek - Veselý",
                        contract = "5♣",
                        declarer = "S",
                        result = "0",
                        score = "+170",
                        notes = "Udržel rovnováhu navzdory tlaku soupeřů."
                    ),
                    Deal(
                        dealNumber = "15",
                        opponents = "Novotný - Černý",
                        contract = "3NT",
                        declarer = "W",
                        result = "+2",
                        score = "-450",
                        notes = "Výrazný rozdíl v úspěšnosti kontraktu."
                    ),
                    Deal(
                        dealNumber = "16",
                        opponents = "Krejčí - Dvořák",
                        contract = "4♠",
                        declarer = "N",
                        result = "=",
                        score = "+220",
                        notes = "Finálový kontrakt s precizní hrou."
                    )
                )
            )
        )
    }
} 