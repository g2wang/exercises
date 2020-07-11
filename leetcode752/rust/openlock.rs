/// 752. Open the Lock
/// Medium
/// 
/// You have a lock in front of you with 4 circular wheels. Each wheel has 10
/// slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate
/// freely and wrap around: for example we can turn '9' to be '0', or '0' to be
/// '9'. Each move consists of turning one wheel one slot.
/// 
/// The lock initially starts at '0000', a string representing the state of the 4
/// wheels.
/// 
/// You are given a list of deadends dead ends, meaning if the lock displays any of
/// these codes, the wheels of the lock will stop turning and you will be unable to
/// open it.
/// 
/// Given a target representing the value of the wheels that will unlock the lock,
/// return the minimum total number of turns required to open the lock, or -1 if it
/// is impossible.
/// 
/// Example 1:
/// Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
/// Output: 6
/// Explanation:
/// A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
/// Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
/// because the wheels of the lock become stuck after the display becomes the dead end "0102".
/// 
/// Example 2:
/// Input: deadends = ["8888"],
/// target = "0009"
/// Output: 1
/// Explanation:
/// We can turn the last wheel in reverse to move from "0000" -> "0009".
/// 
/// Example 3:
/// Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"],
/// target = "8888"
/// Output: -1
/// Explanation:
/// We can't reach the target without getting stuck.
/// 
/// Example 4:
/// Input: deadends = ["0000"],
/// target = "8888"
/// Output: -1
/// 
/// Note:
/// The length of deadends will be in the range [1, 500].
/// target will not be in the list deadends.
/// Every string in deadends and the string target will be a string of 4 digits
/// from the 10,000 possibilities '0000' to '9999'.
///
use std::collections::{HashSet, VecDeque};
use std::iter::FromIterator;

fn main() {

    let deadends = vec![String::from("0201"), String::from("0101"),
        String::from("0102"), String::from("1212"), String::from("2002")];

    let target = String::from("0202");

    assert_eq!(6, open_lock(deadends, target)); 
}

pub fn open_lock(deadends: Vec<String>, target: String) -> i32 {
    let mut visited = HashSet::<u32>::from_iter(deadends.iter().map(
            |x| x.parse::<u32>().unwrap()));
    let target = target.parse::<u32>().unwrap();
    let mut queue = VecDeque::from(vec![0u32]); // enqueue '0000'
    let mut step = 0;
    while !queue.is_empty() {
        for _ in 0..queue.len() {
            let element = queue.pop_front().unwrap();
            if visited.contains(&element) {
                continue;
            }
            if element == target {
                return step; 
            }
            visited.insert(element);
            let positions = [1000u32, 100u32, 10u32, 1u32];
            for position in &positions {
                let quotient = element / position;
                let remainder = element % position;
                let pre_pos_value = quotient / 10;
                let digit = quotient % 10;
                let digit_up = (digit + 1) % 10;
                let digit_down = (digit + 9) % 10;
                queue.push_back((pre_pos_value * 10 + digit_up) * position + remainder);
                queue.push_back((pre_pos_value * 10 + digit_down) * position + remainder);
            }
        }
        step += 1;
    }
    -1
}

