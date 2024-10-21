package the.oronco.plugins

import io.ktor.i18n.i18n
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*
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
    mutableMapOf(
        "Fio" to User("Fio", "Thée", "Roncoletta", "fio@roncoletta.com", 24),
        "Leo" to User("Leo", "Leo", "Roncoletta", "leo@roncoletta.com", 24),
    )


private const val FIRST_NAME = "firstName"
private const val LAST_NAME = "lastName"
private const val EMAIL = "email"


fun Application.configureTemplating() {
    routing {
        htmxRequest {
            get("/users/{id}/edit") {
                call.respondFragmentTemplate(EditUserInfoTemplate(users[call.parameters["id"]])) {}
            }
            put("/users/{id}") {
                val userId = call.parameters["id"]

                updateUserData(userId, call.receiveParameters())

                call.respondFragmentTemplate(UserInfoTemplate(users[userId])) {}
            }
            get("/users/{id}") {
                call.respondFragmentTemplate(UserInfoTemplate(users[call.parameters["id"]]!!)) {}
            }
        }

        get("/users/{id}") {
            call.respondHtmlTemplate(MainPageTemplate()) {
                content {
                    insert(UserInfoTemplate(users[call.parameters["id"]]!!)){}
                }
            }
        }

        get("/users"){
            call.respondHtmlTemplate(MainPageTemplate()) {

                content{
                    h1 {
                        +"All Users"
                    }
                    users.values.forEach {
                        insert(UserInfoTemplate(it)) {}
                    }
                }
            }
        }
    }
}


private fun updateUserData(userId: String?, parameters: StringValues) {
    if (userId != null) {
        val user = users[userId]
        parameters[FIRST_NAME]?.apply { user?.firstName = this }
        parameters[LAST_NAME]?.apply { user?.lastName = this }
        parameters[EMAIL]?.apply { user?.email = this }
    }
}

class EditUserInfoTemplate(private val user: User?): Template<FlowContent> {
    override fun FlowContent.apply() {
        if (user == null) div {} else
        form {
            hxPut = "/users/${user.id}"
            hxTarget = "this"
            hxSwap = outerHtml {}

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
            button(classes = "btn") { hxGet = "/users/${user.id}"; +"Cancel" }
        }
}}


class MainPageTemplate: Template<HTML> {
    val content = Placeholder<FlowContent>()
    override fun HTML.apply() {
        head {
            script(src = "/assets/htmx.org/2.0.2/dist/htmx.min.js") {}
            link(rel = "stylesheet", href = "/assets/bootstrap/bootstrap.min.css") {}
        }
        body {
            insert(content)
        }
    }
}

class UserInfoTemplate(private val user: User?): Template<FlowContent> {
    override fun FlowContent.apply() {
        if (user == null){div{}}else
        div {
            style = "border-style: solid"
            hxTarget = "this"
            hxSwap = outerHtml { }
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
                hxGet = "/users/${user.id}/edit"
                +"Edit users"
            }
        }
    }
}
