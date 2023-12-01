package com.example.composeexercise.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.example.composeexercise.R
import com.example.composeexercise.ui.theme.ComposeExerciseTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen3() {
    //ScrollingList()
    ScrollingListOld()
}

@Composable
fun ScrollingListOld() {
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()
    val datas = mutableListOf<String>()
    repeat(20) {
        datas.add("Item #$it")
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (buttonScrollTop, buttonScrollBottom, lazyColumn) = createRefs()
            createHorizontalChain(
                buttonScrollTop, buttonScrollBottom, chainStyle = ChainStyle.Spread
            )
            val topBarrier = createBottomBarrier(buttonScrollTop, buttonScrollBottom)

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
                    scrollState.animateScrollToItem(datas.size - 1)
                }
            }) {
                Text(text = "Scroll to the end")
            }

            LazyColumn(
                modifier = Modifier.constrainAs(lazyColumn) {
                        top.linkTo(topBarrier)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.preferredWrapContent
                    },
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(4.dp) // Gap between items
            ) {
                items(datas.size) {
                    ImageListItemOld(datas[it])
                }
            }
        }
    }
}

@Composable
fun ImageListItemOld(data: String) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.teal_200))
    ) {
        val (imageView, textContent, divider) = createRefs()
        val bottomBarrier = createBottomBarrier(imageView, textContent)

        Image(modifier = Modifier
            .constrainAs(imageView) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            .size(60.dp), painter = rememberAsyncImagePainter(
            model = "https://developer.android.com/images/brand/Android_Robot.png"
        ), contentDescription = "")

        Text(modifier = Modifier
            .constrainAs(textContent) {
                linkTo(
                    top = imageView.top, bottom = imageView.bottom
                )
                linkTo(
                    start = imageView.end, end = parent.end, bias = 0.0f
                )
            }
            .padding(end = 10.dp), text = data)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(divider) {
                    top.linkTo(bottomBarrier)
                }, color = Color.Black, thickness = 1.dp
        )
    }
}

@Composable
fun ScrollingList() {
    val listSize = 100
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    // 0 is the first item index
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        LazyColumn(
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(4.dp) // Gap between items
        ) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://developer.android.com/images/brand/Android_Robot.png"
            ), contentDescription = "Android Logo", modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            "Item # $index", style = MaterialTheme.typography.headlineSmall, color = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {

    ComposeExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            ScrollingListOld()
        }
    }
}