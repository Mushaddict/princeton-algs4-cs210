import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// Models an N-by-N percolation system.
public class Percolation {
    
    private int N;
    private int source;
    private int sink;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private int openedCount;
    
    // Create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        source = 0;
        sink = N * N + 1;
        uf = new WeightedQuickUnionUF(N * N + 1);
        uf2 = new WeightedQuickUnionUF(N * N + 2);
        openedCount = 0;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            uf.union(encode(0, i), source);
            uf2.union(encode(0, i), source);
            uf2.union(encode(N-1, i), sink);
        }
        
    }
    // Open site (i, j) if it is not open already.
    public void open(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(i, j)) {
            grid[i][j] = true;
            openedCount++;
        }
        if (i < N - 1 && isOpen(i + 1, j)) {
            uf.union(encode(i, j), encode(i + 1, j));
            uf2.union(encode(i, j), encode(i + 1, j));
        }
        if (i > 0 && isOpen(i - 1, j)) {
            uf.union(encode(i, j), encode(i - 1, j));
            uf2.union(encode(i, j), encode(i - 1, j));
        }
        if (j < N - 1 && isOpen(i, j + 1)) {
            uf.union(encode(i, j), encode(i, j + 1));
            uf2.union(encode(i, j), encode(i, j + 1));
        }
        if (j > 0 && isOpen(i, j - 1)) {
            uf.union(encode(i, j), encode(i, j - 1));
            uf2.union(encode(i, j), encode(i, j - 1));
        }
    }
    // Is site (i, j) open?
    public boolean isOpen(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException();
        }
        return grid[i][j];
    }
    // Is site (i, j) full?
    public boolean isFull(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException();
        } else {
            return uf.connected(source, encode(i, j));
        }
    }
    // Number of open sites.
    public int numberOfOpenSites() {
        return openedCount;
    }
    // Does the system percolate?
    public boolean percolates() {
        return uf2.connected(source, sink);
    }
    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
        return i * N + j + 1;
    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
        
        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
