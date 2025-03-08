package com.example.presentation.FavoritesPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.domain.entity.Movies
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ourmovies.presentation.viewModels.FavoriteViewModel

@Composable
fun FavoritePageScreen(
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteMovies = favoriteViewModel.favoriteMovies.value

    LaunchedEffect(Unit) {
        Log.d("FavoritePageScreen", "Fetching favorite movies...")
        favoriteViewModel.fetchFavoriteMovies()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (favoriteViewModel.isLoading.value) {
            Text(text = "Loading favorites...")
            Log.d("FavoritePageScreen", "Loading state active")
        } else {
            favoriteViewModel.errorMessage.value.let { error ->
                if (error.isNotEmpty()) {
                    Text(text = "Error: $error")
                    Log.e("FavoritePageScreen", "Error occurred: $error")
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favoriteMovies) { movie ->
                    FavoriteMovieItem(movie)
                    Log.d("FavoritePageScreen", "Displaying favorite: ${movie.title}")
                }
            }
        }
    }
}


@Composable
fun FavoriteMovieItem(movie: Movies) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(movie.poster),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.title,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Release Year: ${movie.releaseYear}",
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Rating: ${movie.rating}/10",
            )

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


