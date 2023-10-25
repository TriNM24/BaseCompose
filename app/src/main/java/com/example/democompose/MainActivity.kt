package com.example.democompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.democompose.ui.theme.DemoComposeTheme

class MainActivity : ComponentActivity() {
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            context = LocalContext.current
            DemoComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(verticalArrangement = Arrangement.Top) {
                        GreetingText(message = "Happy Birthday Sam!", from = "From Emma")
                        PersonInfo(name = "Alfred Sisley", timeOnline = "3 minutes ago") {
                            Toast.makeText(context, "click view", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingText(message: String, from: String, modifierInput: Modifier = Modifier) {
    Column(
        modifier = modifierInput.padding(8.dp), verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 100.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = from, fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@Composable
fun PersonInfo(name: String, timeOnline: String, callback: () -> Unit) {
    val padding = 10.dp

    Column(
        modifier = Modifier
            .clickable(onClick = callback)
            .padding(padding)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box {
                Image(
                    painterResource(id = R.drawable.ic_man),
                    modifier = Modifier.size(60.dp, 60.dp),
                    contentDescription = "imageCard"
                )
                Icon(
                    Icons.Filled.CheckCircle, contentDescription = "checked image",
                    modifier = Modifier.align(alignment = Alignment.BottomEnd)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = name)
                Text(text = timeOnline)
            }
        }
        Spacer(modifier = Modifier.size(padding))
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.feature_graphic),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun DemoBoxWithConstraint() {
    BoxWithConstraints {
        Text(text = "My minHeight is $minHeight while my maxWidth is $maxWidth")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val context = LocalContext.current
    DemoComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(verticalArrangement = Arrangement.Top) {
                GreetingText(message = "Happy Birthday Sam!", from = "From Emma")
                PersonInfo(name = "Alfred Sisley", timeOnline = "3 minutes ago") {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                }
                DemoBoxWithConstraint()
            }
        }
    }
}
