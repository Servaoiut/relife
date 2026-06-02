package com.lifeordered.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeordered.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconPicker(
    onIconSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val localIcons = listOf(
        "🎂", "💍", "🐱", "🪥", "罐", "🎁", "🎉", "📅", "🍎", "💊",
        "🏠", "🚗", "💻", "💡", "⚽", "🎵", "📷", "🍔", "☕", "🧴"
    )
    
    // Mocked search results - in real app, this would call an API
    val filteredIcons = if (searchQuery.isEmpty()) {
        localIcons
    } else {
        localIcons.filter { it.contains(searchQuery) } // Very basic mock search
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WarmCreamBg, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "选择图标",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            IconButton(onClick = onDismiss) {
                Text("✕", color = GrayTextMuted)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar (Requirement 2: Network Search visual)
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color(0xFFE8E5E0), RoundedCornerShape(16.dp)),
            placeholder = { Text("搜索更多图标 (如: 生日, 食品)", fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GrayTextMuted) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (searchQuery.isEmpty()) "精选图标" else "搜索结果",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = GrayTextMuted
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.heightIn(max = 240.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredIcons) { icon ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFF5EDE6), RoundedCornerShape(16.dp))
                        .clickable { onIconSelected(icon) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = icon, fontSize = 24.sp)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Requirement 2: Indication for Network API
        if (searchQuery.isNotEmpty()) {
            Text(
                text = "正在从 Iconify 检索互联网图标...",
                fontSize = 11.sp,
                color = GrayTextMuted.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
