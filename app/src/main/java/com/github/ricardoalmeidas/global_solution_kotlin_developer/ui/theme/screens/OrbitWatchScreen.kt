package com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.ricardoalmeidas.global_solution_kotlin_developer.model.Satellite
import com.github.ricardoalmeidas.global_solution_kotlin_developer.model.SpaceAlert
import com.github.ricardoalmeidas.global_solution_kotlin_developer.ui.theme.Global_solution_kotlin_developerTheme
import com.github.ricardoalmeidas.global_solution_kotlin_developer.viewmodel.OrbitWatchUiState
import com.github.ricardoalmeidas.global_solution_kotlin_developer.viewmodel.OrbitWatchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrbitWatchScreen(
    viewModel: OrbitWatchViewModel,
    onSatelliteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {})
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val state = uiState) {
                is OrbitWatchUiState.Initial -> {
                    InitialContent(
                        onLoadData = { viewModel.fetchData() }
                    )
                }
                is OrbitWatchUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(56.dp))
                }
                is OrbitWatchUiState.Success -> {
                    OrbitWatchContent(
                        satellites = state.satellites,
                        alerts = state.alerts,
                        onBack = { viewModel.resetToInitial() },
                        onSatelliteClick = onSatelliteClick
                    )
                }
                is OrbitWatchUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.fetchData() }
                    )
                }
            }
        }
    }
}

@Composable
fun InitialContent(onLoadData: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "🛰️",
            fontSize = 120.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Monitoramento espacial\ncom dados satelitais",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Acompanhe satélites em órbita e alertas\nambientais em tempo real",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 26.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onLoadData,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentPadding = ButtonDefaults.ContentPadding
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Carregar Dados",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun OrbitWatchContent(
    satellites: List<Satellite>,
    alerts: List<SpaceAlert>,
    onBack: () -> Unit,
    onSatelliteClick: (Int) -> Unit
) {
    val visibleAlerts = alerts.take(2)
    val visibleSatellites = satellites.take(2)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Alertas Recentes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        visibleAlerts.forEach { alert ->
            AlertItem(alert = alert)
        }

        HorizontalDivider()

        Text(
            text = "Satélites Monitorados",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        visibleSatellites.forEachIndexed { index, satellite ->
            SatelliteItem(
                satellite = satellite,
                onDetailsClick = { onSatelliteClick(index) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(text = "Voltar à Tela Inicial", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun AlertItem(
    alert: SpaceAlert,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = alert.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                SeverityBadge(severity = alert.severity)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "📍 ${alert.region}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "🕐 ${alert.timestamp}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SatelliteItem(
    satellite: Satellite,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = satellite.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = satellite.status,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (satellite.status == "Ativo")
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = satellite.type,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = satellite.country,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onDetailsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Detalhes", fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun SeverityBadge(severity: String) {
    val color = when (severity) {
        "Crítico" -> MaterialTheme.colorScheme.error
        "Alto"    -> MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
        "Médio"   -> MaterialTheme.colorScheme.tertiary
        else      -> MaterialTheme.colorScheme.primary
    }
    Text(
        text = severity,
        style = MaterialTheme.typography.labelLarge,
        color = color,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun InfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "❌", fontSize = 64.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Erro ao carregar dados",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Tentar Novamente", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialContentPreview() {
    Global_solution_kotlin_developerTheme { InitialContent(onLoadData = {}) }
}

@Preview(showBackground = true)
@Composable
fun AlertItemPreview() {
    Global_solution_kotlin_developerTheme {
        AlertItem(
            alert = SpaceAlert(
                id = "ALT001",
                title = "Seca Extrema Detectada",
                description = "Déficit hídrico crítico no semiárido nordestino.",
                severity = "Crítico",
                region = "Nordeste, Brasil",
                timestamp = "Hoje, 08:14",
                satelliteName = "CBERS-4A"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SatelliteItemPreview() {
    Global_solution_kotlin_developerTheme {
        SatelliteItem(
            satellite = Satellite(
                id = "SAT001",
                name = "CBERS-4A",
                type = "Observação Terrestre",
                country = "Brasil / China",
                altitudeKm = 628,
                inclination = 98.4,
                launchYear = 2019,
                status = "Ativo",
                description = "Satélite sino-brasileiro de recursos terrestres."
            ),
            onDetailsClick = {}
        )
    }
}
