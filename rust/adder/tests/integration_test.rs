use adder;
mod common;

#[test]
fn test_add_2() {
    common::setup();
    assert_eq!(4, adder::add_two(2));
}
