import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class NQueensSlow {

    public static void main(String[] args) {
        NQueensSlow nq = new NQueensSlow();
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
     * Runtime: 9 ms, faster than 29.86% of Java online submissions for N-Queens.
     * Memory Usage: 40.6 MB, less than 42.39% of Java online submissions for N-Queens.
     */
    public List<List<String>> solveNQueens(int n) {
        List<int[]> result = new ArrayList<>();
        int[] subResult = new int[n];
        boolean[] rowAttack = new boolean[n];
        boolean[] diag1Attack = new boolean[n];
        boolean[] diag2Attack = new boolean[n];
        placeQueen(n, 0, subResult, result,
                   rowAttack, diag1Attack, diag2Attack);
        return convertResultToOutput(result, n);
    }

    private void placeQueen(int n, int c,
        int[] subResult, List<int[]> result,
        boolean[] rowAttack, boolean[] diag1Attack, boolean[] diag2Attack) {
        for (int r = 0; r < n; r++) {
            if (rowAttack[r] || diag1Attack[r] || diag2Attack[r]) continue;
            subResult[c] = r;
            int nc = c + 1;
            if (nc == n) {
                result.add(subResult);
                return;
            }
            int[] newSubResult = new int[n];
            boolean[] newRowAttack = new boolean[n];
            boolean[] newDiag1Attack = new boolean[n];
            boolean[] newDiag2Attack = new boolean[n];
            for (int i = 0; i < n; i++) {
                newSubResult[i] = subResult[i];
                if (rowAttack[i]) newRowAttack[i] = true;
                if (diag1Attack[i] && i+1 < n) newDiag1Attack[i+1] = true;
                if (diag2Attack[i] && i-1 >= 0) newDiag2Attack[i-1] = true;
            }
            newRowAttack[r] = true;
            if (r+1 < n) newDiag1Attack[r+1] = true;
            if (r-1 >= 0) newDiag2Attack[r-1] = true;
            placeQueen(n, nc, newSubResult, result, newRowAttack,
                      newDiag1Attack, newDiag2Attack);
        }
    }

    private List<List<String>> convertResultToOutput(List<int[]> result, int n) {
        List<List<String>> output = new ArrayList<>(result.size());
        for (int i = 0; i < result.size(); i++) {
            output.add(getSubOutputTemplate(n));
        }
        for (int i = 0; i < result.size(); i++) {
            int[] subResult = result.get(i);
            List<String> subOutput = output.get(i);
            for (int colIndex = 0; colIndex < n; colIndex++) {
                int queenRowIndex = subResult[colIndex];
                String queenRow = subOutput.get(queenRowIndex);
                queenRow = queenRow.substring(0, colIndex)
                    + "Q" + queenRow.substring(colIndex + 1);
                subOutput.set(queenRowIndex, queenRow);
            }
        }
        return output;
    }

    private String outputRowTemplate = null;

    private String getOutputRowTemplate(int n) {
        if (outputRowTemplate != null) return outputRowTemplate;
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(".");
        }
        outputRowTemplate = sb.toString();
        return outputRowTemplate;
    }

    private List<String> getSubOutputTemplate(int n) {
        List<String> subOutput = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            subOutput.add(getOutputRowTemplate(n));
        }
        return subOutput;
    }

}
