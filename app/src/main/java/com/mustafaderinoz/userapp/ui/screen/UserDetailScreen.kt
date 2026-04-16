package com.mustafaderinoz.userapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafaderinoz.userapp.data.model.User
// Yeni renklerimizi Theme paketinden içe aktarıyoruz:
import com.mustafaderinoz.userapp.ui.theme.* @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    user: User,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = SurfaceColor, // Ana arka plan
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Kullanıcı Detayı",
                        color = PrimaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = PrimaryColor
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Dark mode toggle action */ }) {
                        Icon(
                            imageVector = Icons.Default.DarkMode,
                            contentDescription = "Tema Değiştir",
                            tint = PrimaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = SurfaceColor
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Büyük Avatar
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        ambientColor = PrimaryColor,
                        spotColor = PrimaryColor
                    )
                    .clip(CircleShape)
                    .background(SecondaryContainerColor)
            ) {
                Text(
                    text = user.name.first().uppercaseChar().toString(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = OnSurfaceColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // İsim ve Kullanıcı Adı
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceColor
                )
            )
            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.bodyLarge,
                color = OutlineVariantColor
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DetailCard(icon = Icons.Default.Email, label = "EMAİL", value = user.email)
                DetailCard(icon = Icons.Default.Phone, label = "TELEFON", value = user.phone)
                DetailCard(icon = Icons.Default.Language, label = "WEBSİTE", value = user.website, isLink = true)
                DetailCard(icon = Icons.Default.AlternateEmail, label = "KULLANICI ADI", value = user.username)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DetailCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    isLink: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceContainerLowest
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(SurfaceContainerLow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = PrimaryColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    ),
                    color = OutlineVariantColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (isLink) FontWeight.Medium else FontWeight.Normal
                    ),
                    color = if (isLink) PrimaryColor else OnSurfaceColor
                )
            }
        }
    }
}