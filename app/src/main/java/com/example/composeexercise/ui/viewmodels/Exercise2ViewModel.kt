package com.example.composeexercise.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class Exercise2ViewModel @Inject constructor() : ViewModel() {
    var currentFValue = ""
    fun calculateFtoC(fValue: Int): Double {
        val c = (fValue - 32.0) * 5.0 / 9.0
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(c).toDouble()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("testt","Exercise2ViewModel cleared")
    }
}