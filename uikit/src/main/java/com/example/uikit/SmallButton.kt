package com.example.uikit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

sealed class SmallButtonState{
    object Idle: SmallButtonState()
    object Disabled: SmallButtonState()
    object Selected: SmallButtonState()
    object Unselected: SmallButtonState()
}

fun nextBtnState(current: SmallButtonState): SmallButtonState{
    return when(current){
        SmallButtonState.Idle -> SmallButtonState.Selected
        SmallButtonState.Selected -> SmallButtonState.Unselected
        SmallButtonState.Unselected -> SmallButtonState.Disabled
        SmallButtonState.Disabled -> SmallButtonState.Idle
    }
}
@Composable
fun stateSmallButton(){
    var state by remember{mutableStateOf<SmallButtonState>(SmallButtonState.Idle)}
    val containerColor = when (state) {
        SmallButtonState.Idle -> MyTheme.mycolors.accent
        SmallButtonState.Disabled -> MyTheme.mycolors.accent_inactive
        SmallButtonState.Selected -> Color(0xFFFFFFFF)
        SmallButtonState.Unselected -> MyTheme.mycolors.input_bg
    }

    val borderColor = when (state) {
        SmallButtonState.Idle -> MyTheme.mycolors.accent
        SmallButtonState.Disabled -> MyTheme.mycolors.accent_inactive
        SmallButtonState.Selected -> MyTheme.mycolors.accent
        SmallButtonState.Unselected -> MyTheme.mycolors.input_bg
    }
    val buttonText = when (state) {
        SmallButtonState.Idle -> "Добавить"
        SmallButtonState.Selected -> "Убрать"
        SmallButtonState.Unselected -> "Подтвердить"
        SmallButtonState.Disabled -> "Добавить"
    }

    Button(
        onClick = {
            if(state != SmallButtonState.Disabled){
                state = nextBtnState(state)
            }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(96.dp)
            .height(40.dp),
        enabled = state != SmallButtonState.Disabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        border = BorderStroke(1.dp, borderColor)
    )
    {
        Text(buttonText)
    }

}

@Preview
@Composable
fun smallButtonPreview() {
    stateSmallButton()
}