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

/// a list of counters, without the ability to add or remove any
#[component]
fn StaticList(
    /// how many counters to include in this list
    length: usize,
) -> impl IntoView {
    // create counter signals that start at incrementing numbers
    let counters = (1..=length).map(|idx| create_signal(idx));
    // when you have a list that does not change, you can manipuate
    // it using ordinary rust iterrators
    // and collect it into a Vec<_> to insert it into the DOM
    let counter_buttons = counters
        .map(|(count, set_count)| {
            view! {
                <li>
                    <button on:click = move |_| set_count.update(|n| *n += 1)
                    >
                        {count}
                    </button>
                </li>
            }
        })
        .collect::<Vec<_>>();

    // note that if `counter_buttons` were a reactive list and its value
    // changed, this would be very inefficient:
    // it would render every row every time the list chagned.
    view! {
        <ul>{counter_buttons}</ul>
    }
}

// The #[component] macro marks a function as a reusable component
// Components are the building blocks of your user interface
// They define a reusable unit of behavior
#[component]
fn App() -> impl IntoView {
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

        <p>"---------------------------------------------"</p>

        <h1>"Iteration"</h1>
        <h2>"Static List"</h2>
        <p>Use this pattern if the list is static</p>
        <StaticList length = 3 />

        <h2>"Dynamic List"</h2>
        <p>Use this pattern if the rows in your list will change</p>
        <DynamicList initial_length = 3 />

    }
}

/// a list of counters that allows you to add and remove counters
#[component]
fn DynamicList(
    /// the number of counters to begin with
    initial_length: usize,
) -> impl IntoView {
    // this dynamic list will uset the <For/> component.
    // <For/> is a keyed list, which means that each row has
    // a defined key. If the key does not change, the row
    // will not be re-rendered. When the list changes,
    // only the minimum number of changes will be made to the DOM.
    // `next_counter_id` will let us generte unique IDs by incrementing
    // the ID by one each time we add a counter.
    let mut next_coutner_id = initial_length;

    // we generte an initial list the same way as in <StaticList/>
    // but this time, we include the ID along with the signal.
    let initial_counters = (0..initial_length)
        .map(|id| (id, create_signal(id + 1)))
        .collect::<Vec<_>>();

    // now we store the inital list in a signal, which will allow us to modify the list over time
    // by adding or removing counters, and the list will change reactively.
    let (counters, set_counters) = create_signal(initial_counters);

    let add_counter = move |_| {
        // create a signal for the new counter
        let sig = create_signal(next_coutner_id + 1);
        // add this new counter to the list of counters
        set_counters.update(move |counters| {
            // since `update gives us a `&mut T`, we can use normal Vec methods push
            counters.push((next_coutner_id, sig));
        });
        // increment the next_counter_id so that it will be unique
        next_coutner_id += 1;
    };

    view! {
        <div>
            <button on:click = add_counter>
                "Add counter"
            </button>
            <ul>
                // the <For/> component is essential here.
                // it allows for efficient rendering of the keyed list
                <For
                    // `each` takes any function that returns an iterator usually of a signal or
                    // derived signal. If the list is not reactive, just render a Vec<_> instead of
                    // a <For/>
                    each = counters
                    // the key should be unique and stable for each row.
                    // using an index is usually a bad idea unless the list can only grow
                    // because moving items around will change their indices will rerender them
                    key = |counter| counter.0
                    // `children` receives each item from your `each` iterator and returns a view
                    children = move |(id, (count, set_count))| {
                        view! {
                            <li>
                                <button on:click = move |_| set_count.update(|n| *n += 1)>
                                    {count}
                                </button>
                                <button on:click = move |_| {
                                    set_counters.update(|counters| {
                                        counters.retain(|(counter_id, _)| counter_id != &id)
                                    })
                                    }
                                >
                                    "Remove"
                                </button>
                            </li>
                        }
                    }
                />
            </ul>
        </div>
    }
}

// This `main` function is the entry point into the app
// It just mounts our component to the <body>
// Because we defined it as `fn App`, we can now use it in a
// template as <App/>
fn main() {
    leptos::mount_to_body(|| view! { <App/> })
}
