package com.example.android.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.MAX_NO_OF_WORDS
import com.example.android.unscramble.data.SCORE_INCREASE
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameUiState(
    val currentScrambledWord: String = "",
    val isGuessedWrong: Boolean = false,
    val score: Int = 0,
    val currentWordCount: Int = 0,
    val isGameOver: Boolean = false
)

class GameViewModel: ViewModel() {

    private val _uiState: MutableStateFlow<GameUiState> = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")
        private set

    init {
        resetGame()
    }

    fun pickRandomWordAndShuffle(): String {
        // get a random word
        currentWord = allWords.random()

        // check if it's not played
        // false, send it to UI
        return if (!usedWords.contains(currentWord)) {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        } else {         // true, pick new one
            pickRandomWordAndShuffle()
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord: CharArray = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord) == currentWord) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun onUserGuessChanged(currentGuess: String) {
        userGuess = currentGuess
    }

    fun checkUserGuess(){
        if (userGuess.equals(currentWord, ignoreCase = true)){
            // count up
            val score = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(score)
        } else {
            // show error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWrong = true)
            }
        }
        updateUserGuess("")
    }

    private fun updateUserGuess(guess: String) {
        userGuess = guess
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    score = updatedScore,
                    isGuessedWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc()
                )
            }
        }
    }
}