use chrono::{DateTime, NaiveDateTime, TimeZone, Utc};

fn toronto_time_to_utc(toronto_time: &str) -> Option<DateTime<Utc>> {
    let toronto_tz = chrono_tz::America::Toronto;
    let toronto_dt = NaiveDateTime::parse_from_str(toronto_time, "%Y-%m-%d %H:%M:%S")
        .ok()?
        .and_local_timezone(toronto_tz)
        .unwrap();
    let r = Utc
        .timestamp_millis_opt(toronto_dt.timestamp_millis())
        .unwrap();
    Some(r)
}

fn main() {
    let toronto_time = "2023-03-10 10:30:00";
    if let Some(utc_time) = toronto_time_to_utc(toronto_time) {
        println!("Toronto time: {}", toronto_time);
        println!("UTC time: {}", utc_time.to_rfc3339());
    } else {
        println!("Invalid Toronto time format");
    }
}
