mod app;
mod control_flow;
mod error_handling;
mod forms;
mod lists;
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
