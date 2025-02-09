package com.example.registrationwithapsychologist__itcube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.accounts
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.AccountScreen
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.MenuScreen
import com.example.registrationwithapsychologist__itcube.ui.theme.RegistrationWithAPsychologistITCubeTheme

var currentPerson = accounts[0]
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationWithAPsychologistITCubeTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {
                    AccountScreen(modifier = Modifier.padding(it))
                }
            }
        }
    }
}