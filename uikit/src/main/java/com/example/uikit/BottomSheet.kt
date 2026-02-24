package com.example.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uikit.BottomSheet

enum class BottomSheetState {
    Empty,
    Text,
    Error
}

@Composable
fun BottomSheet(
    text: String = "",
    modifier: Modifier = Modifier,
    content: String? = null,
    trailingIcon: (@Composable (() -> Unit))? = null
) {
    val isFilled = text.isNotEmpty()
    val isError = text.isBlank() && !content.isNullOrBlank()

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            // основной текст
            if (isFilled) {
                Text(text)
            } else {
                Text("")

                if (!content.isNullOrBlank()) {
                    Text(
                        text = content,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (isError) {
                    Text(
                        text = "Ошибка",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 164)
@Composable
fun BottomSheetPreview() {
    var demostate by remember { mutableStateOf(BottomSheetState.Empty) }
    var text by remember { mutableStateOf("") }
    LaunchedEffect(demostate) {
        when(demostate){
            BottomSheetState.Empty -> {
                text = ""
            }
            BottomSheetState.Text -> {
                text = "Рубашка Воскресенье для машинного вязания"
            }
            BottomSheetState.Error -> {
                text = "Произошла ошибка\nНу вот опять"
            }
        }
    }
  BottomSheet(
      text = "",
      content = null
  )
}
