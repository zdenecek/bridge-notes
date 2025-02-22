package com.example.bridgenotes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDetailScreen(dealId: String, onNavigateBack: () -> Unit, onEdit: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Velká cena Prahy - 1. kolo", style = MaterialTheme.typography.titleSmall)
                        Text(
                            stringResource(R.string.deal_number, dealId),
                            
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_deal)
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabeledField(stringResource(R.string.deal_opponents_label), "Tomis - Kaštovský")
            LabeledField(stringResource(R.string.deal_line_label), "NS")
            LabeledField(stringResource(R.string.deal_contract_label), "2NT E +1")
            LabeledField(stringResource(R.string.deal_note_label), "Zdeněk to pokazil, musí vrátit ve třetím štychu piku, je to úplně evidentní.")
            LabeledField(stringResource(R.string.deal_lead_label), "5♣")
            LabeledField(stringResource(R.string.deal_other_label), "lorem ipsum")
        }
    }
}

@Composable
private fun LabeledField(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
} 