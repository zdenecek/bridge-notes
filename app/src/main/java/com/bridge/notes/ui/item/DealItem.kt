import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bridge.notes.model.Deal

@Composable
fun DealItem(
    deal: Deal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Deal number if present
            if (deal.dealNumber.isNotBlank()) {
                Text(
                    text = "Deal #${deal.dealNumber}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            // Opponents
            if (deal.opponents.isNotBlank()) {
                Text(
                    text = deal.opponents,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            // Contract info in one row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (deal.contract.isNotBlank()) {
                    Text(text = deal.contract)
                }
                if (deal.declarer.isNotBlank()) {
                    Text(text = deal.declarer)
                }
                if (deal.result.isNotBlank()) {
                    Text(text = deal.result)
                }
            }
            
            // Score if present
            if (deal.score.isNotBlank()) {
                Text(
                    text = deal.score,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            // Notes if present
            if (deal.notes.isNotBlank()) {
                Text(
                    text = deal.notes,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
} 