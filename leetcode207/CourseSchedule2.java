/*
207. Course Schedule
Medium

There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.

Example 1:
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take.
To take course 1 you should have finished course 0. So it is possible.

Example 2:
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take.
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.

Constraints:

1 <= numCourses <= 2000
0 <= prerequisites.length <= 5000
prerequisites[i].length == 2
0 <= ai, bi < numCourses
All the pairs prerequisites[i] are unique.

 */

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CourseSchedule2 {
    public static void main(String[] args) {
        int numCourses = 2;
        //int[][] prerequisites = new int[][]{{1,0}}; //ans = true
        int[][] prerequisites = new int[][]{{0,1}, {1, 0}}; //ans = false
        //int numCourses = 4;
        //int[][] prerequisites = new int[][]{{0,1},{2,3},{1,2},{3,0}}; //ans = false
        //int[][] prerequisites = new int[][]{{0,1},{2,3},{1,2},{0,3}}; //ans = true

        CourseSchedule2 cs = new CourseSchedule2();
        boolean ans = cs.canFinish(numCourses, prerequisites); 

        String[] edges = new String[prerequisites.length];
        for (int i = 0; i < prerequisites.length; i++) {
            edges[i] = Arrays.toString(prerequisites[i]);
        }
        System.out.printf("can finish: %b <- %s\n", ans, Arrays.toString(edges));
    }

    private boolean[] onStack;
    private boolean[] visited;
    private List<List<Integer>> adj;
    
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        
        onStack = new boolean[numCourses];
        visited = new boolean[numCourses];
        adj = new ArrayList<>(numCourses);
        
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<Integer>());
        }
        for (int[] pair : prerequisites) {
            int i = pair[0], j = pair[1];
            List<Integer> list = adj.get(j); 
            list.add(i);
        }
        for (int v = 0; v < numCourses; v++) {
            if (!visited[v]) {
                if (!isDag(v)) return false;
            }
        }
        return true;
    }
    
    private boolean isDag(int v) {
        onStack[v] = true;
        visited[v] = true;
        for (Integer w : adj.get(v)) {
            if (!visited[w]) {
               if (!isDag(w)) return false;
            } else if (onStack[w]) {
                return false; 
            }
        }
        onStack[v] = false;
        return true;
    }

}
