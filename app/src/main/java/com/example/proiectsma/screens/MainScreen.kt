package com.example.proiectsma.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ScaleFactor
import androidx.navigation.NavController
import com.example.proiectsma.NavItem
import com.example.proiectsma.view_models.AuthViewModel

@Composable
fun MainScreen(navController: NavController, authViewModel: AuthViewModel) {

    val navItemList = listOf(
        NavItem("Messages", Icons.Default.Mail),
        NavItem("Announcements", Icons.Default.Search),
        NavItem("My Profile", Icons.Default.AccountCircle)
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = (selectedIndex == index),
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = "Icon")
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            navController = navController,
            authViewModel = authViewModel,
            )
    }
}