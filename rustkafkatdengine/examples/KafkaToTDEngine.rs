use std::collections::HashMap;
use std::time::Duration;

use kafka::consumer::{Consumer, FetchOffset, GroupOffsetStorage};
use kafka::producer::{Producer, Record, RequiredAcks};
use kafka::error::Error as KafkaError;

fn main() {
    let mut consumer_config = HashMap::new();
    consumer_config.insert("group.id", "my-group-id");
    consumer_config.insert("bootstrap.servers", "localhost:9092");
    consumer_config.insert("enable.auto.commit", "false");
    consumer_config.insert("auto.offset.reset", "earliest");

    let mut producer_config = HashMap::new();
    producer_config.insert("bootstrap.servers", "localhost:9092");

    let consumer: Consumer = Consumer::from_hosts(vec!["localhost:9092".to_owned()])
        .with_topic("my-topic".to_owned())
        .with_fallback_offset(FetchOffset::Earliest)
        .with_offset_storage(GroupOffsetStorage::Kafka)
        .create()
        .unwrap();

    let producer = Producer::from_hosts(vec!["localhost:9092".to_owned()])
        .with_ack_timeout(Duration::from_secs(1))
        .with_required_acks(RequiredAcks::One)
        .create()
        .unwrap();

    for message in consumer.poll().unwrap().iter() {
        match message {
            Ok(m) => {
                let record = Record::from_key_value("my-key", m.value.as_ref().unwrap());
                producer.send(&record).unwrap();
            },
            Err(KafkaError::NoMessageReceived) => break,
            Err(e) => println!("Error while receiving message: {:?}", e),
        }
    }
}

