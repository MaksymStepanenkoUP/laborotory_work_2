package com.example.laborotorywork2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laborotorywork2.model.VoteOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class VoteViewModel : ViewModel() {

    private val _options = MutableStateFlow<List<VoteOption>>(emptyList())
    private var nextId = 0L

    /** Список, завжди відсортований за спаданням голосів. */
    val options: StateFlow<List<VoteOption>> = _options
        .map { list -> list.sortedByDescending { it.votes } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** Лідер; null, якщо ще ніхто не голосував. */
    val leader: StateFlow<VoteOption?> = _options
        .map { list -> list.maxByOrNull { o -> o.votes }?.takeIf { it.votes > 0 } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun addOption(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        _options.update { it + VoteOption(id = nextId++, name = trimmed) }
    }

    fun vote(id: Long) {
        _options.update { list ->
            list.map { if (it.id == id) it.copy(votes = it.votes + 1) else it }
        }
    }
}
