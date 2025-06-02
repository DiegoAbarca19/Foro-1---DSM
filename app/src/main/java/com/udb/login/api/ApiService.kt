package com.udb.login.api

import com.udb.login.models.Attendance
import com.udb.login.models.Event
import com.udb.login.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    fun login(@Body user: User): Call<Void> // Cambia Void por el tipo de respuesta esperado

    @POST("register")
    fun register(@Body user: User): Call<Void> // Cambia Void por el tipo de respuesta esperado

    @GET("events")
    fun getEvents(): Call<List<Event>>

    @POST("events/{eventId}/attendance")
    fun confirmAttendance(@Path("eventId") eventId: Int, @Body attendance: Attendance): Call<Void> // Cambia Void por el tipo de respuesta esperado

    @GET("events/{eventId}")
    fun getEventDetail(@Path("eventId") eventId: Int): Call<Event>

    @GET("events/past")
    fun getPastEvents(): Call<List<Event>>
}
