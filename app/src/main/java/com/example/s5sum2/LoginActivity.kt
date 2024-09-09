package com.example.s5sum2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.s5sum2.ui.theme.S5sum2Theme
import com.google.gson.Gson
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            S5sum2Theme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("contactos_prefs", Context.MODE_PRIVATE)
    val gson = remember { Gson() }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val textFieldFontSize = (0.04f * screenWidth.value).sp

    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }  // Declaración de estado

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars), // Respetar las barras del sistema
        content = { paddingValues -> // Aquí recibimos el paddingValues para usarlo
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Usar el paddingValues en el Box
                    .background(Color.White),
                contentAlignment = Alignment.TopCenter
            ) {
                // Top Right Image
                Image(
                    painter = painterResource(id = R.drawable.top_tight),
                    contentDescription = "Top Right Image",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .width((0.4f * screenWidth.value).dp)
                        .height((0.2f * screenHeight.value).dp)
                        .offset(x = (0.02f * screenWidth.value).dp),
                    colorFilter = ColorFilter.tint(Color(0xFF89BDE0))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = (0.05f * screenWidth.value).dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Inicia Sesión",
                        fontSize = (0.08f * screenWidth.value).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDAE089),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = (0.04f * screenWidth.value).dp)
                    )

                    Spacer(modifier = Modifier.height((0.04f * screenHeight.value).dp))

                    Image(
                        painter = painterResource(id = R.drawable.login_ico),
                        contentDescription = "Login Icon",
                        modifier = Modifier
                            .size((0.35f * screenWidth.value).dp)
                            .padding(top = (0.02f * screenHeight.value).dp)
                    )

                    Spacer(modifier = Modifier.height((0.05f * screenHeight.value).dp))

                    // Email TextField
                    OutlinedTextField(
                        value = emailState,
                        onValueChange = { emailState = it },
                        label = { Text("Ingresa Correo") },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.email), contentDescription = "Email Icon")
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = textFieldFontSize),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height((0.03f * screenHeight.value).dp))

                    // Password TextField
                    OutlinedTextField(
                        value = passwordState,
                        onValueChange = { passwordState = it },
                        label = { Text("Ingresa Contraseña", fontSize = textFieldFontSize) },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.password), contentDescription = "Password Icon")
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = textFieldFontSize),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height((0.05f * screenHeight.value).dp))

                    // Login Button
                    Button(
                        onClick = {
                            val contactos = cargarContactos(sharedPreferences, gson)
                            val contactoEncontrado = contactos.find { it.email == emailState && it.password == passwordState }
                            if (contactoEncontrado != null) {
                                val intent = Intent(context, MenuActivity::class.java)
                                context.startActivity(intent)
                            } else {
                                showErrorDialog = true
                            }
                        },
                        modifier = Modifier
                            .padding(top = (0.02f * screenHeight.value).dp)
                            .fillMaxWidth(0.5f)
                            .height((0.07f * screenHeight.value).dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDAE089))
                    ) {
                        Text(text = "Iniciar", color = Color.White, fontSize = (0.03f * screenWidth.value).sp)
                    }

                    Spacer(modifier = Modifier.height((0.04f * screenHeight.value).dp))

                    // Navigation Texts
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = (0.04f * screenWidth.value).dp)
                    ) {
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            fontSize = (0.03f * screenWidth.value).sp,
                            color = Color(0xFF89BDE0),
                            modifier = Modifier.clickable {
                                val intent = Intent(context, ResetpasActivity::class.java)
                                context.startActivity(intent)
                            }
                        )
                        Text(
                            text = "Regístrate",
                            fontSize = (0.03f * screenWidth.value).sp,
                            color = Color(0xFF89E0C4),
                            modifier = Modifier.clickable {
                                val intent = Intent(context, RegisterActivity::class.java)
                                context.startActivity(intent)
                            }
                        )
                    }
                }

                // Bottom Left Image
                Image(
                    painter = painterResource(id = R.drawable.bottom_left),
                    contentDescription = "Bottom Left Image",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .width((0.4f * screenWidth.value).dp)
                        .height((0.2f * screenHeight.value).dp)
                        .offset(x = (-0.02f * screenWidth.value).dp),
                    colorFilter = ColorFilter.tint(Color(0xFF89E0C4))
                )
            }
        }
    )

    // Error Dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text("Las credenciales no son válidas.") },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    S5sum2Theme {
        LoginScreen()
    }
}
