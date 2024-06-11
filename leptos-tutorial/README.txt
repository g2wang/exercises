Leptos is a web framework for building client-side applications in Rust. Below is an example of a Leptos code that builds a client-side rendering (CSR) single-page application with a menu column on the left pane and a main content pane on the right.

First, ensure you have the required dependencies in your `Cargo.toml` file:

```toml
[dependencies]
leptos = "0.2"  # Ensure you have the correct version of Leptos
```

Next, you can create a Rust file (e.g., `main.rs`) with the following code:

```rust
use leptos::*;

#[component]
fn Menu(cx: Scope) -> impl IntoView {
    view! { cx,
        <div class="menu">
            <ul>
                <li><a href="#home" on:click=move |_| set_content("Home")> "Home" </a></li>
                <li><a href="#about" on:click=move |_| set_content("About")> "About" </a></li>
                <li><a href="#contact" on:click=move |_| set_content("Contact")> "Contact" </a></li>
            </ul>
        </div>
    }
}

#[component]
fn MainContent(cx: Scope) -> impl IntoView {
    let (content, set_content) = create_signal(cx, "Home".to_string());

    provide_context(cx, set_content.clone());

    view! { cx,
        <div class="main-content">
            <h1> {move || content.get()} </h1>
            <p> {move || match content.get().as_str() {
                "Home" => "Welcome to the home page!",
                "About" => "Learn more about us on this page.",
                "Contact" => "Get in touch with us!",
                _ => "Page not found.",
            }} </p>
        </div>
    }
}

#[component]
fn App(cx: Scope) -> impl IntoView {
    view! { cx,
        <div class="container">
            <Menu/>
            <MainContent/>
        </div>
    }
}

fn main() {
    console_error_panic_hook::set_once();

    leptos::start(App);
}
```

This code sets up a basic structure for a Leptos single-page application with a menu and main content pane.

- The `Menu` component contains links that change the content of the main pane.
- The `MainContent` component displays content based on the current selection from the menu.
- The `App` component ties everything together in a container.

For the CSS part, you can add styles to make the layout look like a menu column on the left and a main content pane on the right. Create a `styles.css` file with the following content:

```css
body {
    margin: 0;
    font-family: Arial, sans-serif;
}

.container {
    display: flex;
    height: 100vh;
}

.menu {
    width: 200px;
    background-color: #f4f4f4;
    padding: 15px;
}

.menu ul {
    list-style-type: none;
    padding: 0;
}

.menu li {
    margin-bottom: 10px;
}

.menu a {
    text-decoration: none;
    color: #333;
}

.menu a:hover {
    text-decoration: underline;
}

.main-content {
    flex-grow: 1;
    padding: 20px;
}

h1 {
    margin-top: 0;
}
```

Make sure to link this CSS file in your HTML file:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leptos SPA</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div id="root"></div>
    <script src="pkg/bundle.js"></script>
</body>
</html>
```

This setup should give you a basic SPA with a menu on the left and dynamic content on the right using Leptos.
