package com.mustafaderinoz.userapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Senin belirlediğin özel renkleri Light Tema şemasına bağlıyoruz
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    primaryContainer = PrimaryContainerColor,
    secondaryContainer = SecondaryContainerColor,
    surface = SurfaceColor,
    onSurface = OnSurfaceColor,
    outlineVariant = OutlineVariantColor,
    surfaceVariant = SurfaceContainerLowest, // Kart arka planları için
    background = SurfaceColor,
    errorContainer = ErrorContainerLight,
    error = ErrorIconColor
)

// Karanlık mod (Dark Mode) için uyumlu, göz yormayan karanlık tonlar belirliyoruz
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF66A0FF),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    secondaryContainer = Color(0xFF2C3E50), // Karanlıkta soft gri/mavi
    surface = Color(0xFF1A1C1E),
    onSurface = Color(0xFFE2E2E6),
    outlineVariant = Color(0xFF8C9196),
    surfaceVariant = Color(0xFF2D3135), // Karanlıkta kart arka planı
    background = Color(0xFF1A1C1E),
    errorContainer = Color(0xFF93000A),
    error = Color(0xFFFFB4AB)
)

@Composable
fun UserAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Tasarımın ezilmemesi için varsayılan dynamicColor'ı false yaptık
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Status bar rengini direkt arkaplan (surface) rengiyle aynı yapıyoruz
            window.statusBarColor = colorScheme.surface.toArgb()
            // İkonların karanlık/aydınlık durumu
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(), // Typography dosyan varsa aynen kalır
        content = content
    )
}