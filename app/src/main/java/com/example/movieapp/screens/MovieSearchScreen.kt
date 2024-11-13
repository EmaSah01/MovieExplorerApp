package com.example.movieapp.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.movieapp.models.MovieDetails
import com.example.movieapp.ui.theme.viewModel.MoviesViewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(
    navController: NavController,
    moviesViewModel: MoviesViewModel = viewModel(),
){
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var minRating by remember { mutableStateOf(0f) }
    var showRatings by remember { mutableStateOf(true) }
    var showOverviews by remember { mutableStateOf(true) }
    var showGenres by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val darkBlueSwitchColors = SwitchDefaults.colors(
        checkedThumbColor = Color(0xFFB6BBEE),
        uncheckedThumbColor = Color(0xFF00008B),
        checkedTrackColor = Color(0xFF00008B),
        uncheckedTrackColor = Color(0xFFB6BBEE)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Search Movies") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text("Ratings")
                            Switch(
                                checked = showRatings,
                                onCheckedChange = { showRatings = it },
                                colors = darkBlueSwitchColors
                            )
                        }
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text("Genres")
                            Switch(
                                checked = showGenres,
                                onCheckedChange = { showGenres = it },
                                colors = darkBlueSwitchColors
                            )
                        }
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text("Overviews")
                            Switch(
                                checked = showOverviews,
                                onCheckedChange = { showOverviews = it },
                                colors = darkBlueSwitchColors
                            )
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        if (it.text.isNotEmpty()) {
                            moviesViewModel.searchMovies(it.text)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Minimum Rating: ${minRating.toInt()}", fontSize = 16.sp)

                Slider(
                    value = minRating,
                    onValueChange = { minRating = it },
                    valueRange = 0f..10f,
                    steps = 9,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                val moviesList by moviesViewModel.moviesList.collectAsState(initial = emptyList())

                LazyColumn {
                    items(moviesList.filter { it.voteAverage ?: 0.0 >= minRating }, key = { it.id ?: 0 }) { movie ->
                        MovieItem(movie, showRatings, showOverviews, showGenres, navController, context)
                    }
                }
            }
        }
    )
}

@Composable
fun MovieItem(
    movie: MovieDetails,
    showRatings: Boolean,
    showOverviews: Boolean,
    showGenres: Boolean,
    navController: NavController,
    context: Context
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        movie.posterPath?.let {
            Image(
                painter = rememberImagePainter("https://image.tmdb.org/t/p/w500$it"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title ?: "No Title",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (showRatings || expanded) {
            movie.voteAverage?.let {
                Text(
                    text = "Rating: ${it}/10",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        if (showGenres || expanded) {
            Text(
                text = "Genres: ${movie.genres ?: "No Genres"}",
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (showOverviews || expanded) {
            Text(
                text = movie.overview ?: "No Overview",
                fontSize = 16.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (expanded) {
            Text(text = "Release Date: ${movie.releaseDate ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Revenue: ${movie.revenue ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Runtime: ${movie.runtime ?: "N/A"} minutes", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Status: ${movie.status ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Budget: ${movie.budget ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Homepage: ${movie.homepage ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Original Language: ${movie.originalLanguage ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Original Title: ${movie.originalTitle ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Popularity: ${movie.popularity ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tagline: ${movie.tagline ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Production Companies: ${movie.productionCompanies ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Production Countries: ${movie.productionCountries ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Spoken Languages: ${movie.spokenLanguages ?: "N/A"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    expanded = !expanded
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00008B) // Example of custom color
                )
            ) {
                Text(text = if (expanded) "Show less" else "Read more", color = Color.White)
            }

            Button(
                onClick = {
                    shareMovie(context, movie)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text(text = "Share", color = Color.White)
            }
        }
    }
}

fun shareMovie(context: Context, movie: MovieDetails) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${movie.title}\nRating: ${movie.voteAverage}/10\nOverview: ${movie.overview}")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share movie via"))
}
