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
 * Runtime: 19 ms, faster than 81.10% of Java online submissions for Longest Consecutive Sequence.
 * Memory Usage: 51.9 MB, less than 92.32% of Java online submissions for Longest Consecutive Sequence.
 */
public class LongestConsecutive {

    public static void main(String[] args) {
        LongestConsecutive lc = new LongestConsecutive();
        int[] nums = new int[]{0,3,7,2,5,8,4,6,0,1};
        int ans = lc.longestConsecutive(nums) ;
        System.out.printf("%s%nans: %d%n", Arrays.toString(nums), ans);
    }

    private int[] parent;
    private int[] size;
    private int maxSize = 1;

    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        int n = nums.length;
        parent = new int[n];
        size = new int[n];
        Map<Integer, Integer> map = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        for (int i = 0; i < n; i++) {
            if (map.containsKey(nums[i])) continue;
            Integer il = map.get(nums[i] - 1);
            if (il != null) union(i, il);
            Integer ir = map.get(nums[i] + 1);
            if (ir != null) union(i, ir);
            map.put(nums[i], i);
        }

        return maxSize;
    }

    /**
     * if i and j are already unioned, return false; otherwise return true
     */
    public boolean union(int i, int j) {
        int ri = root(i);
        int rj = root(j);
        if (ri != rj) {
            if (size[ri] > size[rj]) {
                size[ri] += size[rj];
                parent[rj] = ri;
                maxSize = Math.max(maxSize, size[ri]);
            } else {
                size[rj] += size[ri];
                parent[ri] = rj;
                maxSize = Math.max(maxSize, size[rj]);
            }
            return true;
        }
        return false;
    }


    private int root(int i) {
        while (i != parent[i]) {
            int j = i;
            i = parent[i];
            parent[j] = i;
        }
        return i;
    }

}
