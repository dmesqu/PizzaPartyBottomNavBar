package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@Composable
fun NavigationGraph(
    navController: NavHostController,
    onBottomBarVisibilityChanged: (Boolean) -> Unit
) {
    NavHost(navController, startDestination = BottomNavigationItems.Welcome.route) {
        composable(BottomNavigationItems.Welcome.route) {
            onBottomBarVisibilityChanged(false)
            SplashScreen(navController = navController)
        }
        composable(BottomNavigationItems.PizzaScreen.route) {
            onBottomBarVisibilityChanged(true)
            PizzaPartyScreen()
        }
        composable(BottomNavigationItems.GpaAppScreen.route) {
            onBottomBarVisibilityChanged(true)
            GpaAppScreen()
        }
        composable(BottomNavigationItems.Screen3.route) {
            onBottomBarVisibilityChanged(true)
            Screen3()
        }
    }
}

// my solution todo8: drawer with styled sheet, icons, selected highlight, dynamic title, snackbar, and about dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraphWithDrawer(
    navController: NavHostController,
    onBottomBarVisibilityChanged: (Boolean) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val destinations = listOf(
        BottomNavigationItems.PizzaScreen,
        BottomNavigationItems.GpaAppScreen,
        BottomNavigationItems.Screen3
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val currentItem = destinations.firstOrNull { it.route == currentRoute }
    // my solution todo8: snackbar feedback for navigation actions
    val snackbarHostState = remember { SnackbarHostState() }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // my solution todo8: themed rounded drawer
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "Pizza Party",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                Divider()
                var showAbout by remember { mutableStateOf(false) }
                // my solution todo8: simple About drawer item
                NavigationDrawerItem(
                    label = { Text("About") },
                    selected = false,
                    onClick = { showAbout = true },
                    icon = { Icon(Icons.Filled.Menu, contentDescription = null) },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Divider()
                // my solution todo8: main destinations with icon + selected highlight
                destinations.forEach { item ->
                    val selected = currentRoute == item.route
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title ?: item.route,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        selected = selected,
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (!selected) {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    popUpTo(BottomNavigationItems.Welcome.route) { inclusive = false }
                                }
                                scope.launch {
                                    snackbarHostState.showSnackbar("Navigated to ${item.title ?: item.route}")
                                }
                            }
                        },
                        icon = { item.icon?.let { Icon(it, contentDescription = item.title) } },
                        modifier = Modifier.padding(horizontal = 12.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
                Spacer(Modifier.padding(8.dp))
                if (showAbout) {
                    AlertDialog(
                        onDismissRequest = { showAbout = false },
                        confirmButton = {
                            TextButton(onClick = { showAbout = false }) { Text("OK") }
                        },
                        title = { Text("About") },
                        text = { Text("Week 8 Assignment for CSC371.To get extra credit in ToDo 8 I added a snackbar that gives feedback when navigating, adding icons to the drawer, highlighting the current location, adding a top bar title to each app, and the about dialog your reading this on right now!") }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                // my solution todo8: dynamic top app bar title + menu icon
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = currentItem?.title
                                ?: if (currentRoute == BottomNavigationItems.Welcome.route) "Welcome"
                                else "Pizza Party"
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationGraph(
                    navController = navController,
                    onBottomBarVisibilityChanged = onBottomBarVisibilityChanged
                )
            }
        }
    }
}
