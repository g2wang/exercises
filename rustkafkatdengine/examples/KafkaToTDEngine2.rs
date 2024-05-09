extern crate kafka;
extern crate taos;

use kafka::consumer::{Consumer, FetchOffset, GroupOffsetStorage};
use taos::*;

fn main() {
    // Create a Kafka consumer
    let mut consumer = Consumer::from_hosts(vec!["localhost:9092".to_owned()])
        .with_topic("my-topic".to_owned())
        .with_group("my-group".to_owned())
        .with_fallback_offset(FetchOffset::Earliest)
        .with_offset_storage(GroupOffsetStorage::Kafka)
        .create()
        .unwrap();

    // Create a TDengine connection
    let mut taos = Taos::new("localhost", "root", "taosdata", None, 0).unwrap();

    loop {
        for ms in consumer.poll().unwrap().iter() {
            for m in ms.messages() {
                let key = match m.key {
                    Some(k) => String::from_utf8_lossy(k).into_owned(),
                    None => "".to_string(),
                };
                let value = match m.value {
                    Some(v) => String::from_utf8_lossy(v).into_owned(),
                    None => "".to_string(),
                };

                // Write the key and value to TDengine
                let sql = format!("insert into my_table values('{}', '{}')", key, value);
                taos.query(&sql).unwrap();
            }
            consumer.consume_messageset(ms);
        }
        consumer.commit_consumed().unwrap();
    }
}

