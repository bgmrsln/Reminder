package com.codemave.mobilecomputing.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codemave.mobilecomputing.R
import com.codemave.mobilecomputing.data.entity.LoginInfo
import com.codemave.mobilecomputing.ui.login.LoginViewModel
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Profile(

    viewModel: ProfileViewModel = viewModel()
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val username = "bgmrsln"//viewModel.LoginInfoSelected()?.username
        val password = "123456"//viewModel.LoginInfoSelected()?.password

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

            Text(
                //when you add to the database, you can use data from there?
                text = "Username: "+username,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                //when you add to the database, you can use data from there?
                text = "Password "+password,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))



        }
    }
}