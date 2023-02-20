fn main() {
    let grades: [&str; 5] = ["3.8", "B+", "4.0",  "A", "2.7"];
    let grades: Vec<f32> = grades.iter().filter_map(
        |s: &&str| s.parse().ok()
        ).collect();
    println!("{grades:?}");
}
