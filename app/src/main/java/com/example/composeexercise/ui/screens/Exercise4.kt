package com.example.composeexercise.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeexercise.R
import com.example.composeexercise.ui.theme.ComposeExerciseTheme
import com.example.composeexercise.ui.viewmodels.Exercise4ViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen4(viewModelExercise4: Exercise4ViewModel = hiltViewModel()) {
    val scrollState = rememberLazyListState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Lazy Column Sync"
                    )
                },
                actions = {
                    IconButton(onClick = {
                        viewModelExercise4.changeSortType()
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp, 30.dp),
                            imageVector = if (viewModelExercise4.mIsAscending.value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->

        var input by rememberSaveable { mutableStateOf("") }
        var isEnableButton by rememberSaveable {
            mutableStateOf(false)
        }
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

            var (text, button, error, list, buttonBot) = createRefs()

            val bottomBarrier = createBottomBarrier(error, text)

            TextField(
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(button.start, margin = 10.dp)
                    width = Dimension.preferredWrapContent
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                },
                trailingIcon = {
                    if (viewModelExercise4.mErrorMsg.value.isNotEmpty()) {
                        Icon(
                            tint = Color.Red,
                            imageVector = Icons.Filled.Info,
                            contentDescription = null
                        )
                    }
                },
                value = input, onValueChange = {
                    input = it
                    isEnableButton = it.isNotEmpty()

                },
                label = {
                    Text(text = "New Word", color = Color.Black)
                },
                supportingText = {
                    if (viewModelExercise4.mErrorMsg.value.isNotEmpty()) {
                        Text(
                            text = viewModelExercise4.mErrorMsg.value,
                            fontSize = 14.sp,
                            color = Color.Red
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                isError = false
            )

            Button(
                enabled = isEnableButton,
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(text.top)
                    bottom.linkTo(text.bottom)
                    end.linkTo(parent.end, margin = 20.dp)
                    start.linkTo(text.end)
                    width = Dimension.preferredWrapContent

                },
                onClick = {
                    focusManager.clearFocus()
                    viewModelExercise4.addData(input)
                    input = ""
                    isEnableButton = false
                }) {
                Row {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .align(Alignment.CenterVertically),
                        text = "Add"
                    )
                }
            }

            ListWord(viewModelExercise4, modifier = Modifier.constrainAs(list) {
                top.linkTo(bottomBarrier, margin = 10.dp)
                height = Dimension.fillToConstraints
                bottom.linkTo(buttonBot.top)
            }, scrollState)

            buttonScroll(viewModelExercise4, modifier = Modifier.constrainAs(buttonBot) {
                bottom.linkTo(parent.bottom, margin = 20.dp)
                linkTo(start = parent.start, end = parent.end)
            }, scrollState = scrollState)


        }
    }
}

@Composable
private fun buttonScroll(viewModelExercise4: Exercise4ViewModel,modifier: Modifier, scrollState: LazyListState) {
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (buttonScrollTop, buttonScrollBottom) = createRefs()
        Button(modifier = Modifier.constrainAs(buttonScrollTop) {
            top.linkTo(parent.top, margin = 10.dp)
            linkTo(start = parent.start, end = buttonScrollBottom.start)
        }, onClick = {
            coroutineScope.launch {
                // 0 is the first item index
                scrollState.animateScrollToItem(0)
            }
        }) {
            Text(text = "Scroll to the top")
        }
        Button(modifier = Modifier.constrainAs(buttonScrollBottom) {
            top.linkTo(buttonScrollTop.top)
            linkTo(start = buttonScrollTop.end, end = parent.end)
        }, onClick = {
            coroutineScope.launch {
                // 0 is the first item index
                scrollState.animateScrollToItem(viewModelExercise4.mData.size - 1)
            }
        }) {
            Text(text = "Scroll to the end")
        }
    }
}

@Composable
private fun ListWord(viewModelExercise4: Exercise4ViewModel ,modifier: Modifier, scrollState: LazyListState) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp), // Gap between items
            state = scrollState,
            content = {
                items(viewModelExercise4.mData.size) {
                    ItemView(viewModelExercise4,viewModelExercise4.mData[it])
                }
            })
    }
}

@Composable
private fun ItemView(viewModelExercise4: Exercise4ViewModel,data: String) {
    ConstraintLayout(
        modifier = Modifier
            .background(colorResource(id = R.color.teal_700))
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        var (icon, content, deleteIcon) = createRefs()

        Icon(
            tint = Color.White,
            modifier = Modifier.constrainAs(icon) {
                linkTo(top = parent.top, bottom = parent.bottom)
                start.linkTo(parent.start)
            }, imageVector = Icons.Filled.Star, contentDescription = ""
        )
        Text(
            modifier = Modifier.constrainAs(content) {
                linkTo(top = parent.top, bottom = parent.bottom)
                start.linkTo(icon.end, margin = 20.dp)
                end.linkTo(deleteIcon.start)
                width = Dimension.fillToConstraints
            },
            fontSize = 14.sp,
            color = Color.White, text = data
        )

        IconButton(modifier = Modifier.constrainAs(deleteIcon) {
            linkTo(top = parent.top, bottom = parent.bottom)
            end.linkTo(parent.end)
        }, onClick = {
            viewModelExercise4.removeData(data)
        }) {
            Icon(
                tint = Color.White, imageVector = Icons.Filled.Delete, contentDescription = ""
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {

    ComposeExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            MainScreen4()
        }
    }
}