use crate::control_flow::ControlFlow;
use crate::effects::{EffectVsDerivedSignal, EffectsApp, ManualVersion};
use crate::error_handling::ErrorHandling;
use crate::forms::{ControlledComponent, UncontrolledComponent};
use crate::lists::{DynamicList, StaticList};
use crate::parent_child_communication::{ButtonA, ButtonB, ButtonC, ButtonD, SmallcapsContext};
use crate::passing_children_to_components::{TakesChildren, WrapsChildren};
use crate::progress_bar::ProgressBar;
use leptos::*;

// Composing different components together is how we build
// user interfaces.

// The #[component] macro marks a function as a reusable component
// Components are the building blocks of your user interface
// They define a reusable unit of behavior
#[component]
pub fn App() -> impl IntoView {
    // here we create a reactive signal
    // and get a (getter, setter) pair
    // signals are the basic unit of change in the framework
    // we'll talk more about them later
    let (count, set_count) = create_signal(0);
    let double_count = move || count() * 2;
    // the `view` macro is how we define the user interface
    // it uses an HTML-like format that can accept certain Rust values

    let (red, set_red) = create_signal(false);
    let (right, set_right) = create_signal(false);
    let (italics, set_italics) = create_signal(false);
    let (smallcaps, set_smallcaps) = create_signal(false);
    provide_context(SmallcapsContext(set_smallcaps));

    // args for calling TakesChildren components
    let (items, _set_items) = create_signal(vec![0, 1, 2]);
    let render_prop = move || {
        // items.with(...) reacts to the value without cloning
        // by applying a function passed to it which is the `len`
        // method on a `Vec<_>` directly
        let len = move || items.with(Vec::len);
        view! {
            <p>"Length: " {len}</p>
        }
    };

    view! {
        <button
            // on:click will run whenever the `click` event fires
            // every event handler is defined as `on:{eventname}`

            // we're able to move `set_count` into the closure
            // because signals are Copy and 'static
            on:click = {move |_| {
                set_count.update(|n| *n += 10);
            }}
            // class = ("red", move || x() % 2 == 1)
            style = "position: absolute"
            // and toggle individual CSS properties with `style:`
            style = ("left", move || format!("{}px", count() + 100))
            style = ("background-color", move || format!("rgb({}, {}, 100)", count(), 100))
            style = ("max-width", "400px")
            // Set a CSS variable for stylesheet use
            style = ("--columns", count)
        >
            // text nodes in RSX should be wrapped in quotes,
            // like a normal Rust string
            "Click to move"
        </button>
        <p>
            <strong>"Reactive: "</strong>
            // you can insert Rust expressions as values in the DOM
            // by wrapping them in curly braces
            // if you pass in a function, it will reactively update
            {move || count.get()}
        </p>
        <p>
            <strong>"Reactive shorthand: "</strong>
            // signals are functions, so we can remove the wrapping closure
            {count}
        </p>
        <p>
            <strong>"Not reactive: "</strong>
            // NOTE: if you write {count()}, this will *not* be reactive
            // it simply gets the value of count once
            {count()}
        </p>
        <progress
            max = "50"
            value = double_count
        />
        <p>
            <strong>"double count: "</strong>
            { double_count }
        </p>
        // In and editor with rust-analyzer support, try hovering over `ProgressBar`,
        // `max`, or `progress` to see the docs we defined above
        <ProgressBar max = 50 progress = count />
        // Let's use the default max of 100, which makes this ProgressBar half as fast.
        <ProgressBar progress = count />
        // Signal::derive creates a signal wrapper of the derived double_count signal,
        // which makes this ProgressBar move twice as fast
        <ProgressBar max = 50 progress = Signal::derive(double_count)/>

        <hr/>
        <h1>"Iteration"</h1>
        <h2>"Static List"</h2>
        <p>Use this pattern if the list is static</p>
        <StaticList length = 3 />

        <h2>"Dynamic List"</h2>
        <p>Use this pattern if the rows in your list will change</p>
        <DynamicList initial_length = 3 />

        <hr/>
        <h1>"Controlled Component"</h1>
        <ControlledComponent/>
        <h1>"Uncontrolled Component"</h1>
        <UncontrolledComponent/>

        <hr/>
        <h1>"Control Flow"</h1>
        <ControlFlow/>

        <hr/>
        <h1>"Error Handling"</h1>
        <ErrorHandling/>

        <hr/>
        <h1>"Parent-Child Communication"</h1>
        <p
            class:red = red
            class:right = right
            class:italics = italics
            class:smallcaps = smallcaps
        >
            "Lorem ipsum sit dolor amet."
        </p>

        // ButtonA gets SignalWitter passed here
        <ButtonA setter = set_red />

        // ButtonB gets a closure
        <ButtonB on_click = move |_| set_right.update(|value| *value = !*value) />

        // ButtonC gets a regular event listener in-situ
        <ButtonC on:click = move |_| set_italics.update(|value| *value = !*value) />

        // ButtonD gets its setter form the SmallcapsContext
        <ButtonD/>

        // calling TakesChildren and WrapsChildren components
        <hr/>
        <TakesChildren
        // for component props, you can shorthand `render_prop=render_prop` => `render_prop`
        // this does not work with HTML element attributes
          render_prop = render_prop
        >
            <p>"In TakesChildren: Here is a child."</p>
            <p>"In TakesChildren: Here is another child."</p>
            <p>"In TakesChildren: Here is a third child."</p>
        </TakesChildren>

        <hr />

        <WrapsChildren>
            <p>"Here is a child."</p>
            <p>"Here is another child."</p>
        </WrapsChildren>

        <hr />
        <h1>"Effects and Derived Signals"</h1>
        <EffectsApp/>

        <h3>"Manual version using form on_change"</h3>
        <ManualVersion/>
        <h3>"Derived Signal"</h3>
        <EffectVsDerivedSignal/>

    }
}
