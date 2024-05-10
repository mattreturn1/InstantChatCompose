package com.example.chatto.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatto.R


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatItem(
    chatNumber: String?,
    chatDate: String?,
    onOpenChat: () -> Unit,
    onClose: () -> Unit
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = BorderStroke(0.dp, Color.Transparent),
        modifier = Modifier.clickable(onClick = onOpenChat)

    ) {
        Box(
            modifier = Modifier.background(
                Brush.verticalGradient(
                    Pair(0f, Color.Transparent),
                    Pair(0.9f, Color.Transparent),
                    Pair(1f, MaterialTheme.colorScheme.primary)
                )
            )
        ) {
            Column(Modifier.padding(vertical = 16.dp)) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        Image(
                            modifier = Modifier
                                .size(35.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.ic_action_name),
                            contentDescription = "avatar"
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleLarge.copy(
                                lineHeight = 28.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            text = "$chatNumber",
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            style = MaterialTheme.typography.titleMedium.copy(
                                lineHeight = 20.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            text = "$chatDate",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        onClick = onClose,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Close")
                    }
                }
            }
        }
    }
}
/*@RequiresApi(Build.VERSION_CODES.O)
fun formatter(date: String?): String? {
    val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss")
    if (date != null && date.substring(0, 10) == LocalDateTime.now().toLocalDate()
            .format(formatterDate)
    ) {
        return LocalDateTime.now().format(formatterTime)
    }
    return LocalDateTime.now().format(formatterDate)
}*/

