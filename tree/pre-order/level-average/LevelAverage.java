import java.util.Arrays;
import java.util.ArrayList;

public class LevelAverage {
    /**
     *        1
     *     3      6
     *   7   4  9   5
     *     2
     *       8
     *  level average:
     *  [1, 4.5, 6.25, 2, 8]
     */
    public static void main(String[] args) {
        Node n = new Node(1); 
        n.l = new Node(3);
        n.r = new Node(6);
        n.l.l = new Node(7);
        n.l.r = new Node(4);
        n.r.l = new Node(9);
        n.r.r = new Node(5);
        n.l.r.l = new Node(2);
        n.l.r.l.r = new Node(8);
        LevelAverage la = new LevelAverage();
        Double[] averages = la.calculate(n);
        System.out.println("averages: " + Arrays.toString(averages));
    }

    public Double[] calculate(Node n) {
        ArrayList<LevelStats> stats = new ArrayList<>();
        visit(n, stats, 0); 
        Double[] ans = new Double[stats.size()];
        for (int i = 0; i < stats.size(); i++) {
            LevelStats ls = stats.get(i);
            ans[i] = 1.0d*ls.sum/ls.num;
        }
        return ans;
    }

    /**
     * pre-order traverse
     */
    public void visit(Node n, ArrayList<LevelStats> stats, int h) {
        LevelStats st;
        if (h == stats.size()) {
            st =  new LevelStats(h);
            stats.add(st);
        } else {
            st = stats.get(h);
        }
        st.num++;
        st.sum += n.v;
        if (n.l != null) {
            visit(n.l, stats, h+1);
        }
        if (n.r != null) {
            visit(n.r, stats, h+1);
        }
    }

    public static class LevelStats {
        int level;
        int num;
        int sum;
        public LevelStats(int level) {
            this.level = level;
            this.num= 0;
            this.sum = 0;
        }
    }

    public static class Node {
        int v;
        Node l;
        Node r;
        public Node(int v) {
            this.v = v;
        }
    }

}
