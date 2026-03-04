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

sealed class ChipButtonState{
    object Enabled: ChipButtonState()
    object Disabled: ChipButtonState()

}
fun chipButtonState(current: ChipButtonState): ChipButtonState{
    return when(current){
        ChipButtonState.Enabled -> ChipButtonState.Disabled
        ChipButtonState.Disabled -> ChipButtonState.Enabled
    }
}
@Composable
fun ChipButton(){
    var state by remember { mutableStateOf<ChipButtonState>(ChipButtonState.Enabled) }
    val containerColor = when(state){
        ChipButtonState.Enabled -> MyTheme.mycolors.accent
        ChipButtonState.Disabled -> MyTheme.mycolors.input_bg
    }
    val borderColor = when(state){
        ChipButtonState.Enabled -> MyTheme.mycolors.accent
        ChipButtonState.Disabled -> MyTheme.mycolors.input_bg
    }
    val textColor = when(state){
        ChipButtonState.Enabled -> Color(0xFFFFFFFF)
        ChipButtonState.Disabled -> MyTheme.mycolors.description
    }
    Button(
        onClick = {
           if(state != ChipButtonState.Disabled){
               state = chipButtonState(state)
           }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.width(129.dp).height(48.dp),
        enabled = state != ChipButtonState.Disabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        border = BorderStroke(1.dp, borderColor)
    )
    {
        Text("Популярные", color = textColor)
    }
}
@Preview
@Composable
fun ChipButtonPreview(){
    ChipButton()
}