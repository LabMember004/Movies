package com.example.presentation.AllMoviesPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.domain.entity.Movies
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowsePageScreen(viewModel: BrowsePageViewModel = hiltViewModel(), navController: NavController) {
    val moviesState by viewModel.data.collectAsState()
    val isLoading = viewModel.isLoading
    val errorMessage by viewModel.error.collectAsState()
    val listState = rememberLazyListState()

    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var showYearPicker by remember { mutableStateOf(false) }
    var selectedYear by remember { mutableStateOf<Int?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val genres = listOf("Action", "Adventure", "Drama", "Comedy")

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

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.applyFilter(
                            genres = selectedGenre,
                            releaseYear = selectedYear,
                            query = if (it.isNotEmpty()) it else null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search movies...") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    },
                     colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(genres) { genre ->
                            FilterChip(
                                selected = selectedGenre == genre,
                                onClick = {
                                    selectedGenre = if (selectedGenre == genre) null else genre
                                    viewModel.applyFilter(
                                        genres = selectedGenre,
                                        releaseYear = selectedYear,
                                        query = if (searchQuery.isNotEmpty()) searchQuery else null
                                    )
                                },
                                label = { Text(genre, style = MaterialTheme.typography.bodySmall) },
                                leadingIcon = if (selectedGenre == genre) {
                                    { Icon(Icons.Filled.Check, contentDescription = "Selected", modifier = Modifier.size(16.dp)) }
                                } else null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        onClick = { showYearPicker = true },
                        modifier = Modifier.wrapContentWidth(),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            selectedYear?.toString() ?: "Year",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (selectedGenre != null || selectedYear != null || searchQuery.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(4.dp))
                        TextButton(
                            onClick = {
                                selectedGenre = null
                                selectedYear = null
                                searchQuery = ""
                                viewModel.applyFilter(genres = null, releaseYear = null, query = null)
                            },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Clear", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }

        if (showYearPicker) {
            YearPickerDialog(
                onDismiss = { showYearPicker = false },
                onYearSelected = { year ->
                    selectedYear = year
                    showYearPicker = false
                    viewModel.applyFilter(
                        genres = selectedGenre,
                        releaseYear = selectedYear,
                        query = if (searchQuery.isNotEmpty()) searchQuery else null
                    )
                }
            )
        }


        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            when {
                isLoading && moviesState.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    Text(
                        text = "Error: $errorMessage",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                moviesState.isEmpty() && !isLoading -> {
                    Text(
                        text = "No movies found with the selected filters",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                        items(moviesState) { movie ->
                            MovieItem(movie = movie, navController)
                        }
                        if (isLoading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
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
fun YearPickerDialog(
    onDismiss: () -> Unit,
    onYearSelected: (Int) -> Unit
) {
    val years = (1980..2025).toList().reversed()
    var selectedYear by remember { mutableStateOf<Int?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Release Year") },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                items(years) { year ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedYear = year }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedYear == year,
                            onClick = { selectedYear = year }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(year.toString())
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedYear?.let { onYearSelected(it) }
                },
                enabled = selectedYear != null
            ) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun MovieItem(movie: Movies, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("movieDetails/${movie.id}")
            }
            .height(250.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(movie.poster),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Release Year: ${movie.releaseYear}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rating: ${movie.rating}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Genres: ${movie.genres.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}