package com.leromaro.sistemapacientes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.leromaro.sistemapacientes.navigation.AppNavigation
import com.leromaro.sistemapacientes.ui.theme.SistemaPacientesTheme
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel : AttendViewModel by viewModels()
        setContent {
            SistemaPacientesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    Box(modifier = Modifier
                        .fillMaxSize()
//                        .background(Color(100,100,100,10))
                    ){
                        AppNavigation(viewModel, context)
                    }

                }
            }
        }
    }
}
