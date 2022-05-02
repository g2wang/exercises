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

/**
 * Given an array of random numbers, find a longest increasing subsequence. This subsequence is not necessarily contiguous, or unique.
 *
 * For example, if the array contains these numbers: [10, 5, 8, 3, 9, 4, 12, 11]
 * a longest increasing subsequence is [5, 8, 9, 11] or [5, 8, 9, 12]
 *
 * Solution:
 * "Patience Sort" is a general purpose sorting algorithm that in the process of sorting also finds a longest increasing subsequence.
 * This video explains how the Patience Sort works by placing numbers (playing cards are used for illustration) into piles of decreasing values.
 * https://www.youtube.com/watch?v=22s1xxRvy28
 *
 * If we also keep a pointer from the placed card to the top of the pile immediately on the left side, chains of pointers get created, leading to the left most pile.
 * So at the end, you can pick any card on the right most pile and follow the pointers to the left most pile, recovering the (or one of the)
 * longest increasing subsequence in reverse order.
 *
 * @author Andre Violentyev
 */
public class LongestIncreasingSubsequence {
	/**
	 * just a container class that holds an integer value and a reference to the top card on the pile
	 * immediately on the left
	 */
	class Card {
		final int i;
		final Card card; // could be null

		Card(int i, Card card) {
			this.i = i;
			this.card = card;
		}

		@Override
		public String toString() {
			return i + "";
		}
	}

	/**
	 * we examine only the top most card of each pile, looking for a card that is greater or equal to the one at hand
	 *
	 * @param piles
	 * @param i
	 * @return -1 if no pile can host
	 */
	int binarySearchHostPileIdx(List<List<Card>> piles, int i) {
        int l = 0; // left
        int r = piles.size() - 1; // right
        int m = -1; // middle

        if (piles.isEmpty()) return -1;

        while (l <= r) {
            /*
             * Pick the middle. Same as (l + r) / 2 but does not run the risk of integer overflow
             */
            m =  l + (r - l) / 2;

            List<Card> pile = piles.get(m);
            Card c = pile.get(pile.size()-1); // get the top card from this pile

            if (c.i == i) return m; // exact match

            if (c.i < i) {
                l = m + 1; // ignore left half
            } else {
                r = m - 1; // ignore right half
            }
        }

        if (l >= piles.size()) return -1;

        List<Card> lPile = piles.get(l);
		return lPile.get(lPile.size()-1).i >= i ? l : -1;
    }

	/**
	 * to retrieve the sub-sequence, start at the right most pile, pick a card (any one of those would do)
	 * and follow the pointers, which will lead all the way to the left most pile.
	 * @param piles
	 * @return
	 */
	private int[] retrieveSeq(List<List<Card>> piles) {
		List<Card> rightPile = piles.get(piles.size()-1);
		Card c = rightPile.get(0);
		int[] seq = new int[piles.size()];
		int k = seq.length-1;

		while (c != null) {
			seq[k--] = c.i;
			c = c.card;
		}

		return seq;
	}

	public int[] run(int[] ar) {

		List<List<Card>> piles = new ArrayList<>();

		/**
		 * "patience sort" each value into piles
		 */
		for (int k = 0; k < ar.length; k++) {
			int hostPileIdx = binarySearchHostPileIdx(piles, ar[k]);

			List<Card> hostPile;
			if (hostPileIdx < 0) { // start a new pile
				hostPile = new ArrayList<>();
				piles.add(hostPile);
				hostPileIdx = piles.size()-1;
			} else {
				hostPile = piles.get(hostPileIdx); // found a host
			}

			Card leftCard = null;
			if (hostPileIdx > 0) {
				// except for left most pile, link the card to the top card of the pile immediately on the left
				List<Card> leftPile = piles.get(hostPileIdx-1);
				leftCard = leftPile.get(leftPile.size()-1); // top most card of the pile immediately on the left
			}

			hostPile.add(new Card(ar[k], leftCard));
		}

		return retrieveSeq(piles);
	}

	public static void main(String[] args) {
		int[] ar = {10, 5, 8, 3, 9, 4, 12, 11};
		LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
		System.out.println(Arrays.toString(lis.run(ar))); // [5, 8, 9, 12 or 11]
	}
}
