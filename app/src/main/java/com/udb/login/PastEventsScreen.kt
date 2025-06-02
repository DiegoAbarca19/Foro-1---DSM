package com.udb.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun PastEventsScreen() {
    var pastEvents by remember { mutableStateOf(listOf<Event>()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loading = true
        error = false
        RetrofitClient.apiService.getPastEvents().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    pastEvents = response.body() ?: emptyList()
                } else {
                    error = true
                }
                loading = false
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
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
            Text("Error al cargar eventos pasados", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
        }
        else -> {
            if (pastEvents.isEmpty()) {
                Text("No hay eventos pasados", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(pastEvents) { event ->
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                            Text(text = event.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Fecha: ${event.date}", style = MaterialTheme.typography.bodySmall)
                            Divider(modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
