package com.example.presentation.FavoritesPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.entity.Movies
import com.example.ourmovies.presentation.viewModels.FavoriteViewModel

@Composable
fun FavoritePageScreen(
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val favoriteMovies = favoriteViewModel.favoriteMovies.value

    LaunchedEffect(Unit) {
        favoriteViewModel.fetchFavoriteMovies()
    }

    Scaffold(
        topBar = { CustomTopBar(onBackPressed) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E1E1E), Color(0xFF3A3A3A))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (favoriteViewModel.isLoading.value) {
                CircularProgressIndicator(color = Color.White)
            } else {
                favoriteViewModel.errorMessage.value.let { error ->
                    if (error.isNotEmpty()) {
                        Text(
                            text = "Error: $error",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                        Log.e("FavoritePageScreen", "Error occurred: $error")
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(favoriteMovies) { movie ->
                        FavoriteMovieItem(movie, favoriteViewModel)
                        Log.d("FavoritePageScreen", "Displaying favorite: ${movie.title}")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(onBackPressed: () -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title = {
            Text(
                text = "Favorite Movies",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }
    )
}

@Composable
fun FavoriteMovieItem(movie: Movies, viewModel: FavoriteViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF252525)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(movie.poster),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = movie.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Release Year: ${movie.releaseYear}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "Rating: ${movie.rating}/10",
                fontSize = 16.sp,
                color = Color.Yellow
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { viewModel.deleteFavoriteMovie(movie.id) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
