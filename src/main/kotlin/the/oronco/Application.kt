package the.oronco

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.i18n.I18n
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.hsts.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.webjars.*
import the.oronco.plugins.configureRouting
import the.oronco.plugins.configureTemplating
import java.io.File
import java.util.*

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Webjars){
        path = "assets"
    }
//    install(I18n){ TODO investigate why this causes problems with some requests
//        // TODO use locales instead of hardcoded strings
//        availableLanguages= listOf("de", "en")
//        defaultLanguage = "en"
//    }
    install(Compression) {
        gzip {
//            condition { request.headers[HttpHeaders.Referrer]?.startsWith("https://my.domain/") == true }
        }
        deflate {
//            condition { request.headers[HttpHeaders.Referrer]?.startsWith("https://my.domain/") == true }
        }
    }
    install(RequestValidation){
        // TODO create some validators
    }
    install(AutoHeadResponse)
    install(CachingHeaders)
    install(ConditionalHeaders) {
        // TODO watch more files
        val file = File("src/main/kotlin/com/example/Application.kt")
        version { _, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> listOf(
                    EntityTagVersion(file.lastModified().hashCode().toString()),
                    LastModifiedVersion(Date(file.lastModified()))
                )
                else -> emptyList()
            }
        }
    }
    install(ContentNegotiation){
        json()
    }
    install(ForwardedHeaders)
    install(HSTS){
        maxAgeInSeconds = 30
    }
    install(StatusPages){
        status(HttpStatusCode.NotFound){ call, status ->
            call.respondText("Not Found", status = status)
        }
        exception<Throwable> {call, cause ->
            call.respondText(text = "oh oh: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    configureTemplating()
    configureRouting()
}
