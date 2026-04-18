package com.mustafaderinoz.userapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafaderinoz.userapp.data.model.User
import com.mustafaderinoz.userapp.ui.screen.UserDetailScreen
import com.mustafaderinoz.userapp.ui.screen.UserListScreen
import com.mustafaderinoz.userapp.ui.theme.UserAppTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Sistemin mevcut temasını varsayılan olarak alıyoruz
            val systemTheme = isSystemInDarkTheme()
            // Tema durumunu tutan State
            var isDarkTheme by remember { mutableStateOf(systemTheme) }

            UserAppTheme(darkTheme = isDarkTheme) {
                UserNavHost(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@Composable
fun UserNavHost(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    val gson = remember { Gson() }

    NavHost(navController = navController, startDestination = "user_list") {

        composable("user_list") {
            UserListScreen(
                isDarkTheme = isDarkTheme, // Durumu ekrana gönder
                onThemeToggle = onThemeToggle, // Tıklama olayını ekrana gönder
                onUserClick = { user ->
                    val userJson = gson.toJson(user)
                    val encoded = java.net.URLEncoder.encode(userJson, "UTF-8")
                    navController.navigate("user_detail/$encoded")
                }
            )
        }

        composable("user_detail/{userJson}") { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson") ?: return@composable
            val decoded = java.net.URLDecoder.decode(userJson, "UTF-8")
            val user = gson.fromJson(decoded, User::class.java)
            UserDetailScreen(
                user = user,
                isDarkTheme = isDarkTheme, // Durumu ekrana gönder
                onThemeToggle = onThemeToggle, // Tıklama olayını ekrana gönder
                onBack = { navController.popBackStack() }
            )
        }
    }
}