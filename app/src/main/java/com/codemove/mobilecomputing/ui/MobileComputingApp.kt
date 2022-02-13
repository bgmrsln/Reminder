package com.codemave.mobilecomputing.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codemave.mobilecomputing.ui.edit.Edit
import com.codemave.mobilecomputing.ui.editTask.EditTask
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
        composable(route = "home/{username}") {
            entry->
            Home(
                navController = appState.navController,
                entry.arguments?.getString("username")?:""
            )
        }
        composable(route = "task") {
            Task(onBackPress = appState::navigateBack)
        }
        composable(route= "signup") {
            Signup(onBackPress = appState::navigateBack)
        }
        composable(route= "profile/{username}"){
            entry->
            Profile(
                navController= appState.navController,
                entry.arguments?.getString("username")?:""

            )
        }

        composable(route= "edit/{username}"){
            entry->
            Edit(
                navController= appState.navController,
                entry.arguments?.getString("username")?:""
            )
        }
        composable(route= "editTask/{taskId}"){

            entry-> EditTask(onBackPress = appState::navigateBack,
        entry.arguments?.getString("taskId")?:""
        )
        }



    }
}