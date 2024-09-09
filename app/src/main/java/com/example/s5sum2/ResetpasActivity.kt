package com.example.s5sum2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.s5sum2.ui.theme.S5sum2Theme
import com.google.gson.Gson
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding

class ResetpasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            S5sum2Theme {
                ResetScreen()
            }
        }
    }
}

@Composable
fun ResetScreen() {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("contactos_prefs", Context.MODE_PRIVATE)
    val gson = Gson()

    var emailState by remember { mutableStateOf("") }
    var usernameState by remember { mutableStateOf("") }
    var newPasswordState by remember { mutableStateOf("") }
    var confirmPasswordState by remember { mutableStateOf("") }
    var showInitialFields by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFF89BDE0)) // Color lila
                ) {
                    Canvas(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)) {
                        val path = Path().apply {
                            moveTo(0f, 100f)
                            cubicTo(size.width / 2, 200f, size.width / 2, 0f, size.width, 100f)
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height)
                            close()
                        }
                        drawPath(
                            path = path,
                            color = Color(0xFF89BDE0)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.logo_app),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = -20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (showInitialFields) "Restablece tu Contraseña" else "Cambia tu Contraseña",
                    fontSize = 24.sp,
                    color = Color.White, // Color blanco
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .offset(y = -15.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                if (showInitialFields) {
                    // Campos para correo y nombre de usuario
                    OutlinedTextField(
                        value = emailState,
                        onValueChange = { emailState = it },
                        label = { Text("Ingresa Correo") },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.email), contentDescription = null)
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 50.dp)
                            .fillMaxWidth(0.8f),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = usernameState,
                        onValueChange = { usernameState = it },
                        label = { Text("Ingresa Nombre de Usuario") },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.name), contentDescription = null)
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(0.8f),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val password = obtenerContraseña(emailState, usernameState, sharedPreferences, gson)
                            if (password != null) {
                                showInitialFields = false
                            } else {
                                dialogMessage = "Correo o nombre de usuario no encontrados"
                                showDialog = true
                            }
                        },
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .fillMaxWidth(0.5f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDAE089))
                    ) {
                        Text(text = "Confirmar", color = Color.White)
                    }
                } else {
                    // Campos para nueva contraseña
                    OutlinedTextField(
                        value = newPasswordState,
                        onValueChange = { newPasswordState = it },
                        label = { Text("Nueva Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 50.dp)
                            .fillMaxWidth(0.8f),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = confirmPasswordState,
                        onValueChange = { confirmPasswordState = it },
                        label = { Text("Confirmar Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(0.8f),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (newPasswordState == confirmPasswordState) {
                                // Aquí guardas la nueva contraseña
                                actualizarContraseña(emailState, usernameState, newPasswordState, sharedPreferences, gson)
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                            } else {
                                dialogMessage = "Las contraseñas no coinciden"
                                showDialog = true
                            }
                        },
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .fillMaxWidth(0.5f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDAE089))
                    ) {
                        Text(text = "Actualizar", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bottom_left),
                        contentDescription = "Bottom Left Image",
                        modifier = Modifier
                            .size(150.dp)
                            .offset(x = (-10.dp)),
                        colorFilter = ColorFilter.tint(Color(0xFF89E0C4))
                    )

                    Image(
                        painter = painterResource(id = R.drawable.bottom_right),
                        contentDescription = "Bottom Right Image",
                        modifier = Modifier
                            .size(150.dp)
                            .offset(x = (10.dp)),
                        colorFilter = ColorFilter.tint(Color(0xFF89E0C4))
                    )
                }

                if (showDialog) {
                    Dialog(onDismissRequest = { showDialog = false }) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = dialogMessage, textAlign = TextAlign.Center)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = { showDialog = false }) {
                                    Text("OK")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ResetScreenPreview() {
    S5sum2Theme {
        ResetScreen()
    }
}
