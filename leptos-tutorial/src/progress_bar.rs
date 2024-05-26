use leptos::*;
// Composing different components together is how we build
// user interfaces. Here, we'll define a reusable <ProgressBar/>.
// You'll see how doc comments can be used to document components
// and their properties.

/// Shows progress toward a goal.
#[component]
pub fn ProgressBar(
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
