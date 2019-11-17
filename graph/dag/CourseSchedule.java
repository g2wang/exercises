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

public class CourseSchedule {
    public static void main(String[] args) {
        //int numCourses = 2;
        //int[][] prerequisites = new int[][]{{1,0}}; //ans = true
        int numCourses = 4;
        //int[][] prerequisites = new int[][]{{0,1},{2,3},{1,2},{3,0}}; //ans = false
        int[][] prerequisites = new int[][]{{0,1},{2,3},{1,2},{1,3}}; //ans = true

        CourseSchedule cs = new CourseSchedule();
        boolean ans = cs.canFinish(numCourses, prerequisites); 

        String[] edges = new String[prerequisites.length];
        for (int i = 0; i < prerequisites.length; i++) {
            edges[i] = Arrays.toString(prerequisites[i]);
        }
        System.out.printf("can finish: %b <- %s\n", ans, Arrays.toString(edges));
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Graph g = new Graph(numCourses, prerequisites);
        ArrayList<Integer> result = g.topoSort();
        if (result != null) {
            System.out.printf("top sort result: %s\n", Arrays.toString(result.toArray(new Integer[]{})));
            return true;
        } 
        return false;
    }

    class Graph {

        private ArrayList<ArrayList<Integer>> adj = null;

        public Graph(int n, int[][] edges) {
            adj = new ArrayList<ArrayList<Integer>>(n);
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<Integer>());
            }
            for (int[] e : edges) {
                addEdge(e);
            }
        }

        public void addEdge(int[] e) {
            adj.get(e[0]).add(e[1]);
        }

        public ArrayList<Integer> getAdj(int i) {
            return adj.get(i);
        }

        public int size() {
            return adj.size();
        }

        public ArrayList<Integer> topoSort() {
            TopoSort t = new TopoSort(this);
            return t.sort();
        } 
    }

    class TopoSort {

        Graph g = null;
        boolean[] visited = null;
        boolean[] visiting = null;
        ArrayList<Integer>result = null;
        boolean canBeSorted = true;

        public TopoSort(Graph g) {
            this.g = g;
            this.visited = new boolean[g.size()];
            this.visiting = new boolean[g.size()];
            this.result = new ArrayList<Integer>(g.size());
        }

        public ArrayList<Integer> sort() {
            for (int i = 0; i < visited.length; i++) {
                if (!visited[i]) {
                    dfs(i);
                }
            }
            if (canBeSorted) {
                return result;
            }
            return null;
        }

        public void dfs(int i) {
            if (visited[i]) return; 
            if (visiting[i]) { canBeSorted = false; return;}
            visiting[i] = true; 
            ArrayList<Integer> a = g.getAdj(i);
            for (Integer j : a) {
                dfs(j);
            }
            result.add(i);
            visited[i] = true; 
        }


    }
}
