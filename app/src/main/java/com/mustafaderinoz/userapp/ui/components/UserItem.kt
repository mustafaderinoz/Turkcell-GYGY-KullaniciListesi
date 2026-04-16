package com.mustafaderinoz.userapp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafaderinoz.userapp.data.model.User
// Tema renklerini dahil ediyoruz
import com.mustafaderinoz.userapp.ui.theme.*

@Composable
fun UserItem(
    user: User,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            // Dış padding'i kaldırdık; UserListScreen'deki spacedBy(24.dp) boşlukları yönetecek.
            .clickable { onClick() }
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        // DESIGN.md: "We reject the drop shadow of 2014." - Derinlik sıfırlandı.
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            // DESIGN.md: İnteraktif kartlar SurfaceContainerLowest (Beyaz) olmalı.
            containerColor = SurfaceContainerLowest
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Büyük, Dairesel Avatar (Soft & Approachable Identity)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(color = SecondaryContainerColor)
            ) {
                Text(
                    text = user.name.first().uppercaseChar().toString(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    ),
                    color = OnSurfaceColor
                )
            }

            // Bilgiler - Editoryal Tipografi Hiyerarşisi
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.02).sp // Otorite hissi için dar harf aralığı
                    ),
                    color = OnSurfaceColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // İkon kalabalığını kaldırarak daha sade, okunabilir bir "Label" stili yarattık
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OutlineVariantColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Sağ ok işareti (Minimalist bir yönlendirme)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Detaya Git",
                tint = OutlineVariantColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}