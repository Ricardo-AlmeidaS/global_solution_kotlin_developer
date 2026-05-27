package com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.ricardoalmeidas.global_solution_kotlin_developer.model.SpaceAlert
import com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.Global_solution_kotlin_developerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(
    navController: NavController,
    alerts: List<SpaceAlert>
) {
    val filterOptions = listOf("Todos", "Crítico", "Alto", "Médio", "Baixo")
    var selectedFilter by remember { mutableStateOf("Todos") }

    val filteredAlerts = if (selectedFilter == "Todos") {
        alerts
    } else {
        alerts.filter { it.severity == selectedFilter }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Alertas (${filteredAlerts.size})") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filterOptions) { option ->
                    ElevatedFilterChip(
                        selected = selectedFilter == option,
                        onClick = { selectedFilter = option },
                        label = { Text(text = option) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                if (filteredAlerts.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum alerta para o filtro selecionado.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    items(filteredAlerts) { alert ->
                        AlertItem(alert = alert)
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertsScreenPreview() {
    Global_solution_kotlin_developerTheme {
        AlertsScreen(
            navController = rememberNavController(),
            alerts = listOf(
                SpaceAlert(
                    id = "ALT001",
                    title = "Seca Extrema Detectada",
                    description = "Déficit hídrico crítico no semiárido nordestino.",
                    severity = "Crítico",
                    region = "Nordeste, Brasil",
                    timestamp = "Hoje, 08:14",
                    satelliteName = "CBERS-4A"
                ),
                SpaceAlert(
                    id = "ALT002",
                    title = "Foco de Desmatamento",
                    description = "Área de supressão vegetal detectada no Pará.",
                    severity = "Alto",
                    region = "Pará, Brasil",
                    timestamp = "Hoje, 06:32",
                    satelliteName = "Amazônia-1"
                )
            )
        )
    }
}
