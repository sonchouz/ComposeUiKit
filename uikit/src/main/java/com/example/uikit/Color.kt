package com.example.uikit


import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Immutable

@Immutable
data class MyColor(
    val accent: Color,
    val accent_inactive: Color,
    val error: Color,
    val success: Color,
    val input_bg: Color,
    val input_stroke: Color,
    val input_icon: Color,
    val placeholder: Color,
    val description: Color,
    val card_stroke: Color
)
val colors = MyColor(
    accent = Color(0xFF2074F2),
    accent_inactive = Color(0xFFC5D2FF),
    error = Color(0xFFFF4646),
    success =  Color(0xFF00B412),
    input_bg = Color(0xFFF7F7FA),
    input_stroke = Color(0xFFE6E6E6),
    input_icon = Color(0xFFBFC7D1),
    placeholder = Color(0xFF98989A),
    description = Color(0xFF8787A1),
    card_stroke = Color(0xFFF2F2F2)

)