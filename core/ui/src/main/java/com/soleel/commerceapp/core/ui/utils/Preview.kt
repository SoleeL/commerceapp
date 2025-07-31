package com.soleel.commerceapp.core.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(
    name = "Smartphone 420dpi",
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420"
)
annotation class LongDevicePreview

@Preview(
    name = "Smartphone - 320dpi",
    showBackground = true,
    device = "spec:width=360dp,height=640dp,dpi=320"
)
annotation class ShortDevicePreview

@Preview(
    name = "Tablet",
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
annotation class TabletPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithFakeTopAppBar(content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("payment app")
                },
                modifier = Modifier.background(Color.DarkGray)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
                content = { content() }
            )
        }
    )
}

@Composable
fun WithFakeSystemBars(content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Fake status bar con íconos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(Color(0xFF121212))
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hora (simulada)
            Text(
                text = "12:30",
                color = Color.White,
                fontSize = 12.sp
            )

            // Íconos de estado simulados
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Señal",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Wi-Fi",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Batería",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Contenido principal
        Box(modifier = Modifier.weight(1f)) {
            content()
        }

        // Fake navigation bar con íconos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFF121212))
                .padding(horizontal = 96.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Atrás",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Recientes",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}