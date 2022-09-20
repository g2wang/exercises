/**
 * 322. Coin Change
Medium

You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.

Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

You may assume that you have an infinite number of each kind of coin.

 

Example 1:
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1

Example 2:
Input: coins = [2], amount = 3
Output: -1

Example 3:
Input: coins = [1], amount = 0
Output: 0

Constraints:

1 <= coins.length <= 12
1 <= coins[i] <= 231 - 1
0 <= amount <= 104
 */
import java.util.Arrays;

public class CoinChange {

    public static void main(String[] args) {
        CoinChange c = new CoinChange();
        int[] coins = new int[]{1, 2, 5};
        int amount = 11;
        int ans = c.coinChange(coins, amount);
        System.out.printf("(coins[]: %s, amount: %d) -> %d%n", Arrays.toString(coins), amount, ans);
    }

    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        for (int i = 1; i <= amount; i++) {
            dp[i] = -1; // -1 stands for positive infinity
        }
        for (int i = 0; i < coins.length; i++) {
            int d = coins[i];
            for (int j = 1; j <= amount; j++) {
                if (j >= d) {
                    dp[j] = findMin(dp[j], addCoin(dp[j-d]));
                }
            }
        }
        return dp[amount];
    }
    
    private int addCoin(int oldVal) {
        if (oldVal == -1) return oldVal;
        return 1 + oldVal;
    }
    
    private int findMin(int val1, int val2) {
        if (val1 == -1) return val2;
        if (val2 == -1) return val1;
        return Math.min(val1, val2);
    }
}
