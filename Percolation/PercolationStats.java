import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    
    private int T;
    private Percolation perc;
    private double[] storage;
    // Perform T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        this.T = T;
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("out of range");
        }
        storage = new double[T];
        for (int i = 0; i < T; i++) {
            perc = new Percolation(N);
            
            while (!perc.percolates()) {
                int j = StdRandom.uniform(0, N);
                int k = StdRandom.uniform(0, N);
                perc.open(j, k);
            }
            double data = (double) perc.numberOfOpenSites()/(N * N);
            storage[i] = data;
        }
    }
    
    // Sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(storage);
    }
    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(storage);
    }
    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - ((1.96 * stddev())/Math.pow(T, 0.5));
    }
    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + ((1.96 * stddev())/Math.pow(T, 0.5));
    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
