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

public class SlidingWindowMax {

    public static void main(String[] args) {
        int[] nums = new int[]{-7,-8,7,5,7,1,6,0};
        int k = 4;
        SlidingWindowMax swm = new SlidingWindowMax();
        int[] ans = swm.maxSlidingWindow(nums, k);
        System.out.printf("ans: %s%n", Arrays.toString(ans));
    }

    /**
     * Runtime: 24 ms, faster than 43.60% of Java online submissions for Sliding Window Maximum.
     * Memory Usage: 48.8 MB, less than 95.26% of Java online submissions for Sliding Window Maximum.
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];
        LinkedList<Integer> q = new LinkedList<>();
        int n = nums.length - k + 1;
        if (n <= 0) n = 1;
        int[] ans = new int[n];
        for (int i = 0; i < k-1; i++) {
            int m = nums[i];
            while (q.peekLast() != null && m >= nums[q.peekLast()])
                q.pollLast();
            q.offerLast(i);
        }
        for (int i = k-1; i < nums.length; i++) {
            int m = nums[i];
            while (q.peekLast() != null && m >= nums[q.peekLast()])
                q.pollLast();
            q.offerLast(i);
            if (i-k == q.peekFirst()) q.pollFirst();
            ans[i-k+1] = nums[q.peekFirst()];
        }
        if (k > nums.length) ans[0] = nums[q.peekFirst()];
        return ans;
    }

}
