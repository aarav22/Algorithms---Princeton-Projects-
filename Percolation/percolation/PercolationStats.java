/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trial_results;
    private int num_trials;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N and T must be <= 0");
        }
        trial_results = new double[trials];
        num_trials = trials;

        for (int i = 0; i < trials; ++i) {
            Percolation test = new Percolation(n);
            while (!test.percolates()) {
                int row_index = StdRandom.uniform(1, n + 1);
                int col_index = StdRandom.uniform(1, n + 1);
                test.open(row_index, col_index);
            }
            int open_sites = test.numberOfOpenSites();
            double result = (double) open_sites / (n * n);
            trial_results[i] = result;
        }
        mean = StdStats.mean(trial_results);
        stddev = StdStats.stddev(trial_results);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(num_trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(num_trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        // Empty
    }

}
