package the.oronco.htmx

import kotlinx.css.i
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
    // todo make this a value with getter
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
private const val HX_ON = "hx-on"
var Tag.hxOn : Map<String, String>
    get() = attributes
        .filterKeys { it.startsWith(HX_ON) }
        .mapKeys { it.key.replace(HX_ON, "") }
    // TODO think about making the separator - as some templating languages don't like `:` in element attributes
    set(eventMap) = eventMap.forEach { event, js -> attributes["${HX_ON}:${event}"] = js }

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
    var modifiers : MutableMap<KClass<out SwapModifier>, SwapModifier>
    override fun display(): String = "${this.swapStyle.representation} ${this.modifiers.values.joinToString(" ", transform = SwapModifier::display)}"

    private fun  addModifier(modifier: SwapModifier): HxSwap {
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
    override var modifiers: MutableMap<KClass<out SwapModifier>, SwapModifier>
) : HxSwap {
    override fun display(): String = "${this.swapStyle.representation} ${this.modifiers.values.joinToString(" ", transform = SwapModifier::display)}"
}

enum class SwapStyle(val representation: String){
    INNER_HTML("innerHTML"),
    OUTER_HTML("outerHTML"),
    TEXT_CONTENT("textContent"),
    BEFORE_BEGIN("beforebegin"),
    AFTER_BEGIN("afterbegin"),
    BEFORE_END("beforeend"),
    AFTER_END("afterend"),
    DELETE("delete"),
    NONE("none"),
}

sealed interface SwapModifier : HxValue

data class Transition(val shouldTransition: Boolean): SwapModifier {
    override fun display(): String = "transition:${shouldTransition}"
}
data class Swap(val delay: HxDuration) : SwapModifier {
    override fun display(): String = "swap:${delay.display()}"
}
data class Settle(val delay: HxDuration) : SwapModifier {
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

data class IgnoreTitle(val ignoreTitle: Boolean): SwapModifier {
    override fun display(): String = "ignoreTitle:${ignoreTitle}"
}

data class Scroll(@Language(value = "CSS", prefix = ":is(", suffix = ")") val target: CssSelector? = null, val verticalLocation: VerticalLocation) :
    SwapModifier {
    override fun display(): String = when {
        target != null -> "scroll:${target}:${verticalLocation.representation}"
        else -> "scroll:${verticalLocation.representation}"
    }
}

data class Show(@Language(value = "CSS", prefix = ":is(", suffix = ")") val target: CssSelector? = null, val verticalLocation: VerticalLocation) :
    SwapModifier {
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

data class FocusScroll(val doFocusScroll: Boolean): SwapModifier {
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

data object HxTargetThis : HxTarget {
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
val targetThis = HxTargetThis
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
var Tag.hxVals : List<HxTrigger>?
    get() = null // todo implement list parsing
    set(newValue) {
        if (newValue != null) {
            attributes[HX_VALS] = newValue.joinToString { it.display()}
        }
    }


// TODO get inspiration from https://github.com/IodeSystems/kotlinx-htmx
sealed interface HxTrigger: HxValue {
    val filter: String
    val modifiers: List<EventModifier>
}
sealed interface EventModifier: HxValue
data object Once : EventModifier {
    override fun display(): String = "once"
}
data object Changed : EventModifier {
    override fun display(): String = "changed"
}
data class Delay(val delay: String) : EventModifier {
    override fun display(): String = "delay:$delay"
}
data class Throttle(val throttle: String) : EventModifier {
    override fun display(): String = "throttle:$throttle"
}
data class From(val selector: HxTarget) : EventModifier {
    override fun display(): String = "throttle:${selector.display()}"
}
data class Target(@Language(value = "CSS", prefix = ":is(", suffix = ")") val cssSelector: CssSelector) : EventModifier {
    override fun display(): String = "throttle:$cssSelector"
}
data object Consume : EventModifier {
    override fun display(): String = "consume"
}
data class Queue(val option: QueueOption) : EventModifier {
    override fun display(): String = "queue:${option.name}"
}
enum class QueueOption {
    first,
    last,
    all,
    none,
}


data class Event(
    val event: String,
    override val filter: String = "",
    override val modifiers: List<EventModifier> = listOf()
) : HxTrigger {
    override fun display(): String {
        var result = event
        if (filter.isNotEmpty()) {
            result += "[$filter]"
        }
        if (modifiers.isNotEmpty()) {
            result += modifiers.joinToString(" ") { it.display() }
        }
        return result
    }
}

data class Polling(
    val interval: String, // is there a specific format?
    override val filter: String = "",
    override val modifiers: List<EventModifier> = listOf()
) : HxTrigger {
    override fun display(): String {
        var result = "every $interval"
        if (filter.isNotEmpty()) {
            result += "[$filter]"
        }
        if (modifiers.isNotEmpty()) {
            result += modifiers.joinToString(" ") { it.display() }
        }
        return result
    }
}