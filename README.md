# 🛰️ OrbitWatch

Aplicativo Android de monitoramento espacial desenvolvido em **Kotlin** com **Jetpack Compose**. O OrbitWatch exibe satélites em órbita e alertas ambientais detectados por eles, como secas, desmatamentos, tempestades e incêndios.

---

## Sobre o Projeto

O OrbitWatch simula um painel de controle para acompanhamento de satélites brasileiros e internacionais. O app apresenta dados de 8 satélites reais (CBERS-4A, Amazônia-1, GOES-16, entre outros) e alertas ambientais gerados a partir das imagens capturadas por eles.

Os dados são simulados localmente (mock), mas a estrutura já está preparada para integração com uma API real via Retrofit.

---

## Tecnologias Utilizadas

| Tecnologia | Finalidade |
|---|---|
| Kotlin | Linguagem principal |
| Jetpack Compose | Interface visual declarativa |
| Navigation Compose | Navegação entre telas |
| ViewModel + StateFlow | Gerenciamento de estado |
| Material Design 3 | Componentes visuais |
| Retrofit + Gson | Preparado para integração com API |

---

## Telas do Aplicativo

### Tela 1 — Inicial

A primeira tela que o usuário vê ao abrir o app. Apresenta o nome e a proposta do aplicativo com um botão central para carregar os dados.

**O que tem nessa tela:**
- Ícone de satélite e descrição do app
- Botão **"Carregar Dados"** para iniciar o carregamento
- Indicador de carregamento (spinner) enquanto os dados são buscados
- Mensagem de erro com botão de nova tentativa, caso algo falhe

<img width="375" height="832" alt="image" src="https://github.com/user-attachments/assets/3f7a5327-b9b0-4af7-9238-34933d9d98da" />


---

### Tela 2 — Painel Principal

Exibida após o carregamento dos dados. Mostra um resumo dos alertas mais recentes e dos satélites monitorados, tudo em uma única tela sem rolagem.

**O que tem nessa tela:**
- Seção **"Alertas Recentes"** com os 2 alertas mais recentes, mostrando título, região, horário e nível de severidade (Crítico, Alto, Médio, Baixo)
- Seção **"Satélites Monitorados"** com 2 satélites, mostrando nome, tipo, país e status operacional
- Botão **"Detalhes"** em cada satélite para ver mais informações
- Botão **"Voltar à Tela Inicial"** no rodapé

<img width="377" height="833" alt="image" src="https://github.com/user-attachments/assets/e9cb77af-d0bc-4b68-b6bd-56fed800fe79" />

---

### Tela 3 — Detalhes do Satélite

Acessada ao tocar em **"Detalhes"** em um satélite. Exibe todas as informações técnicas e operacionais daquele satélite.

**O que tem nessa tela:**
- Nome e tipo do satélite no cabeçalho
- Descrição sobre a função e missão do satélite
- Card com **dados orbitais**:
  - País / Agência responsável
  - Status operacional (Ativo / Standby)
  - Altitude orbital (em km)
  - Inclinação orbital (em graus)
  - Ano de lançamento
  - Tipo de órbita (LEO, MEO ou GEO)
- Botão **"Voltar"** para retornar ao painel

<img width="376" height="831" alt="image" src="https://github.com/user-attachments/assets/de76a5db-b478-4c65-9fd6-f2ce53803b78" />

---

## Como Executar

### Pré-requisitos

- Android Studio instalado
- JDK 11 ou superior
- Dispositivo físico ou emulador Android (API 24 ou superior, recomendado 4 GB de RAM)

### Passos

1. Clone ou baixe o repositório
2. Abra a pasta no Android Studio
3. Aguarde a sincronização do Gradle
4. Conecte um dispositivo ou inicie um emulador
5. Clique em **Run** (▶) ou execute:

```bash
./gradlew installDebug
```

---

## Satélites Disponíveis

| Satélite | Tipo | País | Altitude |
|---|---|---|---|
| CBERS-4A | Observação Terrestre | Brasil / China | 628 km |
| Amazônia-1 | Observação Terrestre | Brasil | 752 km |
| GOES-16 | Meteorológico | EUA | 35.786 km |
| Sentinel-2B | Agrícola | ESA / Europa | 786 km |
| Starlink G4-36 | Comunicação | EUA / SpaceX | 550 km |
| GPS IIF-12 | Navegação | EUA | 20.200 km |
| INPE-SCD2 | Pesquisa | Brasil | 745 km |
| Landsat 9 | Observação Terrestre | EUA | 705 km |

---

## Integrantes

| Nome | RM |
|---|---|
| Carolina Barbosa Pacifico de Almeida| 555000 |
|  Ricardo Henrique de Almeida Santos | 557093 |
