import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prevNode;
        private int moves;
        private int manhattan;

        public SearchNode(Board board, SearchNode prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            this.manhattan = board.manhattan();
            if (prevNode != null) this.moves = prevNode.moves + 1;
            else this.moves = 0;
        }

        @Override
        public int compareTo(SearchNode that) {
            int priorityDiff = (this.manhattan + this.moves) - (that.manhattan + that.moves);
            return priorityDiff == 0 ? this.manhattan - that.manhattan : priorityDiff;
        }
    }


    private SearchNode goal;
    private ArrayList<Board> listSolution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        SearchNode poppedNode = null;
        SearchNode poppedNodeTwin = null;
        listSolution = new ArrayList<Board>();
        MinPQ<SearchNode> searchNodes = new MinPQ<SearchNode>();
        MinPQ<SearchNode> searchNodesTwin = new MinPQ<SearchNode>();
        searchNodes.insert(new SearchNode(initial, null));
        searchNodesTwin.insert(new SearchNode(initial.twin(), null));

        while (true) {
            poppedNode = searchNodes.delMin();
            poppedNodeTwin = searchNodesTwin.delMin();
            listSolution.add(poppedNode.board);

            if (poppedNode.board.isGoal() || poppedNodeTwin.board.isGoal()) {
                goal = poppedNode;
                break;
            }

            SearchNode prevNode = poppedNode.prevNode == null ? poppedNode : poppedNode.prevNode;
            for (Board board : poppedNode.board.neighbors()) {
                if (!board.equals(prevNode.board)) {
                    searchNodes.insert(new SearchNode(board, poppedNode));
                }
            }

            SearchNode prevNodeTwin = poppedNodeTwin.prevNode == null ? poppedNodeTwin :
                                      poppedNodeTwin.prevNode;
            for (Board board : poppedNodeTwin.board.neighbors()) {
                if (!board.equals(prevNodeTwin.board)) {
                    searchNodesTwin.insert(new SearchNode(board, poppedNodeTwin));
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (goal.board.isGoal()) {
            return true;
        }
        return false;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable()) {
            return goal.moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return listSolution;
        }
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
