/**
 740. Delete and Earn
Medium
Given an array nums of integers, you can perform operations on the array.

In each operation, you pick any nums[i] and delete it to earn nums[i] points. After, you must delete every element equal to nums[i] - 1 or nums[i] + 1.

You start with 0 points. Return the maximum number of points you can earn by applying such operations.

Example 1:

Input: nums = [3, 4, 2]
Output: 6
Explanation: 
Delete 4 to earn 4 points, consequently 3 is also deleted.
Then, delete 2 to earn 2 points. 6 total points are earned.

Example 2:

Input: nums = [2, 2, 3, 3, 3, 4]
Output: 9
Explanation: 
Delete 3 to earn 3 points, deleting both 2's and the 4.
Then, delete 3 again to earn 3 points, and 3 again to earn 3 points.
9 total points are earned.

Note:

The length of nums is at most 20000.
Each element nums[i] is an integer in the range [1, 10000].

 */

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class DeleteAndEarn {

    private static final int N = 10000;
    //private static final int N = 6322;

    public static void main(String[] args) {
        int[] nums = new int[]{3, 1}; // ans = 4
        // int[] nums = new int[]{3, 4, 2}; // ans = 6
        // int[] nums = new int[]{2, 2, 3, 3, 3, 4}; // ans = 9
//        int[] nums = new int[N]; 
//        for (int i = 1; i <= N; i++) {
//            nums[i-1] = i;
//        }
        DeleteAndEarn dn = new DeleteAndEarn();
        int ans =  dn.deleteAndEarn(nums);
        System.out.printf("%d <- %s\n", ans, Arrays.toString(nums));
    }

    public int deleteAndEarn(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] counts = new int[N];
        for (int i = 0; i < nums.length; i++) {
            counts[nums[i]-1]++;
        }
        return dp(counts);
    }

    public int dp(int[] counts) {
        int withLast = counts[0] * 1; 
        int noLast = 0;
        int prev = 0;
        int ans = Math.max(withLast, noLast);
        for (int i = 1; i < N; i++) {
            if (counts[i] > 0) {
                if (i - 1 == prev) {
                    withLast = noLast + counts[i] * (i+1);
                    noLast = ans;
                } else {
                    withLast = ans + counts[i] * (i+1);
                    noLast = ans;
                }
                ans = Math.max(withLast, noLast);
                prev = i;
            }
        }
        return ans;
    }
}
