import java.util.ArrayList;
import java.util.Random;

//public class Generator extends Solver{
public class Generator {
    public FileManager fm;

    public Solver s;

    Generator(FileManager fileManager, Solver solver) {
        this.fm = fileManager;
        this.s = solver;
    }

    public boolean triedAll(boolean[] tried) {
        /*Checks whether theirs still numbers it hasn't tried to remove from yet.*/
        for (boolean value : tried) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    public Random random = new Random();

    public ArrayList<Cell[][]> solutions = new ArrayList<>();

    public int numberOfSolutions(Cell[][] grid) {
        /*Runs everySolutions and returns the number of solutions that it finds.*/
        everySolution(grid);
        int numOfSolutions = solutions.size();
        solutions.clear();
        return numOfSolutions;
    }

    public void removeValues(Cell[][] grid, int numToRemove) {
        /*If the number of solutions is 1 and the amount of positions with answer 0 is equal to the amount of values
        * it was told to remove, return.*/
        if (numberOfSolutions(grid) == 1 && s.amountRemoved(grid) == numToRemove) {
            return;
        }
        /*Tried keeps track of which positions we have tried removing the value from to find a unique puzzle at that point.*/
        boolean[] tried = new boolean[]{false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false};
        /*While it hasn't tried to remove from every position at this point.*/
        while (!triedAll(tried)) {
            /*Generate a new random number up to position 81.*/
            int rand = random.nextInt(81);
            /*If the answer at this random position isn't 0 and we haven't tried to remove from this position before.*/
            if (grid[rand / 9][rand % 9].ans != 0 && !tried[rand]) {
                /*Save the answer at the position and then set the answer to 0.*/
                int ans = grid[rand / 9][rand % 9].ans;
                grid[rand / 9][rand % 9].ans = 0;
                /*If the number of solutions with this position set to 0 is > 1. Set the answer back. Else recursively call
                * to remove another number from a different position.*/
                if (numberOfSolutions(grid) > 1) {
                    grid[rand / 9][rand % 9].ans = ans;
                } else {
                    removeValues(grid, numToRemove);
                }
                /*If the number of solutions is 1 and the amount of positions with answer 0 is equal to the amount of values
                 * it was told to remove, return.*/
                if (numberOfSolutions(grid) == 1 && s.amountRemoved(grid) == numToRemove) {
                    return;
                }
            }
            /*Set that we've tried to remove the number at that position.*/
            tried[rand] = true;
        }
    }

    public boolean isSolutionNew(Cell[][] grid) {
        /*For every solution in solutions. It checks whether every position in the grid has the same number as
        * every position for any of the solutions. If every position has the same number then the grid is not a
        * new solution.*/
        for (Cell[][] solution : solutions) {
            boolean foundDifference = false;
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (grid[row][col].ans != solution[row][col].ans) {
                        foundDifference = true;
                        break;
                    }
                }
            }
            if (!foundDifference) {
                return false;
            }
        }
        return true;
    }

    public Cell[][] createNew(Cell[][] grid) {
        /*Creates a copy of the array of cells to add uniquely to the arrayList of solutions.*/
        Cell[][] newGrid = new Cell[9][9];
        int pos = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                newGrid[row][col] = new Cell(pos, grid[row][col].ans, row, col);
                pos++;
            }
        }
        return newGrid;
    }

    public void everySolution(Cell[][] grid) {
        if (solutions.size() > 1) {
            return;
        }
        /*For every cell in the grid*/
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    /*try find a solution with each different candidate at that position.*/
                    for (int cand = 0; cand < 9; cand++) {
                        if (!s.checkHouses(grid, cell, cand)) {
                            cell.ans = cand + 1;
                            everySolution(grid);
                            /*If this candidate leads to a solution and the solution is new add it to the
                            * arrayList of solutions.*/
                            if (s.isSolved(grid) && isSolutionNew(grid)) {
                                solutions.add(createNew(grid));
                            }
                        }
                    }
                    cell.ans = 0;
                    return;
                }
            }
        }
    }

    public void randomlyFill(Cell[][] grid) {
        /*For every cell in the grid. Try place a random number at that position. If this random number leads to the
        * board getting filled keep the number else try a different random number.*/
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    boolean[] tried = new boolean[]{false, false, false, false, false, false, false, false, false};
                    while (!triedAll(tried)) {
                        int rand = random.nextInt(9);
                        if (!s.checkHouses(grid, cell, rand)) {
                            cell.ans = rand + 1;
                            randomlyFill(grid);
                            if (grid[8][8].ans != 0) {
                                return;
                            }
                        } else {
                            tried[rand] = true;
                        }
                    }
                    cell.ans = 0;
                    return;
                }
            }
        }
    }

    public void generateSet(int amount) {
        Cell[][] grid = new Cell[9][9];
        int puzzlesCreated = 0;
        while(puzzlesCreated < amount) {
            for(int emptySpaces = 30; emptySpaces <= 55; emptySpaces++) {
                s.initialiseEmptyGrid(grid);
                randomlyFill(grid);
                removeValues(grid, emptySpaces);
                Cell[][] emptyGrid = createNew(grid);
                s.solve(grid);
                String difficulty = s.getDifficulty();
                System.out.println("Difficulty " + difficulty);
                System.out.println("Puzzles created " + puzzlesCreated + " empty spaces " + emptySpaces);
                if(fm.lineCount(difficulty) < 100) {
                    puzzlesCreated++;
                    fm.write(difficulty, emptyGrid);
                }
            }
        }
    }
}