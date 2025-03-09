package com.example.presentation.FavoritesPage

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ourmovies.presentation.viewModels.FavoriteViewModel

@Composable
fun FavoritePageScreen(
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteMovies = favoriteViewModel.favoriteMovies.value

    LaunchedEffect(Unit) {
        favoriteViewModel.fetchFavoriteMovies()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (favoriteViewModel.isLoading.value) {
            Text(text = "Loading favorites...")
        } else {
            favoriteViewModel.errorMessage.value.let { error ->
                if (error.isNotEmpty()) {
                    Text(text = "Error: $error")
                    Log.e("FavoritePageScreen", "Error occurred: $error")
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favoriteMovies) { movie ->
                    FavoriteMovieItem(movie , favoriteViewModel)
                    Log.d("FavoritePageScreen", "Displaying favorite: ${movie.title}")
                }
            }
        }
    }
}


@Composable
fun FavoriteMovieItem(movie: Movies , viewModel: FavoriteViewModel) {
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

            IconButton(
                onClick = { viewModel.deleteFavoriteMovie(movie.id) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete"


                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


