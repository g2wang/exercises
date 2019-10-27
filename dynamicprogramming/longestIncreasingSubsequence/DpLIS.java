/*
Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
Note:

There may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n2) complexity.

Follow up: Could you improve it to O(n log n) time complexity?
*/

import java.util.Arrays;
import java.util.HashMap;

public class DpLIS {

    public static void main(String[] args){
        int[] nums = new int[]{1,3,6,7,9,4,10,5,6};
        DpLIS dpLIS = new DpLIS();
        int ans = dpLIS.lengthOfLIS(nums);
        System.out.printf("%s -> LIS: %d\n", Arrays.toString(nums), ans);

    }

    private int[] cache;

    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        cache = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            cache[i] = -1;
        }
        int max = 0; 
        for (int i = 0; i < nums.length; i++) {
            int c = lisEndingAt(nums, i);
            if (c > max) max = c;
        }
        return max;
    }

    public int lisEndingAt(int[] nums, int i) {
        if (i == 0) return 1;
        int a = cache[i];
        if (a > 0) {
            System.out.printf("cache hit: lisEndingAt(%d) -> %d\n", i, a);
            return a;
        }
        System.out.printf("cache miss: lisEndingAt(%d) -> %d\n", i, a);
        int max = 1;
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) {
                int c = lisEndingAt(nums, j) + 1;
                if (c > max) max = c;
            }
        }
        cache[i] = max;
        return max;
    }

}
