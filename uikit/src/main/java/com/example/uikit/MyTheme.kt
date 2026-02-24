package com.example.uikit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val color = staticCompositionLocalOf{colors}

object MyTheme {
    val mycolors @Composable get() = color.current
}

@Composable
fun MyUiKitTheme(
    mycolors: MyColor = colors,
    content: @Composable () -> Unit
){
    CompositionLocalProvider(color provides mycolors, content= content)
}