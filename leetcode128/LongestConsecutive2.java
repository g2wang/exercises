/**
 
128. Longest Consecutive Sequence
Medium

Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
You must write an algorithm that runs in O(n) time.

Example 1:
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

Example 2:
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9

Constraints:

0 <= nums.length <= 1E5
-1E9 <= nums[i] <= 1E9

 */

import java.util.*;


/**
 * Runtime: 12 ms, faster than 96.93% of Java online submissions for Longest Consecutive Sequence.
 * Memory Usage: 57.1 MB, less than 90.41% of Java online submissions for Longest Consecutive Sequence.
 */
public class LongestConsecutive2 {

    public static void main(String[] args) {
        LongestConsecutive2 lc = new LongestConsecutive2();
        int[] nums = new int[]{0,3,7,2,5,8,4,6,0,1};
        int ans = lc.longestConsecutive(nums) ;
        System.out.printf("%s%nans: %d%n", Arrays.toString(nums), ans);
    }


    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        Set<Integer> set = new HashSet<>(nums.length);
        for (int num : nums) {
            set.add(num);
        }
        int ans = 1;
        for (int num : set) {
            if (!set.contains(num-1)) {
                int currentNum = num;
                int streak = 1;
                while (set.contains(currentNum+1)) {
                    currentNum += 1;
                    streak++;
                }
                ans = Math.max(ans, streak);
            }
        }
        return ans;
    }

}
