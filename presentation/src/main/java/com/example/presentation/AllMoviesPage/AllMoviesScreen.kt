package com.example.presentation.AllMoviesPage

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.entity.Movies
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AllMoviesScreen(viewModel: AllMoviesScreenViewModel) {
    val moviesState by viewModel.data.collectAsState()
    val isLoading = viewModel.isLoading
    val isEndReached = viewModel.isEndReached
    val errorMessage by viewModel.error.collectAsState()
    val listState = rememberLazyListState()



    LaunchedEffect(Unit) {
        viewModel.fetchMovies(1)
    }

    LaunchedEffect(Unit) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastIndex ->
                if (lastIndex != null && lastIndex == listState.layoutInfo.totalItemsCount - 1 &&
                    !viewModel.isLoading && !viewModel.isEndReached
                ) {
                    viewModel.loadNextPage()
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            isLoading && moviesState.isEmpty() -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            errorMessage != null -> {
                Text(
                    text = "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                    items(moviesState) { movie ->
                        MovieItem(movie)
                    }
                    if (isLoading) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movies) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movie.releaseYear.toString(), style = MaterialTheme.typography.bodySmall)
        }
    }
}