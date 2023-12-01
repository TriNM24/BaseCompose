package com.example.composeexercise.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class Exercise4ViewModel @Inject constructor(): ViewModel() {

    val mData = mutableStateListOf<String>()
    val mErrorMsg = mutableStateOf("")
    var mIsAscending = mutableStateOf(true)

    /*init {
        repeat(20) {
            mData.add("Item #$it")
        }
    }*/

    fun addData(data: String): Boolean{
        return if(mData.contains(data)){
            mErrorMsg.value = "Word is already exist"
            false
        }else{
            mErrorMsg.value = ""
            mData.add(data)
            mData.sort()
            if(!mIsAscending.value){
                mData.reversed()
            }
            true
        }
    }

    fun removeData(data: String){
        mData.remove(data)
    }

    fun changeSortType(){
        mIsAscending.value = !mIsAscending.value
        mData.sort()
        if(!mIsAscending.value){
            mData.reverse()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("testt","Exercise4ViewModel cleared")
    }
}