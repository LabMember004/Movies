package com.example.presentation.AllMoviesPage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.entity.Movies
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AllMoviesScreen(viewModel: AllMoviesScreenViewModel) {
    // Collecting the state from the viewModel
    val moviesState = viewModel.data.collectAsState()
    val isLoading = viewModel.loading.collectAsState()
    val errorMessage = viewModel.error.collectAsState()

    // Fetch movies on initial launch
    LaunchedEffect(true) {
        viewModel.fetchMovies(page = 1) // You can modify this for pagination if needed
    }

    // Simple UI to show data, loading, and errors
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (errorMessage.value != null) {
            Text(
                text = "Error: ${errorMessage.value}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(moviesState.value) { movie ->
                    MovieItem(movie)
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movies) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movie.releaseYear.toString(), style = MaterialTheme.typography.bodySmall)
        }
    }
}

