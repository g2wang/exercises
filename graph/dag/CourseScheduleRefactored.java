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
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CourseScheduleRefactored {
    public static void main(String[] args) {
        int numCourses = 2;
        //int[][] prerequisites = new int[][]{{1,0}}; //ans = true
        int[][] prerequisites = new int[][]{{0,1}, {1, 0}}; //ans = false
        //int numCourses = 4;
        //int[][] prerequisites = new int[][]{{0,1},{2,3},{1,2},{3,0}}; //ans = false
        //int[][] prerequisites = new int[][]{{0,1},{2,3},{1,2},{0,3}}; //ans = true

        CourseScheduleRefactored cs = new CourseScheduleRefactored();
        boolean ans = cs.canFinish(numCourses, prerequisites); 

        String[] edges = new String[prerequisites.length];
        for (int i = 0; i < prerequisites.length; i++) {
            edges[i] = Arrays.toString(prerequisites[i]);
        }
        System.out.printf("can finish: %b <- %s\n", ans, Arrays.toString(edges));
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Digraph g = new Digraph(numCourses, prerequisites);
        Optional<List<Integer>> result = g.topoOrder();
        if (result.isPresent()) {
            System.out.printf("top sort result: %s\n", result.get().toString());
            return true;
        } 
        return false;
    }

    class Digraph {

        private List<List<Integer>> adj; // adjacency list
        private int e; // num edges

        /**
         * number of vertices
         */
        public int v() {
            return adj.size();
        }

        /**
         * number of edges
         */
        public int e() {
            return e;
        }

        public Digraph(int v, int[][] edges) {
            adj = new ArrayList<>(v);
            for (int i = 0; i < v; i++) {
                adj.add(new ArrayList<>()); 
            }
            e = 0;
            if (edges != null) {
                for (int[] e : edges) {
                    addEdge(e[0], e[1]);
                }
            }
        }

        public void addEdge(int from, int to) {
            adj.get(from).add(to);
            e++;
        }

        public List<Integer> getAdj(int i) {
            return adj.get(i);
        }

        /**
         * returns the topo order if there is one, otherwise returns null
         */
        public Optional<List<Integer>> topoOrder() {
            TopoSort t = new TopoSort(this);
            return t.topoOrder();
        }
    }

    class TopoSort {
        private Digraph g;
        private boolean[] visited;
        private boolean[] visiting;
        private List<Integer> topoOrder;
        private boolean hasTopoOrder = true;

        public Optional<List<Integer>> topoOrder() {
            if (hasTopoOrder) {
                return Optional.of(topoOrder);
            }
            return Optional.empty();
        }

        public TopoSort(Digraph g) {
            this.g = g;
            visited = new boolean[g.v()];
            visiting = new boolean[g.v()];
            topoOrder = new ArrayList<>(g.v());
            sort();
        }

        private void sort() {
            for (int i = 0; i < g.v(); i++) {
                dfs(i);
            }
        }

        private void dfs(int i) {
            if (visited[i]) return;
            if (visiting[i]) {
                hasTopoOrder = false;
                return;
            }
            visiting[i] = true;
            for (int j : g.getAdj(i)) {
                dfs(j);
            }
            visited[i] = true;
            topoOrder.add(i);
        }

    }

}
