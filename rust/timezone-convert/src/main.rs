use chrono::{Datelike, NaiveDate, NaiveDateTime, NaiveTime, TimeZone, Timelike, Utc};
use regex::Regex;
use std::env;

fn main() {
    let args: Vec<String> = env::args().collect();
    let mut est = "";
    if args.len() > 1 {
        est = &args[1].as_str();
    }
    let time_pattern = Regex::new(
        r"^(?:(\d{4})-(\d{2})-(\d{2})(?:T| ))?(\d{1,2}):(\d{1,2})(?::(\d{1,2})(?:\.\d+)*)?$",
    )
    .unwrap();

    let est_tz = chrono_tz::America::Toronto;
    let jst_tz = chrono_tz::Asia::Tokyo;
    let ist_tz = chrono_tz::Asia::Calcutta;

    let now = Utc::now();
    let est_now = now.with_timezone(&est_tz);

    let mut year = est_now.year();
    let mut month = est_now.month();
    let mut day = est_now.day();
    let mut hour = est_now.hour();
    let mut minute = est_now.minute();
    let mut second = 0u32;

    if let Some(captures) = time_pattern.captures(est) {
        if let Some(y) = captures.get(1) {
            year = y.as_str().parse::<i32>().unwrap();
        }
        if let Some(m) = captures.get(2) {
            month = m.as_str().parse::<u32>().unwrap();
        }
        if let Some(d) = captures.get(3) {
            day = d.as_str().parse::<u32>().unwrap();
        }
        if let Some(h) = captures.get(4) {
            hour = h.as_str().parse::<u32>().unwrap();
        }
        if let Some(n) = captures.get(5) {
            minute = n.as_str().parse::<u32>().unwrap();
        }
        if let Some(s) = captures.get(6) {
            second = s.as_str().parse::<u32>().unwrap();
        }
    } else {
        println!("No match found");
    }

    let toronto_date = NaiveDate::from_ymd_opt(year, month, day).unwrap();
    let toronto_time = NaiveTime::from_hms_opt(hour, minute, second).unwrap();
    let toronto_date_time = NaiveDateTime::new(toronto_date, toronto_time)
        .and_local_timezone(est_tz)
        .unwrap();

    let utc_date_time = Utc
        .timestamp_millis_opt(toronto_date_time.timestamp_millis())
        .unwrap();

    let tokyo_date_time = jst_tz
        .timestamp_millis_opt(toronto_date_time.timestamp_millis())
        .unwrap();

    let mumbai_date_time = ist_tz
        .timestamp_millis_opt(toronto_date_time.timestamp_millis())
        .unwrap();

    println!("{}", toronto_date_time);
    println!("{}", utc_date_time);
    println!("{}", tokyo_date_time);
    println!("{}", mumbai_date_time);
}
