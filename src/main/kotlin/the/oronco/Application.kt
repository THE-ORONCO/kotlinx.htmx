package the.oronco

import the.oronco.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
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
    configureTemplating()
    configureRouting()
}
