use leptos::*;

#[component]
pub fn ErrorHandling() -> impl IntoView {
    let (value, set_value) = create_signal(Ok(0));

    // used by the input html element on input event to parse the input into an i32
    let on_input = move |ev| set_value(event_target_value(&ev).parse::<i32>());

    view! {
        <label>
        "Type a number (or non-numeric value to see errors)"
        // normally you would use <input type="number" on:input = on_input/>
        // but to show the error messages, we use input type text here
        <input type="text" on:input = on_input/>
        // the <ErrorBoundery/> component below is trying to show the on_input parsing
        // result that is put into the value variable. If there is a parsing error,
        // the fallback value is shown. Otherwise, the child of the <ErrorBoundery/>
        // component is shown.
        <ErrorBoundary fallback = |errors| view! {
            <div class = "error">
                <p>Not a number! Errors:</p>
                <ul>
                    { move || errors.get().into_iter()
                        .map(|(_, e)| view! {<li>{e.to_string()}</li>} )
                        .collect::<Vec<_>>()
                    }
                </ul>
            </div>
        }
        >
        // since value is of type Result<i32, ParseIntError>
        // it can have 2 values: either Ok(i32) or Err(ParseIntError)
        // On Ok(i32), i32 will be displayed below.
        // On Err(ParseIntError), the error will be shown in the follback above
        // the value is a ReadSignal, so it is reactive.
        <p>"You entered: " <strong>{value}</strong></p>
        </ErrorBoundary>
        </label>
    }
}
