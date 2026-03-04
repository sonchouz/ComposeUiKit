package com.example.uikit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

sealed class ButtonState {
    object Idle : ButtonState()
    object Disabled : ButtonState()
    object Selected : ButtonState()
    object Unselected : ButtonState()
}

fun nextState(current: ButtonState): ButtonState {
    return when (current) {
        ButtonState.Idle -> ButtonState.Selected
        ButtonState.Selected -> ButtonState.Unselected
        ButtonState.Unselected -> ButtonState.Disabled
        ButtonState.Disabled -> ButtonState.Idle
    }
}

@Composable
fun StateButton() {
    var state by remember { mutableStateOf<ButtonState>(ButtonState.Idle) }

    val containerColor = when (state) {
        ButtonState.Idle -> MyTheme.mycolors.accent
        ButtonState.Disabled -> MyTheme.mycolors.accent_inactive
        ButtonState.Selected -> Color(0xFFFFFFFF)
        ButtonState.Unselected -> MyTheme.mycolors.input_bg
    }

    val borderColor = when (state) {
        ButtonState.Idle -> MyTheme.mycolors.accent
        ButtonState.Disabled -> MyTheme.mycolors.accent_inactive
        ButtonState.Selected -> MyTheme.mycolors.accent
        ButtonState.Unselected -> MyTheme.mycolors.input_bg
    }

    Button(
        onClick = {
            if (state != ButtonState.Disabled) {
                state = nextState(state)
            }
        },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.width(335.dp).height(48.dp),
        enabled = state != ButtonState.Disabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,

        ),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Text("Подтвердить")
    }
}
@Preview
@Composable
fun ButtonPreview(showBackground: Boolean = true){
    StateButton()
}