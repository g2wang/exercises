import java.util.ArrayList;

public class MaxProfitTrading {

public static void main(String[] args) {
        // given an array of known prices of a stock over time, find the maximum profit
        // by first buying and then selling
        ArrayList<int[]> inputs = new ArrayList<>();
        inputs.add(new int[] {1, 1, 1, 1, 1, 1, 1, 1}); // no profit
        inputs.add(new int[] {1, 1, 24, 1, 1, 25, 25, 17}); // max prfit 24
        inputs.add(new int[] {10, 11, 8, 7, 1, 1, 1, 2}); // max prfit 1
        inputs.add(new int[] {10, 10, 8, 7, 5, 3, 2, 2}); // no profit
        for (int[] p : inputs) {
            int[] result = findMaxProfit(p);
            System.out.print("prices: ");
            for (int i = 0; i < p.length; i++) {
                System.out.printf("%4d", p[i]);
            }
            System.out.println("");
            System.out.print("   day: ");
            for (int i = 0; i < p.length; i++) {
                System.out.printf("%4d", i+1);
            }
            System.out.printf(" -> maxProfit: %d", result[0]);
            if (result[0] > 0) {
                System.out.printf(" (buy at day %d with price %d, sell at day %d with price %d)",
                        result[1]+1, p[result[1]], result[2]+1, p[result[2]]);
            } 
            System.out.println("");
        }
    }

    public static int[] findMaxProfit(int[] p) {
        int maxProfit = 0, iBuy = 0, iSell = 0, iMin = 0;
        for (int i = 1; i < p.length; i++) {
            if (p[i] < p[iMin]) {
                iMin = i;
            } else {
                int dp = p[i] - p[iMin];
                if (dp > maxProfit) {
                    maxProfit = dp; iBuy = iMin; iSell = i;
                }
            }
        }
        return new int[] {maxProfit, iBuy, iSell};
    }
}
