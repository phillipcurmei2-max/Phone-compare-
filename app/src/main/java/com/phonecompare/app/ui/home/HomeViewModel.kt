package com.phonecompare.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.phonecompare.app.PhoneCompareApplication
import com.phonecompare.app.domain.repository.PhoneRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Holds and exposes the immutable [HomeUiState] for the landing screen.
 *
 * The catalogue status is observed reactively from the repository; the search
 * query is local UI state. No searching is performed yet because the catalogue
 * is empty in this milestone — the ViewModel never produces placeholder
 * results.
 */
class HomeViewModel(
    private val phoneRepository: PhoneRepository,
) : ViewModel() {

    private val queryState = MutableStateFlow("")

    val uiState: StateFlow<HomeUiState> =
        combine(queryState, phoneRepository.observeDatabaseStatus()) { query, status ->
            HomeUiState(query = query, databaseStatus = status)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(),
        )

    fun onQueryChange(newQuery: String) {
        queryState.update { newQuery }
    }

    companion object {
        /** Factory that resolves the repository from the application container. */
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as PhoneCompareApplication
                HomeViewModel(app.container.phoneRepository)
            }
        }
    }
}
