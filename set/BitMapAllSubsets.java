/**
LeetCode: 78 Subsets

Given a set of distinct integers, nums, return all possible subsets (the power set).

Note: The solution set must not contain duplicate subsets.

Example:

Input: nums = [1,2,3]
Output:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BitMapAllSubsets {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        BitMapAllSubsets a = new BitMapAllSubsets();
        List<List<Integer>> subsets = a.subsets(nums);
        for (List<Integer> s : subsets) {
            System.out.printf("%s\n", Arrays.toString(s.toArray(new Integer[]{})));
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> a = new ArrayList<>();
        int n = 1;
        for (int i = 0; i < nums.length; i++) {
            n *= 2;
        }
        for (int d = 0; d < n; d++) {
            List<Integer> e = new ArrayList<>(); 
            int m = d;
            for (int i = 0; i < nums.length; i++) {
                if ((m & 1) == 1) {
                    e.add(nums[i]);
                }
                m = m >> 1;
            }
            a.add(e);
        }
        return a;
    }

}
