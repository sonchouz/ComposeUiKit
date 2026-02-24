package com.example.uikit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
enum class MenuItemState{
    Normal, Sex, User
}
@Composable
fun UiKitDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Пол",
    enabled: Boolean = true,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val items = listOf("Мужской", "Женский", "Другое")

    Box(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },

            // ✅ Открываем ТОЛЬКО по нажатию на стрелку
            trailingIcon = {
                Image(
                    painter = painterResource(com.example.uikit.R.drawable.icons),
                    contentDescription = "Открыть список",
                    modifier = Modifier.clickable(enabled = enabled) {
                        expanded = !expanded
                    }
                )
            },

            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MyTheme.mycolors.input_bg,
                focusedBorderColor = MyTheme.mycolors.input_stroke,
                unfocusedContainerColor = MyTheme.mycolors.input_bg,
                unfocusedBorderColor = MyTheme.mycolors.input_stroke,
                focusedPlaceholderColor = Color(0xFF939396),
                unfocusedPlaceholderColor = Color(0xFF939396),
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UiKitDropDownPreview() {

    var sex by rememberSaveable { mutableStateOf("") }

    UiKitDropdown(
        value = sex,
        onValueChange = { sex = it },
        modifier = Modifier.fillMaxWidth()
    )
}
