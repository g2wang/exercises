use chrono::{Datelike, Local, NaiveDate, NaiveDateTime, NaiveTime, TimeZone, Timelike, Utc};
use clap::Parser;
use regex::Regex;

#[derive(Parser)]
#[command(author, version, about, long_about = None)]
struct Cli {
    local_date_time: Option<String>,
}

fn main() {
    let cli = Cli::parse();
    let arg = cli.local_date_time.as_deref();
    let time_pattern = Regex::new(
        r"^(?:(\d{4})-(\d{2})-(\d{2})(?:T| ))?(\d{1,2}):(\d{1,2})(?::(\d{1,2})(?:\.\d+)*)?$",
    )
    .unwrap();

    let local_now = Local::now();
    let local_tz = local_now.timezone();

    let mut year = local_now.year();
    let mut month = local_now.month();
    let mut day = local_now.day();
    let mut hour = local_now.hour();
    let mut minute = local_now.minute();
    let mut second = local_now.second();

    if let Some(ldt) = arg {
        if let Some(captures) = time_pattern.captures(ldt) {
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
                if let Some(s) = captures.get(6) {
                    second = s.as_str().parse::<u32>().unwrap();
                } else {
                    second = 0;
                }
            }
        } else {
            println!(
                "Invalid argument {ldt} - must be of format HH:mm[:ss] or yyyy-MM-dd HH:mm[:ss]"
            );
        }
    }

    let local_date = NaiveDate::from_ymd_opt(year, month, day).unwrap();
    let local_time = NaiveTime::from_hms_opt(hour, minute, second).unwrap();
    let local_date_time = NaiveDateTime::new(local_date, local_time)
        .and_local_timezone(local_tz)
        .unwrap();

    let time_millis = local_date_time.timestamp_millis();

    let est_tz = chrono_tz::America::Toronto;
    let jst_tz = chrono_tz::Asia::Tokyo;
    let ist_tz = chrono_tz::Asia::Calcutta;

    let toronto_date_time = est_tz.timestamp_millis_opt(time_millis).unwrap();
    let utc_date_time = Utc.timestamp_millis_opt(time_millis).unwrap();
    let tokyo_date_time = jst_tz.timestamp_millis_opt(time_millis).unwrap();
    let mumbai_date_time = ist_tz.timestamp_millis_opt(time_millis).unwrap();

    println!("{}", toronto_date_time);
    println!("{}", utc_date_time);
    println!("{}", tokyo_date_time);
    println!("{}", mumbai_date_time);
}
