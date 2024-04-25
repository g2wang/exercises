use chrono::{Datelike, Local, NaiveDate, NaiveDateTime, NaiveTime, TimeZone, Utc};
use chrono_tz::Tz;
use clap::Parser;
use regex::Regex;

#[derive(Parser)]
#[command(author, version, about, long_about = None, after_help="Examples:
  tt
  tt 1514764800000ms # epoch milliseconds for 2018-01-01 00:00:00 UTC
  tt -1ms
  tt 09:30
  tt 09:30IST
  tt '09:30 IST'
  tt '2023-03-01 09:53:21'
  tt '2023-03-01 09:53:21JST'
  tt '2023-03-01 09:53:21 JST'
  tt '2023-03-01 09:00EST'
  tt '2023-03-01 09:00 EST'
  tt '2023-03-13 09:00 EDT'
  tt '1970-01-01 00:00:00UTC' # Unix epoch
  tt '1970-01-01 00:00:00 UTC' # Unix epoch
")]
struct Cli {
    #[arg(
        help = "an optional time or date time value of format
        1234567890123ms (epoch millis) or [yyyy-MM-dd ]HH:mm[:ss][EST|EDT|UTC|JST|IST].
        If this argument is not specified, local time 'now' will be used.
        Note that within this argument itself, the yyyy-MM-dd (default today),
        ss (seconds, default 00)
        and timezone ([EST|EDT|UTC|JST|IST], default your computer's local timezone)
        are also optional.",
        allow_hyphen_values = true
    )]
    time_or_date_time: Option<String>,
}

fn main() {
    let cli = Cli::parse();
    let arg = cli.time_or_date_time.as_deref();
    let time_pattern = Regex::new(
        r"^(?:(-?\d{1,})(?: ?ms)|(?:(\d{4})-(\d{2})-(\d{2})(?:T| ))?(\d{1,2}):(\d{1,2})(?::(\d{1,2})(?:(?:\.)(\d{1,3}))?)?(?: )?(EST|EDT|UTC|UCT|JST|IST)?)$",
    )
    .unwrap();

    let local_now = Local::now();
    let local_tz = local_now.timezone();
    let mut input_tz: Option<Tz> = None;
    let mut year = local_now.year();
    let mut month = local_now.month();
    let mut day = local_now.day();
    let mut hour: u32 = 0;
    let mut minute: u32 = 0;
    let mut second: u32 = 0;
    let mut millisecond: u32 = 0;

    let mut epoch_millis: i64 = local_now.timestamp_millis();

    if let Some(ldt) = arg {
        if let Some(captures) = time_pattern.captures(ldt) {
            if let Some(ms) = captures.get(1) {
                epoch_millis = ms.as_str().parse::<i64>().unwrap();
            } else {
                if let Some(y) = captures.get(2) {
                    year = y.as_str().parse::<i32>().unwrap();
                }
                if let Some(m) = captures.get(3) {
                    month = m.as_str().parse::<u32>().unwrap();
                    if month < 1 || month > 12 {
                        return show_invalid_arg_message(ldt);
                    }
                }
                if let Some(d) = captures.get(4) {
                    day = d.as_str().parse::<u32>().unwrap();
                    if day < 1
                      || (month <= 7 && month % 2 == 0 && day > 30)
                      || (month <= 7 && month % 2 != 0 && day > 31)
                      || (month > 7 && month % 2 != 0 && day > 30)
                      || (month > 7 && month % 2 == 0 && day > 31)
                      || (month == 2 && is_leap_year(year) && day > 29)
                      || (month == 2 && !is_leap_year(year) && day > 28)
                    {
                        return show_invalid_arg_message(ldt);
                    }
                }
                if let Some(h) = captures.get(5) {
                    hour = h.as_str().parse::<u32>().unwrap();
                    if hour > 24 {
                        return show_invalid_arg_message(ldt);
                    }
                }
                if let Some(n) = captures.get(6) {
                    minute = n.as_str().parse::<u32>().unwrap();
                    if minute > 60 {
                        return show_invalid_arg_message(ldt);
                    }
                }
                if let Some(s) = captures.get(7) {
                    second = s.as_str().parse::<u32>().unwrap();
                    if second > 60 {
                        return show_invalid_arg_message(ldt);
                    }
                }
                if let Some(millis) = captures.get(8) {
                    millisecond = millis.as_str().parse::<u32>().unwrap();
                    if millisecond > 1000 {
                        return show_invalid_arg_message(ldt);
                    }
                }
                if let Some(z) = captures.get(9) {
                    input_tz = match z.as_str() {
                        "EST" | "EDT" => Some(chrono_tz::America::Toronto),
                        "UTC" | "UCT" => Some(chrono_tz::UTC),
                        "JST" => Some(chrono_tz::Asia::Tokyo),
                        "IST" => Some(chrono_tz::Asia::Calcutta),
                        _ => None,
                    }
                }
                if let Some(z) = input_tz {
                    let input_date_time = z
                        .with_ymd_and_hms(year, month, day, hour, minute, second)
                        .unwrap();
                    epoch_millis = input_date_time.timestamp_millis() + i64::from(millisecond);
                } else {
                    let input_date = NaiveDate::from_ymd_opt(year, month, day).unwrap();
                    let input_time =
                        NaiveTime::from_hms_milli_opt(hour, minute, second, millisecond).unwrap();
                    let input_date_time = NaiveDateTime::new(input_date, input_time)
                        .and_local_timezone(local_tz)
                        .unwrap();
                    epoch_millis = input_date_time.timestamp_millis();
                }
            }
        } else {
            return show_invalid_arg_message(ldt);
        }
    }

    let est_tz = chrono_tz::America::Toronto;
    let jst_tz = chrono_tz::Asia::Tokyo;
    let ist_tz = chrono_tz::Asia::Calcutta;

    let toronto_date_time = est_tz.timestamp_millis_opt(epoch_millis).unwrap();
    let utc_date_time = Utc.timestamp_millis_opt(epoch_millis).unwrap();
    let tokyo_date_time = jst_tz.timestamp_millis_opt(epoch_millis).unwrap();
    let mumbai_date_time = ist_tz.timestamp_millis_opt(epoch_millis).unwrap();

    println!("{} {}", epoch_millis, "ms");
    println!("{}", toronto_date_time);
    println!("{}", utc_date_time);
    println!("{}", tokyo_date_time);
    println!("{}", mumbai_date_time);
}

fn show_invalid_arg_message(ldt: &str) {
    println!(
        "Invalid argument {ldt} - must be a valid time or date time value of format 1234567890123ms (epoch millis) or [yyyy-MM-dd ]HH:mm[:ss][EST|EDT|UTC|JST|IST]"
    );
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
