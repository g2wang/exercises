/// 1553. Minimum Number of Days to Eat N Oranges
/// Hard
/// 
/// There are n oranges in the kitchen and you decided to eat some of these oranges every day as follows:
/// 
/// Eat one orange.
/// If the number of remaining oranges (n) is divisible by 2 then you can eat  n/2 oranges.
/// If the number of remaining oranges (n) is divisible by 3 then you can eat  2*(n/3) oranges.
/// You can only choose one of the actions per day.
/// 
/// Return the minimum number of days to eat n oranges.
/// 
/// 
/// Example 1:
/// 
/// Input: n = 10
/// Output: 4
/// Explanation: You have 10 oranges.
/// Day 1: Eat 1 orange,  10 - 1 = 9.
/// Day 2: Eat 6 oranges, 9 - 2*(9/3) = 9 - 6 = 3. (Since 9 is divisible by 3)
/// Day 3: Eat 2 oranges, 3 - 2*(3/3) = 3 - 2 = 1.
/// Day 4: Eat the last orange  1 - 1  = 0.
/// You need at least 4 days to eat the 10 oranges.
/// Example 2:
/// 
/// Input: n = 6
/// Output: 3
/// Explanation: You have 6 oranges.
/// Day 1: Eat 3 oranges, 6 - 6/2 = 6 - 3 = 3. (Since 6 is divisible by 2).
/// Day 2: Eat 2 oranges, 3 - 2*(3/3) = 3 - 2 = 1. (Since 3 is divisible by 3)
/// Day 3: Eat the last orange  1 - 1  = 0.
/// You need at least 3 days to eat the 6 oranges.
/// Example 3:
/// 
/// Input: n = 1
/// Output: 1
/// Example 4:
/// 
/// Input: n = 56
/// Output: 6
/// 
/// 
/// Constraints:
/// 
/// 1 <= n <= 2*10^9
/// 

use std::collections::HashMap;
use std::cmp;
use std::env;

fn main() {
    let args: Vec<String> = env::args().collect();
    let n = args[1].parse::<i32>().unwrap();
    let min = min_days(n);
    println!("min_days({})={}", n, min);
}

fn min_days(n: i32) -> i32 {
    let mut cache = HashMap::<i32, i32>::new();
    dfs(n, &mut cache)
}

/// Runtime: 0 ms, faster than 100.00% of Rust online submissions for Minimum Number of Days to Eat N Oranges.
/// Memory Usage: 2.1 MB, less than 27.78% of Rust online submissions for Minimum Number of Days to Eat N Oranges.
fn dfs(n: i32, cache: &mut HashMap::<i32, i32>) -> i32 {
    if n <= 1 {
        return n;
    }

    let ans_result = cache.get(&n);
    match ans_result {
        Some(val) => return *val,
        None => (),
    } 
    
    let ans = 1 + cmp::min(n%3 + dfs(n/3, cache), n%2 + dfs(n/2, cache));
    cache.insert(n, ans);
    ans
}

