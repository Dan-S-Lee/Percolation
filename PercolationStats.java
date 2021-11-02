import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int[] opened;
    private int trialNum;
    private int n;
    private int size;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("Size and trials must be greater"
                                                       + "than zero.");
        }
        trialNum = trials;
        size = n;
        opened = new int[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percTemp = new Percolation(n);

            int randRow, randCol;
            while (!percTemp.percolates()) {
                randRow = StdRandom.uniform(size) + 1;
                randCol = StdRandom.uniform(size) + 1;
                // System.out.printf("Row: %s\nCol: %s\n",randRow, randCol);
                percTemp.open(randRow, randCol);
            }
            opened[i] = percTemp.numberOfOpenSites() / (size * size);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(opened);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(opened);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - ((1.96 * (stddev())) / (Math.sqrt(trialNum))));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((1.96 * (stddev())) / (Math.sqrt(trialNum))));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);

        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.print("95% confidence interval = [ ");
        System.out.print(percolationStats.confidenceLo() + ", ");
        System.out.print(percolationStats.confidenceHi());
        System.out.println("]");
    }

}
