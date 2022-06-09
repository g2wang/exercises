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

public class Skyline {
    public static void main(String[] args) {
        int[][] buildings = new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        Skyline sk = new Skyline();
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

    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<Event> events = buildEvents(buildings);
        List<List<Integer>> ans = new ArrayList<>();
        TreeMap<Integer, Integer> map = new TreeMap<>(); // height -> count
        for (Event e : events) {
            Integer existingCount = map.get(e.y);
            if (existingCount == null) existingCount = 0; 
            if (e.isEntering) {
                if (map.isEmpty() || e.y > map.lastEntry().getKey()) {
                    ans.add(List.of(e.x, e.y)); 
                } 
                map.put(e.y, ++existingCount);
            } else {
                existingCount--;
                if (existingCount <= 0) {
                    map.remove(e.y);
                } else {
                    map.put(e.y, existingCount);
                }
                if (map.isEmpty()) {
                    ans.add(List.of(e.x, 0)); 
                } else if (e.y > map.lastEntry().getKey()) {
                    ans.add(List.of(e.x, map.lastEntry().getKey())); 
                }
            }
        }
        return ans;
    }
    
    private List<Event> buildEvents(int[][] buildings) {
        List<Event> events = new ArrayList<>(buildings.length * 2);
        for (int[] b : buildings) {
            Event e = new Event(b[0], b[2], true); 
            events.add(e);
            e = new Event(b[1], b[2], false); 
            events.add(e);
        } 
        Collections.sort(events); 
        return events;
    }
    
    private class Event implements Comparable<Event> {
        int x, y; 
        boolean isEntering; 
        
        public Event(int x, int y, boolean isEntering) {
            this.x = x; this.y = y;
            this.isEntering = isEntering; 
        }
        
        public int compareTo(Event e) {
            if (x == e.x) {
                if (isEntering && e.isEntering) {
                    return e.y - y; 
                } else if (!isEntering && !e.isEntering) {
                    return y - e.y;
                } else if (!isEntering && e.isEntering) {
                    return 1; 
                } else if (isEntering && !e.isEntering) {
                    return -1; 
                }
            }
            return x - e.x;
        }
    }

}
