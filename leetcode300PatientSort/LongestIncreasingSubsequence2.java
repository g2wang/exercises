/**
 *
300. Longest Increasing Subsequence
Medium

Given an integer array nums, return the length of the longest strictly increasing subsequence.

A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].



Example 1:
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.

Example 2:
Input: nums = [0,1,0,3,2,3]
Output: 4

Example 3:
Input: nums = [7,7,7,7,7,7,7]
Output: 1

Constraints:

1 <= nums.length <= 2500
-104 <= nums[i] <= 104

Follow up: Can you come up with an algorithm that runs in O(n log(n)) time complexity?

 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class LongestIncreasingSubsequence2 {

	public static void main(String[] args) {
		int[] nums = {10, 5, 8, 3, 9, 4, 12, 11};
		LongestIncreasingSubsequence2 lis = new LongestIncreasingSubsequence2();
		System.out.printf("%s -> %d%n", Arrays.toString(nums), lis.lengthOfLIS(nums));
	}

    private int[] cache;

    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        cache = new int[nums.length];
        int max = 1;
        for (int i = 0; i < nums.length; i++) {
            int c = lisEndingAt(nums, i);
            if (c > max) max = c;
        }
        return max;
    }

    private int lisEndingAt(int[] nums, int i) {
        if (i == 0)  return 1;
        int a = cache[i];
        if (a > 0) return a;
        int ans = 1;
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) {
                int c = lisEndingAt(nums, j) + 1;
                if (c > ans) ans = c;
            }
        }
        cache[i] = ans;
        return ans;
    }

}
