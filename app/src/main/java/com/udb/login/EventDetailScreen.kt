package com.udb.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.udb.login.models.Event
import com.udb.login.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EventDetailScreen(eventId: Int) {
    var event by remember { mutableStateOf<Event?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf(false) }

    // Obtener detalles del evento desde la API
    LaunchedEffect(eventId) {
        loading = true
        error = false
        RetrofitClient.apiService.getEventDetail(eventId).enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    event = response.body()
                } else {
                    error = true
                }
                loading = false
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                error = true
                loading = false
            }
        })
    }

    when {
        loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        error -> {
            Text("Error al cargar detalles del evento", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
        }
        event != null -> {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = event!!.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Fecha: ${event!!.date}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                // Puedes agregar más detalles del evento aquí
            }
        }
    }
}
