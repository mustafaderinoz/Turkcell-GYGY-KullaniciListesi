package com.mustafaderinoz.userapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.mustafaderinoz.userapp.viewmodel.UserUiState
import com.mustafaderinoz.userapp.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    isDarkTheme: Boolean, // Tema durumu eklendi
    onThemeToggle: () -> Unit, // Tema değiştirme aksiyonu eklendi
    onUserClick: (User) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullToRefreshState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface, // Dinamik Tema Rengi
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "Kullanıcılar",
                            tint = MaterialTheme.colorScheme.primary, // Dinamik Tema Rengi
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Kullanıcılar",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary // Dinamik Tema Rengi
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onThemeToggle) {
                        Icon(
                            // İkonların yerini değiştirdik: Karanlık moddaysa Ay, değilse Güneş göster
                            imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Tema Değiştir",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface // Dinamik Tema Rengi
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Arama Çubuğu
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
                modifier = Modifier.fillMaxSize(),
                state = pullRefreshState,
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = isRefreshing,
                        state = pullRefreshState,
                        color = MaterialTheme.colorScheme.primary, // Dönme efektinin rengi (İstersen Color.Blue vs. yapabilirsin)
                        containerColor = MaterialTheme.colorScheme.surfaceVariant // Yuvarlak arka plan rengi
                    )
                }
            ) {
                when (val state = uiState) {
                    is UserUiState.Loading -> LoadingContent()
                    is UserUiState.Error -> {
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
            Text("Ara...", color = MaterialTheme.colorScheme.outlineVariant) // Dinamik Tema Rengi
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Ara",
                tint = MaterialTheme.colorScheme.outlineVariant // Dinamik Tema Rengi
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Temizle",
                        tint = MaterialTheme.colorScheme.outlineVariant // Dinamik Tema Rengi
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, // Dinamik Tema Rengi
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, // Dinamik Tema Rengi
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.1f),
            focusedBorderColor = MaterialTheme.colorScheme.primary, // Dinamik Tema Rengi
            cursorColor = MaterialTheme.colorScheme.primary // Dinamik Tema Rengi
        )
    )
}

@Composable
private fun ErrorContent(
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.errorContainer), // Dinamik Tema Rengi
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Hata",
                tint = MaterialTheme.colorScheme.error, // Dinamik Tema Rengi
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bir hata oluştu",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface // Dinamik Tema Rengi
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 24.sp
            ),
            color = MaterialTheme.colorScheme.outlineVariant, // Dinamik Tema Rengi
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Yenilemek için ekranı aşağı kaydırın",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outlineVariant, // Dinamik Tema Rengi
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Aşağı Kaydır",
            tint = MaterialTheme.colorScheme.outlineVariant, // Dinamik Tema Rengi
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary) // Dinamik Tema Rengi
    }
}

@Composable
private fun EmptySearchResult(query: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "\"$query\" için sonuç bulunamadı",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outlineVariant // Dinamik Tema Rengi
        )
    }
}