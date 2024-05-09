use leptos::{component, create_signal, mount_to_body, view, IntoView};

fn main() {
    mount_to_body(|| view! { <App/> })
}

#[component]
fn app() -> impl IntoView {
    let (count, set_count) = create_signal(0);
    view! {
        <button
            on:click = move |_| {
                // on stable, this is set_count.set(3);
                set_count(3);
            }
        >
            "Click me: "
            // on stable, this is {move || count.get()}
            { count }
        </button>
    }
}
