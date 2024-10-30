package the.oronco.htmx

import org.intellij.lang.annotations.Language
import the.oronco.htmx.HtmxEvent.Companion.abort


/**
 * https://htmx.org/reference/#events
 * TODO make these closures that take a JS string in the context of the specific event (this would allow typing through doc comments in) and return a Pair<String, String>
 *      this would allow for nicer syntax highlighting while writing the JS to process the events
 * @author the_oronco@posteo.net
 * @since 19.06.24
 **/
class HtmxEvent {
    companion object {
        /**send this event to an element to abort a request*/
        val abort: String = "abort"

        /**triggered after an AJAX request has completed processing a successful response*/
        val afterOnLoad: String = "after-on-load"

        /**triggered after htmx has initialized a node*/
        val afterProcessNode: String = "after-process-node"

        /**triggered after an AJAX request has completed*/
        val afterRequest: String = "after-request"

        /**triggered after the DOM has settled*/
        val afterSettle: String = "after-settle"

        /**triggered after new content has been swapped in*/
        val afterSwap: String = "after-swap"

        /**triggered before htmx [disables](https://htmx.org/attributes/hx-disable/) an element or removes it from the DOM*/
        val beforeCleanupElement: String = "before-cleanup-element"

        /**triggered before any response processing occurs*/
        val beforeOnLoad: String = "before-on-load"

        /**triggered before htmx initializes a node*/
        val beforeProcessNode: String = "before-process-node"

        /**triggered before an AJAX request is made*/
        val beforeRequest: String = "before-request"

        /**triggered before a swap is done, allows you to configure the swap*/
        val beforeSwap: String = "before-swap"

        /**triggered just before an ajax request is sent*/
        val beforeSend: String = "before-send"

        /**triggered before the [View Transition](https://developer.mozilla.org/en-US/docs/Web/API/View_Transitions_API) wrapped swap occurs*/
        val beforeTransition: String = "before-transition"

        /**triggered before the request, allows you to customize parameters, headers*/
        val configRequest: String = "config-request"

        /**triggered after a trigger occurs on an element, allows you to cancel (or delay) issuing the AJAX request*/
        val confirm: String = "confirm"

        /**triggered on an error during cache writing*/
        val historyCacheError: String = "history-cache-error"

        /**triggered on a cache miss in the history subsystem*/
        val historyCacheMiss: String = "history-cache-miss"

        /**triggered on a unsuccessful remote retrieval*/
        val historyCacheMissError: String = "history-cache-miss-error"

        /**triggered on a successful remote retrieval*/
        val historyCacheMissLoad: String = "history-cache-miss-load"

        /**triggered on a successful remote retrieval*/
        val historyRestore: String = "history-restore"

        /**triggered before content is saved to the history cache*/
        val beforeHistorySave: String = "before-history-save"

        /**triggered when new content is added to the DOM*/
        val load: String = "load"

        /**triggered when an element refers to an SSE event in its trigger, but no parent SSE source has been defined*/
        val noSseSourceError: String = "no-ssesource-error"

        /**triggered when an exception occurs during the onLoad handling in htmx*/
        val onLoadError: String = "on-load-error"

        /**triggered after an out-of-band element as been swapped in*/
        val oobAfterSwap: String = "oob-after-swap"

        /**triggered before an out-of-band element swap is done, allows you to configure the swap*/
        val oobBeforeSwap: String = "oob-before-swap"

        /**triggered when an out-of-band element does not have a matching ID in the current DOM*/
        val oobErrorNoTarget: String = "oob-error-no-target"

        /**triggered after a prompt is shown*/
        val prompt: String = "prompt"

        /**triggered after an url is pushed into history*/
        val pushedIntoHistory: String = "pushed-into-history"

        /**triggered when an HTTP response error (non-200 or 300 response code) occurs*/
        val responseError: String = "response-error"

        /**triggered when a network error prevents an HTTP request from happening*/
        val sendError: String = "send-error"

        /**triggered when an error occurs with an SSE source*/
        val sseError: String = "sse-error"

        /**triggered when an SSE source is opened*/
        val sseOpen: String = "sse-open"

        /**triggered when an error occurs during the swap phase*/
        val swapError: String = "swap-error"

        /**triggered when an invalid target is specified*/
        val targetError: String = "target-error"

        /**triggered when a request timeout occurs*/
        val timeout: String = "timeout"

        /**triggered before an element is validated*/
        val validationValidate: String = "validation:validate"

        /**triggered when an element fails validation*/
        val validationFailed: String = "validation:failed"

        /**triggered when a request is halted due to validation errors*/
        val validationHalted: String = "validation:halted"

        /**triggered when an ajax request aborts*/
        val xhrAbort: String = "xhr:abort"

        /**triggered when an ajax request ends*/
        val xhrLoadend: String = "xhr:loadend"

        /**triggered when an ajax request starts*/
        val xhrLoadstart: String = "xhr:loadstart"

        /**triggered periodically during an ajax request that supports progress events*/
        val xhrProgress: String = "xhr:progress"
    }
}
