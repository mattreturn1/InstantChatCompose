package com.example.chatto.ui.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatto.R

@Composable
fun MainCover(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box {
        Box(modifier = modifier) {
            Image(
                painter = painterResource(id = R.drawable.cover),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = "cover"

            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.82f to Color.Transparent,
                            1f to Color.White
                        )
                    )
            )
        }
        Box(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}