/**
239. Sliding Window Maximum

You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

Return the max sliding window.

Example 1:
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation:
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7

Example 2:
Input: nums = [1], k = 1
Output: [1]

Example 3:
Input: nums = [1,-1], k = 1
Output: [1,-1]

Example 4:
Input: nums = [9,11], k = 2
Output: [11]

Example 5:
Input: nums = [4,-2], k = 2
Output: [4]

Example 6:
Input: nums = [-7,-8,7,5,7,1,6,0], k = 4
Output: [7,7,7,7,7]

Constraints:

1 <= nums.length <= 105
-104 <= nums[i] <= 104
1 <= k <= nums.length
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

public class SlidingWindowMax2 {

    public static void main(String[] args) {
        int[] nums = new int[]{-7,-8,7,5,7,1,6,0};
        int k = 4;
        SlidingWindowMax2 swm = new SlidingWindowMax2();
        int[] ans = swm.maxSlidingWindow(nums, k);
        System.out.printf("ans: %s%n", Arrays.toString(ans));
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];
        int[] result = new int[nums.length-k+1];
        for (int i = 0; i+k-1 < nums.length; i++) {
            int max = Integer.MIN_VALUE;
            if (i != 0 && result[i-1] != nums[i-1]){
               max = Integer.max(result[i-1], nums[i+k-1]);
            } else {
                int start = i;
                int end = i+k-1;
                while (start <= end) max = Math.max(max, nums[start++]);
            }
            result[i] = max;
        }

        return result;
    }

}
