package com.example.aigul

import auth.JwtService
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.FileInputStream

import auth.MySession
import auth.hash
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.gson.*
import io.ktor.locations.*
import io.ktor.sessions.*
import repository.DatabaseFactory
import repository.TodoRepository
import routes.todos
import routes.users


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Locations) {
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

//    DatabaseFactory.init()
    val db = com.example.aigul.repository.NewRepository
    val jwtService = JwtService()
    val hashFunction = { s: String -> hash(s) }

    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = "Todo Server"
            validate {
                val payload = it.payload
                val claim = payload.getClaim("id")
                val claimString = claim.asInt()
                val user = db.findUser(claimString)
                user
            }
        }
    }
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

    routing {
        users(db, jwtService, hashFunction)
        todos(db)
    }
    val client = HttpClient(Jetty) {
    }

    routing {
//        get("/") {
//
//            val registrationToken = "ddVXm3akQla9uWMC6wHX55:APA91bFbkkKB2eWligs-dq624qwCItctbQTecyrMfSVxLGARWWBi_J9MdPVshui-zJVuMaZfquMDO5WlOI5N3n9xF_p_5jzym2y71Co8ZosoFZDEU8W7wxLBm8YsPHtrSzxFX1fBQOxt"
//            println("fdfd sent message: start")
//
//// See documentation on defining a message payload.
//
//// See documentation on defining a message payload.
//            val message: Message = Message.builder()
//                    .putData("score", "850")
//                    .putData("time", "2:45")
//                    .setToken(registrationToken)
//                    .build()
//
//// Send a message to the device corresponding to the provided
//// registration token.
//
//// Send a message to the device corresponding to the provided
//// registration token.
////            sendCommonMessage()
//            val response = FirebaseMessaging.getInstance().send(message)
//// Response is a message ID string.
//// Response is a message ID string.
//            println("Successfully sent message: $response")
//            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
//        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }

}
const val API_VERSION = "/v1"