use leptos::{ev::SubmitEvent, *};

#[component]
pub fn ControlledComponent() -> impl IntoView {
    // create a signal to hold the value
    let (name, set_name) = create_signal("Controlled".to_string());

    view! {
        <input type="text"
            // fire an event whenever the input changes
            on:input = move |ev| {
                // event_target_value is a Leptos helper function similar to
                // the JavaScript event.target.value with necessary type casting for rust.
                set_name(event_target_value(&ev));
            }

            // the `prop:` syntax updates a DOM peroperty as opposed to an HTML attribute
            // IMPORTANT: the `value` HTML attribute only sets the initial value of the input HTML
            // element, which will change when you start typing into the input field.
            // the `value` property, however, sets the current value of the DOM.
            prop:value = name
        />
        <p>"Name is: " {name}</p>
    }
}

#[component]
pub fn UncontrolledComponent() -> impl IntoView {
    // import the type for <input>
    use leptos::html::Input;

    let (name, set_name) = create_signal("Uncontrolled".to_string());

    // we'll use a NodeRef to store a reference to the input element
    // which will be filled when the element is created.
    let input_element: NodeRef<Input> = create_node_ref();

    // this `on_submit_handler` closure will be called when the form `sumbmit` event fires,
    // opon which the value of the input will be stored into the signal.
    let on_submit_handler = move |ev: SubmitEvent| {
        // first stop the page from reloading
        ev.prevent_default();

        // next, extract the value from the input_element NodeRef
        let value = input_element()
            // envent can only fire after the view is mounted to the DOM
            // which guarantees that the NodeRef will be `Some`
            // and the following `expect` call will not panic
            .expect("the <input> element should have already been attached to the DOM")
            // `NodeRef` implements `Defef` for the DOM element type
            // therefor, we can call the `HtmlInputElement::value` method
            // to get the current value of the input_element
            .value();

        set_name(value);
    }; // end of on_submit_handler closure

    // begin the view return value
    view! {
        <form on:submit = on_submit_handler>
            <input type = "text"
                // use the `value` HTML *attribute* to specify the initial value,
                // after which, the browser will maintain the state of the input
                value = name

                // bind this element to the input_element NodeRef
                node_ref = input_element
            />
            // the submit button
            <input type = "submit" value = "submit" />
        </form>
        // after the form, we show the name <p>
        <p>"Name is: " {name}</p>
    }
}
