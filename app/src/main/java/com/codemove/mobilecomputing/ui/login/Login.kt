package com.codemave.mobilecomputing.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codemave.mobilecomputing.data.entity.LoginInfo
import com.codemave.mobilecomputing.ui.profile.ProfileViewModel
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch

@Composable
fun Login(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    viewModel2: ProfileViewModel= viewModel()


) {

    val coroutineScope = rememberCoroutineScope()
    val onLoginInfoSelected= viewModel2::onLoginSelected

    Surface(modifier = Modifier.fillMaxSize()) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = username.value,
                onValueChange = { data -> username.value = data },
                label = { Text("Username")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { data -> password.value = data },
                label = { Text("Password")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    var loginInfo= LoginInfo("bgmrsln", "123456")
                    coroutineScope.launch {
                        loginInfo=  com.codemave.mobilecomputing.data.entity.LoginInfo(
                            username = username.value,
                            password = password.value,

                            )
                        if(
                            viewModel.checkUsername(
                                loginInfo
                            )
                        ){

                            navController.navigate("home")
                        }

                    }
                    onLoginInfoSelected(loginInfo)


                },

                enabled = password.value.length >= 6,
                //if(username.value=="bgmrsln" &&  password.value== "1234") true else false,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {  navController.navigate("signup") },
                enabled = true,
                //if(username.value=="bgmrsln" &&  password.value== "1234") true else false,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "SignUp")
            }

        }
    }
}

