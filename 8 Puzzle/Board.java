import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdOut;
// Models a board in the 8-puzzle game or its generalization.
public class Board {
    private int[][] tiles;
    private int N;
    private int hamming;
    private int manhatten;
    // Construct a board from an N-by-N array of tiles, where 
    // tiles[i][j] = tile at row i and column j, and 0 represents the blank 
    // square.
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.N = tiles.length;
        
        hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != 0) {
                    int correctTile = i * N + j + 1;
                    int tile = tileAt(i, j);
                    if (tile != correctTile) { //the right index
                        hamming++;
                    }
                }
            }
        }
        
        manhatten = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != 0) {
                    int correct = i * N + j + 1;
                    int tile = tileAt(i, j);
                    
                    int edge = 0;
                    if (tile != correct) {
                        int row = (tile - 1)/ N;
                        int column = (tile - 1) % N;
                        edge = Math.abs(row - i)
                            + Math.abs(column - j);
                    }
                    manhatten += edge;
                }
            }
        }
    }
    // Tile at row i and column j.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }
    
    // Size of this board.
    public int size() {
        return N;
    }
    // Number of tiles out of place.
    public int hamming() {
        return hamming;
    }
    // Sum of Manhattan distances between tiles and goal.
    public int manhattan() {
        return manhatten;
    }
    // Is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != 0) {
                    int correctIndex = i * N + j + 1;
                    if (tileAt(i, j) != correctIndex) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    // Is this board solvable?
    public boolean isSolvable() {
        if (N % 2 != 0) {
            if (inversions() % 2 != 0) {
                return false;
            }
        } else {
            if ((blankPos() + inversions()) % 2 == 0) {
                return false;
            }
        }
        return true;
    }
    // Does this board equal that?
    public boolean equals(Board that) {
        if (N != that.N) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != that.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    // All neighboring boards.
    public Iterable<Board> neighbors() {
        LinkedQueue<Board> queue = new LinkedQueue<Board>();
        
        //position for tile i and j
        int i = blankPos();
        int j = blankCol();
        
        //up case
        if (i != 0) {
            int[][] up = cloneTiles();
            up[i][j] = up[i-1][j];
            up[i - 1][j] = 0;
            queue.enqueue(new Board(up));
        }
        
        //right case
        if (j != N - 1) {
            int[][] right = cloneTiles();
            right[i][j] = right[i][j + 1];
            right[i][j + 1] = 0;
            queue.enqueue(new Board(right));
        }
        
        //down case
        if (i != N - 1) {
            int[][] down = cloneTiles();
            down[i][j] = down[i+1][j];
            down[i + 1][j] = 0;
            queue.enqueue(new Board(down));
        }
        
        //left case
        if (j != 0) {
            int[][] left = cloneTiles();
            left[i][j] = left[i][j - 1];
            left[i][j - 1] = 0;
            queue.enqueue(new Board(left));
        }
        
        return queue;
    }
    // String representation of this board.
    public String toString() {
        String s = N + "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s += String.format("%2d", tiles[i][j]);
                if (j < N - 1) {
                    s += " ";
                }
            }
            if (i < N - 1) {
                s += "\n";
            }
        }
        return s;
    }
    // Helper method that returns the position (in row-major order) of the 
    // blank (zero) tile.
    private int blankPos() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    //return the position in column major order
    private int blankCol() {
        int i = blankPos();
        for (int j = 0; j < N; j++) {
            if (tileAt(i, j) == 0) {
                return j;
            }
        }
        return -1;
    }
    // Helper method that returns the number of inversions.
    private int inversions() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != 0) {
                    for (int x = i; x < N; x++) {
                        for (int y = j + 1; y < N; y++) {
                            if (tileAt(i, j) < tileAt(x, y)) {
                                //check if any number is smaller than 
                                //the current number
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
    // Helper method that clones the tiles[][] array in this board and 
    // returns it.
    private int[][] cloneTiles() {
        int[][] clone = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
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
        Board board = new Board(tiles);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }
    }
}
