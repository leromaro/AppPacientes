package com.leromaro.sistemapacientes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.leromaro.sistemapacientes.navigation.AppNavigation
import com.leromaro.sistemapacientes.ui.theme.SistemaPacientesTheme
import com.leromaro.sistemapacientes.ui.viewModel.PracticasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = PracticasViewModel()
        setContent {
            SistemaPacientesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    AppNavigation(viewModel, context)
                }
            }
        }
    }
}
