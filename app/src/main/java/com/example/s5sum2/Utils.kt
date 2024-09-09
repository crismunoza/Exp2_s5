package com.example.s5sum2

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Contacto(
    val nombre: String,
    val fechaNacimiento: String,
    val email: String,
    var password: String
)

// Llamadas para guardar, cargar y obtener los datos del JSON
fun cargarContactos(sharedPreferences: SharedPreferences, gson: Gson): MutableList<Contacto> {
    val json = sharedPreferences.getString("contactos_key", null)
    return if (json != null) {
        val type = object : TypeToken<MutableList<Contacto>>() {}.type
        gson.fromJson(json, type)
    } else {
        mutableListOf()
    }
}

fun guardarContactos(contactos: MutableList<Contacto>, sharedPreferences: SharedPreferences, gson: Gson) {
    val editor = sharedPreferences.edit()
    val json = gson.toJson(contactos)
    editor.putString("contactos_key", json)
    editor.apply()
}

fun obtenerContraseña(email: String, nombre: String, sharedPreferences: SharedPreferences, gson: Gson): String? {
    val contactos = cargarContactos(sharedPreferences, gson)
    val contacto = contactos.find { it.email == email && it.nombre == nombre }
    return contacto?.password
}

fun actualizarContraseña(email: String, nombre: String, nuevaContraseña: String, sharedPreferences: SharedPreferences, gson: Gson) {
    val contactos = cargarContactos(sharedPreferences, gson)
    val contactoIndex = contactos.indexOfFirst { it.email == email && it.nombre == nombre }
    if (contactoIndex != -1) {
        contactos[contactoIndex].password = nuevaContraseña
        guardarContactos(contactos, sharedPreferences, gson)
    }
}
