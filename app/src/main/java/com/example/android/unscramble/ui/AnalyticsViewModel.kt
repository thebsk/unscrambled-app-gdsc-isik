package com.example.android.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.Analytics
import com.example.android.unscramble.data.analyticsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class ResultUiState(
    val analyticsData: List<Analytics> = emptyList()
)

class AnalyticsViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = ResultUiState(analyticsData)
    }
}