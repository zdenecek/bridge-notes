package com.bridge.notes.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CalendarLocale
import androidx.compose.ui.input.nestedscroll.nestedScroll
import java.time.LocalDateTime
import androidx.compose.material3.DatePickerFormatter
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.bridge.notes.R
import com.bridge.notes.model.Tournament
import com.bridge.notes.model.TournamentViewModel
import java.time.ZoneId
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTournamentScreen(
    onNavigateBack: () -> Unit,
    viewModel: TournamentViewModel
) {
    var name by rememberSaveable { mutableStateOf("") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var date by rememberSaveable { mutableStateOf(LocalDateTime.now()) }
    var resultsLink by rememberSaveable { mutableStateOf("") }
    var pairTeam by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_tournament)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = {
                            val newTournament = Tournament(
                                name = name,
                                date = date,
                                resultsLink = resultsLink,
                                pairOrTeam = pairTeam,
                                note = note,
                                deals = emptyList()
                            )
                            viewModel.createTournament(newTournament)
                            onNavigateBack()
                        },
                        enabled = name.isNotBlank()
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.tournament_name_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = date.atZone(ZoneId.systemDefault())
                        .toInstant().toEpochMilli()
                )
                val customFormatter = remember { CustomDateFormatter() }

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
                    DatePicker(state = datePickerState, dateFormatter = customFormatter)
                }
            }

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

            OutlinedTextField(
                value = resultsLink,
                onValueChange = { resultsLink = it },
                label = { Text(stringResource(R.string.tournament_results_link_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pairTeam,
                onValueChange = { pairTeam = it },
                label = { Text(stringResource(R.string.tournament_pair_team_label)) },
                modifier = Modifier.fillMaxWidth()
            )

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

@OptIn(ExperimentalMaterial3Api::class)
class CustomDateFormatter : DatePickerFormatter {
    private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    private val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    override fun formatDate(
        dateMillis: Long?,
        locale: CalendarLocale,
        forContentDescription: Boolean
    ): String? {
        return dateMillis?.let { dateFormat.format(it) }
    }

    override fun formatMonthYear(monthMillis: Long?, locale: CalendarLocale): String? {
        return monthMillis?.let { monthYearFormat.format(it) }
    }
}
