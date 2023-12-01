package com.example.composeexercise.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeexercise.ui.theme.ComposeExerciseTheme
import com.example.composeexercise.ui.viewmodels.Exercise2ViewModel

@Composable
fun MainScreen2(
    navControlerParent: NavController,
    exercise2ViewModel: Exercise2ViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "first") {
        composable("first") {
            FirstScreen2(exercise2ViewModel) {
                exercise2ViewModel.currentFValue = it
                navController.navigate("second?fValue=$it")
            }
        }
        composable("second?fValue={fValue}", arguments = listOf(navArgument("fValue") {
            defaultValue = "0"
        }
        )) { backStackEntry ->
            SecondScreen2(exercise2ViewModel, backStackEntry.arguments?.getString("fValue") ?: "") {
                navController.popBackStack()
            }
        }
    }

    val context = LocalContext.current
    BackHandler {
        //Check if there are more screens in the child NavHost
        if (navController.currentBackStackEntry?.destination?.route != "first") {
            navController.popBackStack()
        } else {
            //Navigate back to parent screen
            navControlerParent.popBackStack()
        }
        Toast.makeText(context, "pressed back", Toast.LENGTH_SHORT).show()
    }

    //Similar onDestroy in Fragment
    DisposableEffect(Unit) {
        onDispose {
            Log.d("testt","Exercise2 is disposed")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen2(viewModel: Exercise2ViewModel = hiltViewModel(), callBack: (String) -> Unit) {
    var text by remember { mutableStateOf(viewModel.currentFValue) }
    var isEnable by remember {
        mutableStateOf(viewModel.currentFValue.isNotEmpty())
    }

    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(
                start = 20.dp,
                end = 20.dp
            )
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        OutlinedTextField(
            singleLine = true,
            label = {
                Text(text = "°F to °C")
            }, value = text, onValueChange = {
                text = it
                isEnable = text.isNotEmpty()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .padding(
                    top = (LocalConfiguration.current.screenHeightDp * 0.2f).dp
                )
                .align(Alignment.CenterHorizontally)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 0.dp,
                    top = 10.dp,
                    end = 0.dp,
                    bottom = 0.dp,
                ), onClick = { callBack.invoke(text) },
            enabled = isEnable,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Calculate")
        }
    }
}

@Composable
fun SecondScreen2(
    viewModel: Exercise2ViewModel = hiltViewModel(),
    fValue: String,
    callBack: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${fValue}°F = ${viewModel.calculateFtoC(viewModel.currentFValue.toIntOrNull() ?: 0)}°C",
            color = Color.Black,
            style = MaterialTheme.typography.displayMedium
        )
        Button(onClick = { callBack.invoke() }, shape = RoundedCornerShape(4.dp)) {
            Text(text = "Calculate Again")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {

    ComposeExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FirstScreen2() {}
        }
    }
}