package the.oronco.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.html.*
import the.oronco.htmx.*
import kotlin.contracts.ExperimentalContracts

// TODO configs from https://htmx.org/docs/#config


@OptIn(ExperimentalContracts::class)
inline fun <T : Tag> T.attributes(block: T.() -> Unit): T {
    kotlin.contracts.contract {
        callsInPlace(block, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
    }
    block()
    return this
}


data class User(
    val id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    val age: Int
)

var users: Map<String, User> =
    mutableMapOf("Fio" to User("Fio", "Thée", "Roncoletta", "fio@roncoletta.com", 24))


private const val FIRST_NAME = "firstName"
private const val LAST_NAME = "lastName"
private const val EMAIL = "email"


fun Application.configureTemplating() {
    routing {
        get("/contact/{id}") {
            call.respondHtml {
                head {
                    script(src = "/assets/htmx.org/2.0.2/dist/htmx.min.js") {}
                    link(rel = "stylesheet", href = "/assets/bootstrap/bootstrap.min.css") {}
                }
                body {
                    displayUserInfo(users[call.parameters["id"]])
                }
            }
        }

        htmxRequest {
            get("/contact/{id}/edit") {
                call.respondFragment {
                    val user = users[call.parameters["id"]]
                    editUserDataForm(user)
                }
            }
            put("/contact/{id}") {
                val userId = call.parameters["id"]

                if (userId != null) {
                    val parameters = call.receiveParameters()
                    val user = users[userId]
                    parameters[FIRST_NAME]?.apply { user?.firstName = this }
                    parameters[LAST_NAME]?.apply { user?.lastName = this }
                    parameters[EMAIL]?.apply { user?.email = this }
                }

                call.respondFragment {
                    displayUserInfo(users[userId])
                }
            }
        }
    }
}

private fun FlowContent.editUserDataForm(user: User?) {
    if (user == null) div {} else
        form {
            hxPut = "/contact/${user.id}"
            hxTarget = "this"
            hxSwap = "outerHTML"

            table {
                tr {
                    td { label { htmlFor = FIRST_NAME; +"First Name" } }
                    td {
                        input(type = InputType.text, name = FIRST_NAME) {
                            value = user.firstName
                        }
                    }
                }
                tr {
                    td { label { htmlFor = LAST_NAME; +"Last Name" } }
                    td {
                        input(type = InputType.text, name = LAST_NAME) {
                            value = user.lastName
                        }
                    }
                }
                tr {
                    td { label { htmlFor = EMAIL; +"Email" } }
                    td {
                        input(type = InputType.text, name = EMAIL) {
                            value = user.email
                        }
                    }
                }
            }
            button(classes = "btn") { +"Submit" }
            button(classes = "btn") { hxGet = "/contact/${user.id}"; +"Cancel" }
        }
}

private fun FlowContent.displayUserInfo(user: User?) {
    if (user == null) div {} else
        div {
            hxTarget = "this"
            hxSwap = "outerHTML"
            table {
                tr {
                    td { label { htmlFor = FIRST_NAME; +"First Name" } }
                    td { div { id = FIRST_NAME; +": ${user.firstName}" } }
                }
                tr {
                    td { label { htmlFor = LAST_NAME; +"Last Name" } }
                    td { div { id = LAST_NAME; +": ${user.lastName}" } }
                }
                tr {
                    td { label { htmlFor = EMAIL; +"Email" } }
                    td { div { id = LAST_NAME; +": ${user.email}" } }
                }
            }

            button(classes = "btn primary") {
                hxGet = "/contact/${user.id}/edit"
                +"Edit Contact"
            }
        }
}

