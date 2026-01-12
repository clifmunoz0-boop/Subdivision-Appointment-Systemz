package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.border
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import java.util.Locale

// Color palettes
val DarkBlueGray = Color(0xFF5A5C71)
val LightLavender = Color(0xFFD8DBFA)
val DeepNavy = Color(0xFF34394E)
val MediumGray = Color(0xFF878DA5)

// Sample events data
val sampleEvents = listOf(
    CommunityEvent(
        id = 1,
        title = "Event",
        timeRange = "Fri, Jan 5 9:00 AM – 5:00 PM",
        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit.",
        schedules = listOf(
            EventSchedule("January 5, 2026", "9:00 AM - 12:00 PM", "Barangay Hall - Room 101"),
            EventSchedule("January 5, 2026", "1:00 PM - 5:00 PM", "Barangay Hall - Room 101"),
            EventSchedule("January 12, 2026", "9:00 AM - 5:00 PM", "Barangay Hall - Room 101")
        )
    ),
    CommunityEvent(
        id = 2,
        title = "Event",
        timeRange = "Fri, Jan 5 9:00 AM – 5:00 PM",
        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit.",
        schedules = listOf(
            EventSchedule("January 6, 2026", "9:00 AM - 12:00 PM", "Community Center"),
            EventSchedule("January 6, 2026", "1:00 PM - 5:00 PM", "Community Center")
        )
    ),
    CommunityEvent(
        id = 3,
        title = "Event",
        timeRange = "Fri, Jan 5 9:00 AM – 5:00 PM",
        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit.",
        schedules = listOf(
            EventSchedule("January 7, 2026", "10:00 AM - 4:00 PM", "Barangay Gym")
        )
    ),
    CommunityEvent(
        id = 4,
        title = "Event",
        timeRange = "Fri, Jan 5 9:00 AM – 5:00 PM",
        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit.",
        schedules = listOf(
            EventSchedule("January 8, 2026", "9:00 AM - 5:00 PM", "Main Office")
        )
    ),
    CommunityEvent(
        id = 5,
        title = "Event",
        timeRange = "Fri, Jan 5 9:00 AM – 5:00 PM",
        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit.",
        schedules = listOf(
            EventSchedule("January 9, 2026", "8:00 AM - 12:00 PM", "City Hall")
        )
    ),
    CommunityEvent(
        id = 6,
        title = "Event",
        timeRange = "Fri, Jan 5 9:00 AM – 5:00 PM",
        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit.",
        schedules = listOf(
            EventSchedule("January 10, 2026", "9:00 AM - 5:00 PM", "Plaza Area")
        )
    )
)

// Reactive list of calendar events
val calendarEvents = mutableStateListOf<CalendarEvent>(
    CalendarEvent(1, "Community Meeting", "2026-01-05", "9:00 AM", "12:00 PM", "Barangay Hall", "Monthly community gathering"),
    CalendarEvent(2, "Health Seminar", "2026-01-05", "2:00 PM", "5:00 PM", "Community Center", "Health and wellness discussion"),
    CalendarEvent(3, "Sports Event", "2026-01-06", "10:00 AM", "3:00 PM", "Barangay Gym", "Basketball tournament"),
    CalendarEvent(4, "Workshop", "2026-01-08", "1:00 PM", "4:00 PM", "Main Office", "Skills training workshop"),
    CalendarEvent(5, "Town Hall", "2026-01-12", "9:00 AM", "11:00 AM", "City Hall", "Public consultation"),
    CalendarEvent(6, "Festival", "2026-01-15", "8:00 AM", "6:00 PM", "Plaza Area", "Annual community festival")
)

val availableFacilities = listOf(
    Facility(1, "Chapel", "⛪"),
    Facility(2, "Basketball Court", "🏀"),
    Facility(3, "Multipurpose Hall", "🏛️"),
    Facility(4, "Tennis Court", "🎾")
)

val timeSlots = listOf(
    "8:00 AM - 9:00 AM", "9:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 NN",
    "12:00 NN - 1:00 PM", "1:00 PM - 2:00 PM", "2:00 PM - 3:00 PM", "3:00 PM - 4:00 PM",
    "4:00 PM - 5:00 PM", "5:00 PM - 6:00 PM", "6:00 PM - 7:00 PM", "7:00 PM - 8:00 PM",
    "8:00 PM - 9:00 PM", "9:00 PM - 10:00 PM", "10:00 PM - 11:00 PM"
)

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
fun LoginScreen(onLoginSuccess: (User) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightLavender),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Login to your account",
                    fontSize = 14.sp,
                    color = MediumGray
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DarkBlueGray,
                        unfocusedBorderColor = LightLavender,
                        focusedLabelColor = DarkBlueGray
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = DeepNavy
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DarkBlueGray,
                        unfocusedBorderColor = LightLavender,
                        focusedLabelColor = DarkBlueGray
                    ),
                    singleLine = true
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val user = UserRepository.authenticate(username, password)
                        if (user != null) {
                            onLoginSuccess(user)
                        } else {
                            errorMessage = "Invalid username or password"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlueGray),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
fun HomeScreen() {
    var selectedEvent by remember { mutableStateOf<CommunityEvent?>(null) }
    val listState = rememberLazyListState()

    val scrollPercentage by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo

            if (visibleItemsInfo.isEmpty()) {
                0f
            } else {
                val firstVisibleItem = visibleItemsInfo.first()
                val firstItemOffset = firstVisibleItem.offset
                val firstItemIndex = firstVisibleItem.index

                val totalItemsCount = layoutInfo.totalItemsCount
                val viewportHeight = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset

                val averageItemHeight = if (visibleItemsInfo.isNotEmpty()) {
                    visibleItemsInfo.sumOf { it.size } / visibleItemsInfo.size
                } else {
                    1
                }

                val totalContentHeight = totalItemsCount * averageItemHeight
                val maxScrollOffset = (totalContentHeight - viewportHeight).coerceAtLeast(1)

                val currentScrollOffset = (firstItemIndex * averageItemHeight - firstItemOffset).coerceAtLeast(0)

                (currentScrollOffset.toFloat() / maxScrollOffset).coerceIn(0f, 1f)
            }
        }
    }

    val showScrollbar by remember {
        derivedStateOf {
            listState.layoutInfo.totalItemsCount > 0 &&
                    (listState.canScrollForward || listState.canScrollBackward)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(LightLavender)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Home",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Community Events",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = MediumGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            items(sampleEvents) { event ->
                EventCard(
                    event = event,
                    onClick = { selectedEvent = event },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }
        }

        if (showScrollbar) {
            val trackHeight = 300f
            val thumbHeight = 80f

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .width(4.dp)
                    .height(trackHeight.dp)
                    .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(2.dp))
            ) {
                Box(
                    modifier = Modifier
                        .height(thumbHeight.dp)
                        .fillMaxWidth()
                        .offset(y = ((trackHeight - thumbHeight) * scrollPercentage).dp)
                        .background(DarkBlueGray, RoundedCornerShape(2.dp))
                )
            }
        }

        selectedEvent?.let { event ->
            EventScheduleDialog(
                event = event,
                onDismiss = { selectedEvent = null }
            )
        }
    }
}

@Composable
fun EventCard(
    event: CommunityEvent,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = event.timeRange,
                    fontSize = 11.sp,
                    color = MediumGray,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = event.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy,
                    maxLines = 1
                )
            }

            Text(
                text = event.description,
                fontSize = 13.sp,
                color = DeepNavy.copy(alpha = 0.6f),
                lineHeight = 18.sp,
                maxLines = 2
            )
        }
    }
}

@Composable
fun EventScheduleDialog(
    event: CommunityEvent,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = DeepNavy
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Available Schedules",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DeepNavy
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    event.schedules.forEach { schedule ->
                        ScheduleItem(schedule)
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleItem(schedule: EventSchedule) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightLavender
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = schedule.date,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy
                )

                Text(
                    text = schedule.time,
                    fontSize = 12.sp,
                    color = MediumGray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = schedule.venue,
                fontSize = 12.sp,
                color = DeepNavy.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun Reservation(user: User) {
    var viewMode by remember { mutableStateOf("month") }
    var selectedDate by remember { mutableIntStateOf(5) }
    var selectedMonth by remember { mutableIntStateOf(0) }
    var selectedYear by remember { mutableIntStateOf(2026) }
    var showEventDetails by remember { mutableStateOf(false) }
    var selectedEvents by remember { mutableStateOf(emptyList<CalendarEvent>()) }
    var showScheduleDialog by remember { mutableStateOf(false) }

    val monthNames = listOf("January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")

    // Helper function to get events for a specific date
    fun getEventsForDate(year: Int, month: Int, day: Int): List<CalendarEvent> {
        val dateString = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day)
        return calendarEvents.filter { it.date == dateString }
    }

    // Helper function to get days in month
    fun getDaysInMonth(month: Int, year: Int): Int {
        return when (month) {
            0, 2, 4, 6, 7, 9, 11 -> 31
            3, 5, 8, 10 -> 30
            1 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            else -> 30
        }
    }

    // Helper function to get first day of week (0 = Sunday)
    fun getFirstDayOfMonth(month: Int, year: Int): Int {
        val cal = java.util.Calendar.getInstance()
        cal.set(year, month, 1)
        return cal.get(java.util.Calendar.DAY_OF_WEEK) - 1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightLavender)
    ) {
        // Top bar with year selector and view mode
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Year selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                IconButton(
                    onClick = { selectedYear-- },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("<", color = DeepNavy, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Text(
                    text = selectedYear.toString(),
                    color = DeepNavy,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                IconButton(
                    onClick = { selectedYear++ },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text(">", color = DeepNavy, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            // View mode selector
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { viewMode = "month" },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            if (viewMode == "month") DarkBlueGray else Color.White,
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Month view",
                        tint = if (viewMode == "month") Color.White else DeepNavy
                    )
                }
            }
        }

        if (viewMode == "month") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightLavender)
            ) {
                // Month name
                Text(
                    text = monthNames[selectedMonth],
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp)
                )

                // Days of week header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                        Text(
                            text = day,
                            color = MediumGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                val daysInMonth = getDaysInMonth(selectedMonth, selectedYear)
                val firstDayOfWeek = getFirstDayOfMonth(selectedMonth, selectedYear)

                // Calendar grid
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (week in 0..5) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (day in 0..6) {
                                val dayNumber = week * 7 + day - firstDayOfWeek + 1
                                val hasEvents = if (dayNumber in 1..daysInMonth) {
                                    getEventsForDate(selectedYear, selectedMonth, dayNumber).isNotEmpty()
                                } else false

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(2.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            when {
                                                dayNumber == selectedDate && dayNumber in 1..daysInMonth -> DarkBlueGray
                                                dayNumber in 1..daysInMonth -> Color.White
                                                else -> Color.Transparent
                                            }
                                        )
                                        .clickable(enabled = dayNumber in 1..daysInMonth) {
                                            selectedDate = dayNumber
                                            val events = getEventsForDate(selectedYear, selectedMonth, dayNumber)
                                            if (events.isNotEmpty()) {
                                                selectedEvents = events
                                                showEventDetails = true
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (dayNumber in 1..daysInMonth) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = dayNumber.toString(),
                                                color = if (dayNumber == selectedDate) Color.White else DeepNavy,
                                                fontSize = 14.sp,
                                                fontWeight = if (dayNumber == selectedDate) FontWeight.Bold else FontWeight.Normal
                                            )
                                            if (hasEvents) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(4.dp)
                                                        .clip(CircleShape)
                                                        .background(
                                                            if (dayNumber == selectedDate) Color.White else DarkBlueGray
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Month navigation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (selectedMonth == 0) {
                                selectedMonth = 11
                                selectedYear--
                            } else {
                                selectedMonth--
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Previous", color = DeepNavy, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (selectedMonth == 11) {
                                selectedMonth = 0
                                selectedYear++
                            } else {
                                selectedMonth++
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkBlueGray),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Next", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                // Schedule Button
                Button(
                    onClick = { showScheduleDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlueGray),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Schedule Facility", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Event details dialog
        if (showEventDetails && selectedEvents.isNotEmpty()) {
            CalendarEventDialog(
                date = String.format(Locale.US, "%s %d, %d", monthNames[selectedMonth], selectedDate, selectedYear),
                events = selectedEvents,
                onDismiss = { showEventDetails = false }
            )
        }

        // Schedule dialog
        if (showScheduleDialog) {
            ScheduleFacilityDialog(
                user = user,
                selectedDate = selectedDate,
                selectedMonth = selectedMonth,
                selectedYear = selectedYear,
                monthNames = monthNames,
                onDismiss = { showScheduleDialog = false }
            )
        }
    }
}

@Composable
fun CalendarEventDialog(
    date: String,
    events: List<CalendarEvent>,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Events",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Text(
                            text = date,
                            fontSize = 14.sp,
                            color = MediumGray
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = DeepNavy
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    events.forEach { event ->
                        CalendarEventItem(event)
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarEventItem(event: CalendarEvent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightLavender
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = event.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DeepNavy
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        tint = DarkBlueGray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${event.startTime} - ${event.endTime}",
                        fontSize = 12.sp,
                        color = MediumGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = event.venue,
                fontSize = 12.sp,
                color = DeepNavy.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = event.description,
                fontSize = 12.sp,
                color = DeepNavy.copy(alpha = 0.6f),
                lineHeight = 16.sp
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleFacilityDialog(
    user: User,
    selectedDate: Int,
    selectedMonth: Int,
    selectedYear: Int,
    monthNames: List<String>,
    onDismiss: () -> Unit
) {
    var selectedFacility by remember { mutableStateOf<Facility?>(null) }
    var selectedTimeSlots by remember { mutableStateOf(setOf<String>()) }
    var clientName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.95f) // Made it slightly wider
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Book a Facility",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Text(
                            text = String.format(Locale.US, "%s %d, %d", monthNames[selectedMonth], selectedDate, selectedYear),
                            fontSize = 14.sp,
                            color = MediumGray
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = DeepNavy
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Name and Phone fields
                Text("Client Name", fontSize = 12.sp, color = DeepNavy, fontWeight = FontWeight.Medium)
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE8EBFA),
                        unfocusedContainerColor = Color(0xFFE8EBFA),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Phone Number", fontSize = 12.sp, color = DeepNavy, fontWeight = FontWeight.Medium)
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE8EBFA),
                        unfocusedContainerColor = Color(0xFFE8EBFA),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Select Facility", fontSize = 12.sp, color = DeepNavy, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                
                // Facility selection using Text buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    availableFacilities.forEach { facility ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .clickable { selectedFacility = facility },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedFacility == facility) DarkBlueGray else Color(0xFFE8EBFA)
                            )
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = facility.name, 
                                    fontSize = 9.sp, 
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedFacility == facility) Color.White else DeepNavy,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 10.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Available Time Slots Box
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            "Available Time Slots",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier.height(180.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(timeSlots) { slot ->
                                val isSelected = selectedTimeSlots.contains(slot)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            if (isSelected) Color(0xFF1E88E5) else Color(0xFFE0F2F1)
                                        )
                                        .clickable {
                                            selectedTimeSlots = if (isSelected) {
                                                selectedTimeSlots - slot
                                            } else {
                                                selectedTimeSlots + slot
                                            }
                                        }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = slot,
                                        fontSize = 9.sp,
                                        color = if (isSelected) Color.White else Color(0xFF2E7D32),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Close", color = Color.DarkGray)
                    }

                    Button(
                        onClick = {
                            if (selectedFacility != null && selectedTimeSlots.isNotEmpty()) {
                                val sortedSlots = selectedTimeSlots.toList().sorted()
                                val newEvent = CalendarEvent(
                                    id = calendarEvents.size + 1,
                                    title = "${selectedFacility!!.name} - $clientName",
                                    date = String.format(Locale.US, "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDate),
                                    startTime = sortedSlots.first().split(" - ").first(),
                                    endTime = sortedSlots.last().split(" - ").last(),
                                    venue = selectedFacility!!.name,
                                    description = "Contact: $phoneNumber"
                                )
                                calendarEvents.add(newEvent)
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f).height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Confirm", color = Color.White)
                    }
                }
            }
        }
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
fun Account(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightLavender)
            .padding(20.dp)
    ) {
        Text(
            text = "My Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DeepNavy,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8EBFA)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👤",
                            fontSize = 32.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = user.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Text(
                            text = user.role,
                            fontSize = 14.sp,
                            color = MediumGray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFB5B9D8))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = DeepNavy,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Login Credentials",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepNavy
                    )
                }

                AccountTextField(
                    label = "Username",
                    value = user.username
                )

                Spacer(modifier = Modifier.height(12.dp))

                AccountTextField(
                    label = "Email",
                    value = user.email
                )

                Spacer(modifier = Modifier.height(12.dp))

                AccountPasswordField(
                    label = "Password",
                    value = user.password
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFB5B9D8))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = DeepNavy,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Personal Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepNavy
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFD8DBFA).copy(alpha = 0.5f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        PersonalInfoRow("Name:", user.name)
                        Spacer(modifier = Modifier.height(8.dp))
                        PersonalInfoRow("Contact Num:", user.contactNum)
                        Spacer(modifier = Modifier.height(8.dp))
                        PersonalInfoRow("Address:", user.address)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountTextField(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = DeepNavy.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFD8DBFA).copy(alpha = 0.5f)
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                color = DeepNavy,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun AccountPasswordField(
    label: String,
    value: String
) {
    var showPassword by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = DeepNavy.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFD8DBFA).copy(alpha = 0.5f)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showPassword) value else "••••••••••••••••",
                    fontSize = 14.sp,
                    color = DeepNavy
                )
                IconButton(
                    onClick = { showPassword = !showPassword },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (showPassword) "Hide password" else "Show password",
                        tint = DeepNavy,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PersonalInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = DeepNavy.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = DeepNavy,
            fontWeight = FontWeight.Normal
        )
    }
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