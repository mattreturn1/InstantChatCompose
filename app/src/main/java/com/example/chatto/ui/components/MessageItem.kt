package com.example.chatto.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MessageItem(
    modifier: Modifier,
    messageText: String?,
    messageDate: String?,
    isSelected : Boolean
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            if (isSelected){
                MaterialTheme.colorScheme.tertiaryContainer
            }else{
                MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        border = BorderStroke(0.dp, Color.Transparent),
        modifier = modifier

    ) {
        Box(
            modifier = Modifier.background(
                Brush.verticalGradient(
                    Pair(0f, Color.Transparent),
                    Pair(0.9f, Color.Transparent),
                    Pair(1f, Color.Black)
                )
            )
        ) {
            Column(Modifier.padding(vertical = 16.dp)) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            style = MaterialTheme.typography.titleLarge.copy(
                                lineHeight = 20.sp
                            ),
                            text = "$messageText",
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            style = MaterialTheme.typography.titleMedium.copy(
                                lineHeight = 20.sp
                            ),
                            text = "$messageDate",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

