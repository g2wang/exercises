use chrono::{Datelike, Local, NaiveDate, NaiveDateTime, NaiveTime, TimeZone, Timelike, Utc};
use clap::Parser;
use regex::Regex;

#[derive(Parser)]
#[command(author, version, about, long_about = None)]
struct Cli {
    #[arg(help = "local time or date time value of format HH:mm[:ss] or yyyy-MM-dd HH:mm[:ss]")]
    local_time_or_date_time: Option<String>,
}

fn main() {
    let cli = Cli::parse();
    let arg = cli.local_time_or_date_time.as_deref();
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

    let mut input_is_valid = true;
    if let Some(ldt) = arg {
        if let Some(captures) = time_pattern.captures(ldt) {
            if let Some(y) = captures.get(1) {
                year = y.as_str().parse::<i32>().unwrap();
            }
            if let Some(m) = captures.get(2) {
                month = m.as_str().parse::<u32>().unwrap();
                if month < 1 || month > 12 {
                    input_is_valid = false;
                }
            }
            if let Some(d) = captures.get(3) {
                day = d.as_str().parse::<u32>().unwrap();
                if day < 1
                    || (month <= 7 && month % 2 == 0 && day > 30)
                    || (month <= 7 && month % 2 != 0 && day > 31)
                    || (month > 7 && month % 2 != 0 && day > 30)
                    || (month > 7 && month % 2 == 0 && day > 31)
                    || (month == 2 && is_leap_year(year) && day > 29)
                    || (month == 2 && !is_leap_year(year) && day > 28)
                {
                    input_is_valid = false;
                }
            }
            if let Some(h) = captures.get(4) {
                hour = h.as_str().parse::<u32>().unwrap();
                if hour > 24 {
                    input_is_valid = false;
                }
            }
            if let Some(n) = captures.get(5) {
                minute = n.as_str().parse::<u32>().unwrap();
                if minute > 60 {
                    input_is_valid = false;
                }
                if let Some(s) = captures.get(6) {
                    second = s.as_str().parse::<u32>().unwrap();
                    if second > 60 {
                        input_is_valid = false;
                    }
                } else {
                    second = 0;
                }
            }
        } else {
            input_is_valid = false;
        }
        if !input_is_valid {
            println!(
                "Invalid argument {ldt} - must be valiate time or date time values of format HH:mm[:ss] or yyyy-MM-dd HH:mm[:ss]"
            );
            return;
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

fn is_leap_year(year: i32) -> bool {
    if year % 4 == 0 {
        if year % 100 == 0 {
            if year % 400 == 0 {
                true
            } else {
                false
            }
        } else {
            true
        }
    } else {
        false
    }
}
