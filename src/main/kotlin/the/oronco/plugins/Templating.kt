package the.oronco.plugins

import the.oronco.htmx.hxGet
import the.oronco.htmx.hxPut
import the.oronco.htmx.hxSwap
import the.oronco.htmx.hxTarget
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CSSBuilder
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import kotlin.contracts.ExperimentalContracts

var H1.fufa: String?
    get() = this.attributes[""]
    set(newValue) {
        if (newValue != null) {
            attributes["fufa"] = newValue
        }
    }

// TODO configs from https://htmx.org/docs/#config





@OptIn(ExperimentalContracts::class)
inline fun <T: Tag> T.attributes(block: T.() -> Unit): T {
    kotlin.contracts.contract {
        callsInPlace(block, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
    }
    block()
    return this
}



public suspend fun ApplicationCall.respondPartialHtml(status: HttpStatusCode = HttpStatusCode.OK, block: TagConsumer<*>.() -> Unit) {
    val text = buildString {
        appendHTML().apply(block)
    }
    respond(TextContent(text, ContentType.Text.Html.withCharset(Charsets.UTF_8), status))
}

fun Application.configureTemplating() {
    routing {
        get("/contact/1") {
            call.respondHtml {
                head{
                    script(src = "/assets/htmx.org/1.9.12/dist/htmx.min.js") {}
                    link(rel = "stylesheet", href = "/assets/bootstrap/bootstrap.min.css" ) {}
                }
                body {
                    div {
                        hxTarget = "this"
                        hxSwap = "outerHTML"
                        label { +"First Name" }; +": Joe"; br
                        label { +"Last Name" }; +": Joe"; br
                        label { +"Email Address" }; +": joe@blow.com"; br
                        button (classes = "btn primary"){
                            hxGet = "/contact/1/edit"
                            +"Edit Contact"
                        }
                    }
                }
            }
        }

        get("/contact/1/edit") {
            call.respondPartialHtml{
                form{
                    hxPut = "/contact/1"
                    hxTarget = "this"
                    hxSwap = "outerHTML"

                    div { label {+"First Name" } ; input(type = InputType.text, name = "firstName") { value = "Joe" } }
                    div(classes = "form-group") { label {+"Last Name" } ; input(type = InputType.text, name = "lastName") { value = "Blow" } }
                    div(classes = "form-group") { label {+"Email Address" } ; input(type = InputType.email, name = "email") { value = "joe@blow.com" } }
                    button(classes = "btn") { +"Submit" }
                    button(classes = "btn") { hxGet= "/contact/1"; +"Cancel" }
                }
            }
        }
    }
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
