use leptos::*;

// Composing different components together is how we build
// user interfaces. Here, we'll define a reusable <ProgressBar/>.
// You'll see how doc comments can be used to document components
// and their properties.

/// Shows progress toward a goal.
#[component]
fn ProgressBar(
    // Marks this as an optional prop. It defaults to the specified value.
    // If the default value is not specified, it will default to the default
    // value of its type, '0' for this case
    /// The maximum value of the progress bar.
    #[prop(default = 100)]
    max: u16,
    // will run the `.into()` on the value passed into the prop.
    #[prop(into)]
    // Signal<T> is a wrapper for several reactive types.
    // It can be helpful in component APIs like this, where we
    // might want to take any kind of reactive value
    // How much progress should be displayed.
    progress: Signal<i32>,
) -> impl IntoView {
    view! {
        <progress
            max = {max}
            value = progress
        />
        <br/>
    }
}

// The #[component] macro marks a function as a reusable component
// Components are the building blocks of your user interface
// They define a reusable unit of behavior
#[component]
fn app() -> impl IntoView {
    // here we create a reactive signal
    // and get a (getter, setter) pair
    // signals are the basic unit of change in the framework
    // we'll talk more about them later
    let (count, set_count) = create_signal(0);
    let double_count = move || count() * 2;
    // the `view` macro is how we define the user interface
    // it uses an HTML-like format that can accept certain Rust values
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
    }
}

// This `main` function is the entry point into the app
// It just mounts our component to the <body>
// Because we defined it as `fn App`, we can now use it in a
// template as <App/>
fn main() {
    leptos::mount_to_body(|| view! { <App/> })
}
