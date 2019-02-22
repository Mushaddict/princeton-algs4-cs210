import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
// A solver based on the A* algorithm for the 8-puzzle and its generalizations.
public class Solver {
    private LinkedStack<SearchNode> solution;
    private int moves = 0;
    
    // Helper search node class.
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode previous;
        SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }
    }
     
    // Find a solution to the initial board (using the A* algorithm).
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        if (!initial.isSolvable()) {
            throw new IllegalArgumentException();
        }
        
        solution = new LinkedStack<SearchNode>();
        
        ManhattanOrder mo = new ManhattanOrder();
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(mo);
        
        //constructor
        SearchNode current = new SearchNode(initial, 0, null);
        pq.insert(current);
        
        //insert neighbours of the initial SearchNode
        for (Board item : current.board.neighbors()) {
            if ((current.previous == null) 
                || (!item.equals(current.previous.board))) {
                    SearchNode temp = new SearchNode(item,
                        current.moves, current);
                        pq.insert(temp);
            } 
        }
        //find the goal
        while (!current.board.isGoal()) {
            current = pq.delMin();
            
            for (Board item : current.board.neighbors()) {
                if ((current.previous == null) 
                    || (!item.equals(current.previous.board))) {
                        SearchNode temp = new SearchNode(item, 
                            current.moves, current);
                        pq.insert(temp); 
                }
            }
            //save the min in solution
            solution.push(current);
            moves++;
        }
    }
    // The minimum number of moves to solve the initial board.
    public int moves() {
        return moves;
    }
    // Sequence of boards in a shortest solution.
    public Iterable<Board> solution() {
        LinkedStack<Board> sol = new LinkedStack<Board>();
        for (SearchNode node : solution) {
            sol.push(node.board);
        }
        return sol;
    }
    // Helper hamming priority function comparator.
    private static class HammingOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return a.board.hamming() + a.moves 
             - b.board.hamming() - b.moves;
        }
    }
    
    // Helper manhattan priority function comparator.
    private static class ManhattanOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return a.board.manhattan() + a.moves 
             - b.board.manhattan() - b.moves;
        }
    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
        else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
