package com.example.chatto.ui.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatto.domain.vo.DbNumber
import com.example.chatto.domain.vo.DbProfile
import com.example.chatto.domain.vo.ValidPrefix
import com.example.chatto.ui.home.components.isValidText
import com.example.chatto.ui.utils.PreferencesManager
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {

    var number by rememberSaveable { mutableStateOf("") }
    var prefix by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val data = remember { mutableStateOf(preferencesManager.getData("LOGGED", "")) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Please enter you phone number")
        OutlinedTextField(
            value = prefix,
            onValueChange = { input ->
                prefix = input
            },
            leadingIcon = { Text(text = "+") },
            modifier = Modifier.padding(8.dp),
            label = { Text("Prefix") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = !ValidPrefix.contains(prefix)

        )
        OutlinedTextField(
            value = number,
            onValueChange = { input ->
                number = input
            },
            modifier = Modifier.padding(8.dp),
            label = { Text("Cell-Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = !isValidText(number)
        )
        OutlinedButton(
            modifier = Modifier
                .padding(8.dp),
            onClick = {
                coroutineScope.launch {
                    loginViewModel.addProfile(DbProfile(0, DbNumber(prefix, number)))

                    preferencesManager.saveData("LOGGED", "user-logged")
                    data.value = "user-logged"

                }
                navController.popBackStack("dispatcher", inclusive = false)
            },
            enabled = ValidPrefix.contains(prefix) && isValidText(number)
        ) {
            Text(text = "Confirm")
        }
    }
}

