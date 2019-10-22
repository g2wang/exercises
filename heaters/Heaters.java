/*
Winter is coming! Your first job during the contest is to design a standard heater with fixed warm radius to warm all the houses.

Now, you are given positions of houses and heaters on a horizontal line, find out minimum radius of heaters so that all houses could be covered by those heaters.

So, your input will be the positions of houses and heaters seperately, and your expected output will be the minimum radius standard of heaters.

Note:

Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
As long as a house is in the heaters' warm radius range, it can be warmed.
All the heaters follow your radius standard and the warm radius will the same.
 

Example 1:

Input: [1,2,3],[2]
Output: 1
Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the houses can be warmed.
 

Example 2:

Input: [1,2,3,4],[1,4]
Output: 1
Explanation: The two heater was placed in the position 1 and 4. We need to use radius 1 standard, then all the houses can be warmed.

Example 3:
[636807826,563613512,101929267,580723810,704877633,358580979,624379149,128236579]
[530511967,110010672]

*/

import java.util.Arrays;

public class Heaters {

    public static void main(String[] args) {
        Heaters h = new Heaters();
        int[] houses = new int[]{1,2,3};
        int[] heaters = new int[]{2};
        int r = h.findRadius(houses, heaters);
        System.out.println("houses: " + Arrays.toString(houses) + "; heaters: " + Arrays.toString(heaters) + "; radius: " + r);

        houses = new int[]{1,2,3,4};
        heaters = new int[]{1,4};
        r = h.findRadius(houses, heaters);
        System.out.println("houses: " + Arrays.toString(houses) + "; heaters: " + Arrays.toString(heaters) + "; radius: " + r);
        
    }

    public int findRadius (int[] houses, int[] heaters) {
        Arrays.parallelSort(houses);
        Arrays.parallelSort(heaters);
        int r = 0;
        int j = 0;
        for (int i = 0; i < houses.length; i++) {
            int rmin = 1_000_000_000;
            do {
                int d = houses[i] - heaters[j];
                if (d < 0) d = -d;
                if (d <= rmin) {
                    rmin = d;
                    if (j < heaters.length - 1) {
                       j++;
                    } else {
                       break;
                    }
                } else {
                    j--;
                    break;
                }
            } while (true);

            if (rmin > r) {
                r = rmin;
            }
        }
        return r;
    }
}
