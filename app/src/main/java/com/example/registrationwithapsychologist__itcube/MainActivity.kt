package com.example.registrationwithapsychologist__itcube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.registrationwithapsychologist__itcube.CustomComposable.Interface.MenuScreen
import com.example.registrationwithapsychologist__itcube.ui.theme.RegistrationWithAPsychologistITCubeTheme

class MainActivity : ComponentActivity() {
    // var mainScreen : @Composable() { MenuScreen() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationWithAPsychologistITCubeTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {
                    MenuScreen(modifier = Modifier.padding(it))
                }
            }
        }
    }
}

