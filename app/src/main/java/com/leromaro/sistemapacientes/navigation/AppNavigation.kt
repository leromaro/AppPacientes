package com.leromaro.sistemapacientes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leromaro.sistemapacientes.screens.ResultScreen
import com.leromaro.sistemapacientes.screens.SplashScreen
import com.leromaro.sistemapacientes.screens.StartScreen
import com.leromaro.sistemapacientes.PracticasViewModel
import com.leromaro.sistemapacientes.screens.AppBar
import com.leromaro.sistemapacientes.screens.DialogScreen
import com.leromaro.sistemapacientes.screens.Banner

@Composable
fun AppNavigation(practicasViewModel: PracticasViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(route = AppScreens.StartScreen.route) {
            StartScreen(navController, practicasViewModel)
            AppBar(navController)
        }
        composable(route = AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = AppScreens.ResultScreen.route) {
            ResultScreen(navController, practicasViewModel)
        }
        composable(route = AppScreens.DialogScreen.route) {
            DialogScreen(navController)
        }
        composable(route = AppScreens.AppBar.route){
            AppBar(navController)
        }
        composable(route = AppScreens.Banner.route){
            Banner()
        }
    }
}