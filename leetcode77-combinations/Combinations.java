/**
 * 77. Combinations
Medium

Given two integers n and k, return all possible combinations of k numbers out of the range [1, n].

You may return the answer in any order.

Example 1:
Input: n = 4, k = 2
Output:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]

Example 2:
Input: n = 1, k = 1
Output: [[1]]
 
Constraints:

1 <= n <= 20
1 <= k <= n
 */

import java.util.*;

public class Combinations {

    public static void main(String[] args) {
        Combinations cb = new Combinations();
        int n = 4, k = 2;
        System.out.printf("n=%d, k=%d -> %s%n", n, k, cb.combine(n, k));
    }

    /**
     * Runtime: 25 ms, faster than 67.95% of Java online submissions for Combinations.
     * Memory Usage: 59.7 MB, less than 18.14% of Java online submissions for Combinations.
     */
    public List<List<Integer>> combine(int n, int k) {
         return combine(n, k, 1); 
    }
    
    public List<List<Integer>> combine(int n, int k, int start) {
        List<List<Integer>> ans = new ArrayList<>();
        if (k == 1) {
            for (int i = start; i <= n; i++) {
                List<Integer> comb = new ArrayList<>();
                comb.add(i);
                ans.add(comb);
            }
            return ans;
        }
        k--;
        for (int i = start; i <= n-k+1; i++) {
            List<List<Integer>> tailList = combine(n, k, ++start);
            for (List<Integer> tail : tailList) {
                List<Integer> comb = new ArrayList<>();
                comb.add(i);
                comb.addAll(tail);
                ans.add(comb);
            }
        } 
        return ans;
    }

}
