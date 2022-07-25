/**
 * 787. Cheapest Flights Within K Stops
Medium

There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.

You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.

Example 1:

Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
Output: 700
Explanation:
The graph is shown above.
The optimal path with at most 1 stop from city 0 to 3 is marked in red and has cost 100 + 600 = 700.
Note that the path through cities [0,1,2,3] is cheaper but is invalid because it uses 2 stops.
 *
 */
import java.util.*;

public class CheapestKStopFlight {

    public static void main(String[] args) {
        CheapestKStopFlight cf = new CheapestKStopFlight();
        int n = 4;
        int[][] flights = new int[][]{new int[]{0,1,100}, new int[]{1,2,100}, new int[]{2,0,100}, new int[]{1,3,600}, new int[]{2,3,200}};
        int src = 0;
        int dst = 3; 
        int k = 1;
        int ans = cf.findCheapestPrice(n, flights, src, dst, k);
        System.out.printf("ans: %d%n", ans);
    }

    /**
     * Runtime: 5 ms, faster than 89.86% of Java online submissions for Cheapest Flights Within K Stops.
     * Memory Usage: 47.3 MB, less than 45.52% of Java online submissions for Cheapest Flights Within K Stops.
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
       
        int[] prices = new int[n];
        for (int i = 0; i < n; i++) {
            prices[i] = Integer.MAX_VALUE; 
        }
        prices[src] = 0;
        
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>()); 
        }
        for (int i = 0; i < flights.length; i++) {
            int[] e = flights[i];
            adj.get(e[0]).add(new int[]{e[1], e[2]});
        }
        
        /*
        for (int i = 0; i < n; i++) {
            List<int[]> a = adj.get(i);
            System.out.printf("i: %d -> ", i); 
            for (int[] e : a) { 
                System.out.printf("%s ", Arrays.toString(e)); 
            }
            System.out.println(""); 
        }
        */
       
        List<int[]> children = adj.get(src);
        for (int[] c : children) {
            prices[c[0]] = c[1]; 
        }
       
        bfs(adj, k, prices, 0, children);
        
        int ans = prices[dst];
        if (ans == Integer.MAX_VALUE) return -1;
        return ans;
    }

    private void bfs(List<List<int[]>> adj, int k, int[] prices, int depth, List<int[]> from) {
        if (depth == k) {
            return; 
        }
        List<int[]> newFrom = new ArrayList<>();
        int[] newPrices = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            newPrices[i] = prices[i]; 
        }
        for (int[] f : from) {
            List<int[]> children = adj.get(f[0]);
            for (int[] c : children) {
                int newP = prices[f[0]] + c[1];
                if (newPrices[c[0]] > newP) {
                    newPrices[c[0]] = newP;
                    newFrom.add(c); 
                }
            }
        }
        for (int i = 0; i < prices.length; i++) {
            prices[i] = newPrices[i];
        }
        // System.out.printf("prices: %s%n", Arrays.toString(prices));
        bfs(adj, k, prices, depth+1, newFrom); 
    }
}
