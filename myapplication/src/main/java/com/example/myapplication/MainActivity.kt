package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = DarkBlueGray,
                    secondary = MediumGray,
                    background = LightLavender,
                    surface = Color.White,
                    onPrimary = Color.White,
                    onSecondary = DeepNavy,
                    onBackground = DeepNavy,
                    onSurface = DeepNavy
                )
            ) {
                var currentUser by remember { mutableStateOf<User?>(null) }
                
                if (currentUser == null) {
                    LoginScreen(onLoginSuccess = { user -> currentUser = user })
                } else {
                    ProfileSidebarApp(currentUser!!, onLogout = { currentUser = null })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSidebarApp(user: User, onLogout: () -> Unit) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ProfileDrawerContent(
                user = user,
                onNavigate = { route ->
                    if (route == "logout") {
                        onLogout()
                    } else {
                        navController.navigate(route)
                    }
                    scope.launch { drawerState.close() }
                }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                "Home",
                                color = DeepNavy,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    modifier = Modifier.size(32.dp),
                                    tint = DarkBlueGray
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            titleContentColor = DeepNavy,
                            navigationIconContentColor = DarkBlueGray
                        )
                    )
                },
                containerColor = LightLavender
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("home") { HomeScreen() }
                    composable("reservation") { Reservation(user) }
                    composable("reservations") { Reservations() }
                    composable("balance") { Balance() }
                    composable("account") { Account(user) }
                }
            }
        }
    )
}

@Composable
fun ProfileDrawerContent(user: User, onNavigate: (String) -> Unit) {
    ModalDrawerSheet(
        drawerContainerColor = LightLavender,
        modifier = Modifier.width(280.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp)
        ) {
            DrawerMenuItem(
                icon = Icons.Default.Home,
                title = "Home",
                onClick = { onNavigate("home") }
            )

            DrawerMenuItem(
                icon = Icons.Default.CalendarMonth,
                title = "Make a Reservation",
                onClick = { onNavigate("reservation") }
            )

            DrawerMenuItem(
                icon = Icons.Default.Event,
                title = "Reservations",
                onClick = { onNavigate("reservations") }
            )

            DrawerMenuItem(
                icon = Icons.Default.AccountBalance,
                title = "My Balance",
                onClick = { onNavigate("balance") }
            )

            DrawerMenuItem(
                icon = Icons.Default.Person,
                title = "My Account",
                onClick = { onNavigate("account") }
            )

            Spacer(modifier = Modifier.weight(1f))
            
            DrawerMenuItem(
                icon = Icons.Default.Close,
                title = "Logout",
                onClick = { onNavigate("logout") }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    tint = DarkBlueGray
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = user.name,
                    fontSize = 14.sp,
                    color = DeepNavy
                )
            }
        }
    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = DeepNavy,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            color = DeepNavy,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun Reservations() {
    ScreenContent("Reservations")
}

@Composable
fun Balance() {
    ScreenContent("My Balance")
}

@Composable
fun ScreenContent(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightLavender),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DeepNavy
        )
    }
}
