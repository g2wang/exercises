use std::vec;

fn main() {
    let x = vec![1, 2, 3];
    for y in &x {
        println!("{}", y);
    }
    let x_itr = x.iter();
    for y in x_itr {
        println!("{}", y);
    }

}

