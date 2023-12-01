package com.example.composeexercise.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeexercise.ui.theme.ComposeExerciseTheme

@Composable
fun MainScreen1() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "first") {
        composable("first") {
            FirstScreen {
                navController.navigate("second?name=$it")
            }
        }
        composable("second?name={name}", arguments = listOf(navArgument("name") {
            defaultValue = "defaultValue"
        }
        )) { backStackEntry ->
            SecondScreen(backStackEntry.arguments?.getString("name") ?: "") {
                navController.popBackStack()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(callBack: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    var isEnable by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = (LocalConfiguration.current.screenHeightDp * 0.2f).dp
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
                Text(text = "Your first name")
            }, value = text, onValueChange = {
                text = it
                isEnable = text.isNotEmpty()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(4.dp))
                .fillMaxWidth(0.8f)
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
            Text(text = "Enter")
        }
    }
}

@Composable
fun SecondScreen(name: String, callBack: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome $name", color = Color.Black)
        Button(onClick = { callBack.invoke() }, shape = RoundedCornerShape(4.dp)) {
            Text(text = "Exit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    ComposeExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FirstScreen() {}
        }
    }
}