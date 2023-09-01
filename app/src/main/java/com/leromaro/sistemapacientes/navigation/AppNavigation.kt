package com.leromaro.sistemapacientes.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leromaro.sistemapacientes.ui.screens.ResultScreen
import com.leromaro.sistemapacientes.ui.screens.SplashScreen
import com.leromaro.sistemapacientes.ui.screens.StartScreen
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel
import com.leromaro.sistemapacientes.ui.screens.components.DialogScreen

@Composable
fun AppNavigation(viewModel: AttendViewModel, context : Context) {
    val navController = rememberNavController()
    LaunchedEffect(true) {
        viewModel.loadSavedData(context)
    }
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(route = AppScreens.StartScreen.route) {
            StartScreen(navController, viewModel)
        }
        composable(route = AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = AppScreens.ResultScreen.route) {
            ResultScreen(navController, viewModel)
        }
        composable(route = AppScreens.DialogScreen.route) {
            DialogScreen(navController)
        }
    }
}