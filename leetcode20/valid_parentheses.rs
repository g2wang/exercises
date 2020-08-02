pub fn main() {
    let s = String::from("(5+1) * ((x-1)*[a + b]-{c * (d -e)})");
    let ans = is_valid(s);
    assert_eq!(ans, true);
}

///
/// Runtime: 0 ms, faster than 100.00% of Rust online submissions for Valid Parentheses.
/// Memory Usage: 2 MB, less than 100.00% of Rust online submissions for Valid Parentheses.
///
pub fn is_valid(s: String) -> bool {
    let mut stack = Vec::with_capacity(s.len());
    let mut head = 0;
    for c in s.chars() {
        match c {
            '(' | '[' | '{' => {
                head = on_stack(head, c, &mut stack);
            }
            ')' | ']' | '}' => {
                if head == 0 {
                    return false;
                } 
                head -= 1;
                if c != stack[head] {
                   return false;
                }
            }
            _ => (),
        }
    }
    head == 0
}

fn on_stack(mut head: usize, c: char, stack: &mut Vec<char>) -> usize {
    let c1 = match c {
        '(' => ')',
        '[' => ']',
        '{' => '}',
        _ => panic!("valid input"),
    };      
    if head >= stack.len() {
        stack.push(c1);
    } else {
        stack[head] = c1;
    }
    head += 1;
    head
}
