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

public class AllSubsets {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        AllSubsets a = new AllSubsets();
        List<List<Integer>> subsets = a.subsets(nums);
        for (List<Integer> s : subsets) {
            System.out.printf("%s\n", Arrays.toString(s.toArray(new Integer[]{})));
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
       return sub(nums, nums.length);
    }

    public List<List<Integer>> sub(int[] nums, int length) {
        List<List<Integer>> sets = new ArrayList<>();
        if (length == 0) {
            sets.add(new ArrayList<Integer>());
            return sets;
        }
        int last = nums[length - 1]; 
        List<List<Integer>> sets0 = sub(nums, length - 1);
        for (List<Integer> s : sets0) {
            sets.add(s);
            List<Integer> clone = new ArrayList<>(s); 
            clone.add(last);
            sets.add(clone);
        }
        return sets;
    }
}
