/**
 *
55. Jump Game
Medium

You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.

Return true if you can reach the last index, or false otherwise.

Example 1:
Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

Example 2:
Input: nums = [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.


Constraints:

1 <= nums.length <= 104
0 <= nums[i] <= 105
Accepted
982,310
Submissions
2,601,074
 *
 */

import java.util.*;

public class JumpGame {

    public static void main(String[] args) {
        int[] nums = new int[]{3,2,1,0,4};
        JumpGame j = new JumpGame();
        boolean ans = j.canJump(nums);
        System.out.printf("%s -> %b%n", Arrays.toString(nums), ans);
    }

    /**
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Jump Game.
     * Memory Usage: 68.7 MB, less than 21.10% of Java online submissions for Jump Game.
     */
    public boolean canJump(int[] nums) {
        if (nums.length == 1) return true;
        return canJump(nums, nums.length, 0, true);
    }
    public boolean canJump(int[] nums, int j, int min, boolean ans) {
        int prev = -1;
        for (int i=j-2; i>=0; i--) {
            if (nums[i]==0 && prev!=0) {
                if (min > 1) {
                    return canJump(nums, i+1, min+1, false);
                }
                min = 1; 
                ans = false;
            } else {
                min++;
                if (nums[i] >= min) {
                    min = 0; 
                    ans = true; 
                }
            }
            prev = nums[i];
        }
        return ans;
    }

}
