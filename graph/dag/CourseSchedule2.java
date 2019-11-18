/*
207. Course Schedule

There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

Example 1:

Input: 2, [[1,0]] 
Output: true
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.
Note:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.
 */
import java.util.ArrayList;
import java.util.Arrays;

public class CourseSchedule2 {
    public static void main(String[] args) {
        //int numCourses = 2;
        //int[][] prerequisites = new int[][]{{1,0}}; //ans = true
        //int[][] prerequisites = new int[][]{{0,1}, {1, 0}}; //ans = false
        int numCourses = 4;
        //int[][] prerequisites = new int[][]{{0,1},{2,3},{1,2},{3,0}}; //ans = false
        int[][] prerequisites = new int[][]{{0,1},{2,3},{1,2},{0,3}}; //ans = true

        CourseSchedule2 cs = new CourseSchedule2();
        boolean ans = cs.canFinish(numCourses, prerequisites); 

        String[] edges = new String[prerequisites.length];
        for (int i = 0; i < prerequisites.length; i++) {
            edges[i] = Arrays.toString(prerequisites[i]);
        }
        System.out.printf("can finish: %b <- %s\n", ans, Arrays.toString(edges));
    }

    /**
     * copyright credit: code from LeetCode submissions
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] map = new int[numCourses];

        for(int i = 0; i < prerequisites.length; i++) map[prerequisites[i][1]]++;

        boolean changed = true;
        boolean[] visited = new boolean[prerequisites.length];

        while (changed) {
            changed = false;
            for (int i = 0; i < prerequisites.length; i++) {
                if (!visited[i]) {
                    int from = prerequisites[i][0];
                    int to = prerequisites[i][1];
                    if (map[from] == 0 && map[to] != 0) {
                        visited[i] = true;
                        map[to]--;
                        changed = true;
                    }
                }
            }
        }

        for (int i : map) {
            if (i > 0) return false;
        }

        return true;
    }

}
