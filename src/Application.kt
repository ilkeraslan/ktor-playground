package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import java.time.LocalDate

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

data class Users(val users: List<User>)
data class User(val userId: String, val firstName: String, val lastName: String)

val users = Users(
    listOf(
        User("1", "Mario", "Rossi"),
        User("2", "Luca", "Neri"),
        User("3", "Foo", "Corti")
    )
)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        get("/") {
            call.respond(users)
        }

        get("/users/") {
            call.respond(users)
        }

        get("/users/{key}") {
            val item = users.users.firstOrNull { it.userId == call.parameters["key"] }
            if (item == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(item)
            }
        }
    }
}

