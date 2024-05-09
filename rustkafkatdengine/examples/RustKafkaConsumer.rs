use rdkafka::consumer::{Consumer, StreamConsumer};
use rdkafka::Message;
use rdkafka::config::ClientConfig;

fn main() {
    let mut consumer: StreamConsumer = ClientConfig::new()
        .set("bootstrap.servers", "localhost:9092")
        .set("group.id", "my-group")
        .set("enable.auto.commit", "false")
        .create()
        .expect("Consumer creation failed");

    consumer.subscribe(&["my-topic"]).expect("Can't subscribe to specified topic");

    loop {
        let message: Option<Message> = consumer.poll(None);

        match message {
            Some(Ok(m)) => {
                println!("Received message: {:?}", m.payload());
                consumer.commit_message(&m).expect("Commit failed");
            },
            Some(Err(e)) => println!("Error while receiving message: {:?}", e),
            None => (),
        }
    }
}
