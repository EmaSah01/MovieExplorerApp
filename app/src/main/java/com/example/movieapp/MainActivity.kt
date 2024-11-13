package com.example.movieapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.screens.LandingPageScreen
import com.example.movieapp.screens.MovieSearchScreen
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current

    MovieAppTheme {
        Surface(
            color = Color.LightGray,
            modifier = Modifier.fillMaxSize()
        ) {
            val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

            if (isPortrait) {
                PortraitLayout(navController)
            } else {
                LandscapeLayout(navController)
            }
        }
    }
}


@Composable
fun PortraitLayout(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "landing_page") {
        composable("landing_page") {
            LandingPageScreen(navController = navController)
        }
        composable("movie_search") {
            MovieSearchScreen(navController = navController)
        }
    }
}

@Composable
fun LandscapeLayout(navController: NavHostController) {
    Row(Modifier.fillMaxSize()) {
        Column(Modifier.weight(1f)) {
            NavHost(navController = navController, startDestination = "landing_page") {
                composable("landing_page") {
                    LandingPageScreen(navController = navController)
                }
                composable("movie_search") {
                    MovieSearchScreen(navController = navController)
                }

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
