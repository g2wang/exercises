/*
72. Edit Distance

Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.

You have the following 3 operations permitted on a word:

Insert a character
Delete a character
Replace a character
Example 1:

Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
Example 2:

Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u') 
*/
import java.util.Arrays;
public class MinimumEditDistance {
    public static void main(String[] args) {
        String word1 = "horse";
        String word2 = "ros";
        MinimumEditDistance med = new MinimumEditDistance();
        int ans = med.minDistance(word1, word2);
        System.out.printf("Edit distance: %d, %s -> %s\n", ans, word1, word2);
    }

    public int minDistance(String word1, String word2) {
        char[] c1 = word1.toCharArray();
        char[] c2 = word2.toCharArray();
        int n1 = c1.length;
        int n2 = c2.length;
        int[][] dp = new int[n1+1][n2+1]; 
        dp[0][0] = 0;
        for (int i = 1; i < n1+1; i++) dp[i][0] = i;
        for (int j = 1; j < n2+1; j++) dp[0][j] = j;
        for (int i = 1; i < n1+1; i++) {
            for (int j = 1; j < n2+1; j++) {
                if (c1[i-1] == c2[j-1]) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
                }
            }
        }
        for (int i = 0; i < n1+1; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        return dp[n1][n2];
    }
}
