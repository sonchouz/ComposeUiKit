package com.example.uikit

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Header(
    title: String = "Корзина",
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}

)
{
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp)
            .background(MyTheme.mycolors.input_bg)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically

    )
    {
        HeaderIconButton(
            icon = R.drawable.back,
            onClick = onBackClick
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        )
        {
            Text(title)
        }
        HeaderIconButton(
            icon = R.drawable.icons,
            onClick = onDeleteClick
        )
    }
}

@Composable
fun HeaderIconButton(
    icon: Int,
    onClick: () -> Unit

    )
{
    Box(
        modifier = Modifier.size(36.dp)
            .background(color = Color.White,
                shape = RoundedCornerShape(10.dp)
            ).clickable{onClick()},
        contentAlignment = Alignment.Center
    )
    {
        Icon(
            painter = painterResource(icon),
            contentDescription = null
        )
    }
}
@Preview
@Composable
fun HeaderPreview(){
    Header()
}