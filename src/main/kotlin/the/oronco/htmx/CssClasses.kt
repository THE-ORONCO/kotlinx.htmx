package the.oronco.htmx

/**
 * https://htmx.org/reference/#classes
 * @author the_oronco@posteo.net
 * @since 19.06.24
 **/
enum class CssClasses(val className: String) {

    /** Applied to a new piece of content before it is swapped, removed after it is settled. **/
    HTMX_ADDED("htmx-added"),

    /** A dynamically generated class that will toggle visible (opacity:1) when a htmx-request class is present **/
    HTMX_INDICATOR("htmx-indicator"),

    /** Applied to either the element or the element specified with hx-indicator while a request is ongoing **/
    HTMX_REQUEST("htmx-request"),

    /** Applied to a target after content is swapped, removed after it is settled. The duration can be modified via hx-swap. **/
    HTMX_SETTLING("htmx-setting"),

    /** Applied to a target before any content is swapped, removed after it is swapped. The duration can be modified via hx-swap. **/
    HTMX_SWAPPING("htmx-swapping"),
}
