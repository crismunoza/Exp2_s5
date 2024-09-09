package com.example.s5sum2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.s5sum2.ui.theme.S5sum2Theme

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S5sum2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MenuScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fondo gris claro
    ) {
        // Esquinas superiores e inferiores
        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopStart)
                .background(Color(0xFF89BDE0)) // Color azul
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .background(Color(0xFFDAE089)) // Color naranja
        )

        // Contenido principal
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Menú Principal",
                fontSize = 24.sp,
                color = Color(0xFF64B5F6), // Color del texto
                modifier = Modifier.padding(bottom = 32.dp)
            )

            NotificationButton(text = "Notificación 1")
            Spacer(modifier = Modifier.height(16.dp))
            NotificationButton(text = "Notificación 2")
            Spacer(modifier = Modifier.height(16.dp))
            NotificationButton(text = "Notificación 3")
        }
    }
}

@Composable
fun NotificationButton(text: String) {
    Button(
        onClick = { /* Acciones para cada botón */ },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2DFDB)), // Color verde claro
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(48.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    S5sum2Theme {
        MenuScreen()
    }
}
