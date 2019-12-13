import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public final class BaseballElimination {

    private final int n; // number of teams
    private final Map<String, Integer> index; // team name -> array index map
    private final String[] name;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] games;
    private final Boolean[] eliminated;
    private final List<Bag<String>> eliminatedBy;

    /**
     * create a baseball division from given filename in format specified below
     */
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = in.readInt();
        index = new HashMap<String, Integer>(n);
        name = new String[n];
        wins = new int[n];
        losses = new int[n];
        remaining = new int[n];
        games = new int[n][n];
        eliminated = new Boolean[n];
        eliminatedBy = new ArrayList<Bag<String>>(n);
        for (int i = 0; i < n; i++) {
            String team = in.readString();
            index.put(team, i);
            name[i] = team;
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < n; j++) {
                games[i][j] = in.readInt();
            }
            eliminatedBy.add(null);
        }
//        System.out.println(n);
//        for (int i = 0; i < n; i++) {
//            StdOut.printf("%-20s %4d %4d %4d ", name[i], wins[i], losses[i], remaining[i]);
//            for (int j = 0; j < n; j++) {
//                StdOut.printf("%4d", games[i][j]);
//            }
//            StdOut.printf("%s\n", "");
//        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    /**
     * number of teams
     */
    public int numberOfTeams() {
        return n;
    }

    /**
     * all teams
     */
    public Iterable<String> teams() {
        return index.keySet();
    }

    /**
     * number of wins for given team
     */
    public int wins(String team) {
        Integer i = index.get(team);
        if (i == null) throw new IllegalArgumentException("Invalid team name: " + team);
        return wins[i];
    }

    /**
     * number of losses for given team
     */
    public int losses(String team) {
        Integer i = index.get(team);
        if (i == null) throw new IllegalArgumentException("Invalid team name: " + team);
        return losses[i];
    }

    /**
     * number of remaining games for given team
     */
    public int remaining(String team) {
        Integer i = index.get(team);
        if (i == null) throw new IllegalArgumentException("Invalid team name: " + team);
        return remaining[i];
    }

    /**
     * number of remaining games between team1 and team2
     */
    public int against(String team1, String team2) {
        Integer i = index.get(team1);
        if (i == null) throw new IllegalArgumentException("Invalid team name 1: " + team1);
        Integer j = index.get(team2);
        if (j == null) throw new IllegalArgumentException("Invalid team name 2: " + team2);
        return games[i][j];
    }

    /**
     * is given team eliminated?
     */
    public boolean isEliminated(String team) {
        Integer idx = index.get(team);
        if (idx == null) throw new IllegalArgumentException("Invalid team name: " + team);

        if (eliminated[idx] != null) return eliminated[idx];

        int fullCapacity = 0; // initial value
        int numVertices = 0; // initial value
        int mustWin = wins[idx] + remaining[idx]; // team must win all the remaining games

        // check for trivial eliminations
        for (int i = 0; i < n; i++) {
            if (i != idx && wins[i] > mustWin) {
                if (eliminated[idx] == null || !eliminated[idx]) {
                    eliminated[idx] = true;
                    eliminatedBy.set(idx, new Bag<String>());
                }
                eliminatedBy.get(idx).add(name[i]);
            }
        }
        if (eliminated[idx] != null && eliminated[idx]) return true;

        Bag<FlowEdge> edges = new Bag<FlowEdge>();
        Map<Integer, Integer> teamNodes = new HashMap<Integer, Integer>(n);
        Integer nodeT = null;

        for (int i = 0; i < n; i++) {
            if (i != idx) {
                for (int j = i; j < n; j++) {
                    if (j != idx && games[i][j] > 0) {
                        numVertices++; // game node
                        int gameNode = numVertices;
                        fullCapacity += games[i][j];
                        edges.add(new FlowEdge(0, gameNode, games[i][j]));
                        Integer teamNodeI = teamNodes.get(i);
                        Integer teamNodeJ = teamNodes.get(j);
                        if (teamNodeI == null) {
                            numVertices++; // team node
                            teamNodeI = numVertices;
                            teamNodes.put(i, teamNodeI);
                            if (nodeT == null) {
                                numVertices++; // node t
                                nodeT = numVertices;
                            }
                            if (mustWin - wins[i] < 0) { // trivial elimination
                                eliminated[idx] = true;
                                eliminatedBy.set(idx, new Bag<String>());
                                eliminatedBy.get(idx).add(name[i]);
                                return eliminated[idx];
                            }
                            edges.add(new FlowEdge(teamNodeI, nodeT, mustWin - wins[i]));
                        }
                        if (teamNodeJ == null) {
                            numVertices++; // team node
                            teamNodeJ = numVertices;
                            teamNodes.put(j, teamNodeJ);
                            if (nodeT == null) {
                                numVertices++; // node t
                                nodeT = numVertices;
                            }
                            if (mustWin - wins[j] < 0) { // trivial elimination
                                eliminated[idx] = true;
                                eliminatedBy.set(idx, new Bag<String>());
                                eliminatedBy.get(idx).add(name[j]);
                                return eliminated[idx];
                            }
                            edges.add(new FlowEdge(teamNodeJ, nodeT, mustWin - wins[j]));
                        }
                        edges.add(new FlowEdge(gameNode, teamNodeI, Integer.MAX_VALUE));
                        edges.add(new FlowEdge(gameNode, teamNodeJ, Integer.MAX_VALUE));
                    }
                }
            }
        }

        if (fullCapacity == 0) { // no games left to play other than games involving this team
            for (int i = 0; i < n; i++) {
                if (i != idx && wins[i] > mustWin) {
                    if (eliminated[idx] == null || !eliminated[idx]) {
                        eliminated[idx] = true;
                        eliminatedBy.set(idx, new Bag<String>());
                    }
                    eliminatedBy.get(idx).add(name[i]);
                }
            }
            if (eliminated[idx] == null) eliminated[idx] = false;
            return eliminated[idx];
        }

        numVertices++; // for start node s

        // build flow network
        FlowNetwork fnet = new FlowNetwork(numVertices);
        for (FlowEdge e : edges) {
            fnet.addEdge(e);
        }

        // use FordFulkerson algorithm to find out if a team is eliminated
        FordFulkerson algo = new FordFulkerson(fnet, 0, nodeT); 
        // StdOut.printf("team: %s; maxFlow: %f; fullCapacity: %d\n", team, algo.value(), fullCapacity);
        eliminated[idx] = (int) algo.value() != fullCapacity;

        if (eliminated[idx]) {
            eliminatedBy.set(idx, new Bag<String>());
            // find eliminatedBy
            for (int i = 0; i < n; i++) {
                if (i != idx && teamNodes.get(i) != null && algo.inCut(teamNodes.get(i)))
                    eliminatedBy.get(idx).add(name[i]);
            }
        }
        return eliminated[idx];
    }

    /**
     * subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team) {
        Integer idx = index.get(team);
        if (idx == null) throw new IllegalArgumentException("Invalid team name: " + team);
        if (eliminated[idx] != null) return eliminatedBy.get(idx);
        isEliminated(team);
        return eliminatedBy.get(idx);
    }
}
