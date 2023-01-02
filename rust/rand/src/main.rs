use rand::{Rng, thread_rng};
use rand::prelude::SliceRandom;
use rand::distributions::Alphanumeric;

fn main() {
    let mut rng = thread_rng();
    let y: f64 = rng.gen(); // generates a float between 0 and 1
    let mut nums: Vec<i32> = (1..100).collect();
    nums.shuffle(&mut rng);
    println!("y: {}", y);
    println!("nums: {:?}", nums);

    let rnd_string: String = thread_rng()
        .sample_iter(&Alphanumeric)
        .take(30)
        .map(char::from)
        .collect();
    println!("rnd_string: {}", rnd_string);

}
