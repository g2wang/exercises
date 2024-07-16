mod app;
mod control_flow;
mod effects;
mod error_handling;
mod forms;
mod lists;
mod parent_child_communication;
mod passing_children_to_components;
mod progress_bar;

use app::App;
use leptos::*;

// This `main` function is the entry point into the app
// It just mounts our component to the <body>
// Because we defined it as `fn App`, we can now use it in a
// template as <App/>
fn main() {
    leptos::mount_to_body(|| view! { <App/> })
}
