import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private int[][] board;
    private int dimension;
    private int[][] zeroManhattanDistance;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        board = new int[dimension][dimension];
        zeroManhattanDistance = new int[dimension * dimension + 1][2];
        int counterZero = 1;

        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                board[i][j] = tiles[i][j];
                zeroManhattanDistance[counterZero][0] = i;
                zeroManhattanDistance[counterZero][1] = j;
                counterZero++;
            }
        }
        zeroManhattanDistance[0][0] = dimension - 1;
        zeroManhattanDistance[0][1] = dimension - 1;
    }

    // string representation of this board
    public String toString() {
	StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();   
     }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                if ((i * dimension + j + 1) != dimension * dimension
                        && board[i][j] != dimension * i + j + 1) {
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
		if (board[i][j] != 0) {
                manhattanDistance += Math.abs(zeroManhattanDistance[board[i][j]][0] - i) + Math
                        .abs(zeroManhattanDistance[board[i][j]][1] - j);
		}
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board boardCheck = (Board) y;
        if (this == boardCheck) {
            return true;
        }
	if (y.getClass() != this.getClass()) return false;
        if (this.dimension != boardCheck.dimension) {
            return false;
        }
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                if (this.board[i][j] != boardCheck.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[][] combinationsList = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        ArrayList<Board> neighbors = new ArrayList<Board>();

        int[][] neighbourBoard = new int[dimension][dimension];
        int rowIndex = -1, colIndex = -1;
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                neighbourBoard[i][j] = board[i][j];
                if (board[i][j] == 0) {
                    rowIndex = i;
                    colIndex = j;
                }
            }
        }
        int newRowIndex = 0;
        int newColIndex = 0;
        for (int i = 0; i < 4; ++i) {
            newRowIndex = rowIndex + combinationsList[i][0];
            newColIndex = colIndex + combinationsList[i][1];

            if (!checkValidity(newRowIndex, newColIndex)) {
                continue;
            }
            Board neighbour = new Board(neighbourBoard);
            neighbour.board[rowIndex][colIndex] = neighbour.board[newRowIndex][newColIndex];
            neighbour.board[newRowIndex][newColIndex] = 0;
            neighbors.add(neighbour);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(board);
        int temp = twinBoard.board[0][0];
        twinBoard.board[0][0] = twinBoard.board[dimension - 1][dimension - 1];
        twinBoard.board[dimension - 1][dimension - 1] = temp;
        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] input_1 = new int[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                input_1[i][j] = i * 3 + j + 1;
            }
        }
        input_1[2][2] = 0;
        //input_1[0][0] = 2;
        //input_1[0][1] = 1;
        Board b = new Board(input_1);
        System.out.println(b.toString());
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        System.out.println(b.isGoal());
        Iterable<Board> c = b.neighbors();
        for (Board a : c) {
            System.out.println(a.toString());
        }
    }

    private boolean checkValidity(int rowIndex, int colIndex) {
        return (rowIndex < dimension && rowIndex >= 0 && colIndex < dimension && colIndex >= 0);
    }

}
