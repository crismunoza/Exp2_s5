package com.example.s5sum2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.s5sum2.ui.theme.S5sum2Theme
import com.google.gson.Gson
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.*

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S5sum2Theme {
                RegisterScreen()
            }
        }
    }
}

@Composable
fun RegisterScreen() {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("contactos_prefs", Context.MODE_PRIVATE)
    val gson = Gson()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    LocalConfiguration.current.screenHeightDp.dp
    val labelTextSize = (0.03f * screenWidth.value).sp

    var nombre by rememberSaveable { mutableStateOf("") }
    var fechaNacimiento by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars), // Respetar las barras del sistema
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Aplicar el padding del Scaffold
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Header Box con la curva decorativa y el logo
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
                        painter = painterResource(id = R.drawable.logo_app), // Actualiza con tu logo
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = -20.dp)
                    )
                }

                Text(
                    text = "Registrate",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campos de registro
                CustomTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = "Ingresa Nombre",
                    icon = R.drawable.name, // Actualiza con tu icono
                    labelTextSize = labelTextSize
                )

                Spacer(modifier = Modifier.height(16.dp))

                DateInputField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    label = "Fecha Nacimiento",
                    labelTextSize = labelTextSize,
                    icon = R.drawable.btn_5 // Actualiza con tu icono
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Ingresa Correo",
                    icon = R.drawable.email, // Actualiza con tu icono
                    labelTextSize = labelTextSize
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Ingresa Contraseña",
                    icon = R.drawable.password, // Actualiza con tu icono
                    isPassword = true,
                    labelTextSize = labelTextSize
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Repetir Contraseña",
                    icon = R.drawable.password,
                    isPassword = true,
                    labelTextSize = labelTextSize
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (password == confirmPassword) {
                            val contactos = cargarContactos(sharedPreferences, gson)
                            val nuevoContacto = Contacto(nombre, fechaNacimiento, email, password)
                            contactos.add(nuevoContacto)
                            guardarContactos(contactos, sharedPreferences, gson)
                            dialogMessage = "¡Tu cuenta ha sido creada con éxito!"
                            dialogTitle = "Cuenta Creada"
                        } else {
                            dialogMessage = "Las contraseñas no coinciden."
                            dialogTitle = "Error"
                        }
                        showDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89E0C4))
                ) {
                    Text(text = "Registrate", color = Color(0xFFFDFDFD))
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                            if (dialogMessage == "¡Tu cuenta ha sido creada con éxito!") {
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    if (dialogMessage == "¡Tu cuenta ha sido creada con éxito!") {
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                    }
                                }
                            ) {
                                Text("OK")
                            }
                        },
                        title = { Text(dialogTitle) },  // Muestra el título dinámicamente
                        text = { Text(dialogMessage) }  // Muestra el mensaje dinámicamente
                    )
                }

            }
        }
    )
}

@Composable
fun DateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    labelTextSize: TextUnit,
    icon: Int
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(formatAsDate(newValue))
        },
        label = {
            Text(
                text = label,
                fontSize = labelTextSize
            )
        },
        leadingIcon = {
            Icon(painter = painterResource(id = icon), contentDescription = null)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontSize = labelTextSize),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(0.8f)
    )
}

fun formatAsDate(input: String): String {
    val digits = input.filter { it.isDigit() }
    return when {
        digits.length >= 8 -> "${digits.take(2)}/${digits.drop(2).take(2)}/${digits.drop(4)}"
        else -> digits
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: Int,
    isPassword: Boolean = false,
    labelTextSize: TextUnit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontSize = labelTextSize
            )
        },
        leadingIcon = {
            Icon(painter = painterResource(id = icon), contentDescription = null)
        },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontSize = labelTextSize),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(0.8f)
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(){
    S5sum2Theme {
        RegisterScreen()
    }
}
