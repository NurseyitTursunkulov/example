package com.example.aigul

import com.fasterxml.jackson.databind.SerializationFeature
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.gson.GsonBuilder
import com.pusher.rest.Pusher
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {


    val serviceAccount = FileInputStream("jj.json")

    val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://avtovokzal-ceb37.firebaseio.com")
            .build()

    FirebaseApp.initializeApp(options)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    val client = HttpClient(Jetty) {
    }

    routing {
        get("/") {

            val registrationToken = "c0vq8vV-RRSNanARH84EWY:APA91bGCIjMwrF-pU3fD7ulu2GxyOnD2vGpuL2okDNN75PKNo2JhIgjvsonbNU91ewCzu7yStN0h46LMNdxtHSZChuHH5yEMUwDvSA91iSaEHYONZLVynMgGQK8I54WkQ7BpQf-YWDZj"
            println("fdfd sent message: start")

// See documentation on defining a message payload.

// See documentation on defining a message payload.
            val message: Message = Message.builder()
                    .putData("score", "850")
                    .putData("time", "2:45")
                    .setToken(registrationToken)
                    .build()

// Send a message to the device corresponding to the provided
// registration token.

// Send a message to the device corresponding to the provided
// registration token.
//            sendCommonMessage()
            val response = FirebaseMessaging.getInstance().send(message)
// Response is a message ID string.
// Response is a message ID string.
            println("Successfully sent message: $response")
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }

}