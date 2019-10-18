public class MaxProfitTrading {

    public static void main(String[] args) {
        // given an array of known prices of a stock over time, find the maximum profit
        // by first buying and then selling
        //int[] p = new int[] {1, 1, 1, 1, 1, 1, 1, 1};
        //int[] p = new int[] {1, 1, 24, 1, 1, 25, 25, 17};
        //int[] p = new int[] {10, 11, 8, 7, 1, 1, 1, 2};
        int[] p = new int[] {10, 10, 8, 7, 5, 3, 2, 2};
        int[] result = findMaxProfit(p);
        System.out.println("maxProfit = " + result[0]);
        if (result[0] > 0) {
            System.out.println("iBuy = " + result[1] + "; buying price = " + p[result[1]]);
            System.out.println("iSell = " + result[2] + "; selling price = " + p[result[2]]);
        }
    }

    public static int[] findMaxProfit(int[] p) {
        int maxProfit = 0;
        int iBuy = 0;
        int iSell = 0;
        int iPotential = 0;
        for (int i = 1; i < p.length; i++) {
            int d = p[i] - p[iBuy];
            int dp = p[i] - p[iPotential];
            if (d > maxProfit) {
                maxProfit = d;
                iSell = i;
            }
            if (dp > maxProfit) {
                iBuy = iPotential;
                maxProfit = dp;
                iSell = i;
            }
            if (p[i] < p[iPotential]) {
                iPotential = i;
            }
        }
        return new int[] {maxProfit, iBuy, iSell};
    }
}
