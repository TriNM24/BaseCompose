package com.example.composeexercise.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeexercise.ui.theme.ComposeExerciseTheme

@Composable
fun MainMenu() {
    val navControllerParent = rememberNavController()
    NavHost(navController = navControllerParent, startDestination = "main") {
        composable("main") {
            Menus { menu ->
                when (menu) {
                    1 -> navControllerParent.navigate("exercise1")
                    2 -> navControllerParent.navigate("exercise2")
                    3 -> navControllerParent.navigate("exercise3")
                    4 -> navControllerParent.navigate("exercise4")
                    5 -> navControllerParent.navigate("exercise5")
                    else -> navControllerParent.navigate("exercise1")
                }
            }
        }
        composable("exercise1") {
            MainScreen1()
        }
        composable("exercise2") {
            MainScreen2(navControllerParent)
        }
        composable("exercise3") {
            MainScreen3()
        }
        composable("exercise4") {
            MainScreen4()
        }
        composable("exercise5") {
            MainScreen5(navControllerParent = navControllerParent)
        }
    }
}

@Composable
private fun Menus(callback: (Int) -> Unit) {

    Column(verticalArrangement = Arrangement.Center) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var (btnExercise1, btnExercise2, btnExercise3, btnExercise4,
                btnExercise5) = createRefs()

            Button(
                modifier = Modifier
                    .constrainAs(btnExercise1) {
                        top.linkTo(parent.top)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = 20.dp,
                            endMargin = 20.dp
                        )
                        width = Dimension.fillToConstraints
                    }
                    .height(50.dp),
                onClick = { callback.invoke(1) }) {
                Text(text = "Exercise 1")
            }

            Button(
                modifier = Modifier
                    .constrainAs(btnExercise2) {
                        top.linkTo(btnExercise1.bottom, margin = 20.dp)
                        bottom.linkTo(btnExercise3.top)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = 20.dp,
                            endMargin = 20.dp
                        )
                        width = Dimension.fillToConstraints
                    }
                    .height(50.dp),
                onClick = { callback.invoke(2) }) {
                Text(text = "Exercise 2")
            }

            Button(
                modifier = Modifier
                    .constrainAs(btnExercise3) {
                        top.linkTo(btnExercise2.bottom, margin = 20.dp)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = 20.dp,
                            endMargin = 20.dp
                        )
                        width = Dimension.fillToConstraints
                    }
                    .height(50.dp),
                onClick = { callback.invoke(3) }) {
                Text(text = "Exercise 3")
            }

            Button(
                modifier = Modifier
                    .constrainAs(btnExercise4) {
                        top.linkTo(btnExercise3.bottom, margin = 20.dp)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = 20.dp,
                            endMargin = 20.dp
                        )
                        width = Dimension.fillToConstraints
                    }
                    .height(50.dp),
                onClick = { callback.invoke(4) }) {
                Text(text = "Exercise 4")
            }

            Button(
                modifier = Modifier
                    .constrainAs(btnExercise5) {
                        top.linkTo(btnExercise4.bottom, margin = 20.dp)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = 20.dp,
                            endMargin = 20.dp
                        )
                        width = Dimension.fillToConstraints
                    }
                    .height(50.dp),
                onClick = { callback.invoke(5) }) {
                Text(text = "Exercise 5")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {

    ComposeExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            MainMenu()
        }
    }
}