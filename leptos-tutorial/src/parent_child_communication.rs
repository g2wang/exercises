use leptos::{ev::MouseEvent, *};

// demonstrate 4 ways child components can communicate with parent
// 1) <ButtonA/>: passing a WriteSignal as a prop of the child
// 2) <ButtonB/>: passing a closure as a prop of the child
// 3) <ButtonC/>: adding an `on:` event listener to a component
// 4) <ButtonD/>: providing a context for the components to pass WriteSignal

#[derive(Copy, Clone)]
pub struct SmallcapsContext(pub WriteSignal<bool>);

/// Button receives a signal setter and updates the signal
#[component]
pub fn ButtonA(setter: WriteSignal<bool>) -> impl IntoView {
    view! {
        <button
            on:click = move |_| setter.update(|value| *value = !*value)
        >
            "Toggle Red"
        </button>
    }
}

/// ButtonB receives a closure
#[component]
pub fn ButtonB<F>(on_click: F) -> impl IntoView
where
    F: Fn(MouseEvent) + 'static,
{
    view! {
        <button
            on:click = on_click
        >
            "Toggle Right"
        </button>
        // note: in an ordianary function,
        // ButtonB could take on_click: impl Fu(MouseEvent) + 'sttatic
        // and save you from typing out the generic.
        // The micro actually expands to definal a
        // struct ButtonBProps<F> where F: Fn<MoucseEvent> + 'static {
        //    on_click: F
        // }
        //
        // This is what allows us to have named props in our component invocation
        // instead of an ordered list of function arguments.
        //
        // If Rust ever had named fucntion arguments, we could drop this requirement
    }
}

/// ButtonC is a dummy: it renders a button but does not handle its click.
/// Instead, the parent adds an event listner to it
#[component]
pub fn ButtonC() -> impl IntoView {
    view! {
        <button>"Goggle Italics"</button>
    }
}

/// ButtonD is similar to ButtonA. But instead of passing SignalWriter as a prop, we put the
/// SignalWrite to a context in the parent and ButtonD gets it from the context
#[component]
pub fn ButtonD() -> impl IntoView {
    let setter = use_context::<SmallcapsContext>().unwrap().0;
    view! {
        <button
            on:click = move |_| setter.update(|value| *value = !*value)
        >
            "Toggle Small Caps"
        </button>
    }
}
