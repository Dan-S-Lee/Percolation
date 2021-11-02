import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] grid;
    private WeightedQuickUnionUF gridUF;
    private WeightedQuickUnionUF gridUF2;
    private int numOpened;
    private boolean percolateBool;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }
        size = n;
        numOpened = 0;
        grid = new boolean[n][n];
        gridUF = new WeightedQuickUnionUF(n * n + 2);
        gridUF2 = new WeightedQuickUnionUF(n * n + 1);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validateInputs(row, col)) {
            throw new IllegalArgumentException("Indexes must be less than n "
                                                       + "and greater than zero.");
        }
        int gridRow = row - 1;
        int gridCol = col - 1;
        if (!grid[gridRow][gridCol]) {
            // System.out.println("OPENED:");
            // System.out.printf("Row: %s\nCol: %s\n", row, col);
            grid[gridRow][gridCol] = true;
            numOpened++;
            // System.out.println(Arrays.deepToString(grid));
            updateFill(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validateInputs(row, col)) {
            throw new IllegalArgumentException("Indexes must be less than n "
                                                       + "and greater than zero.");
        }
        int gridRow = row - 1;
        int gridCol = col - 1;
        return (grid[gridRow][gridCol]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validateInputs(row, col)) {
            throw new IllegalArgumentException("Indexes must be less than n "
                                                       + "and greater than zero.");
        }
        int gridRow = row - 1;
        int gridCol = col - 1;
        boolean b = isOpen(row, col) &&
                (gridUF.find(gridRow * size + gridCol) ==
                        gridUF.find(size * size)) &&
                (gridUF2.find(gridRow * size + gridCol) ==
                        gridUF2.find(size * size));
        return b;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolateBool;
    }

    private boolean validateInputs(int row, int col) {
        if ((row < 1) || (col < 1)) {
            return false;
        }
        if ((row > size) || (col > size)) {
            return false;
        }
        return true;
    }

    private void updateFill(int row, int col) {
        int gridRow = row - 1;
        int gridCol = col - 1;
        // check above, right, and left for full cells that can update
        if (row == 1) {
            gridUF.union(size * size, gridRow * size + gridCol);
            gridUF2.union(size * size, gridRow * size + gridCol);
            // System.out.println("UNION TO VIRTUAL TOPSITE");
        }
        else if (row == size) {
            gridUF.union(size * size + 1, gridRow * size + gridCol);
            // System.out.println("UNION TO VIRTUAL BOTTOMSITE");
        }
        if (row > 1 && isOpen(row - 1, col)) {
            gridUF.union((gridRow - 1) * size + gridCol,
                         gridRow * size + gridCol);
            gridUF2.union((gridRow - 1) * size + gridCol,
                          gridRow * size + gridCol);
        }
        if (col < size && isOpen(row, col + 1)) {
            gridUF.union((gridRow) * size + gridCol + 1,
                         gridRow * size + gridCol);
            gridUF2.union((gridRow) * size + gridCol + 1,
                          gridRow * size + gridCol);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            gridUF.union((gridRow) * size + gridCol - 1,
                         gridRow * size + gridCol);
            gridUF2.union((gridRow) * size + gridCol - 1,
                          gridRow * size + gridCol);
        }
        if (row < size && isOpen(row + 1, col)) {
            gridUF.union((gridRow + 1) * size + gridCol,
                         gridRow * size + gridCol);
            gridUF2.union((gridRow + 1) * size + gridCol,
                          gridRow * size + gridCol);
        }
        if (col < size && isOpen(row, col + 1)) {
            gridUF.union((gridRow) * size + gridCol + 1,
                         gridRow * size + gridCol);
            gridUF2.union((gridRow) * size + gridCol + 1,
                          gridRow * size + gridCol);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            gridUF.union((gridRow) * size + gridCol - 1,
                         gridRow * size + gridCol);
            gridUF2.union((gridRow) * size + gridCol - 1,
                          gridRow * size + gridCol);
        }
        if ((gridUF.find(size * size) == gridUF.find(size * size + 1)) ||
                (numOpened == size * size)) {
            percolateBool = true;
            // System.out.println("Percolation build complete.");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        // WeightedQuickUnionUF gridUF = new WeightedQuickUnionUF(16);
        // for (int n = 0; n<16;n++) {
        //   System.out.println(gridUF.find(n));
        // }
        System.out.println(percolation.numberOfOpenSites());
    }
}
