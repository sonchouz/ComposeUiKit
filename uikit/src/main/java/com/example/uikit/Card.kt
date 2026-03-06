package com.example.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductCard(
    title: String,
    category: String,
    price: String,
    modifier: Modifier = Modifier
) {
    var buttonState by rememberSaveable { mutableStateOf<SmallButtonState>(SmallButtonState.Idle) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MyTheme.mycolors.input_bg,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Text(text = title, color = Color(0xFF000000), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = category, color = Color(0xFF939396))

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = price, fontWeight = FontWeight.Bold)

            StateSmallButton(
                state = buttonState,
                onClick = {
                    buttonState = when (buttonState) {
                        SmallButtonState.Idle -> SmallButtonState.Selected
                        SmallButtonState.Selected -> SmallButtonState.Idle
                        SmallButtonState.Unselected -> SmallButtonState.Selected
                        SmallButtonState.Disabled -> SmallButtonState.Disabled
                    }
                }
            )
        }
    }
}
@Preview
@Composable
fun CardPreview(){
    ProductCard( title = "Рубашка Воскресенье для машинного\nвязания",
        category = "Мужская одежда",
        price = "300 ₽",
        )
}