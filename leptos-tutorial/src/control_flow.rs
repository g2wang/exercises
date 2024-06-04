use leptos::*;

#[component]
pub fn ControlFlow() -> impl IntoView {
    let (value, set_value) = create_signal(0);
    let is_odd = move || (value() & 1) == 1; // bitwise and with 1 equals 1 => is_odd
    let odd_text = move || if is_odd() { Some("How odd!") } else { None };

    view! {
        <h1>"Control Flow"</h1>

        // simple UI to update and show a value
        <button on:click = move |_| set_value.update(|n| *n += 1 )>
            "+1"
        </button>
        <p>"Value is: " {value}</p>

        <hr/>
        <h2><code>"Option<T>"</code></h2>
        // for any `T` that implements `IntoView`, so does `Option<T>`
        <p>{odd_text}</p>
        // the following line uses `Option` methods on odd_text() which returns an Option<&str>
        <p>{move || odd_text().map(|text| format!("using odd_text().map to show the length of text 'How odd!': {}", text.len()))}</p>

        <h2>"Conditional Logic"</h2>
        // You can do if-then-else in severial ways
        // a. use "if" expression in a function
        //   re-render every time value changes, good for lightweight UI
        <p>
            {
                move || if is_odd() {
                    "using {move || if ...}: Odd"
                } else {
                    "using {move || if ...}: Even"
                }
            }
        </p>
        // b. use the class:hidden attribute of an html element that toggles very often
        // which is efficient because the the element is not destroyed between states
        // `hidden` class is defined in `index.html`
        <p class:hidden = is_odd>"Using class:hidden: Appears if even"</p>

        // c. use <Show/> component which renders the fallback and child only once, lazily, and
        // toggles between them when needed, more efficient than the { move || if ... } block for
        // heavyweight re-rendering
        <Show when = is_odd
        fallback = || view! {<p>"using <Show/>: Even steven"</p>}
        >
        <p>"using <Show/>: Oddment"</p>
        </Show>

        // d. use `bool::then`, which returns an Option to toggle
        {move || is_odd().then(|| view! {<p>"using bool::then to show Odditity"</p>})}


        <h2>"Conterting between Types"</h2>
        // e. if branches return different types, you can convert between then using
        // `.into_any()` for different html types or `.into_view()` for all view types
        {move || match is_odd() {
            true if value() == 1 => {
                // returns HtmlElement <pre/>, so we convert it using into_any()
                view! { <pre>"using into_any(): One" </pre>}.into_any()
            },
            false if value() == 2 => {
                // returns HtmlElement <p/>, so we convert it using into_any()
                view! { <p>"using into_any(): Two" </p>}.into_any()
            }
            _ => view! {<textarea>{value()}</textarea>}.into_any()
        }}
    }
}
