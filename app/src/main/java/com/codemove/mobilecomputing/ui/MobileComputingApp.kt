package com.codemave.mobilecomputing.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codemave.mobilecomputing.ui.edit.Edit
import com.codemave.mobilecomputing.ui.home.Home

import com.codemave.mobilecomputing.ui.login.Login
import com.codemave.mobilecomputing.ui.profile.Profile
import com.codemave.mobilecomputing.ui.signup.Signup
import com.codemave.mobilecomputing.ui.task.Task

@Composable
fun MobileComputingApp(
    appState: MobileComputingAppState = rememberMobileComputingAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(navController = appState.navController)
        }
        composable(route = "home") {
            Home(
                navController = appState.navController
            )
        }
        composable(route = "task") {
            Task(onBackPress = appState::navigateBack)
        }
        composable(route= "signup") {
            Signup(onBackPress = appState::navigateBack)
        }
        composable(route= "profile"){
            Profile(navController= appState.navController)
        }

        composable(route= "edit"){
            Edit(navController= appState.navController)
        }
    }
}