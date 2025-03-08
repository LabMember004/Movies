package com.example.presentation.MovieDetailsPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.domain.entity.Movies
import com.example.ourmovies.presentation.viewModels.FavoriteViewModel
import com.example.presentation.AllMoviesPage.BrowsePageViewModel

@Composable
fun MovieDetailsScreen(
    movieId: String,
    navController: NavController,
    viewModel: BrowsePageViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val movie = viewModel.data.collectAsState().value.find { it.id == movieId }

    LaunchedEffect(movieId) {
        Log.d("MovieDetailsScreen", "Movie ID: $movieId, Fetching details...")
        if (movie == null) {
            viewModel.fetchMovies(1)
            Log.d("MovieDetailsScreen", "Fetching movie list because movie is null")
        } else {
            Log.d("MovieDetailsScreen", "Movie found: ${movie.title}")
        }
    }

    val isFavorite = remember { mutableStateOf(false) }

    if (movie != null) {
        MovieDetailsContent(movie, navController, favoriteViewModel, isFavorite)
    } else {
        Text(text = "Loading...")
        Log.d("MovieDetailsScreen", "Movie not found, displaying loading state")
    }
}


@Composable
fun MovieDetailsContent(
    movie: Movies,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel,
    isFavorite: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(movie.poster),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = movie.title,
                modifier = Modifier.weight(1f)
            )

            // Heart icon to favorite/unfavorite the movie
            IconButton(
                onClick = {
                    favoriteViewModel.addFavoriteMovie(movie)
                    isFavorite.value = !isFavorite.value  // Toggle the favorite state
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = if (isFavorite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Release Year: ${movie.releaseYear}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Rating: ${movie.rating}/10")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Runtime: ${movie.runtime} min")
                Text(text = "Genres: ${movie.genres}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back")
        }
    }
}

