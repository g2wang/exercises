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

public class Combinations2 {

    public static void main(String[] args) {
        Combinations2 cb = new Combinations2();
        int n = 4, k = 2;
        System.out.printf("n=%d, k=%d -> %s%n", n, k, cb.combine(n, k));
    }

    /**
     * copyright: author of a leetcode submission
     *
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> combs = new ArrayList<List<Integer>>();
		combine(combs, new ArrayList<Integer>(), 1, n, k);
		return combs;
	}

	private void combine(List<List<Integer>> combs, List<Integer> comb, int start, int n, int k) {
		if (k == 0) {
			combs.add(new ArrayList<Integer>(comb));
			return;
		}
		for(int i = start; i <= n - k + 1; i++) {
			comb.add(i);
			combine(combs, comb, i + 1, n, k - 1);
			comb.remove(comb.size() - 1);
		}
    }
}
