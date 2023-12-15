package com.example.composeexercise.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.composeexercise.ui.data.Word
import com.example.composeexercise.ui.data.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Exercise8ViewModel @Inject constructor(
    @ApplicationContext context:Context,
    val wordRepository: WordRepository
): ViewModel() {

    var newWord = mutableStateOf("")
    val allWords: LiveData<List<Word>> = wordRepository.allWords.asLiveData()
    private val _openRowIndex = mutableIntStateOf(-1)
    val openRowIndex: MutableIntState = _openRowIndex

    fun addWord(word: Word) = viewModelScope.launch {
        wordRepository.insertWord(word)
    }

    fun updateOpenRowIndex(updatedIndex: Int) {
        _openRowIndex.value = updatedIndex
    }

    fun updateWord(word: Word) = viewModelScope.launch {
        wordRepository.updateWord(word)
        _openRowIndex.value = -1

    }

    fun deleteWord(word: Word) = viewModelScope.launch {
        wordRepository.deleteWord(word)
        _openRowIndex.value = -1
    }

    fun clearWords() = viewModelScope.launch {
        wordRepository.deleteAllWords()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("testt","Exercise8ViewModel cleared")
    }
}