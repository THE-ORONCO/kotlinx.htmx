package the.oronco.htmx

import kotlinx.html.Tag
import org.intellij.lang.annotations.Language
import kotlin.reflect.KClass
import kotlin.time.Duration

/**
 * https://htmx.org/reference/#attributes
 * @author the_oronco@posteo.net
 * @since 19.06.24
 **/

fun interface HxValue {
    fun display(): String // TODO think about sanitization
}


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
var Tag.hxSwap : HxSwap?
    get() = null // TODO parse attributes[HX_SWAP] into the data structure
    set(newValue) {
        when {
            newValue != null -> attributes[HX_SWAP] = newValue.display()
        }
    }

fun innerHtml(modifiers: HxSwap.() -> Unit = {}): HxSwap = initSwap(SwapStyle.INNER_HTML, modifiers)
val innerHtml = innerHtml()
fun outerHtml(modifiers: HxSwap.() -> Unit= {}): HxSwap = initSwap(SwapStyle.OUTER_HTML, modifiers)
val outerHtml = outerHtml()
fun textContent(modifiers: HxSwap.() -> Unit= {}): HxSwap = initSwap(SwapStyle.TEXT_CONTENT, modifiers)
val textContent = textContent()
fun beforeBegin(modifiers: HxSwap.() -> Unit= {}): HxSwap = initSwap(SwapStyle.BEFORE_BEGIN, modifiers)
val beforeBegin = beforeBegin()
fun afterBegin(modifiers: HxSwap.() -> Unit= {}): HxSwap = initSwap(SwapStyle.AFTER_BEGIN, modifiers)
val afterBegin = afterBegin()
fun beforeEnd(modifiers: HxSwap.() -> Unit= {}): HxSwap = initSwap(SwapStyle.BEFORE_END, modifiers)
val beforeEnd = beforeEnd()
fun afterEnd(modifiers: HxSwap.() -> Unit= {}): HxSwap = initSwap(SwapStyle.AFTER_END, modifiers)
val afterEnd = afterEnd()
fun delete(modifiers: HxSwap.() -> Unit= {}): HxSwap = initSwap(SwapStyle.DELETE, modifiers)
val delete = delete()
fun none(modifiers: HxSwap.() -> Unit= {}): HxSwap = initSwap(SwapStyle.NONE, modifiers)
val none = none()

private fun initSwap(style: SwapStyle, modifiers: HxSwap.() -> Unit): HxSwap{
    val swap = HxSwapImpl(style, mutableMapOf())
    swap.modifiers()
    return swap
}


interface HxSwap : HxValue, HxSwapOob {
    val swapStyle: SwapStyle
    var modifiers : MutableMap<KClass<out Modifier>, Modifier>
    override fun display(): String = "${this.swapStyle.representation} ${this.modifiers.values.joinToString(" ", transform = Modifier::display)}"

    private fun  addModifier(modifier: Modifier): HxSwap {
        this.modifiers[modifier.javaClass.kotlin] = modifier
        return this
    }

    fun transition(shouldTransition: Boolean): HxSwap = addModifier(Transition(shouldTransition))
    // TODO implement nicer passing of time unit
    fun swap(delay: HxDuration): HxSwap = addModifier(Swap(delay))
    fun swap(delay: Number): HxSwap = addModifier(Swap(delay.asHxDuration()))
    fun settle(delay: HxDuration): HxSwap = addModifier(Settle(delay))
    fun settle(delay: Number): HxSwap = addModifier(Settle(delay.asHxDuration()))
    fun ignoreTitle(doIgnoreTitle: Boolean): HxSwap = addModifier(IgnoreTitle(doIgnoreTitle))
    // TODO nicer interface than passing enums
    fun scroll(target: String? = null, verticalLocation: VerticalLocation): HxSwap = addModifier(Scroll(target, verticalLocation))
    fun scroll(verticalLocation: VerticalLocation): HxSwap = addModifier(Scroll(verticalLocation =  verticalLocation))
    fun show(target: String? = null, verticalLocation: VerticalLocation): HxSwap = addModifier(Show(target, verticalLocation))
    fun show(verticalLocation: VerticalLocation): HxSwap = addModifier(Show(verticalLocation = verticalLocation))
    fun focusScroll(doFocusScroll: Boolean): HxSwap = addModifier(IgnoreTitle(doFocusScroll))
}


private data class HxSwapImpl(
    override val swapStyle: SwapStyle,
    override var modifiers: MutableMap<KClass<out Modifier>, Modifier>
) : HxSwap {
    override fun display(): String = "${this.swapStyle.representation} ${this.modifiers.values.joinToString(" ", transform = Modifier::display)}"
}

enum class SwapStyle(val representation: String){
    INNER_HTML("innerHtml"),
    OUTER_HTML("outerHtml"),
    TEXT_CONTENT("textContent"),
    BEFORE_BEGIN("beforeBegin"),
    AFTER_BEGIN("afterBegin"),
    BEFORE_END("beforeEnd"),
    AFTER_END("afterEnd"),
    DELETE("delete"),
    NONE("none"),
}

sealed interface Modifier : HxValue

data class Transition(val shouldTransition: Boolean): Modifier{
    override fun display(): String = "transition:${shouldTransition}"
}
data class Swap(val delay: HxDuration) : Modifier{
    override fun display(): String = "swap:${delay.display()}"
}
data class Settle(val delay: HxDuration) : Modifier {
    override fun display(): String = "settle:${delay.display()}"
}

interface HxDuration : HxValue {
    data class Minutes(val duration: Double) : HxDuration {
        override fun display(): String = "${duration}m"
    }
    data class Seconds(val duration: Double) : HxDuration {
        override fun display(): String = "${duration}s"
    }
    data class MilliSeconds(val duration: Double) : HxDuration {
        override fun display(): String = "${duration}ms"
    }
}

fun Number.asHxDuration(): HxDuration = HxDuration.MilliSeconds(this.toDouble())
fun Duration.asHxDuration(): HxDuration = HxDuration.MilliSeconds(this.inWholeMilliseconds.toDouble())

data class IgnoreTitle(val ignoreTitle: Boolean): Modifier {
    override fun display(): String = "ignoreTitle:${ignoreTitle}"
}

data class Scroll(@Language(value = "CSS", prefix = ":is(", suffix = ")") val target: CssSelector? = null, val verticalLocation: VerticalLocation) : Modifier {
    override fun display(): String = when {
        target != null -> "scroll:${target}:${verticalLocation.representation}"
        else -> "scroll:${verticalLocation.representation}"
    }
}

data class Show(@Language(value = "CSS", prefix = ":is(", suffix = ")") val target: CssSelector? = null, val verticalLocation: VerticalLocation) : Modifier {
    override fun display(): String = when {
        target != null -> "show:${target}:${verticalLocation.representation}"
        else -> "show:${verticalLocation.representation}"
    }
}
enum class VerticalLocation(val representation: String) {
    TOP("top"),
    BOTTOM("bottom"),
    NONE("none"),
}

data class FocusScroll(val doFocusScroll: Boolean): Modifier {
    override fun display(): String = "focus-scroll:${doFocusScroll}"
}


// https://htmx.org/attributes/hx-swap-oob/
private const val HX_SWAP_OOB = "hx-swap-oob"
var Tag.hxSwapOob : HxSwapOob?
    get() = null // TODO parse attributes[HX_SWAP_OOB] into object
    set(newValue) { if (newValue != null) { attributes[HX_SWAP_OOB] = newValue.display() } }

sealed interface HxSwapOob : HxValue
class DoSwapOob : HxSwapOob {
    override fun display(): String = "true"
}

fun <T> T.select(
    @Language(value = "CSS", prefix = ":is(", suffix = ")") cssSelector: CssSelector?
): HxSwapWithSelect where T : HxSwap, T : HxSwapOob =
    HxSwapWithSelect(this, cssSelector)


data class HxSwapWithSelect(@Language(value = "CSS", prefix = ":is(", suffix = ")") val swap: HxSwap, val cssSelector: CssSelector?) : HxSwapOob {
    override fun display(): String = when {
        cssSelector != null -> "${swap.display()}:${cssSelector}"
        else -> swap.display()
    }
}

fun doSwapOob(): HxSwapOob = DoSwapOob()
val doSwapOob: HxSwapOob = DoSwapOob()
fun swapOob(swap: HxSwap): HxSwapOob = HxSwapWithSelect(swap, null)
fun swapOob(swap: HxSwap, @Language(value = "CSS", prefix = ":is(", suffix = ")") selector: CssSelector?): HxSwapOob = HxSwapWithSelect(swap, selector)



// https://htmx.org/attributes/hx-target/
private const val HX_TARGET = "hx-target"
var Tag.hxTarget : HxTarget?
    get() = null // TODO parse attributes[HX_TARGET] to objects
    set(newValue) { if (newValue != null) { attributes[HX_TARGET] = newValue.display() } }

sealed interface HxTarget: HxValue
data class HxTargetCss(@Language(value = "CSS", prefix = ":is(", suffix = ")") val cssSelector: CssSelector) : HxTarget {
    override fun display(): String = cssSelector
}
class HxTargetThis: HxTarget{
    override fun display(): String = "this"
}

data class HxTargetClosest(@Language(value = "CSS", prefix = ":is(", suffix = ")") val cssSelector: CssSelector) : HxTarget {
    override fun display(): String = "closest:${cssSelector}"
}

data class HxTargetFind(@Language(value = "CSS", prefix = ":is(", suffix = ")") val cssSelector: CssSelector) : HxTarget { override fun display(): String = "find:${cssSelector}" }
data class HxTargetNext(@Language(value = "CSS", prefix = ":is(", suffix = ")") val cssSelector: CssSelector?) : HxTarget {
    override fun display(): String = when {
        cssSelector != null -> "next:${cssSelector}"
        else -> "next"
    }
}
data class HxTargetPrevious(@Language(value = "CSS", prefix = ":is(", suffix = ")") val cssSelector: CssSelector?) : HxTarget {
    override fun display(): String = when {
        cssSelector != null -> "previous:${cssSelector}"
        else -> "previous"
    }
}
fun targetCss(@Language(value = "CSS", prefix = ":is(", suffix = ")") cssSelector: CssSelector) = HxTargetCss(cssSelector)
val targetThis = HxTargetThis()
fun targetClosest(@Language(value = "CSS", prefix = ":is(", suffix = ")") cssSelector: CssSelector) = HxTargetClosest(cssSelector)
fun targetFind(@Language(value = "CSS", prefix = ":is(", suffix = ")") cssSelector: CssSelector) = HxTargetFind(cssSelector)
val targetNext = HxTargetNext(null)
fun targetNext(@Language(value = "CSS", prefix = ":is(", suffix = ")") cssSelector: CssSelector?) = HxTargetNext(cssSelector)
val targetPrevious = HxTargetPrevious(null)
fun targetPrevious(@Language(value = "CSS", prefix = ":is(", suffix = ")") cssSelector: CssSelector?) = HxTargetPrevious(cssSelector)

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
