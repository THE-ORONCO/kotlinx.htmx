package the.oronco

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.webjars.*
import the.oronco.plugins.configureRouting
import the.oronco.plugins.configureTemplating

fun main() {
    embeddedServer(Netty, port = 8080, host = "innomes-dev-vm.atr.local", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Webjars){
        path = "assets"
    }
    install(Compression) {
        gzip {
//            condition { request.headers[HttpHeaders.Referrer]?.startsWith("https://my.domain/") == true }
        }
        deflate {
//            condition { request.headers[HttpHeaders.Referrer]?.startsWith("https://my.domain/") == true }
        }
    }
    configureTemplating()
    configureRouting()
}
