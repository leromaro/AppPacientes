package com.leromaro.sistemapacientes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.leromaro.sistemapacientes.ui.screens.components.RedPoint
import com.leromaro.sistemapacientes.ui.screens.components.ShowIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController,
           viewModel: AttendViewModel,
           icon : ImageVector,
           text : String,
           onIconClick: ()-> Unit,
           color : Color,
           start : Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val erase = stringResource(id = R.string.toast_borrado)
    val resume : Boolean by viewModel.resume.observeAsState(initial = false )

    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        TopAppBar(
            title = {
            Text(text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.onPrimary)
        },
            navigationIcon = {
            ShowIcon(
                icon,
                text,
                onIconClick,
                color
            ) },
            actions = {
            Box(modifier = Modifier){
                Icon(imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }, tint = MaterialTheme.colorScheme.onPrimary
                    )
                if (resume && start){
                    RedPoint(modifier = Modifier.align(Alignment.TopEnd))
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .widthIn(max = 200.dp)
            ) {
                if (start){
                    DropdownMenuItem(text = { Text(text = stringResource(id = R.string.resumen_mensual)) },
                        onClick = {
                            expanded = false
                            navController.navigate(AppScreens.ResultScreen.route)
                        })
                    DropdownMenuItem(onClick = {
                        expanded = false
                        coroutineScope.launch {
                            viewModel.clearData(context)
                            viewModel.showToast(context, erase)
                        }
                    }, text = {
                        Text(
                            text = stringResource(id = R.string.borrar)
                        )
                    })
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    coroutineScope.launch {
                        viewModel.clearData(context)
                        viewModel.showToast(context, erase)
                        delay(1000)
                    }
                    exitProcess(0)
                }, text = { Text(text = stringResource(id = R.string.salir)) })
            }
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
        )
    }
}


