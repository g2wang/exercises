use leptos::*;
// Composing different components together is how we build
// user interfaces. Here, we'll define a reusable <StaticList/> and <DynmicList/>.
// You'll see how doc comments can be used to document components
// and their properties.

/// a list of counters, without the ability to add or remove any
#[component]
pub fn StaticList(
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

/// a list of counters that allows you to add and remove counters
#[component]
pub fn DynamicList(
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
