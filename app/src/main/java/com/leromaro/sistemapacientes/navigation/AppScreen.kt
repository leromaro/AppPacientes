package com.leromaro.sistemapacientes.navigation

sealed class AppScreens(val route : String){
    object ResultScreen: AppScreens("result_screen")
    object SplashScreen: AppScreens("splash_screen")
    object StartScreen: AppScreens("start_screen")
    object DialogScreen: AppScreens("dialog_screen")
    object AppBar: AppScreens("app_bar")
    object Banner: AppScreens("banner")
}
