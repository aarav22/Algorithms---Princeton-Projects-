/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[][] matrix;
    private int num_open;
    private WeightedQuickUnionUF grid;
    private int size_matrix;
    private int hypothetical_top;
    private int hypothetical_bottom;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Value of n <= 0");
        }
        else {
            matrix = new int[n][n];
            size_matrix = n;
            grid = new WeightedQuickUnionUF(n * n + 2);
            hypothetical_top = n * n;
            hypothetical_bottom = n * n + 1;
            num_open = 0;

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    matrix[i][j] = 0;  //blocked
                }
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int shifted_row = row - 1, shifted_col = col - 1;
        check_validity(shifted_row, shifted_col);
        if (isOpen(row, col)) {
            return;
        }
        matrix[shifted_row][shifted_col] = 1;  // Open site
        num_open++;
        int single_index = get_single_index(shifted_row, shifted_col);

        if (shifted_row == 0) { // Top Row
            grid.union(single_index, hypothetical_top);
        }

        if (shifted_row == size_matrix - 1) { // Top Row
            grid.union(single_index, hypothetical_bottom);
        }

        // Check left for open sites:
        if (check_on_grid(shifted_row, shifted_col - 1) && isOpen(row, col - 1)) {
            grid.union(single_index, get_single_index(shifted_row, shifted_col - 1));
        }

        //Check right for open sites:
        if (check_on_grid(shifted_row, shifted_col + 1) && isOpen(row, col + 1)) {
            grid.union(single_index, get_single_index(shifted_row, shifted_col + 1));
        }

        //Check top for open sites:
        if (check_on_grid(shifted_row - 1, shifted_col) && isOpen(row - 1, col)) {
            grid.union(single_index, get_single_index(shifted_row - 1, shifted_col));
        }

        //Check bottom for open sites:
        if (check_on_grid(shifted_row + 1, shifted_col) && isOpen(row + 1, col)) {
            grid.union(single_index, get_single_index(shifted_row + 1, shifted_col));
        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        check_validity(row - 1, col - 1);
        if (matrix[row - 1][col - 1] == 1) { // Open site
            return true;
        }

        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check_validity(row - 1, col - 1);
        boolean val = false;
        if (isOpen(row, col)) { // Open site
            val = (grid.find(get_single_index(row - 1, col - 1)) == grid.find(hypothetical_top));
        }
        return val;
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return num_open;
    }

    // does the system percolate?
    public boolean percolates() {
        return (grid.find(hypothetical_top) == grid.find(hypothetical_bottom));
    }

    // test client (optional)
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);

        Percolation percolation = new Percolation(size);
        int argCount = args.length;
        for (int i = 1; argCount >= 2; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            StdOut.printf("Adding row: %d  col: %d %n", row, col);
            percolation.open(row, col);
            if (percolation.percolates()) {
                StdOut.printf("%nThe System percolates %n");
            }
            argCount -= 2;
        }
        if (!percolation.percolates()) {
            StdOut.print("Does not percolate %n");
        }
    }

    private void check_validity(int shifted_row, int shifted_col) {
        if (!check_on_grid(shifted_row, shifted_col)) {
            throw new IllegalArgumentException("Value of row or col undefined");
        }
    }

    private boolean check_on_grid(int shifted_row, int shifted_col) {
        if ((shifted_row < 0 || shifted_row >= size_matrix) || (shifted_col < 0
                || shifted_col >= size_matrix)) {
            return false;
        }
        return true;
    }

    private int get_single_index(int shifted_row, int shifted_col) {
        return size_matrix * shifted_row + shifted_col;
    }


}
