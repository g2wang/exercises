use leptos::*;

// Often, you want to pass children views to another component.
// There are two basic patterns for doing this:
// - "render props": creating a component prop that takes a function that creates a view.
// - the `children` prop: a special property that contains content passed as the children of
// a component in your view, not as a property.

/// Displays a `render_prop` and some children within markup
#[component]
pub fn TakesChildren<F, IV>(
    /// takes a function of type F that returns a IV type that implements IntoView
    render_prop: F,
    /// children prop takes the `Children` type
    /// which is an alias for `Box<dyn FnOnce() -> Fragment>`
    children: Children,
) -> impl IntoView
where
    F: Fn() -> IV,
    IV: IntoView,
{
    view! {
        <h1>"This is the " <code>"<TakesChildren/>"</code>" component"</h1>
        <h2>"From calling " <code>"render_prop()"</code></h2>
        {render_prop()}
        <hr/>
        <h2>"Form the " <code> "children" </code> " prop"</h2>
        {children()}
    }
}

/// wraps each child in <li/>  and then embeds them to an ordered list `<ol/>`
#[component]
pub fn WrapsChildren(
    /// children is an type alias for Box<dyn FnOnce() -> Fragment>
    children: Children,
) -> impl IntoView {
    let wrapped_children = children()
        .nodes
        .into_iter()
        .map(|child| view! {<li>{child}</li>})
        .collect::<Vec<_>>();
    view! {
        <h1>"This is the " <code>"WrapsChildren"</code> " component"</h1>
        // wrap the wrapped_children in an ordered list <ol/>
        <ol>{wrapped_children}</ol>
    }
}
