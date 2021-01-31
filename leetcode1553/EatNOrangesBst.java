/**
 * 1553. Minimum Number of Days to Eat N Oranges
Hard

There are n oranges in the kitchen and you decided to eat some of these oranges every day as follows:

Eat one orange.
If the number of remaining oranges (n) is divisible by 2 then you can eat  n/2 oranges.
If the number of remaining oranges (n) is divisible by 3 then you can eat  2*(n/3) oranges.
You can only choose one of the actions per day.

Return the minimum number of days to eat n oranges.


Example 1:

Input: n = 10
Output: 4
Explanation: You have 10 oranges.
Day 1: Eat 1 orange,  10 - 1 = 9.
Day 2: Eat 6 oranges, 9 - 2*(9/3) = 9 - 6 = 3. (Since 9 is divisible by 3)
Day 3: Eat 2 oranges, 3 - 2*(3/3) = 3 - 2 = 1.
Day 4: Eat the last orange  1 - 1  = 0.
You need at least 4 days to eat the 10 oranges.
Example 2:

Input: n = 6
Output: 3
Explanation: You have 6 oranges.
Day 1: Eat 3 oranges, 6 - 6/2 = 6 - 3 = 3. (Since 6 is divisible by 2).
Day 2: Eat 2 oranges, 3 - 2*(3/3) = 3 - 2 = 1. (Since 3 is divisible by 3)
Day 3: Eat the last orange  1 - 1  = 0.
You need at least 3 days to eat the 6 oranges.
Example 3:

Input: n = 1
Output: 1
Example 4:

Input: n = 56
Output: 6


Constraints:

1 <= n <= 2*10^9
 */

import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

public class EatNOrangesBst {

    public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        EatNOrangesBst eno = new EatNOrangesBst();
        int min = eno.minDays(n);
        System.out.printf("minDays(%d)=%d%n", n, min);
    }

    public int minDays(int n) {
        Set<Integer> seen = new HashSet<>();
        LinkedList<Integer> q = new LinkedList<>();
        q.offer(n);
        int d = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int m = q.poll();
                if (m == 0) return d;
                int r = m - 1;
                if (!seen.contains(r)) {
                    seen.add(r);
                    q.offer(r);
                }
                if (m%2 == 0) {
                    r = m/2;
                    if (!seen.contains(r)) {
                        seen.add(r);
                        q.offer(r);
                    }
                }
                if (m%3 == 0) {
                    r = m/3;
                    if (!seen.contains(r)) {
                        seen.add(r);
                        q.offer(r);
                    }
                }
            }
            d++;
        } 
        return -1;
    }
}
