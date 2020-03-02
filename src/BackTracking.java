import java.util.ArrayList;

public class BackTracking {
    private static int[][] grid = new int[9][9];
    private static int lastEmpty = 0;

    private static ArrayList<int[][]> solutions = new ArrayList<>();

    private static int[] automorphic = {0, 0, 0, 2, 1, 0, 0, 0, 0,
            0, 0, 7, 3, 0, 0, 0, 0, 0,
            0, 5, 8, 0, 0, 0, 0, 0, 0,
            4, 3, 0, 0, 0, 0, 0, 0, 0,
            2, 0, 0, 0, 0, 0, 0, 0, 8,
            0, 0, 0, 0, 0, 0, 0, 7, 6,
            0, 0, 0, 0, 0, 0, 2, 5, 0,
            0, 0, 0, 0, 0, 7, 3, 0, 0,
            0, 0, 0, 0, 9, 8, 0, 0, 0};

    private static int[] empty = {0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static int[] hardest = {0, 0, 0, 7, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 4, 3, 0, 2, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 6,
            0, 0, 0, 5, 0, 9, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 4, 1, 8,
            0, 0, 0, 0, 8, 1, 0, 0, 0,
            0, 0, 2, 0, 0, 0, 0, 5, 0,
            0, 4, 0, 0, 0, 0, 3, 0, 0};

    private static int[] hardestPuzzle = {8, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 3, 6, 0, 0, 0, 0, 0,
            0, 7, 0, 0, 9, 0, 2, 0, 0,
            0, 5, 0, 0, 0, 7, 0, 0, 0,
            0, 0, 0, 0, 4, 5, 7, 0, 0,
            0, 0, 0, 1, 0, 0, 0, 3, 0,
            0, 0, 1, 0, 0, 0, 0, 6, 8,
            0, 0, 8, 5, 0, 0, 0, 1, 0,
            0, 9, 0, 0, 0, 0, 4, 0, 0};

    private static int[] puzzle = {0, 6, 0, 3, 0, 0, 8, 0, 4,
            5, 3, 7, 0, 9, 0, 0, 0, 0,
            0, 4, 0, 0, 0, 6, 3, 0, 7,
            0, 9, 0, 0, 5, 1, 2, 3, 8,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            7, 1, 3, 6, 2, 0, 0, 4, 0,
            3, 0, 6, 4, 0, 0, 0, 1, 0,
            0, 0, 0, 0, 6, 0, 5, 2, 3,
            1, 0, 2, 0, 0, 9, 0, 8, 0};

    private static int[] hard = {8, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 3, 6, 0, 0, 0, 0, 0,
            0, 7, 0, 0, 9, 0, 2, 0, 0,
            0, 5, 0, 0, 0, 7, 0, 0, 0,
            0, 0, 0, 0, 4, 5, 7, 0, 0,
            0, 0, 0, 1, 0, 0, 0, 3, 0,
            0, 0, 1, 0, 0, 0, 0, 6, 8,
            0, 0, 8, 5, 0, 0, 0, 1, 0,
            0, 9, 0, 0, 0, 0, 4, 0, 0};

    public static void main(String[] args) {
//        initialise(puzzle);
        initialise(automorphic);
//        initialise(hardest);
//        initialise(hardestPuzzle);
//        initialise(hard);
//        initialise(empty);
        printGrid(grid);
        solve(grid);
        printGrid(grid);


    }

    public static void printGrid(int[][] grid) {
        System.out.println();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int pos = (row * 9 + col);
                if (pos % 3 == 0 && pos % 9 != 0) {
                    System.out.print(" ");
                }
                if (pos % 27 == 0 && pos != 0) {
                    System.out.println();
                }
                if(grid[row][col] == 0) {
                    System.out.print("+");
                }
                else {
                    System.out.print(grid[row][col]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void solve(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) {
                    for (int cand = 1; cand <= 9; cand++) {
                        if (!rowCheck(row, cand) && !colCheck(col, cand) && !boxCheck(row, col, cand)) {
                            grid[row][col] = cand;
                            solve(grid);
                            if (grid[lastEmpty / 9][lastEmpty % 9] != 0) {
                                return;
                            }
//                            if (isSolved(grid)) {
//                                return;
//                            }
                            // if we want to find multiple solutions just add code here to add the solution to an arraylist
                            // don't return. and it should go on to find all solutions.
                        }
                    }
                    grid[row][col] = 0;
                    return;
                }
            }
        }
    }

    private static boolean isSolved(int[][] grid) {
        for (int[] cells : grid) {
            for (int cell : cells) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean rowCheck(int row, int cand) {
        for (int cell : grid[row]) {
            if (cell == cand) {
                return true;
            }
        }
        return false;
    }

    private static boolean colCheck(int col, int cand) {
        for (int[] cells : grid) {
            if (cells[col] == cand) {
                return true;
            }
        }
        return false;
    }

    private static boolean boxCheck(int row, int col, int cand) {
        for (int rowC = 0; rowC < 9; rowC++) {
            for (int colC = 0; colC < 9; colC++) {
                if (rowC != row && colC != col && boxCalc(rowC, colC) == boxCalc(row, col) && grid[rowC][colC] == cand) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int boxCalc(int row, int col) {
        return ((row / 3) * 3 + (col / 3));
    }

    private static void initialise(int[] puzzle) {
        int pos = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (puzzle[pos] == 0) {
                    lastEmpty = pos;
                }
                grid[row][col] = puzzle[pos];
                pos++;
            }
        }
    }
}
