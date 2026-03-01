package com.example.uikit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class SearchInputState{
    Close,
    Open
}
@Composable
fun SearchInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    errorText: String? = null,
    onFocusLost: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    forcedState: SearchInputState? = null
    )
{
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isError = !errorText.isNullOrBlank()
    var wasFocused by remember { mutableStateOf(false) }
    val autoState: SearchInputState = when{
        isFocused -> SearchInputState.Close
        else -> SearchInputState.Open
    }
    val state = forcedState ?: autoState
    val containerTarget = when (state){
        SearchInputState.Open -> MyTheme.mycolors.input_bg
        SearchInputState.Close -> MyTheme.mycolors.input_bg
    }
    val borderTarget = when (state){
        SearchInputState.Open -> MyTheme.mycolors.input_stroke
        SearchInputState.Close -> MyTheme.mycolors.input_stroke
    }
    val placeholderTarget = when(state){
        SearchInputState.Open -> Color(0xFF939396)
        SearchInputState.Close -> Color(0xFF939396)
    }
    val containerColor by animateColorAsState(containerTarget, tween(200), label="")
    val borderColor by animateColorAsState(borderTarget, tween(200), label="")
    val placeholderColor by animateColorAsState(placeholderTarget, tween(200), label="")

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.onFocusChanged{ fs ->
            if(wasFocused && !fs.isFocused)onFocusLost?.invoke()
            wasFocused = fs.isFocused
        },
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
        label = label?.let {{Text(it)} },
        placeholder = placeholder?.let{{Text(it)}} ,
        supportingText = errorText?.let{{Text(it)}},
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            focusedPlaceholderColor = placeholderColor,
            unfocusedPlaceholderColor = placeholderColor
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchInputPreview(){
    var demoState by remember {mutableStateOf(SearchInputState.Open)}
    var text by remember {mutableStateOf("")}
    var enabled by remember {mutableStateOf(true)}
    var placeholder by remember {mutableStateOf("Искать описание")}
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isCrossVisible by remember { mutableStateOf(true) }
    var visual by remember { mutableStateOf<VisualTransformation>(VisualTransformation.None) }
    val leading = @Composable {
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
    }
    val trailing = if (demoState == SearchInputState.Open) {
        @Composable {
            IconButton(onClick = {
                isCrossVisible = !isCrossVisible
            })
            {
                Icon(
                    painter = painterResource(R.drawable.cross),
                    contentDescription = null,
                    tint = if (isCrossVisible) Color.Unspecified else Color.Transparent,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    } else null

    LaunchedEffect(demoState)
    {
        enabled = true
        placeholder = "Искать описание"

        when (demoState) {
            SearchInputState.Open -> {
                text = "Искать описание"
                focusManager.clearFocus()
            }

            SearchInputState.Close -> {
                text = "Искать описание"
                focusManager.clearFocus()
            }
        }
    }
    MyUiKitTheme{
        SearchInput(
            value = "",
            onValueChange = {text = it},
            placeholder = placeholder,
            enabled = enabled,
            leadingIcon = leading,
            trailingIcon = trailing,
            forcedState = demoState,
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)

        )
    }

}