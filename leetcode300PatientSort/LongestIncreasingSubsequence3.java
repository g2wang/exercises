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
public class LongestIncreasingSubsequence3 {

	public static void main(String[] args) {
		int[] nums = {10, 5, 8, 3, 9, 4, 12, 11};
		LongestIncreasingSubsequence3 lis = new LongestIncreasingSubsequence3();
		System.out.printf("%s -> %d%n", Arrays.toString(nums), lis.lengthOfLIS(nums));
	}

    /**
     * Runtime: 8 ms, faster than 78.30% of Java online submissions for Longest Increasing Subsequence.
     * Memory Usage: 44.9 MB, less than 23.09% of Java online submissions for Longest Increasing Subsequence.
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null) return 0;
        int n = nums.length;
        if (n <= 1) return n;
        List<List<Integer>> piles = new ArrayList<>();
        for (int v : nums) {
            Integer hostPileIndex = findHostPileIndex(piles, v);
            List<Integer> hostPile;
            if (hostPileIndex == null) {
                hostPile = new ArrayList<>();
                piles.add(hostPile);
            } else {
                hostPile = piles.get(hostPileIndex);
            }
            hostPile.add(v);
        }
        return piles.size();
    }

    private Integer findHostPileIndex(List<List<Integer>> piles, int cardValueAtHand) {
        if (piles == null) return null;
        int l = 0, h = piles.size()-1;
        while (l <= h) {
            int m = l + (h-l)/2;
            List<Integer> pile = piles.get(m);
            Integer topCard = pile.get(pile.size()-1);
            if (topCard.intValue() == cardValueAtHand) {
                return m;
            } else if (topCard.intValue() > cardValueAtHand) {
                h = m-1;
            } else {
                l = m+1;
            }
        }
        if (l >= piles.size()) return null;
        List<Integer> pile = piles.get(l);
        if (pile.get(pile.size()-1).intValue() > cardValueAtHand) return l;
        return null;
    }

}
