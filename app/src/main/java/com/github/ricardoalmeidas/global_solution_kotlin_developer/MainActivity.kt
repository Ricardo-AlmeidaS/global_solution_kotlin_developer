package com.github.ricardoalmeidas.global_solution_kotlin_developer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.Global_solution_kotlin_developerTheme
import com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.screens.AlertsScreen
import com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.screens.SkyTrackScreen
import com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.screens.SatelliteDetailScreen
import com.github.ricardoalmeidas.global_solution_kotlin_developer.viewmodel.SkyTrackUiState
import com.github.ricardoalmeidas.global_solution_kotlin_developer.viewmodel.SkyTrackViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SkyTrackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Global_solution_kotlin_developerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val navController = rememberNavController()
                    val uiState by viewModel.uiState.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = "orbitwatch"
                    ) {

                        // Tela 1: tela principal (home + loading + erro)
                        composable("orbitwatch") {
                            SkyTrackScreen(
                                viewModel = viewModel,
                                onSatelliteClick = { index ->
                                    navController.navigate("satellite/$index")
                                }
                            )
                        }

                        // Tela 2: detalhe do satélite
                        composable(
                            route = "satellite/{index}",
                            arguments = listOf(
                                navArgument("index") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("index") ?: 0
                            val satellites = (uiState as? SkyTrackUiState.Success)?.satellites
                            val satellite = satellites?.getOrNull(index)

                            if (satellite != null) {
                                SatelliteDetailScreen(
                                    navController = navController,
                                    satellite = satellite
                                )
                            }
                        }

                        // Tela 3: lista de alertas com filtro por severidade
                        composable(
                            route = "alerts?severity={severity}",
                            arguments = listOf(
                                navArgument("severity") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                            )
                        ) {
                            val alerts = (uiState as? SkyTrackUiState.Success)?.alerts ?: emptyList()
                            AlertsScreen(
                                navController = navController,
                                alerts = alerts
                            )
                        }
                    }
                }
            }
        }
    }
}
