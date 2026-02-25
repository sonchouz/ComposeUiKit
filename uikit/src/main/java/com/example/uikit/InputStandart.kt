package com.example.uikit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class FieldUiState {
    Empty,
    Filled,
    FocusedEmpty,
    FocusedFilled,
    Error,
    Disabled,
    Password,
    Date
}

@Composable
fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    errorText: String? = null,
    onFocusLost: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: (@Composable (() -> Unit))? = null,
    forcedState: FieldUiState? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isFilled = value.isNotBlank()
    val isError = !errorText.isNullOrBlank()

    var wasFocused by remember { mutableStateOf(false) }

    // Auto UI state (normal app behavior)
    val autoState: FieldUiState = when {
        !enabled -> FieldUiState.Disabled
        isError -> FieldUiState.Error
        isFocused && !isFilled -> FieldUiState.FocusedEmpty
        isFocused && isFilled -> FieldUiState.FocusedFilled
        !isFocused && isFilled -> FieldUiState.Filled
        else -> FieldUiState.Empty
    }

    // Forced UI state (demo/preview behavior)
    val state = forcedState ?: autoState

    // ---- Colors per state (adapt to your design system) ----
    val containerTarget = when (state) {
        FieldUiState.Error -> MyTheme.mycolors.error.copy(alpha = 0.10f)
        FieldUiState.Disabled -> MyTheme.mycolors.input_bg.copy(alpha = 0.50f)
        else -> MyTheme.mycolors.input_bg
    }

    val borderTarget = when (state) {
        FieldUiState.Error -> MyTheme.mycolors.error
        FieldUiState.FocusedEmpty, FieldUiState.FocusedFilled -> MyTheme.mycolors.accent
        FieldUiState.Filled -> MyTheme.mycolors.input_icon
        FieldUiState.Disabled -> MyTheme.mycolors.input_stroke.copy(alpha = 0.50f)
        FieldUiState.Empty, FieldUiState.Password, FieldUiState.Date -> MyTheme.mycolors.input_stroke
    }

    val placeholderTarget = when (state) {
        FieldUiState.FocusedEmpty, FieldUiState.Empty -> Color(0xFF939396)
        FieldUiState.FocusedFilled, FieldUiState.Filled -> Color(0xFF000000)
        FieldUiState.Error -> Color(0xFF939396)
        FieldUiState.Disabled -> Color(0xFF939396).copy(alpha = 0.6f)
        FieldUiState.Password, FieldUiState.Date -> Color(0xFF939396)
    }

    val labelTarget = when (state) {
        FieldUiState.Error -> MyTheme.mycolors.error
        FieldUiState.FocusedEmpty, FieldUiState.FocusedFilled -> MyTheme.mycolors.accent
        else -> Color(0xFF939396)
    }

    // ---- Animations ----
    val containerColor by animateColorAsState(containerTarget, tween(200), label = "")
    val borderColor by animateColorAsState(borderTarget, tween(200), label = "")
    val placeholderColor by animateColorAsState(placeholderTarget, tween(200), label = "")
    val labelColor by animateColorAsState(labelTarget, tween(200), label = "")

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.onFocusChanged { fs ->
            if (wasFocused && !fs.isFocused) onFocusLost?.invoke()
            wasFocused = fs.isFocused
        },
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,

        label = label?.let { { Text(it, color = labelColor) } },
        placeholder = placeholder?.let { { Text(it, color = placeholderColor) } },

        supportingText = errorText?.let {
            @Composable { Text(it, color = MyTheme.mycolors.error) }
        },

        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,

            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            disabledBorderColor = borderColor,
            errorBorderColor = MyTheme.mycolors.error,

            focusedLabelColor = labelColor,
            unfocusedLabelColor = labelColor,
            disabledLabelColor = labelColor.copy(alpha = 0.6f),

            focusedPlaceholderColor = placeholderColor,
            unfocusedPlaceholderColor = placeholderColor,
            disabledPlaceholderColor = placeholderColor.copy(alpha = 0.6f),
        )
    )
}

// ---------- ONE input + ONE button switches through 8 states ----------
@Preview(showBackground = true, heightDp = 700)
@Composable
fun TextInputPreview() {
    var demoState by remember { mutableStateOf(FieldUiState.Empty) }

    var text by remember { mutableStateOf("") }
    var enabled by remember { mutableStateOf(true) }
    var errorText by remember { mutableStateOf<String?>(null) }
    var label by remember { mutableStateOf("Имя") }
    var placeholder by remember { mutableStateOf("Введите имя") }

    var passwordVisible by remember { mutableStateOf(false) }
    var visual by remember { mutableStateOf<VisualTransformation>(VisualTransformation.None) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Configure field props for each demo state
    LaunchedEffect(demoState) {
        // reset defaults
        enabled = true
        errorText = null
        label = ""
        placeholder = "Введите имя"
        passwordVisible = false
        visual = VisualTransformation.None

        when (demoState) {
            FieldUiState.Empty -> {
                text = ""
                focusManager.clearFocus()
            }

            FieldUiState.Filled -> {
                text = "Иван"
                focusManager.clearFocus()
            }

            FieldUiState.FocusedEmpty -> {
                text = ""
                focusRequester.requestFocus()
            }

            FieldUiState.FocusedFilled -> {
                text = "Иван"
                focusRequester.requestFocus()
            }

            FieldUiState.Error -> {
                text = "Ввдите имя"
                errorText = "Введите ваше имя"
                focusManager.clearFocus()
            }

            FieldUiState.Disabled -> {
                text = ""
                enabled = false
                focusManager.clearFocus()
            }

            FieldUiState.Password -> {
                label = "Пароль"
                placeholder = "Введите пароль"
                text = "*********"
                visual = PasswordVisualTransformation()
                focusManager.clearFocus()
            }

            FieldUiState.Date -> {
                label = "Дата"
                placeholder = "--.--.----"
                text = ""
                focusManager.clearFocus()
            }
        }
    }

    // Password eye icon only in Password state
    val trailing = if (demoState == FieldUiState.Password) {
        @androidx.compose.runtime.Composable {
            IconButton(onClick = {
                passwordVisible = !passwordVisible
                visual = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            }) {
                Icon(
                    painter = painterResource(R.drawable.group_1),
                    contentDescription = null
                )
            }
        }
    } else null

    MyUiKitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clearFocusOnTap()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("State: $demoState")

            TextInput(
                value = text,
                onValueChange = { text = it },
                label = label,
                placeholder = placeholder,
                enabled = enabled,
                errorText = errorText,
                visualTransformation = visual,
                trailingIcon = trailing,
                forcedState = demoState,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                // валидатор “ушла и пусто” (для реального поведения)
                onFocusLost = {
                    if (demoState !in listOf(FieldUiState.Password, FieldUiState.Date, FieldUiState.Disabled)) {
                        if (text.isBlank()) errorText = "Введите ваше имя"
                    }
                }
            )

            Button(
                onClick = {
                    val all = FieldUiState.entries
                    demoState = all[(all.indexOf(demoState) + 1) % all.size]
                }
            ) {
                Text("Next state")
            }
        }
    }
}
