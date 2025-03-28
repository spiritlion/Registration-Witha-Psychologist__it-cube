package com.example.registrationwithapsychologist__itcube___newversion

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.accounts
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.AccountScreen
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.MenuScreen
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.RegistrationScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.InfoScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.LogScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.MainMenuScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.SettingsScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.TestingScreen
import com.example.registrationwithapsychologist__itcube___newversion.ui.theme.RegistrationWithAPsychologistITCubeNewVersionTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

var calendarDate : MutableMap<String, MutableList<MutableList<Int>>> = mutableMapOf(
    "пн" to mutableListOf(
        mutableListOf(
            8, 0
        ),
        mutableListOf(
            9, 0
        ),
        mutableListOf(
            10, 0
        ),
        mutableListOf(
            11, 0
        ),
        mutableListOf(
            12, 0
        ),
        mutableListOf(
            13, 0
        ),
        mutableListOf(
            14, 0
        ),
        mutableListOf(
            15, 0
        ),
        mutableListOf(
            16, 0
        ),
        mutableListOf(
            17, 0
        )
    ),
    "вт" to mutableListOf(
        mutableListOf(
            8, 0
        ),
        mutableListOf(
            9, 0
        ),
        mutableListOf(
            10, 0
        ),
        mutableListOf(
            11, 0
        ),
        mutableListOf(
            12, 0
        ),
        mutableListOf(
            13, 0
        ),
        mutableListOf(
            14, 0
        ),
        mutableListOf(
            15, 0
        ),
        mutableListOf(
            16, 0
        ),
        mutableListOf(
            17, 0
        )
    ),
    "ср" to mutableListOf(
        mutableListOf(
            8, 0
        ),
        mutableListOf(
            9, 0
        ),
        mutableListOf(
            10, 0
        ),
        mutableListOf(
            11, 0
        ),
        mutableListOf(
            12, 0
        ),
        mutableListOf(
            13, 0
        ),
        mutableListOf(
            14, 0
        ),
        mutableListOf(
            15, 0
        ),
        mutableListOf(
            16, 0
        ),
        mutableListOf(
            17, 0
        )
    ),
    "чт" to mutableListOf(
        mutableListOf(
            8, 0
        ),
        mutableListOf(
            9, 0
        ),
        mutableListOf(
            10, 0
        ),
        mutableListOf(
            11, 0
        ),
        mutableListOf(
            12, 0
        ),
        mutableListOf(
            13, 0
        ),
        mutableListOf(
            14, 0
        ),
        mutableListOf(
            15, 0
        ),
        mutableListOf(
            16, 0
        ),
        mutableListOf(
            17, 0
        )
    ),
    "пт" to mutableListOf(
        mutableListOf(
            8, 0
        ),
        mutableListOf(
            9, 0
        ),
        mutableListOf(
            10, 0
        ),
        mutableListOf(
            11, 0
        ),
        mutableListOf(
            12, 0
        ),
        mutableListOf(
            13, 0
        ),
        mutableListOf(
            14, 0
        ),
        mutableListOf(
            15, 0
        ),
        mutableListOf(
            16, 0
        ),
        mutableListOf(
            17, 0
        )
    )
)

var avatars = listOf(
    R.drawable.avatar_base,
    R.drawable.avatar_man,
    R.drawable.avatar_woman
)
var currentPerson = accounts[0]
var loggedInPerson = mutableMapOf(
    0 to currentPerson.password
)

val auth = Firebase.auth
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationWithAPsychologistITCubeNewVersionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Main(modifier =  Modifier.padding(it))
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Column(modifier) {
        NavBar(navController = navController)
    }
}
@Composable
fun NavBar(navController: NavHostController){
    val items = listOf("Информация о психологе", "Запись", "Аккаунт", "Настройки", "TEST_SCREEN")
    val selectedItem = remember { mutableStateOf("Запись к психологу. IT-куб") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                Text(" Запись\n к психологу.\n IT-куб", fontSize = 33.sp)
                items.forEach { item ->
                    TextButton(
                        onClick = {
                            scope.launch { drawerState.close() }
                            when (item) {
                                "Информация о психологе" -> { navController.navigate(NavRoutes.Info.route) }
                                "Запись" -> { navController.navigate(NavRoutes.Schedule.route) }
                                "Аккаунт" -> { navController.navigate(NavRoutes.Account.route) }
                                "Настройки" -> { navController.navigate(NavRoutes.Setting.route) }
                                "TEST_SCREEN" -> { navController.navigate(NavRoutes.Test.route) }
                            }
                            selectedItem.value = item
                        },
                    ) {
                        Row {
                            when (item) {
                                "Информация о психологе" -> Icon(Icons.Filled.Info, null)
                                "Запись" -> Icon(Icons.Filled.Call, null)
                                "Аккаунт" -> Icon(Icons.Filled.AccountCircle, null)
                                "Настройки" -> Icon(Icons.Filled.Settings, null)
                                "TEST_SCREEN" -> Icon(Icons.Filled.Build, null)
                            }
                            Text(item, fontSize = 22.sp)
                        }
                    }
                }
            }
        },
        content = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }, content = {
                            Icon(Icons.Filled.Menu, "Меню")
                        })
                        Text(selectedItem.value, fontSize = 30.sp)
                    }
                }
                NavHost(navController, startDestination = NavRoutes.Main.route) {
                    composable(NavRoutes.Info.route) { InfoScreen() }
                    composable(NavRoutes.Schedule.route) { MenuScreen() }
                    composable(NavRoutes.Account.route) { AccountScreen(navController = navController) }
                    composable(NavRoutes.Setting.route) { SettingsScreen() }

                    composable(NavRoutes.Registration.route) { RegistrationScreen(navController = navController) }
                    composable(NavRoutes.Log.route) { LogScreen(navController = navController) }

                    composable(NavRoutes.Main.route) { MainMenuScreen(scope = scope, drawerState = drawerState) }
                    composable(NavRoutes.Test.route) { TestingScreen() }
                }
            }
        }
    )
}




sealed class NavRoutes(val route: String) {
    data object Info : NavRoutes("info")
    data object Schedule : NavRoutes("schedule")
    data object Account : NavRoutes("account")
    data object Setting : NavRoutes("setting")

    data object Registration : NavRoutes("registration")
    data object Log : NavRoutes("log")

    data object Main : NavRoutes("main")
    data object Test : NavRoutes("test")
}
