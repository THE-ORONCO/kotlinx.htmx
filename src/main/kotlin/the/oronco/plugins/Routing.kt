package the.oronco.plugins

import io.ktor.i18n.i18n
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText(
//                i18n("meme")
                "hi UwU"
            )
        }
    }
}
