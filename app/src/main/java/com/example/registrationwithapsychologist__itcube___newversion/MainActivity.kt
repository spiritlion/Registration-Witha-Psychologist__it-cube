package com.example.registrationwithapsychologist__itcube___newversion

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.AccountScreen
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.MenuScreen
import com.example.registrationwithapsychologist__itcube.custom_composable.Interface.RegistrationScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts.PsycgologData
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts.Record
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.InfoScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.LogScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.MainMenuScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.SettingsScreen
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface.TestingScreen
import com.example.registrationwithapsychologist__itcube___newversion.ui.theme.RegistrationWithAPsychologistITCubeNewVersionTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.jvm.java

var loggedInAccounts = mutableStateListOf<PersonData>()
var currentPerson : Int? = 0
var listUsersWithId = mutableStateListOf<Pair<String, PersonData>>()
var listRecordsWithId = mutableStateListOf<Pair<String, Record>>()
var listPsychologs = mutableStateListOf<PsycgologData>()

val Context.dataStore by preferencesDataStore(name = "settings")
var isTheme = mutableIntStateOf(0) // 0 - system 1 - light 2 - dark

class MainActivity : ComponentActivity() {
    val auth = Firebase.auth

    var db = Firebase.firestore
    var users = db.collection("users")
    var records = db.collection("records")
    var psychologs = db.collection("psychologs")
    private val isNonOlineBut = mutableStateOf(false)

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isOnline(this)) {
            // start verification on click of the button
            GlobalScope.launch {
                if (auth.uid != null) {
                    users.document(auth.uid!!)
                        .get()
                        .await()
                        .toObject(PersonData::class.java)
                        ?.let { loggedInAccounts.add(it) }
                    currentPerson = loggedInAccounts.size - 1
                }
                val userSnapshot = users
                    .get()
                    .await()
                listUsersWithId.clear()
                for (document in userSnapshot.documents) {
                    val user = document.toObject(PersonData::class.java)
                    if (user != null) {
                        listUsersWithId.add(Pair(document.id, user))
                    }
                }
                val recordsSnapshot = records
                    .get()
                    .await()
                listRecordsWithId.clear() // Очищаем список перед добавлением новых данных
                for (document in recordsSnapshot.documents) {
                    val record = document.toObject(Record::class.java)
                    if (record != null) {
                        // Добавляем пару (ID документа, объект Record) в список
                        listRecordsWithId.add(Pair(document.id, record))
                    }
                }
                val psychologsSnapshot = psychologs
                    .get()
                    .await()
                listPsychologs.clear()
                for (document in psychologsSnapshot.documents) {
                    val psycgolog = document.toObject(PsycgologData::class.java)
                    if (psycgolog != null) {
                        listPsychologs.add(psycgolog)
                    }
                }
            }
            enableEdgeToEdge()
            setContent {
                val context = LocalContext.current
                val repository = remember { DataStoreRepository(context.dataStore) }

                // Слушаем изменения в DataStore
                LaunchedEffect(Unit) {
                    repository.intFlow.collectLatest { value ->
                        isTheme.intValue = value
                    }
                }
                var isDarkTheme = when (isTheme.intValue) {
                    1 -> false
                    2 -> true
                    else -> isSystemInDarkTheme()
                }
                RegistrationWithAPsychologistITCubeNewVersionTheme(isDarkTheme = isDarkTheme) {
                    Scaffold(modifier = Modifier.fillMaxSize()) {
                        if (isOnline(this) || isNonOlineBut.value) {
                            Main(
                                modifier = Modifier.padding(it),
                                auth,
                                db,
                                users,
                                repository,
                                records
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = null
                                )
                                Text("Вы не в сети")
                                Text("Зайдите позже пожалуйста")
                                Button({
                                    isNonOlineBut.value = true
                                }) {
                                    Text("Продолжить без интернета")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
@Composable
fun Main(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    db: FirebaseFirestore,
    users: CollectionReference,
    repository: DataStoreRepository,
    records: CollectionReference
) {
    val navController = rememberNavController()
    Column(modifier) {
        NavBar(navController = navController, auth, db, users, repository, records)
    }
}

@Composable
fun NavBar(
    navController: NavHostController,
    auth: FirebaseAuth,
    db: FirebaseFirestore,
    users: CollectionReference,
    repository: DataStoreRepository,
    records: CollectionReference
){
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
                    composable(NavRoutes.Schedule.route) { MenuScreen(
                        navController,
                        records = records,
                        auth = auth
                    ) }
                    composable(NavRoutes.Account.route) { AccountScreen(navController = navController, auth = auth, db = db, users = users, records = records) }
                    composable(NavRoutes.Setting.route) { SettingsScreen(repository = repository) }

                    composable(NavRoutes.Registration.route) { RegistrationScreen(navController = navController, auth = auth, db = db, users = users) }
                    composable(NavRoutes.Log.route) { LogScreen(navController = navController, auth = auth, db = db, users = users) }

                    composable(NavRoutes.Main.route) { MainMenuScreen(scope = scope, drawerState = drawerState) }
                    composable(NavRoutes.Test.route) { TestingScreen(navController = navController, auth = auth, db = db, records = records) }
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

suspend fun registrationUserWithEmail(email: String, password: String, auth : FirebaseAuth, db: FirebaseFirestore, users: CollectionReference, newPerson: PersonData) {
    auth.createUserWithEmailAndPassword(email, password)
        .await()
        .let { result ->
            if (result.user != null) {
                Log.d(TAG, "It`s ok")
            } else Log.w(TAG, "It`s non ok")
        }
    users.document(auth.uid!!).set(newPerson)
    users.document(auth.uid!!)
        .get()
        .await()
        .toObject(PersonData::class.java)
        ?.let { loggedInAccounts.add(it); currentPerson = loggedInAccounts.size - 1 }
}

suspend fun logUserWithEmail(email: String, password: String, auth : FirebaseAuth, users: CollectionReference) : Boolean {
    try {
        auth.signInWithEmailAndPassword(email, password)
            .await()
            .let { result ->
                if (result.user != null) {
                    Log.d(TAG, "It`s ok")
                } else Log.w(TAG, "It`s non ok")
            }
        return auth.uid?.let { uid ->
            if (auth.currentUser != null){
                users.document(uid)
                    .get()
                    .await()
                    .toObject(PersonData::class.java)
                    ?.let { loggedInAccounts.add(it); currentPerson = loggedInAccounts.size - 1 }
                return true
            } else {
                return false
            }
        } ?: false
    }
    catch(e:Throwable) {
        return false
    }
}


suspend fun recordDate(records: CollectionReference, record: Record) {
    records.document()
        .set(record)
        .await()
}

suspend fun updateRecords(records: CollectionReference) {
    val recordsSnapshot = records
        .get()
        .await()
    listRecordsWithId.clear() // Очищаем список перед добавлением новых данных
    for (document in recordsSnapshot.documents) {
        val record = document.toObject(Record::class.java)
        if (record != null) {
            // Добавляем пару (ID документа, объект Record) в список
            listRecordsWithId.add(Pair(document.id, record))
        }
    }
}

/*
suspend fun findRecord(record: Record, db: FirebaseFirestore, records: CollectionReference): DocumentSnapshot? {
    return try {
        val querySnapshotTime = records
            .whereEqualTo("time", record.time)
            .get()
            .await() // Используем корутины для асинхронного вызова
        val querySnapshotUser = records
            .whereEqualTo("user", record.user)
            .get()
            .await() // Используем корутины для асинхронного вызова
        val querySnapshotPsychologist = records
            .whereEqualTo("psychologist", record.psychologist)
            .get()
            .await() // Используем корутины для асинхронного вызова
        val querySnapshotState = records
            .whereEqualTo("state", record.state)
            .get()
            .await() // Используем корутины для асинхронного вызова
        val querySnapshotReason = records
            .whereEqualTo("reason", record.reason)
            .get()
            .await() // Используем корутины для асинхронного вызова
        val querySnapshotReasonForRefusal = records
            .whereEqualTo("reasonForRefusal", record.reasonForRefusal)
            .get()
            .await() // Используем корутины для асинхронного вызова
        val commonElements = querySnapshotTime.documents
            .intersect(querySnapshotUser.documents)
            .intersect(querySnapshotPsychologist)
        if (!querySnapshotTime.isEmpty) {
            querySnapshotTime.documents.first() // Возвращаем первый найденный документ
        } else {
            null // Если документы не найдены
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null // В случае ошибки возвращаем null
    }
}

 */
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}

