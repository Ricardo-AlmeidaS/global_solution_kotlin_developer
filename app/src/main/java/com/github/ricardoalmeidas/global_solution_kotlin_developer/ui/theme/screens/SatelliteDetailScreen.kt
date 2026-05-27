package com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.ricardoalmeidas.global_solution_kotlin_developer.model.Satellite
import com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.Global_solution_kotlin_developerTheme
import com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.screens.InfoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SatelliteDetailScreen(
    navController: NavController,
    satellite: Satellite
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = satellite.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = satellite.type,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = satellite.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Dados Orbitais",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    InfoItem(label = "País / Agência", value = satellite.country)
                    InfoItem(label = "Status Operacional", value = satellite.status)
                    InfoItem(label = "Altitude Orbital", value = "${satellite.altitudeKm} km")
                    InfoItem(label = "Inclinação Orbital", value = "${satellite.inclination}°")
                    InfoItem(label = "Ano de Lançamento", value = satellite.launchYear.toString())
                    InfoItem(
                        label = "Tipo de Órbita",
                        value = when {
                            satellite.altitudeKm < 2000  -> "Baixa Órbita (LEO)"
                            satellite.altitudeKm < 20000 -> "Órbita Média (MEO)"
                            else                         -> "Geoestacionária (GEO)"
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(text = "Voltar", style = MaterialTheme.typography.titleSmall)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SatelliteDetailScreenPreview() {
    Global_solution_kotlin_developerTheme {
        SatelliteDetailScreen(
            navController = rememberNavController(),
            satellite = Satellite(
                id = "SAT001",
                name = "CBERS-4A",
                type = "Observação Terrestre",
                country = "Brasil / China",
                altitudeKm = 628,
                inclination = 98.4,
                launchYear = 2019,
                status = "Ativo",
                description = "Satélite sino-brasileiro de recursos terrestres. Monitora biomas, recursos hídricos e desastres naturais com câmeras de alta resolução."
            )
        )
    }
}
