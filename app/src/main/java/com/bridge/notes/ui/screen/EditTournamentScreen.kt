package com.bridge.notes.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bridge.notes.R
import com.bridge.notes.model.TournamentViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTournamentScreen(
    tournamentId: Long,
    onNavigateBack: () -> Unit,
    viewModel: TournamentViewModel
) {
    val tournament by viewModel.tournaments.collectAsState()
        .value.find { it.id == tournamentId }
        .let { remember(it) { mutableStateOf(it) } }
    
    // State for form fields
    var name by rememberSaveable(tournament) { mutableStateOf(tournament?.name ?: "") }
    var date by rememberSaveable(tournament) { mutableStateOf(tournament?.date ?: LocalDateTime.now()) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var resultsLink by rememberSaveable(tournament) { mutableStateOf(tournament?.resultsLink ?: "") }
    var pairOrTeam by rememberSaveable(tournament) { mutableStateOf(tournament?.pairOrTeam ?: "") }
    var note by rememberSaveable(tournament) { mutableStateOf(tournament?.note ?: "") }

    if (showDatePicker)     {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = date.atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli()
        )
        
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        date = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(millis),
                            ZoneId.systemDefault()
                        )
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.dismiss))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tournament?.name ?: stringResource(R.string.edit_tournament)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            tournament?.let {
                                viewModel.updateTournament(
                                    it.copy(
                                        name = name,
                                        date = date,  // Now using LocalDateTime directly
                                        resultsLink = resultsLink,
                                        pairOrTeam = pairOrTeam,
                                        note = note
                                    )
                                )
                            }
                            onNavigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.save_changes)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (tournament == null) {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.loading),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.tournament_name_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                    onValueChange = { },
                    label = { Text(stringResource(R.string.tournament_date_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = stringResource(R.string.select_date)
                            )
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = resultsLink,
                    onValueChange = { resultsLink = it },
                    label = { Text(stringResource(R.string.tournament_results_link_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = pairOrTeam,
                    onValueChange = { pairOrTeam = it },
                    label = { Text(stringResource(R.string.tournament_pair_team_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text(stringResource(R.string.tournament_notes_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        }
    }
} 