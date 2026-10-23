package com.example.laborotorywork2.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laborotorywork2.model.VoteOption

@Composable
fun VoteCard(
    option: VoteOption,
    isLeader: Boolean,
    onVote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        border = if (isLeader) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option.name,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = option.votes.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(12.dp))
            FilledTonalButton(onClick = onVote) { Text("👍") }
        }
    }
}
