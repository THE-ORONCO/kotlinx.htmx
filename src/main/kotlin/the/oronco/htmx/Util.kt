package the.oronco.htmx

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.html.consumers.filter
import kotlinx.html.stream.appendHTML

suspend fun ApplicationCall.respondFragment(status: HttpStatusCode = HttpStatusCode.OK, block: FlowContent.() -> Unit) {
    val text = buildString {
        appendHTML().filter { if (it is FRAGMENT) SKIP else PASS }.fragment(block = block)
    }
    respond(TextContent(text, ContentType.Text.Html.withCharset(Charsets.UTF_8), status))
}

@Suppress("unused")
private class FRAGMENT(override val consumer: TagConsumer<*>) :
    HTMLTag("fragment", consumer, emptyMap(), null, false, false), HtmlBlockTag {

}

@HtmlTagMarker
private inline fun <T, C : TagConsumer<T>> C.fragment(crossinline block: FRAGMENT.() -> Unit = {}): T =
    FRAGMENT(this).visitAndFinalize(this, block)

fun Route.htmxRequest(build: Route.()->Unit): Route = header("HX-Request", "true", build)
