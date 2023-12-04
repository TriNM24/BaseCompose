package com.example.composeexercise.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.example.composeexercise.R
import com.example.composeexercise.ui.objets.Exercise5Data
import com.example.composeexercise.ui.theme.ButtonCommon
import com.example.composeexercise.ui.theme.ComposeExerciseTheme
import com.example.composeexercise.ui.viewmodels.Exercise5ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen5(
    viewModel: Exercise5ViewModel = hiltViewModel(),
    navControllerParent: NavController?
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        customHandleBack(navControllerParent, navController)
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
                        text = viewModel.mTitleActionBar.value
                    )
                }
            )
        }
    ) { padding ->
        val focusManager = LocalFocusManager.current
        ConstraintLayout(
            modifier = Modifier
                .padding(padding)
                .fillMaxHeight()
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            ScreenExercise5(viewModel, navController)
        }
    }
    BackHandler {
        customHandleBack(navControllerParent, navController)
    }

    //Similar onDestroy in Fragment
    DisposableEffect(Unit) {
        onDispose {
            Log.d("testt", "Exercise5 is disposed")
        }
    }
}

@Composable
private fun ScreenExercise5(viewModel: Exercise5ViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "gallery",
        enterTransition = {
            Log.d("testt","enterTransition")
            fadeIn(animationSpec = tween(1000)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(1000)
            )
        },
        exitTransition = {
            Log.d("testt","exitTransition")
            fadeOut(animationSpec = tween(1000)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(1000)
            )
        },
        popEnterTransition = {
            Log.d("testt","popEnterTransition")
            fadeIn(animationSpec = tween(1000)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                tween(1000)
            )
        },
        popExitTransition = {
            Log.d("testt","popExitTransition")
            fadeOut(animationSpec = tween(1000)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                tween(1000)
            )
        }
    ) {
        composable("gallery") {
            gallery(viewModel) {
                navController.navigate("detail?id=$it")
            }
        }
        composable("detail?id={id}", arguments = listOf(navArgument("id") {
            defaultValue = 0
        }
        )) { backStackEntry ->
            val dataDetail = viewModel.mData[backStackEntry.arguments?.getInt("id") ?: 0]
            viewModel.updateTitleActionBar(dataDetail.name)
            galleryDetail(dataDetail, viewModel) {
                navController.navigateUp()
            }
        }
    }
}

@Composable
private fun galleryDetail(
    data: Exercise5Data,
    viewModel: Exercise5ViewModel,
    backOnClick: () -> Unit
) {

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        var (image, title, content, backButton) = createRefs()
        Image(
            modifier = Modifier
                .background(Color.Green)
                .height(200.dp)
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        endMargin = 10.dp,
                        startMargin = 10.dp
                    )
                    top.linkTo(parent.top, margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
            painter = rememberAsyncImagePainter(
                model = data.imageLink
            ), contentDescription = "Android Logo"
        )
        Text(
            modifier = Modifier
                .constrainAs(title) {
                    linkTo(start = image.start, end = image.end, startMargin = 20.dp)
                    top.linkTo(image.bottom, 10.dp)
                    width = Dimension.fillToConstraints

                }
                .fillMaxWidth(), text = data.name, color = Color.Black, fontSize = 22.sp)
        Text(
            modifier = Modifier
                .constrainAs(content) {
                    linkTo(start = image.start, end = image.end, startMargin = 20.dp)
                    top.linkTo(title.bottom, 10.dp)
                    width = Dimension.fillToConstraints

                }
                .fillMaxWidth(), text = data.content, color = Color.Black, fontSize = 22.sp)


        ButtonCommon(modifier = Modifier.constrainAs(backButton) {
            top.linkTo(content.bottom, 20.dp)
            end.linkTo(content.end)
        }, onClick = backOnClick) {
            Text(text = "Back to Destinations")
        }

        /*OutlinedButton(
            modifier = Modifier.constrainAs(backButton) {
                top.linkTo(content.bottom, 20.dp)
                end.linkTo(content.end)
            },
            border = BorderStroke(1.dp, colorResource(id = R.color.teal_700)),
            shape = RoundedCornerShape(5.dp),
            onClick = { backOnClick.invoke() }) {
            Text(text = "Back to Destinations")
        }*/
    }

    val context = LocalContext.current
    //Similar onDestroy in Fragment
    DisposableEffect(Unit) {
        onDispose {
            Log.d("testt", "Detail is disposed")
            viewModel.updateTitleActionBar(context.getString(R.string.exercise5_default_title))
        }
    }
}

@Composable
private fun gallery(viewModel: Exercise5ViewModel, itemClick: (Int) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        items(viewModel.mData) {
            Column(
                modifier = Modifier
                    .background(colorResource(id = R.color.teal_700))
                    .fillMaxWidth()
                    .clickable {
                        itemClick.invoke(it.id)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .background(Color.Green)
                        .fillMaxWidth()
                        .heightIn(100.dp),
                    painter = rememberAsyncImagePainter(
                        model = it.imageLink
                    ), contentDescription = "Android Logo"
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 10.dp),
                    text = it.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
        }
    }
}

private fun customHandleBack(
    navControllerParent: NavController?,
    navController: NavHostController
) {
    //Check if there are more screens in the child NavHost
    if (navController.currentBackStackEntry?.destination?.route != "gallery") {
        navController.navigateUp()
    } else {
        //Navigate back to parent screen
        navControllerParent?.navigateUp()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {

    ComposeExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            //MainScreen5(navControllerParent = null)
            galleryDetail(
                Exercise5Data(
                    1,
                    "https://developer.android.com/images/brand/Android_Robot.png",
                    "name title"
                ), hiltViewModel()
            ) {

            }
        }
    }
}