package com.example.composeexercise.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.composeexercise.R
import com.example.composeexercise.ui.objets.Exercise5Data
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class Exercise5ViewModel @Inject constructor(
    @ApplicationContext context:Context
): ViewModel() {

    val mData = mutableStateListOf<Exercise5Data>()
    val mTitleActionBar = mutableStateOf(context.getString(R.string.exercise5_default_title))
    init {
        repeat(20) {
            mData.add(
                Exercise5Data(
                    it,
                    "https://developer.android.com/images/brand/Android_Robot.png",
                    "name $it"
                )
            )
        }
    }

    fun updateTitleActionBar(title: String){
        mTitleActionBar.value = title
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("testt","Exercise5ViewModel cleared")
    }
}