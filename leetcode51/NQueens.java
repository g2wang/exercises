import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class NQueens {

    public static void main(String[] args) {
        NQueens nq = new NQueens();
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
     * Runtime: 1 ms, faster than 99.97% of Java online submissions for N-Queens.
     * Memory Usage: 39.8 MB, less than 69.22% of Java online submissions for N-Queens.
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        int[] subResult = new int[n];
        boolean[] rowAttack = new boolean[n];
        boolean[] diag1Attack = new boolean[2*n-1];
        boolean[] diag2Attack = new boolean[2*n-1];
        placeQueen(n, 0, subResult, result,
                   rowAttack, diag1Attack, diag2Attack);
        return result;
    }

    private void placeQueen(int n, int c,
        int[] subResult, List<List<String>> result,
        boolean[] rowAttack, boolean[] diag1Attack, boolean[] diag2Attack) {
        for (int r = 0; r < n; r++) {
            if (rowAttack[r] || diag1Attack[r+c] || diag2Attack[r-c+n-1]) continue;
            subResult[c] = r;
            int nc = c + 1;
            if (nc == n) {
                List<String> list = new ArrayList<>(n);
                for (int i = 0; i < n; i++) {
                    char[] row = new char[n];
                    for (int j = 0; j < n; j++) {
                        if (subResult[j] == i) {
                            row[j] = 'Q';
                        } else {
                            row[j] = '.';
                        }
                    }
                    list.add(String.valueOf(row));
                }
                result.add(list);
                return;
            }
            rowAttack[r] = true;
            diag1Attack[r+c] = true;
            diag2Attack[r-c+n-1] = true;
            placeQueen(n, nc, subResult, result, rowAttack, diag1Attack, diag2Attack);
            rowAttack[r] = false;
            diag1Attack[r+c] = false;
            diag2Attack[r-c+n-1] = false;
        }
    }
}
