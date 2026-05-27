package com.github.ricardoalmeidas.global_solution_kotlin_developer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ricardoalmeidas.global_solution_kotlin_developer.model.Satellite
import com.github.ricardoalmeidas.global_solution_kotlin_developer.model.SpaceAlert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SkyTrackUiState {
    object Initial : SkyTrackUiState()
    object Loading : SkyTrackUiState()
    data class Success(
        val satellites: List<Satellite>,
        val alerts: List<SpaceAlert>
    ) : SkyTrackUiState()
    data class Error(val message: String) : SkyTrackUiState()
}

class SkyTrackViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SkyTrackUiState>(SkyTrackUiState.Initial)
    val uiState: StateFlow<SkyTrackUiState> = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            _uiState.value = SkyTrackUiState.Loading
            try {
                val satellites = getMockSatellites()
                val alerts = getMockAlerts()
                _uiState.value = SkyTrackUiState.Success(satellites, alerts)
            } catch (e: Exception) {
                _uiState.value = SkyTrackUiState.Error("Falha ao carregar dados: ${e.message}")
            }
        }
    }

    fun resetToInitial() {
        _uiState.value = SkyTrackUiState.Initial
    }

    private fun getMockSatellites(): List<Satellite> {
        return listOf(
            Satellite(
                id = "SAT001",
                name = "CBERS-4A",
                type = "Observação Terrestre",
                country = "Brasil / China",
                altitudeKm = 628,
                inclination = 98.4,
                launchYear = 2019,
                status = "Ativo",
                description = "Satélite sino-brasileiro de recursos terrestres. Monitora biomas, recursos hídricos e desastres naturais com câmeras de alta resolução."
            ),
            Satellite(
                id = "SAT002",
                name = "Amazônia-1",
                type = "Observação Terrestre",
                country = "Brasil",
                altitudeKm = 752,
                inclination = 98.4,
                launchYear = 2021,
                status = "Ativo",
                description = "Primeiro satélite de observação da Terra totalmente desenvolvido pelo Brasil. Monitora o desmatamento da Amazônia e outros biomas."
            ),
            Satellite(
                id = "SAT003",
                name = "GOES-16",
                type = "Meteorológico",
                country = "EUA",
                altitudeKm = 35786,
                inclination = 0.0,
                launchYear = 2016,
                status = "Ativo",
                description = "Satélite geoestacionário que cobre a América do Sul. Fornece imagens de alta resolução para previsão de tempo e alertas climáticos."
            ),
            Satellite(
                id = "SAT004",
                name = "Sentinel-2B",
                type = "Agrícola",
                country = "ESA / Europa",
                altitudeKm = 786,
                inclination = 98.6,
                launchYear = 2017,
                status = "Ativo",
                description = "Satélite da ESA especializado em monitoramento agrícola. Fornece dados multiespectrais para análise de safras, solo e vegetação."
            ),
            Satellite(
                id = "SAT005",
                name = "Starlink G4-36",
                type = "Comunicação",
                country = "EUA / SpaceX",
                altitudeKm = 550,
                inclination = 53.0,
                launchYear = 2022,
                status = "Ativo",
                description = "Constelação de satélites de internet de baixa latência. Provê conectividade de banda larga para regiões remotas e zonas rurais."
            ),
            Satellite(
                id = "SAT006",
                name = "GPS IIF-12",
                type = "Navegação",
                country = "EUA",
                altitudeKm = 20200,
                inclination = 55.0,
                launchYear = 2016,
                status = "Ativo",
                description = "Satélite do sistema de navegação global GPS. Suporta posicionamento de precisão para aviação, logística e agronegócio."
            ),
            Satellite(
                id = "SAT007",
                name = "INPE-SCD2",
                type = "Pesquisa",
                country = "Brasil",
                altitudeKm = 745,
                inclination = 25.0,
                launchYear = 1998,
                status = "Standby",
                description = "Satélite científico brasileiro de coleta de dados ambientais. Recebe informações de plataformas terrestres sobre clima e hidrologia."
            ),
            Satellite(
                id = "SAT008",
                name = "Landsat 9",
                type = "Observação Terrestre",
                country = "EUA",
                altitudeKm = 705,
                inclination = 98.2,
                launchYear = 2021,
                status = "Ativo",
                description = "Satélite da NASA/USGS para observação da Terra. Cobre todo o globo em 16 dias com imagens multiespectrais para análise ambiental."
            )
        )
    }

    private fun getMockAlerts(): List<SpaceAlert> {
        return listOf(
            SpaceAlert(
                id = "ALT001",
                title = "Seca Extrema Detectada",
                description = "Imagens do CBERS-4A identificam déficit hídrico crítico no semiárido nordestino. Índice de vegetação (NDVI) abaixo de 0.2 em 3 estados.",
                severity = "Crítico",
                region = "Nordeste, Brasil",
                timestamp = "Hoje, 08:14",
                satelliteName = "CBERS-4A"
            ),
            SpaceAlert(
                id = "ALT002",
                title = "Foco de Desmatamento",
                description = "Amazônia-1 detectou área de supressão vegetal de aproximadamente 420 km² no Pará. Alerta encaminhado ao IBAMA.",
                severity = "Alto",
                region = "Pará, Brasil",
                timestamp = "Hoje, 06:32",
                satelliteName = "Amazônia-1"
            ),
            SpaceAlert(
                id = "ALT003",
                title = "Tempestade Tropical em Formação",
                description = "GOES-16 registra organização convectiva no Atlântico Sul. Sistema pode se intensificar nas próximas 48h.",
                severity = "Alto",
                region = "Atlântico Sul",
                timestamp = "Hoje, 05:50",
                satelliteName = "GOES-16"
            ),
            SpaceAlert(
                id = "ALT004",
                title = "Anomalia em Lavouras de Soja",
                description = "Sentinel-2B detectou anomalias espectrais em lavouras do Mato Grosso indicando possível infestação de pragas. Área afetada: ~180 km².",
                severity = "Médio",
                region = "Mato Grosso, Brasil",
                timestamp = "Ontem, 14:21",
                satelliteName = "Sentinel-2B"
            ),
            SpaceAlert(
                id = "ALT005",
                title = "Incêndio Florestal Confirmado",
                description = "CBERS-4A e Landsat 9 confirmam foco ativo de incêndio no Cerrado. Frentes de fogo em expansão com vento favorecendo propagação.",
                severity = "Crítico",
                region = "Goiás, Brasil",
                timestamp = "Ontem, 11:05",
                satelliteName = "CBERS-4A"
            ),
            SpaceAlert(
                id = "ALT006",
                title = "Excesso Hídrico em Área Agrícola",
                description = "Sentinel-2B identificou inundação em áreas de cultivo no Sul do Brasil. Prejuízo estimado a culturas de milho e trigo.",
                severity = "Médio",
                region = "Rio Grande do Sul",
                timestamp = "Ontem, 09:30",
                satelliteName = "Sentinel-2B"
            ),
            SpaceAlert(
                id = "ALT007",
                title = "Condições Climáticas Estáveis",
                description = "GOES-16 reporta condições climáticas dentro da normalidade na Região Sudeste. Sem anomalias significativas nas últimas 24h.",
                severity = "Baixo",
                region = "Sudeste, Brasil",
                timestamp = "2 dias atrás",
                satelliteName = "GOES-16"
            )
        )
    }
}
