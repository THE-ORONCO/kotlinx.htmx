package the.oronco.htmx

import kotlinx.html.Tag

/**
 * https://htmx.org/reference/#attributes-additional
 * @author the_oronco@posteo.net
 * @since 19.06.24
 **/

// https://htmx.org/attributes/hx-boost/
private const val HX_BOOST = "hx-boost"
var Tag.hxBoost : Boolean
    get() = attributes[HX_BOOST]?.equals("true") == true
    set(newValue) { attributes[HX_BOOST] = if (newValue) { "true" } else { "false" } }

// https://htmx.org/attributes/hx-confirm/
private const val HX_CONFIRM = "hx-confirm"
var Tag.hxConfirm : String?
    get() = attributes[HX_CONFIRM]
    set(newValue) { if (newValue != null) { attributes[HX_CONFIRM] = newValue } }

// https://htmx.org/attributes/hx-delete/
private const val HX_DELETE = "hx-delete"
var Tag.hxDelete : String?
    get() = attributes[HX_DELETE]
    set(newValue) { if (newValue != null) { attributes[HX_DELETE] = newValue } }

// https://htmx.org/attributes/hx-disable/
private const val HX_DISABLE = "hx-disable"
var Tag.hxDisable : Boolean
    get() = attributes[HX_BOOST]?.equals("true") ?: false
    set(newValue) { attributes[HX_BOOST] = if (newValue) { "true" } else { "false" } }

// https://htmx.org/attributes/hx-disabled-elt/
private const val HX_DISABLE_ELT = "hx-disable-elt"
var Tag.hxDisableElt : String? // TODO a list of enums for cleaner syntax possible?
    get() = attributes[HX_DISABLE_ELT]
    set(newValue) { if (newValue != null) { attributes[HX_DISABLE_ELT] = newValue } }

// https://htmx.org/attributes/hx-disinherit/
private const val HX_DISINHERIT = "hx-disinherit"
var Tag.hxDisinherit : String? // TODO a list of enums for cleaner syntax possible?
    get() = attributes[HX_DISINHERIT]
    set(newValue) { if (newValue != null) { attributes[HX_DISINHERIT] = newValue } }

// https://htmx.org/attributes/hx-encoding/
private const val HX_ENCODING = "hx-encoding"
var Tag.hxEncoding : String? // TODO enums for cleaner syntax possible?
    get() = attributes[HX_ENCODING]
    set(newValue) { if (newValue != null) { attributes[HX_ENCODING] = newValue } }

// https://htmx.org/attributes/hx-ext/
private const val HX_EXT = "hx-ext"
var Tag.hxExt : String? // TODO interfaces for cleaner syntax possible?
    get() = attributes[HX_EXT]
    set(newValue) { if (newValue != null) { attributes[HX_EXT] = newValue } }

// https://htmx.org/attributes/hx-headers/
private const val HX_HEADERS = "hx-headers"
var Tag.hxHeaders : String? // TODO a map of enums for cleaner syntax possible?
    get() = attributes[HX_HEADERS]
    set(newValue) { if (newValue != null) { attributes[HX_HEADERS] = newValue } }

// https://htmx.org/attributes/hx-history/
private const val HX_HISTORY = "hx-history"
var Tag.hxHistory : Boolean?
    get() = attributes[HX_HISTORY]?.equals("true")
    set(newValue) { if (newValue != null) attributes[HX_HISTORY] = if (newValue) { "true" } else { "false" } }

// https://htmx.org/attributes/hx-history-elt/
private const val HX_HISTORY_ELT = "hx-history-elt"
var Tag.hxHistoryElt : Boolean // TODO check how to create attributeless elements
    get() = attributes.containsKey(HX_HISTORY_ELT)
    set(newValue) { if (newValue) attributes[HX_HISTORY] = "" }

// https://htmx.org/attributes/hx-include/
private const val HX_INCLUDE = "hx-include"
var Tag.hxInclude : String? // TODO a map of enums for cleaner syntax possible?
    get() = attributes[HX_INCLUDE]
    set(newValue) { if (newValue != null) { attributes[HX_INCLUDE] = newValue } }

// https://htmx.org/attributes/hx-indicator/
private const val HX_INDICATOR = "hx-indicator"
var Tag.hxIndicator : String? // TODO a list of enums for cleaner syntax possible?
    get() = attributes[HX_INDICATOR]
    set(newValue) { if (newValue != null) { attributes[HX_INDICATOR] = newValue } }

// https://htmx.org/attributes/hx-inherit/
private const val HX_INHERIT = "hx-inherit"
var Tag.hxInherit : String? // TODO a list of enums for cleaner syntax possible?
    get() = attributes[HX_INHERIT]
    set(newValue) { if (newValue != null) { attributes[HX_INHERIT] = newValue } }

// https://htmx.org/attributes/hx-params/
private const val HX_PARAMS = "hx-params"
var Tag.hxParams : String? // TODO a list of enums for cleaner syntax possible?
    get() = attributes[HX_PARAMS]
    set(newValue) { if (newValue != null) { attributes[HX_PARAMS] = newValue } }

// https://htmx.org/attributes/hx-patch/
private const val HX_PATCH = "hx-patch"
var Tag.hxPatch : String?
    get() = attributes[HX_PATCH]
    set(newValue) { if (newValue != null) { attributes[HX_PATCH] = newValue } }

// https://htmx.org/attributes/hx-preserve/
private const val HX_PRESERVE = "hx-preserve"
var Tag.hxPreserve : String?
    get() = attributes[HX_PRESERVE]
    set(newValue) { if (newValue != null) { attributes[HX_PRESERVE] = newValue } }

// https://htmx.org/attributes/hx-prompt/
private const val HX_PROMPT = "hx-prompt"
var Tag.hxPrompt : String?
    get() = attributes[HX_PROMPT]
    set(newValue) { if (newValue != null) { attributes[HX_PROMPT] = newValue } }

// https://htmx.org/attributes/hx-put/
private const val HX_PUT = "hx-put"
var Tag.hxPut : String?
    get() = attributes[HX_PUT]
    set(newValue) { if (newValue != null) { attributes[HX_PUT] = newValue } }

// https://htmx.org/attributes/hx-replace-url/
private const val HX_REPLACE_URL = "hx-replace-url"
var Tag.hxReplaceUrl : String? // TODO ADTs might be nice here
    get() = attributes[HX_REPLACE_URL]
    set(newValue) { if (newValue != null) { attributes[HX_REPLACE_URL] = newValue } }

// https://htmx.org/attributes/hx-request/
private const val HX_REQUEST = "hx-request"
var Tag.hxRequest : String? // TODO Map of Enums might be nice here
    get() = attributes[HX_REQUEST]
    set(newValue) { if (newValue != null) { attributes[HX_REQUEST] = newValue } }

// https://htmx.org/attributes/hx-sync/
private const val HX_SYNC = "hx-sync"
var Tag.hxSync : String? // TODO Map of Enums might be nice here
    get() = attributes[HX_SYNC]
    set(newValue) { if (newValue != null) { attributes[HX_SYNC] = newValue } }

// https://htmx.org/attributes/hx-validate/
private const val HX_VALIDATE = "hx-validate"
var Tag.hxValidate : Boolean?
    get() = attributes[HX_HISTORY]?.equals("true")
    set(newValue) { if (newValue != null) attributes[HX_HISTORY] = if (newValue) { "true" } else { "false" } }

// https://htmx.org/attributes/hx-vars/
private const val HX_VARS = "hx-vars"
@Deprecated(message = "hx-vars has been deprecated in favor of hx-vals, which is safer by default.", replaceWith = ReplaceWith(expression = "hxVals"))
var Tag.hxVars : Boolean?
    get() = attributes[HX_VARS]?.equals("true")
    set(newValue) { if (newValue != null) attributes[HX_VARS] = if (newValue) { "true" } else { "false" } }
