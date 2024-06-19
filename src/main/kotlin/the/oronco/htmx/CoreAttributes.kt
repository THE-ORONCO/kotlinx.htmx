package the.oronco.htmx

import kotlinx.html.Tag

/**
 * https://htmx.org/reference/#attributes
 * @author the_oronco@posteo.net
 * @since 19.06.24
 **/


// https://htmx.org/attributes/hx-get/
private const val HX_GET = "hx-get"
var Tag.hxGet : String?
    get() = attributes[HX_GET]
    set(newValue) { if (newValue != null) { attributes[HX_GET] = newValue } }

// https://htmx.org/attributes/hx-post/
private const val HX_POST = "hx-post"
var Tag.hxPost : String?
    get() = attributes[HX_POST]
    set(newValue) { if (newValue != null) { attributes[HX_POST] = newValue } }

// https://htmx.org/attributes/hx-on/
//private const val HX_ON = "hx-on"
//var Tag.hxOn : Map<String, String> // TODO convert events to wrapper data classes instead of using strings
//    get() = attributes.filter { it.value.startsWith(HX_ON) }
//    set(newValue: Event, ) { if (newValue != null) { attributes[HX_ON] } }

sealed interface Event{
    val event: String;
}
data class CustomEvent(override val event: String) : Event
enum class HTMXEvent(private val value: String) : Event {
    ABORT("abort"),
    AFTER_ON_LOAD("afterOnLoad"),
    AFTER_PROCESS_NODE("afterProcessNode"),
    AFTER_REQUEST("afterRequest"),
    AFTER_SETTLE("afterSettle"),
    AFTER_SWAP("afterSwap"),
    BEFORE_CLEANUP_ELEMENT("beforeCleanupElement"),
    BEFORE_ON_LOAD("beforeOnLoad"),
    BEFORE_PROCESS_NODE("beforeProcessNode"),
    BEFORE_REQUEST("beforeRequest"),
    BEFORE_SWAP("beforeSwap"),
    BEFORE_SEND("beforeSend"),
    CONFIG_REQUEST("configRequest"),
    CONFIRM("confirm"),
    HISTORY_CACHE_ERROR("historyCacheError"),
    HISTORY_CACHE_MISS("historyCacheMiss"),
    HISTORY_CACHE_MISS_ERROR("historyCacheMissError"),
    HISTORY_CACHE_MISS_LOAD("historyCacheMissLoad"),
    HISTORY_RESTORE("historyRestore"),
    BEFORE_HISTORY_SAVE("beforeHistorySave"),
    LOAD("load"),
    NO_SSE_SOURCE_ERROR("noSSESourceError"),
    ON_LOAD_ERROR("onLoadError"),
    OOB_AFTER_SWAP("oobAfterSwap"),
    OOB_BEFORE_SWAP("oobBeforeSwap"),
    OOB_ERROR_NO_TARGET("oobErrorNoTarget"),
    PROMPT("prompt"),
    PUSHED_INTO_HISTORY("pushedIntoHistory"),
    RESPONSE_ERROR("responseError"),
    SEND_ERROR("sendError"),
    SSE_ERROR("sseError"),
    SSE_OPEN("sseOpen"),
    SWAP_ERROR("swapError"),
    TARGET_ERROR("targetError"),
    TIMEOUT("timeout"),
    VALIDATION_VALIDATE("validation:validate"),
    VALIDATION_FAILED("validation:failed"),
    VALIDATION_HALTED("validation:halted"),
    XHR_ABORT("xhr:abort"),
    XHR_LOADEND("xhr:loadend"),
    XHR_LOADSTART("xhr:loadstart"),
    XHR_PROGRESS("xhr:progress"),
    ;

    override val event: String
        get() = "htmx::$value"
}

// https://htmx.org/attributes/hx-push-url/
private const val HX_PUSH_URL = "hx-push-url"
var Tag.hxPushUrl : String?
    get() = attributes[HX_PUSH_URL]
    set(newValue) { if (newValue != null) { attributes[HX_PUSH_URL] = newValue } }

// https://htmx.org/attributes/hx-select/
private const val HX_SELECT = "hx-select"
var Tag.hxSelect : String?
    get() = attributes[HX_SELECT]
    set(newValue) { if (newValue != null) { attributes[HX_SELECT] = newValue } }

// https://htmx.org/attributes/hx-select-oob/
private const val HX_SELECT_OOB = "hx-select-oob"
var Tag.hxSelectOob : String?
    get() = attributes[HX_SELECT_OOB]
    set(newValue) { if (newValue != null) { attributes[HX_SELECT_OOB] = newValue } }

// https://htmx.org/attributes/hx-swap/
private const val HX_SWAP = "hx-swap"
var Tag.hxSwap : String?
    get() = attributes[HX_SWAP]
    set(newValue) { if (newValue != null) { attributes[HX_SWAP] = newValue } }

// https://htmx.org/attributes/hx-swap-oob/
private const val HX_SWAP_OOB = "hx-swap-oob"
var Tag.hxSwapOob : String?
    get() = attributes[HX_SWAP_OOB]
    set(newValue) { if (newValue != null) { attributes[HX_SWAP_OOB] = newValue } }

// https://htmx.org/attributes/hx-target/
private const val HX_TARGET = "hx-target"
var Tag.hxTarget : String?
    get() = attributes[HX_TARGET]
    set(newValue) { if (newValue != null) { attributes[HX_TARGET] = newValue } }

// https://htmx.org/attributes/hx-trigger/
private const val HX_TRIGGER = "hx-trigger" // TODO a list of enums for cleaner syntax possible?
var Tag.hxTrigger : String?
    get() = attributes[HX_TRIGGER]
    set(newValue) { if (newValue != null) { attributes[HX_TRIGGER] = newValue } }

// https://htmx.org/attributes/hx-vals/
private const val HX_VALS = "hx-vals" // TODO cleaner syntax than using strings possible?
var Tag.hxVals : String?
    get() = attributes[HX_VALS]
    set(newValue) { if (newValue != null) { attributes[HX_VALS] = newValue } }
