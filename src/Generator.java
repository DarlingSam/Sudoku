import java.util.ArrayList;
import java.util.Random;

public class Generator extends Solver {
    /*To remove numbers, use this algorithm:
Pick a random number you haven't tried removing before
Remove the number, run your solver with the added condition that it cannot use the removed number here
If the solver finds a solution, you can't remove the number
Repeat, until you have removed enough numbers (or you can't remove any more)*/

    /*Have a full randomized board. Remove a number and try solve it. See which method was used to place the number back in, to attempt to track difficulty.
     * Or see which methods were used as a result to place any number(s) back in.
     * Keeping track using backtracking that there is only a single solution to the puzzle.*/

//    public Cell[][] randBoard = new Cell[9][9];

    public boolean triedAll(boolean[] tried) {
        for (boolean value : tried) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

//    public boolean isSolved(Cell[][] grid) {
//        for (Cell[] cells : grid) {
//            for (Cell cell : cells) {
//                if (cell.ans == 0) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    public void initialiseEmptyGrid(Cell[][] grid) {
        int pos = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = new Cell(pos, 0, row, col);
                pos++;
            }
        }
    }

    public Random random = new Random();

    public ArrayList<Cell[][]> solutions = new ArrayList<>();

    public int numberOfSolutions(Cell[][] grid) {
        everySolution(grid);
        int numOfSolutions = solutions.size();
        solutions.clear();
        return numOfSolutions;
    }

    public void removeValues(Cell[][] grid, int numToRemove) {
        Random random = new Random();
        Cell[][] backUp = createNew(grid);
        while (numToRemove > 0) {
//            boolean[] tried = new boolean[]{false, false, false, false, false, false, false, false, false,
//                    false, false, false, false, false, false, false, false, false,
//                    false, false, false, false, false, false, false, false, false,
//                    false, false, false, false, false, false, false, false, false,
//                    false, false, false, false, false, false, false, false, false,
//                    false, false, false, false, false, false, false, false, false,
//                    false, false, false, false, false, false, false, false, false,
//                    false, false, false, false, false, false, false, false, false,
//                    false, false, false, false, false, false, false, false, false};
//
//            while (!triedAll(tried)) {
            int rand = random.nextInt(81);
            int row = rand / 9;
            int col = rand % 9;
            if (grid[row][col].ans != 0) {
                int ans = grid[row][col].ans;
//                System.out.println(rand);
                grid[row][col].ans = 0;

                if (numberOfSolutions(grid) == 1) {
                    grid[row][col].ans = 0;
                    numToRemove--;
                } else {
//                        tried[rand] = true;
                    grid[row][col].ans = ans;
                }
            }
//            }
        }
    }

//    public void removeValuesPrime(Cell[][] grid, int numToRemove) {
//        if (numberOfSolutions(grid) == 1 && amountRemoved(grid) == numToRemove) {
//            System.out.println("                                        Here5");
//            return;
//        }
//
//        boolean[] tried = new boolean[]{false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false};
//        while (!triedAll(tried)) {
//            System.out.println("      Here1");
//            int rand = random.nextInt(81);
//            int row = rand / 9;
//            int col = rand % 9;
//            tried[rand] = true;
//            int ans = grid[row][col].ans;
//            if (grid[row][col].ans != 0) {
//                System.out.println("      RANDOM " + rand);
//                System.out.println("              Here2");
////                int ans = grid[row][col].ans;
//                grid[row][col].ans = 0;
////                numToRemove--;
//                System.out.println("                                                    NUMBER OF SOL AT HERE2 " + numberOfSolutions(grid));
//                System.out.println("                                                    AMOUNT REMOVED HERE2 " + amountRemoved(grid));
//                System.out.println("                                                    NUMTOREMOVE HERE2 " + numToRemove);
//                if(numberOfSolutions(grid) > 1){
//                    System.out.println("                     Here3");
//                    grid[row][col].ans = ans;
////                    numToRemove++;
//                }
//                else {
//                    removeValuesPrime(grid, numToRemove);
//                }
//                if (numberOfSolutions(grid) == 1 && amountRemoved(grid) == numToRemove) {
//                    System.out.println("                           Here4");
//                    return;
//                }
//            }
//            int count = 0;
//            for(boolean thing : tried) {
//                if(thing) {
//                    count++;
//                }
//            }
//            grid[row][col].ans = ans;
//            System.out.println("COUNT " + count);
//            System.out.println("AMOUNT REMOVED " + amountRemoved(grid));
//            System.out.println("NUMBER OF SOLUTIONS " + numberOfSolutions(grid));
//        }
//    }

    public void removeValuesPrime(Cell[][] grid, int numToRemove) {
        if (numberOfSolutions(grid) == 1 && amountRemoved(grid) == numToRemove) {
            return;
        }
        boolean[] tried = new boolean[]{false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false};
        while (!triedAll(tried)) {
            int rand = random.nextInt(81);
            tried[rand] = true;
            int ans = grid[rand / 9][rand % 9].ans;
            if (grid[rand / 9][rand % 9].ans != 0) {
                grid[rand / 9][rand % 9].ans = 0;
                if (numberOfSolutions(grid) > 1) {
                    grid[rand / 9][rand % 9].ans = ans;
                } else {
                    removeValuesPrime(grid, numToRemove);
                }
                if (numberOfSolutions(grid) == 1 && amountRemoved(grid) == numToRemove) {
                    return;
                }
            }
            grid[rand / 9][rand % 9].ans = ans;
        }
    }

    public int amountRemoved(Cell[][] grid) {
        int count = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col].ans == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isSolutionNew(Cell[][] grid) {
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
        if (solutions.size() == 10) {
            return;
        }
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    for (int cand = 0; cand < 9; cand++) {
                        if (!checkHouses(grid, cell, cand)) {
                            cell.ans = cand + 1;
                            everySolution(grid);
//                            if (grid[lastEmpty / 9][lastEmpty % 9].ans != 0) {
                            if (isSolved(grid) && isSolutionNew(grid)) {
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
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    boolean[] tried = new boolean[]{false, false, false, false, false, false, false, false, false};
                    while (!triedAll(tried)) {
                        int rand = random.nextInt(9);
                        if (!checkHouses(grid, cell, rand)) {
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

    public void removeNumbers(Cell[][] grid, int amount) {
        int removed = 0;
        Random random = new Random();
        while (removed < amount) {
            int rand = random.nextInt(81);
            int row = rand / 9;
            int col = rand % 9;
            int ans = grid[row][col].ans;
            if (grid[row][col].ans != 0) {
                grid[row][col].ans = 0;
                if (numberOfSolutions(grid) == 1) {
                    removed++;
                } else {
                    grid[row][col].ans = ans;
                }
            }
        }
    }

//    public static void main(String[] args) {
//        initialiseEmptyGrid();
//        printGrid(randBoard);
//        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
//        randomlyFill(randBoard);
////        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
//        printGrid(randBoard);
//        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
////        removeValues(randBoard, 56);
////        removeValuesPrime(randBoard, 30);
//        removeNumbers(randBoard, 50);
//        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
//        printGrid(randBoard);
//        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
//        backTrack(createNew(randBoard));
////        solve(randBoard);
//        printGrid(randBoard);
//    }
}
