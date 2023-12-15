package com.example.composeexercise.ui.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.example.composeexercise.util.ExtensionFunctions.isValidUrl
import com.example.composeexercise.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Exercise7ViewModel @Inject constructor(
    @ApplicationContext context:Context
): ViewModel() {

    val mShowWebInput = mutableStateOf(false)
    val inputWebValue = mutableStateOf("https://developer.android.com/images/brand/Android_Robot.png")
    val imageLoadType = mutableStateOf("")
    var imageUri : Uri? = null


    val mMessage by lazy {
        SingleLiveEvent<String>()
    }

    @Composable
    fun getImagePainter(type: String): Painter{
        return when(type){
            "web" -> {
                rememberAsyncImagePainter(
                    model = inputWebValue.value
                )
            }
            "gallery" -> {
                rememberAsyncImagePainter(
                    model = imageUri
                )
            }
            "camera" -> {
                rememberAsyncImagePainter(
                    model = imageUri
                )
            }
            else -> rememberVectorPainter(image = Icons.Filled.Person)
        }
    }

    fun checkValidURL(url: String){
        viewModelScope.launch(Dispatchers.IO) {
            if(url.isValidUrl()){
                inputWebValue.value = url
                imageLoadType.value = "web"
            }else{
                mMessage.postValue("Invalid URL")
            }
        }
    }


    fun showHideInputWeb(){
        mShowWebInput.value = !mShowWebInput.value
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("testt","Exercise7ViewModel cleared")
    }
}