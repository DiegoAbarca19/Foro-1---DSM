package com.udb.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udb.login.models.Event
import com.udb.login.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EventListScreen(navController: NavController) {
    var events by remember { mutableStateOf(listOf<Event>()) }
    var loading by remember { mutableStateOf(true) }

    // Obtener eventos de la API
    RetrofitClient.apiService.getEvents().enqueue(object : Callback<List<Event>> {
        override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
            if (response.isSuccessful) {
                events = response.body() ?: emptyList()
            }
            loading = false
        }

        override fun onFailure(call: Call<List<Event>>, t: Throwable) {
            loading = false
        }
    })

    if (loading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            events.forEach { event ->
                Text(event.name, modifier = Modifier.padding(8.dp))
                Button(onClick = { navController.navigate("confirm_attendance/${event.id}") }) {
                    Text("Confirmar Asistencia")
                }
            }
        }
    }
}
