use serde_json::Value;
use uuid::Uuid;

fn calculate_unique_id(json: &str) -> Uuid {
    let parsed_json: Value = serde_json::from_str(json).unwrap();
    let json_string = parsed_json.to_string();
    let unique_id = Uuid::new_v5(&Uuid::NAMESPACE_URL, json_string.as_bytes());
    unique_id
}

fn main() {
    let json_1 = r#"{
        "name": "John",
        "age": 30,
        "address": {
            "street": "123 Main St",
            "city": "New York"
        },
        "hobbies": ["reading", "coding"]
    }"#;

    let json_2 = r#"{
        "name": "John",
        "address": {
            "city": "New York",
            "street": "123 Main St"
        },
        "hobbies": [ "reading",  "coding" ],
        "age": 30
    }"#;

    let json_1_unique_id = calculate_unique_id(json_1);
    println!("JSON document 1: {}", json_1);
    println!(
        "The unique ID for the JSON document 1 is: {}",
        json_1_unique_id
    );

    let json_2_unique_id = calculate_unique_id(json_2);
    println!("JSON document 2: {}", json_2);
    println!(
        "The unique ID for the JSON document 2 is: {}",
        json_2_unique_id
    );
}
