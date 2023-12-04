package com.example.composeexercise.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composeexercise.R

@Composable
fun ButtonCommon(
    style: ButtonStyle = ButtonStyle(
        backgroundColor = Color.Transparent,
        textColor = MaterialTheme.colorScheme.onPrimary,
        cornerRadius = 4.dp,
        padding = 16.dp
    ),
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(1.dp, colorResource(id = R.color.teal_700)),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = style.backgroundColor),
        onClick = onClick) {
        content()
    }
}

data class ButtonStyle(
    val backgroundColor: Color,
    val textColor: Color,
    val cornerRadius: Dp,
    val padding: Dp
)