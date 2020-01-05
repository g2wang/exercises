/**
 *
221. Maximal Square
Medium

Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

Example:

Input: 

1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output: 4
 */

public class MaximalSquare {

    public static void main(String[] args) {
        char[][] m = new char[][]{
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
        }; // ans = 4
//        char[][] m = new char[][]{
//            {'0', '1'}
//        }; // ans = 1

        MaximalSquare ms = new MaximalSquare();
        int a = ms.maximalSquare(m); 
        System.out.printf("area of maximal square of 1s: %d\n", a);
    }

    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int[][] dp = new int[matrix.length+1][matrix[0].length+1];
        int h = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (matrix[i-1][j-1] == '0') {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
                }
                // System.out.printf("dp[%d][%d]=%d\n", i, j, dp[i][j]);
                h = Math.max(h, dp[i][j]);
            }
        }
        return h*h;
    }
}
