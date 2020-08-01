/// 301. Remove Invalid Parentheses
/// Hard
/// 
/// Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
/// 
/// Note: The input string may contain letters other than the parentheses ( and ).
/// 
/// Example 1:
/// 
/// Input: "()())()"
/// Output: ["()()()", "(())()"]
/// Example 2:
/// 
/// Input: "(a)())()"
/// Output: ["(a)()()", "(a())()"]
/// Example 3:
/// 
/// Input: ")("
/// Output: [""]

fn main() {
    let s = String::from("(r(()()("); // ans: ["r()()","r(())","(r)()","(r())"]
    let result = remove_invalid_parentheses(s);
    println!("result: {:?}", result);
}

///
/// Runtime: 0 ms, faster than 100.00% of Rust online submissions for Remove Invalid Parentheses.
/// Memory Usage: 2 MB, less than 100.00% of Rust online submissions for Remove Invalid Parentheses.
///
pub fn remove_invalid_parentheses(s: String) -> Vec<String> {
    let mut result = Vec::new();
    remove(s, 0, 0, &mut result, '(', ')');
    result
}

fn remove(s: String, start: usize, last_remove: usize, result_ref: &mut Vec<String>, p1: char, p2: char) {
    let mut balance = 0;
    let mut char_vec : Vec<char> = s.chars().collect();
    for i in start..char_vec.len() {
        let c = char_vec[i];
        if c == p1 {
            balance += 1;
        } else if c == p2 {
            balance -= 1;
        }
        if balance < 0 {
            let mut prev_c = ' ';
            for j in last_remove..=i {
                let cj = char_vec[j];
                if cj == p2 && cj != prev_c {
                    let mut new_char_vec = char_vec.clone();
                    new_char_vec.remove(j);
                    let new_s = new_char_vec.iter().collect::<String>();
                    remove(new_s, i, j, result_ref, p1, p2);
                }
                prev_c = cj;
            }
            return;
        }
    } 
    char_vec.reverse();
    let reverse = char_vec.iter().collect::<String>();
    if p1 == '(' {
        remove(reverse, 0, 0, result_ref, p2, p1);
    } else {
        result_ref.push(reverse);
    }
}
