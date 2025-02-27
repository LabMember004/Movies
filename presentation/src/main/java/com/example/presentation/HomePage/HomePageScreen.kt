package com.example.presentation.HomePage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomePageScreen(viewModel: HomePageViewModel = hiltViewModel()) {
    val sections by viewModel.sections.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSections()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "HOME PAGE", style = MaterialTheme.typography.headlineLarge)

        if (sections.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(sections) { section ->
                    Text(text = section.title, style = MaterialTheme.typography.headlineMedium)
                    section.movies.forEach { movie ->
                        Text(text = movie.title, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}