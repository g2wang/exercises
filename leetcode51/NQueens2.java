import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class NQueens2 {

    public static void main(String[] args) {
        NQueens2 nq = new NQueens2();
        int n = 14;
        long start = System.currentTimeMillis();
        List<List<String>> ans = nq.solveNQueens(n);
        long span = System.currentTimeMillis() - start;
        System.out.printf("answer for %d-queens:%n", n);
        for (int i = 0; i < ans.size(); i++) {
            List<String> subAns = ans.get(i);
            System.out.printf("solution %d:%n", i+1);
            for (String s : subAns) {
                System.out.printf("%s%n", s);
            }
            System.out.println("");
        }
        System.out.printf("time taken: %d%n", span);
    }
    
    /**
     * copyright: Author of a LeetCode submission
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        if (n == 0) return res;
        boolean[] vertical = new boolean[n];
        int[] h = new int[n];
        boolean[] diag1 = new boolean[2 * n - 1];   // x + y
        boolean[] diag2 = new boolean[2 * n - 1];   // x - y + (n - 1)
        dfs(res, h, vertical, diag1, diag2, 0, n);
        return res;
    }
    
    private void dfs(List<List<String>> res, int[] h, boolean[] vertical,
            boolean[] diag1, boolean[] diag2, int row, int n) {
        if (row == n) {
            List<String> temp = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                char[] chs = new char[n];
                Arrays.fill(chs, '.');
                chs[h[i]] = 'Q';
                temp.add(String.valueOf(chs));
            }
            res.add(new ArrayList<>(temp));
            return;
        }
        
        for (int col = 0; col < n; col++) {
            if (vertical[col] || diag1[row + col] || diag2[row - col + n - 1]) continue;
            vertical[col] = true;
            diag1[row + col] = true;
            diag2[row - col + n - 1] = true;
            h[row] = col;
            dfs(res, h, vertical, diag1, diag2, row + 1, n);
            vertical[col] = false;
            diag1[row + col] = false;
            diag2[row - col + n - 1] = false;
        }
    }
}
