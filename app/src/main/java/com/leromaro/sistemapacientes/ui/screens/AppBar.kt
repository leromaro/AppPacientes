package com.leromaro.sistemapacientes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leromaro.sistemapacientes.R
import com.leromaro.sistemapacientes.navigation.AppScreens
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.leromaro.sistemapacientes.ui.screens.components.ShowIcon
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController, viewModel: AttendViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val erase = stringResource(id = R.string.toast_borrado)
    Surface(
        modifier = Modifier.fillMaxWidth(), color = Color.Green
    ) {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }, navigationIcon = {
            ShowIcon(
                icon = Icons.Default.Info,
                description = "info",
                onIconClick = {
                    navController.navigate(AppScreens.DialogScreen.route)
                },
                color = Color.White)
        }, actions = {
            Icon(imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu",
                modifier = Modifier.clickable {
                    expanded = !expanded
                })
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .widthIn(max = 200.dp)
            ) {
                DropdownMenuItem(text = { Text(text = stringResource(id = R.string.resumen_mensual)) },
                    onClick = {
                        expanded = false
                        expandedMenu = true
                    })
                DropdownMenuItem(onClick = {
                    expanded = false
                    viewModel.resetData()
                    coroutineScope.launch {
                        viewModel.clearDataFiles(context)
                        viewModel.showToast(context, erase)
                    }
                }, text = {
                    Text(
                        text = stringResource(id = R.string.borrar)
                    )
                })
                DropdownMenuItem(onClick = {
                    expanded = false
                    coroutineScope.launch {
                        viewModel.clearDataFiles(context)
                    }
                    exitProcess(0)
                }, text = { Text(text = stringResource(id = R.string.salir)) })
            }
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.LightGray
        )
        )
    }
    if (expandedMenu) {
        navController.navigate(AppScreens.ResultScreen.route)
        expandedMenu = false
    }
}


