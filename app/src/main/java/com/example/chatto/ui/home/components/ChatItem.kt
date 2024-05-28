package com.example.chatto.ui.home.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatto.domain.vo.DbNumber
import com.example.chatto.ui.utils.formatter

/**
 * a chat item composed by a Card and all information about the chat:
 * the phone number, the avatar, the creation data and trash icon
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatItem(
    chatNumber: DbNumber,
    chatDate: String,
    chatAvatar: Int,
    onOpenChat: () -> Unit,
    onClose: () -> Unit
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        border = BorderStroke(0.dp, Color.Transparent),
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onOpenChat)

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
                                .size(50.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = chatAvatar),
                            contentDescription = "avatar"
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleLarge.copy(
                                lineHeight = 28.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.W500
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = "+" + chatNumber.prefix + " " + chatNumber.number,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            style = MaterialTheme.typography.titleMedium.copy(
                                lineHeight = 24.sp,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            text = "Created: ${formatter(chatDate)}",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        modifier = Modifier.padding(end = 50.dp),
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




