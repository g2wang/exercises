/*
leetcode 218. The Skyline Problem
Hard
https://leetcode.com/problems/the-skyline-problem/

A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A), write a program to output the skyline formed by these buildings collectively (Figure B).

Buildings Skyline Contour
The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.

For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .

The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.

For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].

Notes:

The number of buildings in any input list is guaranteed to be in the range [0, 10000].
The input list is already sorted in ascending order by the left x position Li.
The output list must be sorted by the x position.
There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]

 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class Skyline2 {
    public static void main(String[] args) {
        int[][] buildings = new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        Skyline2 sk = new Skyline2();
        List<List<Integer>> skyline = sk.getSkyline(buildings);
        StringBuilder sb = new StringBuilder("[");
        Boolean first = true;
        for (List<Integer> list : skyline) {
            if (first) { first = false; } else { sb.append(", ");}
            sb.append("[").append(list.get(0)).append(", ")
                .append(list.get(1)).append("]");
        }
        sb.append("]");
        
        System.out.printf("buildings: %s\n-> skyline: %s\n",
               Arrays.deepToString(buildings), sb.toString());

    }

    /**
     * copyright: author of the code submission to LeetCode
     */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> keyPoints = new ArrayList<>();
        int lastLowXIdx = -1;
        for (int[] building : buildings){
            int li = building[0];
            int ri = building[1];
            int hi = building[2];
            lastLowXIdx = insertBuilding(li, ri, hi, keyPoints, lastLowXIdx);
        }

        return keyPoints;
    }

    protected int insertBuilding(int li, int ri, int hi, List<List<Integer>> keyPoints, int lastLowXIdx){
        int nextIdx = keyPoints.size();
        for (int i = lastLowXIdx + 1; i < keyPoints.size(); i++){
            if (keyPoints.get(i).get(0) > li){
                nextIdx = i;
                break;
            }
        }

        int currIdx = nextIdx - 1;
        int oldHight = currIdx >= 0 ? keyPoints.get(currIdx).get(1) : 0;
        int currentHigh = oldHight;
        if (hi > oldHight){
            if (currIdx < 0 || keyPoints.get(currIdx).get(0) < li) {
                keyPoints.add(currIdx + 1, makeKeyPoint(li, hi));
                currentHigh = hi;
                currIdx++;
            } else {
                if (currIdx >= 1 && keyPoints.get(currIdx - 1).get(1) == hi){
                    keyPoints.remove(currIdx);
                    currIdx--;
                    currentHigh = hi;
                } else {
                    keyPoints.get(currIdx).set(1, hi);
                    currentHigh = hi;
                }
            }
        } else {
            currentHigh = oldHight;
        }
        lastLowXIdx = currIdx;

        currIdx ++;
        while (currIdx < keyPoints.size() && keyPoints.get(currIdx).get(0) < ri){
            oldHight = keyPoints.get(currIdx).get(1);
            if (hi >= oldHight){
                if (currentHigh == hi){
                    keyPoints.remove(currIdx);
                    currIdx--;
                } else {
                    keyPoints.get(currIdx).set(1, hi);
                    currentHigh = hi;
                }
            } else {
                currentHigh = oldHight;
            }
            currIdx ++;
        }

        if (hi > oldHight && currentHigh != oldHight){
            if (currIdx >= keyPoints.size() || keyPoints.get(currIdx).get(0) > ri) {
                keyPoints.add(currIdx, makeKeyPoint(ri, oldHight));
                currentHigh = oldHight;
            }
        }

        return lastLowXIdx;
    }

    protected List<Integer> makeKeyPoint(int x, int y){
        List<Integer> keyPoint = new ArrayList<>(2);
        keyPoint.add(x);
        keyPoint.add(y);
        return keyPoint;
    }

}
