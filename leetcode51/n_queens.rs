use std::time::Instant;

pub fn main() {
    let n = 14;
    let now = Instant::now();
    let ans = solve_n_queens(n);
    let time_taken_ms = now.elapsed().as_millis();
    println!("{}-queens problem, answer:", n);
    for (i, solution) in ans.iter().enumerate() {
        println!("solution {}", i);
        for row in solution.iter() {
            println!("{:?}", row);
        }
    }
    println!("time taken ms: {}", time_taken_ms);
}

///
/// Runtime: 0 ms, faster than 100.00% of Rust online submissions for N-Queens.
/// Memory Usage: 2.3 MB, less than 77.27% of Rust online submissions for N-Queens.
pub fn solve_n_queens(n: i32) -> Vec<Vec<String>> {
    let n1 = n as usize;
    let mut sub_ans = vec![0usize; n1];
    let mut ans = Vec::new();
    let mut c_atk = vec![false; n1];
    let mut d1_atk = vec![false; 2*n1-1];
    let mut d2_atk = vec![false; 2*n1-1];
    place_queen(n1, 0, &mut sub_ans, &mut ans, &mut c_atk,
    &mut d1_atk, &mut d2_atk);
    ans
}

fn place_queen(n: usize, r: usize, sub_ans: &mut Vec<usize>,
        ans: &mut Vec<Vec<String>>, c_atk: &mut Vec<bool>,
        d1_atk: &mut Vec<bool>, d2_atk: &mut Vec<bool>) {
    for c in 0..n {
        if !c_atk[c] && !d1_atk[r+c] && !d2_atk[n+r-c-1] {
            sub_ans[r] = c;
            if r+1 == n {
                let mut rows = Vec::with_capacity(n);
                for i in 0..n {
                    let mut chars = vec!['.'; n];
                    chars[sub_ans[i]] = 'Q';
                    let row: String = chars.iter().collect();
                    rows.push(row);
                }
                ans.push(rows);
                return;
            }
            c_atk[c] = true;
            d1_atk[r+c] = true;
            d2_atk[n+r-c-1] = true;
            place_queen(n, r+1, sub_ans, ans, c_atk, d1_atk, d2_atk);
            c_atk[c] = false;
            d1_atk[r+c] = false;
            d2_atk[n+r-c-1] = false;
        }
    }
}
