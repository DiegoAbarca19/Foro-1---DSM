package com.udb.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udb.login.models.Attendance
import com.udb.login.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ConfirmAttendanceScreen(eventId: Int, navController: NavController) {
    var userId by remember { mutableStateOf(1) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Confirmar asistencia para el evento ID: $eventId", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val attendance = Attendance(eventId, userId)
            RetrofitClient.apiService.confirmAttendance(eventId, attendance).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        showSuccess = true
                        showError = false
                    } else {
                        showSuccess = false
                        showError = true
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    showSuccess = false
                    showError = true
                }
            })
        }) {
            Text("Confirmar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showSuccess) {
            Text(text = "Asistencia confirmada", color = MaterialTheme.colorScheme.primary)
        }

        if (showError) {
            Text(text = "Error al confirmar asistencia", color = MaterialTheme.colorScheme.error)
        }
    }
}