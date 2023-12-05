package com.example.composeexercise.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.composeexercise.BuildConfig
import com.example.composeexercise.R
import com.example.composeexercise.ui.theme.ButtonCommon
import com.example.composeexercise.ui.theme.ComposeExerciseTheme
import com.example.composeexercise.ui.viewmodels.Exercise7ViewModel
import com.example.composeexercise.util.ExtensionFunctions.createImageFile
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen7(
    navControllerParent: NavHostController?,
    viewModel: Exercise7ViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        customHandleBack(navControllerParent)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Exercise 7"
                    )
                }
            )
        }
    ) { padding ->
        val focusManager = LocalFocusManager.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            contentAlignment = BiasAlignment(0f, -0.4f)
        ) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                ScreenExercise7(viewModel)
            }
        }
    }
    BackHandler {
        customHandleBack(navControllerParent)
    }

    //Similar onDestroy in Fragment
    DisposableEffect(Unit) {
        onDispose {
            Log.d("testt", "Exercise7 is disposed")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
private fun ScreenExercise7(viewModel: Exercise7ViewModel) {
    ViewModelInit(viewModel = viewModel)
    val launcherGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
            uri: Uri? ->
        viewModel.imageUri = uri
        viewModel.imageLoadType.value = "gallery"
    }

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                viewModel.imageUri = uri
                viewModel.imageLoadType.value = "camera"
            }
        }
    )


    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA,
        onPermissionResult = { granted ->
            if (granted) {
                cameraLauncher.launch(uri)
            } else Toast.makeText(context, "camera permission is denied", Toast.LENGTH_SHORT).show()
        }
    )

    ConstraintLayout {
        var (image, layoutButtons, inputWeb) = createRefs()
        Image(
            modifier = Modifier
                .constrainAs(image) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                    height = Dimension.value(200.dp)
                }
                .background(Color.LightGray),
            painter = viewModel.getImagePainter(type = viewModel.imageLoadType.value),
            contentDescription = null
        )

        Row(modifier = Modifier.constrainAs(layoutButtons) {
            top.linkTo(image.bottom, 10.dp)
            linkTo(start = parent.start, end = parent.end)
        }) {
            ButtonCommon(modifier = Modifier, onClick = {
                viewModel.showHideInputWeb()
            }) {
                Text(text = "Web")
            }
            ButtonCommon(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                onClick = {
                    launcherGallery.launch("image/*")
                }) {
                Text(text = "Gallery")
            }
            ButtonCommon(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                onClick = {
                    cameraPermissionState.launchPermissionRequest()
                }) {
                Text(text = "Camera")
            }
        }
        val context = LocalContext.current
        if (viewModel.mShowWebInput.value) {
            TextField(
                modifier = Modifier.constrainAs(inputWeb) {
                    top.linkTo(layoutButtons.bottom, 10.dp)
                    linkTo(start = parent.start, end = parent.end)
                }, value = viewModel.inputWebValue.value, onValueChange = {
                    viewModel.inputWebValue.value = it
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            //viewModel.imageLoadType.value = "web"
                            viewModel.checkValidURL(viewModel.inputWebValue.value)
                        }, imageVector = Icons.Filled.Check, contentDescription = ""
                    )
                }
            )
        }
    }
}

@Composable
private fun ViewModelInit(viewModel: Exercise7ViewModel){
    val message = viewModel.mMessage.observeAsState()
    if(!message.value.isNullOrEmpty()){
        Toast.makeText(LocalContext.current, message.value, Toast.LENGTH_SHORT).show()
        viewModel.mMessage.value = ""
    }else{
        Toast.makeText(LocalContext.current, "emit null value" , Toast.LENGTH_SHORT).show()
    }
}

private fun customHandleBack(
    navControllerParent: NavController?
) {
    //Navigate back to parent screen
    navControllerParent?.navigateUp()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview7() {

    ComposeExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            //  MainScreen7(navControllerParent = null, hiltViewModel())
            Log.d("testt","GreetingPreview7")
            ScreenExercise7(hiltViewModel())
        }
    }
}