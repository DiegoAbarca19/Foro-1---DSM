package com.udb.login

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udb.login.models.User
import com.udb.login.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }
    var contrasena by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF8EC5FC), Color(0xFFE0C3FC))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier.padding(12.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(20.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    RowImage()
                    RowEmail(
                        email = email,
                        emailChange = {
                            email = it
                            isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        },
                        isValid = isValidEmail
                    )
                    RowPassword(
                        contrasena = contrasena,
                        passwordChange = {
                            contrasena = it
                            isValidPassword = contrasena.length >= 6
                        },
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = { passwordVisible = !passwordVisible },
                        isValidPassword = isValidPassword
                    )
                    RowButtonLogin(
                        showError = showError,
                        onClickLogin = {
                            val user = User(email, contrasena)
                            RetrofitClient.apiService.login(user).enqueue(object : Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    if (response.isSuccessful) {
                                        navController.navigate("event_list") // Navegar a la lista de eventos
                                    } else {
                                        showError = true
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    showError = true
                                }
                            })
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { navController.navigate("registration") },
                            modifier = Modifier.fillMaxWidth(0.8f) // Igual tamaño que 'Iniciar Sesión'
                        ) {
                            Text("Registrarse")
                        }
                    }
                }
            }
        }
    }
}

// --- Imagen ---
@Composable
fun RowImage() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(100.dp),
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Imagen login"
        )
    }
}

// --- Email Row ---
@Composable
fun RowEmail(
    email: String,
    emailChange: (String) -> Unit,
    isValid: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = emailChange,
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isValid) Color.Green else Color.Red,
                focusedLabelColor = if (isValid) Color.Green else Color.Red
            )
        )
    }
}

// --- Password Row ---
@Composable
fun RowPassword(
    contrasena: String,
    passwordChange: (String) -> Unit,
    passwordVisible: Boolean,
    passwordVisibleChange: () -> Unit,
    isValidPassword: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = contrasena,
            onValueChange = passwordChange,
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(onClick = passwordVisibleChange) {
                    Icon(imageVector = image, contentDescription = "Ver contraseña")
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isValidPassword) Color.Green else Color.Red,
                focusedLabelColor = if (isValidPassword) Color.Green else Color.Red
            )
        )
    }
}

// --- Boton de inicio de sesion Row ---
@Composable
fun RowButtonLogin(
    showError: Boolean,
    onClickLogin: () -> Unit
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = onClickLogin,
                enabled = true
            ) {
                Text(text = "Iniciar Sesión")
            }
        }

        if (showError) {
            Text(
                text = "Correo o contraseña inválidos",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}