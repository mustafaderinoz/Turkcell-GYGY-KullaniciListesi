package com.mustafaderinoz.userapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mustafaderinoz.userapp.data.model.User
import com.mustafaderinoz.userapp.ui.components.UserItem
import com.mustafaderinoz.userapp.ui.theme.*
import com.mustafaderinoz.userapp.viewmodel.UserUiState
import com.mustafaderinoz.userapp.viewmodel.UserViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    onUserClick: (User) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = SurfaceColor, // Base Tonal Architecture
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "Kullanıcılar",
                            tint = PrimaryColor,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Kullanıcılar",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryColor
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Dark mode toggle action */ }) {
                        Icon(
                            imageVector = Icons.Default.DarkMode, // Ay ikonu (Ekran görüntüsü)
                            contentDescription = "Tema Değiştir",
                            tint = PrimaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceColor
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Arama Çubuğu (Editorial Voice & Ghost Border)
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            )

            // İçerik
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = viewModel::refresh,
                modifier = Modifier.fillMaxSize()
            ) {
                when (val state = uiState) {
                    is UserUiState.Loading -> LoadingContent()
                    is UserUiState.Error -> {
                        // Hata ekranı artık kaydırılarak yenilenebilecek
                        ErrorContent(message = state.message)
                    }
                    is UserUiState.Success -> {
                        if (state.users.isEmpty()) {
                            EmptySearchResult(query = searchQuery)
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                items(
                                    items = state.users,
                                    key = { user -> user.id }
                                ) { user ->
                                    UserItem(
                                        user = user,
                                        onClick = { onUserClick(user) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = {
            Text("Ara...", color = OutlineVariantColor)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Ara",
                tint = OutlineVariantColor
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Temizle",
                        tint = OutlineVariantColor
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = SurfaceContainerLowest,
            focusedContainerColor = SurfaceContainerLowest,
            unfocusedBorderColor = OutlineVariantColor.copy(alpha = 0.1f), // 10% Ghost Border
            focusedBorderColor = PrimaryColor,
            cursorColor = PrimaryColor
        )
    )
}

@Composable
private fun ErrorContent(
    message: String
) {
    // Ekran görüntüsündeki hata ekranının birebir kurgusu
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // AŞAĞI KAYDIRMAYI ALGILAMASI İÇİN ŞART
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(ErrorContainerLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Hata",
                tint = ErrorIconColor,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bir hata oluştu",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = OnSurfaceColor
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 24.sp
            ),
            color = OutlineVariantColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Yenilemek için ekranı aşağı kaydırın", // Kullanıcıyı yönlendiren ufak bir ipucu metni
            style = MaterialTheme.typography.bodyMedium,
            color = OutlineVariantColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Aşağı Kaydır",
            tint = OutlineVariantColor,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PrimaryColor)
    }
}

@Composable
private fun EmptySearchResult(query: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "\"$query\" için sonuç bulunamadı",
            style = MaterialTheme.typography.bodyLarge,
            color = OutlineVariantColor
        )
    }
}