package com.example.laborotorywork2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laborotorywork2.viewmodel.VoteViewModel

@Composable
fun VoteScreen(vm: VoteViewModel = viewModel()) {
    val options by vm.options.collectAsState()
    val leader by vm.leader.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }

    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        // Заголовок з лідером
        Column(Modifier.padding(16.dp)) {
            Text("🏆 Голосування", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(
                text = leader?.let { "Лідер: ${it.name} (${it.votes} голосів)" }
                    ?: "Голосування ще не розпочато",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        HorizontalDivider()

        // Форма додавання
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Додати варіант...") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { vm.addOption(text); text = "" },
                enabled = text.isNotBlank()
            ) { Text("+ Додати") }
        }

        // Список
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(options, key = { _, o -> o.id }) { index, option ->
                VoteCard(
                    option = option,
                    isLeader = index == 0 && option.votes > 0,
                    onVote = { vm.vote(option.id) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}
