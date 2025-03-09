package com.example.registrationwithapsychologist__itcube___newversion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.accounts
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.AccountScreen
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.MenuScreen
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.RegistrationScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.MainMenuScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.SettingsScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.TestingScreen
import com.example.registrationwithapsychologist__itcube___newversion.ui.theme.RegistrationWithAPsychologistITCubeNewVersionTheme
import kotlinx.coroutines.launch

var currentPerson = accounts[0]
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
    val items = listOf("Запись к психологу. IT-куб", "Расписание", "Аккаунт", "Настройки", "TEST_SCREEN")
    val selectedItem = remember { mutableStateOf(items[0]) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                items.forEach { item ->
                    TextButton(
                        onClick = {
                            scope.launch { drawerState.close() }
                            when (item) {
                                "Запись к психологу. IT-куб" -> { navController.navigate(NavRoutes.Main.route) }
                                "Расписание" -> { navController.navigate(NavRoutes.Schedule.route) }
                                "Аккаунт" -> { navController.navigate(NavRoutes.Account.route) }
                                "Настройки" -> { navController.navigate(NavRoutes.Setting.route) }
                                "TEST_SCREEN" -> { navController.navigate(NavRoutes.Test.route) }
                            }
                            selectedItem.value = item
                        },
                    ) {
                        Row {
                            when (item) {
                                "Расписание" -> Icon(Icons.Filled.DateRange, null)
                                "Аккаунт" -> Icon(Icons.Filled.AccountCircle, null)
                                "Настройки" -> Icon(Icons.Filled.Settings, null)
                                "TEST_SCREEN" -> Icon(Icons.Filled.Build, null)
                            }
                            if (item == "Запись к психологу. IT-куб") {
                                Column {
                                    Text("Запись",
                                        fontSize = 30.sp,
                                        modifier = Modifier
                                            .padding(horizontal = 20.dp),
                                        fontStyle = FontStyle.Italic
                                    )
                                    Text("к психологу.",
                                        fontSize = 30.sp,
                                        modifier = Modifier
                                            .padding(horizontal = 20.dp),
                                        fontStyle = FontStyle.Italic
                                    )
                                    Text("IT-куб.",
                                        fontSize = 30.sp,
                                        modifier = Modifier
                                            .padding(horizontal = 20.dp),
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            } else {
                                Text(item, fontSize = 22.sp)
                            }
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
                    composable(NavRoutes.Main.route) { MainMenuScreen(scope, drawerState) }
                    composable(NavRoutes.Schedule.route) { MenuScreen() }
                    composable(NavRoutes.Account.route) { AccountScreen() }
                    composable(NavRoutes.Setting.route) { SettingsScreen() }
                    composable(NavRoutes.Test.route) { TestingScreen() }
                }
            }

        }
    )
}




sealed class NavRoutes(val route: String) {
    data object Main : NavRoutes("main")
    data object Schedule : NavRoutes("schedule")
    data object Account : NavRoutes("account")
    data object Setting : NavRoutes("setting")
    data object Test : NavRoutes("test")
}
