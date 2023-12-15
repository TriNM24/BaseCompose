package com.example.composeexercise.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.composeexercise.R
import com.example.composeexercise.ui.data.Word
import com.example.composeexercise.ui.theme.ComposeExerciseTheme
import com.example.composeexercise.ui.viewmodels.Exercise8ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen8(
    navControllerParent: NavHostController?,
    viewModel: Exercise8ViewModel = hiltViewModel()
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
                        text = "Exercise 8"
                    )
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            mainContentExcersice8(viewModel)
        }
    }
    BackHandler {
        customHandleBack(navControllerParent)
    }

    //Similar onDestroy in Fragment
    DisposableEffect(Unit) {
        onDispose {
            Log.d("testt", "Exercise8 is disposed")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun mainContentExcersice8(viewModel: Exercise8ViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val words: List<Word> by viewModel.allWords.observeAsState(listOf())

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            TextField(
                value = viewModel.newWord.value,
                onValueChange = { viewModel.newWord.value = it },
                label = { Text("New Word") },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
            // Add Button
            Button(
                onClick = {
                    if (viewModel.newWord.value.trim().isNotEmpty()) {
                        viewModel.addWord(Word(viewModel.newWord.value.trim()))
                        viewModel.newWord.value = ""
                        keyboardController?.hide()
                        Toast.makeText(context, "Word added", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .height(56.dp)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text("Add", modifier = Modifier.padding(start = 8.dp))
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(words) { index, word ->
                val openRowIndex = viewModel.openRowIndex

                WordItemLayout(
                    word = word,
                    index = index,
                    openRowIndex = openRowIndex.intValue,
                    onUpdateOpenedRow = { viewModel.updateOpenRowIndex(it) },
                    onTrashClicked = { viewModel.deleteWord(it) },
                    onSaveUpdatedWord = { viewModel.deleteWord(it) }
                )
            }
        }

        // Clear Button
        Button(
            onClick = { viewModel.clearWords() },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            Text("Clear Words", modifier = Modifier.padding(start = 16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordItemLayout(
    word: Word,
    index: Int,
    openRowIndex: Int,
    onUpdateOpenedRow: (Int) -> Unit,
    onTrashClicked: (Word) -> Unit,
    onSaveUpdatedWord: (Word) -> Unit
) {
    var editFormOpened by remember { mutableStateOf(false) }
    var editedWord by remember { mutableStateOf(word.word) }
    val context = LocalContext.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.teal_700))
                .padding(vertical = 12.dp, horizontal = 24.dp)
                .clickable {
                    if (!editFormOpened) {
                        onUpdateOpenedRow(index)
                        editedWord = word.word
                        editFormOpened = true
                    } else {
                        onUpdateOpenedRow(-1)
                        editFormOpened = false
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Update Word")
            Text(
                text = word.word,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )
            // Delete Button
            IconButton(
                onClick = {
                    editFormOpened = false
                    onTrashClicked(word)
                    Toast.makeText(context, "Word deleted", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Word",
                    tint = Color.White
                )
            }
        }

        // word edit form
        if (index == openRowIndex) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {

                TextField(
                    modifier = Modifier.weight(1f),
                    value = editedWord, onValueChange = {
                        editedWord = it
                    },
                    textStyle = TextStyle(color = Color.Green)
                )

                // Update Button
                Button(
                    onClick = {
                        val updatedWord: Word = word

                        if (updatedWord.word != editedWord.trim()) {
                            updatedWord.word = editedWord.trim()
                            onSaveUpdatedWord(updatedWord)
                            Toast.makeText(context, "Word updated", Toast.LENGTH_SHORT).show()
                        }

                        //showEditForm = false
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Update Word")
                }
            }
        }
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
fun GreetingPreview8() {

    ComposeExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            mainContentExcersice8(hiltViewModel())
        }
    }
}