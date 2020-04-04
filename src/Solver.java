import java.util.ArrayList;

public class Solver {

    /*Cell[row][col]*/
    public Cell[][] grid = new Cell[9][9];

    private boolean change;

    private int passes = 0;

    public void xWingCol(Cell[][] grid) {
        /*For every candidate look at every pair of columns where in each column they appear in every cell. */
        for (int cand = 0; cand < 9; cand++) {
            for (int col1 = 0; col1 < 9; col1++) {
                for (int col2 = col1 + 1; col2 < 9; col2++) {
                    boolean inEveryCellCol1 = true;
                    boolean inEveryCellCol2 = true;
                    for (Cell[] cell : grid) {
                        if (!cell[col1].candidates[cand] && cell[col1].ans == 0) {
                            inEveryCellCol1 = false;
                            break;
                        }
                    }
                    for (Cell[] cell : grid) {
                        if (!cell[col2].candidates[cand] && cell[col2].ans == 0) {
                            inEveryCellCol2 = false;
                            break;
                        }
                    }
                    /*If the candidate appears in every cell of both columns look at every pair of rows and determine whether
                     * the candidate only appears in one cell of each these rows for each column. */
                    if (inEveryCellCol1 && inEveryCellCol2) {
                        for (int row1 = 0; row1 < 9; row1++) {
                            for (int row2 = row1 + 1; row2 < 9; row2++) {
                                boolean inOneCellRow1 = true;
                                boolean inOneCellRow2 = true;
                                ArrayList<Cell> maybeExcept = new ArrayList<>();
                                for (Cell cell : grid[row1]) {
                                    if (cell.candidates[cand] && cell.col != col1 && cell.col != col2) {
                                        inOneCellRow1 = false;
                                    } else if (cell.candidates[cand]) {
                                        maybeExcept.add(cell);
                                    }
                                }
                                for (Cell cell : grid[row2]) {
                                    if (cell.candidates[cand] && cell.col != col1 && cell.col != col2) {
                                        inOneCellRow2 = false;
                                    } else if (cell.candidates[cand]) {
                                        maybeExcept.add(cell);
                                    }
                                }
                                /*If the candidate only appears in the 4 cells where the pair of columns and pair of rows intersect.
                                 * Then remove the candidate from every cell in each column aside from the 4 intersections.*/
                                if (inOneCellRow1 && inOneCellRow2 && maybeExcept.size() == 4) {
                                    Cell[] exceptions = maybeExcept.toArray(new Cell[0]);
                                    System.out.println("xWingCol removing");
                                    removeCandInCol(grid, col1, new int[]{cand}, exceptions);
                                    removeCandInCol(grid, col2, new int[]{cand}, exceptions);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void xWingRow(Cell[][] grid) {
        /*For every candidate look at every pair of rows where in each row they appear in every cell. */
        for (int cand = 0; cand < 9; cand++) {
            for (int row1 = 0; row1 < 9; row1++) {
                for (int row2 = row1 + 1; row2 < 9; row2++) {
                    boolean inEveryCellRow1 = true;
                    boolean inEveryCellRow2 = true;
                    for (Cell cell : grid[row1]) {
                        if (!cell.candidates[cand] && cell.ans == 0) {
                            inEveryCellRow1 = false;
                            break;
                        }
                    }
                    for (Cell cell : grid[row2]) {
                        if (!cell.candidates[cand] && cell.ans == 0) {
                            inEveryCellRow2 = false;
                            break;
                        }
                    }
                    /*If the candidate appears in every cell of both rows look at every pair of columns and determine whether
                     * the candidate only appears in one cell of each these columns for each row.*/
                    if (inEveryCellRow1 && inEveryCellRow2) {
                        for (int col1 = 0; col1 < 9; col1++) {
                            for (int col2 = col1 + 1; col2 < 9; col2++) {
                                boolean inOneCellCol1 = true;
                                boolean inOneCellCol2 = true;
                                ArrayList<Cell> maybeExcept = new ArrayList<>();
                                for (Cell[] cell : grid) {
                                    if (cell[col1].candidates[cand] && cell[col1].row != row1 && cell[col1].row != row2) {
                                        inOneCellCol1 = false;
                                    } else if (cell[col1].candidates[cand]) {
                                        maybeExcept.add(cell[col1]);
                                    }
                                }
                                for (Cell[] cell : grid) {
                                    if (cell[col2].candidates[cand] && cell[col2].row != row1 && cell[col2].row != row2) {
                                        inOneCellCol2 = false;
                                    } else if (cell[col2].candidates[cand]) {
                                        maybeExcept.add(cell[col2]);
                                    }
                                }
                                /*If the candidate only appears in the 4 cells where the pair of rows and pair of columns intersect.
                                 * Then remove the candidate from every cell in each row aside from the 4 intersections.*/
                                if (inOneCellCol1 && inOneCellCol2 && maybeExcept.size() == 4) {
                                    Cell[] exceptions = maybeExcept.toArray(new Cell[0]);
                                    System.out.println("xWingRow removing");
                                    removeCandInRow(grid, row1, new int[]{cand}, exceptions);
                                    removeCandInRow(grid, row2, new int[]{cand}, exceptions);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean unionOfCandidates(boolean[][] numInCells) {
        /*Takes the boolean[][] where each array represents a different candidate, and their array represents the cells they belong to.
         * It goes through each of these arrays and sets the corresponding position of the cells to true in appearances.
         * If appearances has as many true values as there are number of candidates (between each candidate they appear in as many cells
         * as amount of candidates) then return true.*/
        boolean[] appearances = new boolean[]{false, false, false, false, false, false, false, false, false};
        for (boolean[] numInCell : numInCells) {
            for (int cell = 0; cell < numInCell.length; cell++) {
                if (numInCell[cell]) {
                    appearances[cell] = true;
                }
            }
        }
        int count = 0;
        for (boolean appearance : appearances) {
            if (appearance) {
                count++;
            }
        }
        return count == numInCells.length;
    }

    public boolean allMadeContribution;

    public void removeForHiddenSubsetBox(Cell[][] grid, int box, int... nums) {
        /*Finds every cell in the box which which contains any one of the candidates then removes every other candidate of that cell.*/
        Cell[] toRemoveFrom = new Cell[nums.length];
        int exPos = 0;
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.box == box) {
                    boolean appeared = false;
                    for (int num : nums) {
                        if (cell.candidates[num]) {
                            if (!appeared) {
                                exPos++;
                                appeared = true;
                            }
                        }
                    }
                    if (appeared) {
                        toRemoveFrom[exPos - 1] = cell;
                    }
                }
            }
        }
        for (Cell cellR : toRemoveFrom) {
            for (int cand = 0; cand < 9; cand++) {
                boolean found = false;
                for (int num : nums) {
                    if (cand == num && cellR.candidates[cand]) {
                        found = true;
                        break;
                    }
                }
                if (!found && cellR.candidates[cand]) {
//                    System.out.println("HSB removing " + (cand + 1) + " from " + cellR.pos);
                    cellR.candidates[cand] = false;
                    change = true;
                }
            }
        }
    }

    public boolean[][] numInCellsBox(Cell[][] grid, int box, int... nums) {
        /*Creates a boolean[][] where each array represents the cells in the box which the number of the array appeared in.
         * Also checks that each number appears in at-least one of these cells.*/
        boolean[][] numInCells = new boolean[nums.length][9];
        boolean[] contributions = new boolean[nums.length];
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.box == box) {
                    int numPos = 0;
                    for (int num : nums) {
                        int innerRow = cell.row % 3;
                        int innerCol = cell.col % 3;
                        int innerIndex = innerRow * 3 + innerCol;
                        if (cell.candidates[num]) {
                            numInCells[numPos][innerIndex] = true;
                            contributions[numPos] = true;
                        } else {
                            numInCells[numPos][innerIndex] = false;
                        }
                        numPos++;
                    }
                }
            }
        }
        for (boolean contribution : contributions) {
            if (!contribution) {
                allMadeContribution = false;
                break;
            }
        }
        return numInCells;
    }

    public void hiddenSubsetBox(Cell[][] grid, int box) {
        /*For every amount of candidates from 4 to 2, check for every combination of numbers (where no digit is repeated in the combination
         * and every combination is unique ie 1234 == 1243) whether between the combination they only appear in an amount of cells in the box
         * equal in size to the combination. If this is the case and each number made a contribution of a new cell to which they appeared then
         * remove every other candidate from the group of cells.*/
        for (int amount = 4; amount >= 2; amount--) {
            for (int num1 = 0; num1 < 9; num1++) {
                for (int num2 = num1 + 1; num2 < 9; num2++) {
                    if (amount == 2) {
                        allMadeContribution = true;
                        if (unionOfCandidates(numInCellsBox(grid, box, num1, num2)) && allMadeContribution) {
//                            System.out.println("HSB hidden pair " + (num1 + 1) + "," + (num2 + 1) + " in box " + box);
                            removeForHiddenSubsetBox(grid, box, num1, num2);
                        }
                    } else {
                        for (int num3 = num2 + 1; num3 < 9; num3++) {
                            if (amount == 3) {
                                allMadeContribution = true;
                                if (unionOfCandidates(numInCellsBox(grid, box, num1, num2, num3)) && allMadeContribution) {
//                                    System.out.println("HSB hidden triple " + (num1 + 1) + "," + (num2 + 1) + "," + (num3 + 1) + " in box " + box);
                                    removeForHiddenSubsetBox(grid, box, num1, num2, num3);
                                }
                            } else {
                                for (int num4 = num3 + 1; num4 < 9; num4++) {
                                    allMadeContribution = true;
                                    if (unionOfCandidates(numInCellsBox(grid, box, num1, num2, num3, num4)) && allMadeContribution) {
//                                        System.out.println("HSB hidden quad " + (num1 + 1) + "," + (num2 + 1) + "," + (num3 + 1) + "," + (num4 + 1) + " in box " + box);
                                        removeForHiddenSubsetBox(grid, box, num1, num2, num3, num4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void removeForHiddenSubsetCol(Cell[][] grid, int col, int... nums) {
        /*Finds every cell in the col which which contains any one of the candidates then removes every other candidate of that cell.*/
        Cell[] toRemoveFrom = new Cell[nums.length];
        int exPos = 0;
        for (Cell[] cell : grid) {
            boolean appeared = false;
            for (int num : nums) {
                if (cell[col].candidates[num]) {
                    if (!appeared) {
                        exPos++;
                        appeared = true;
                    }
                }
            }
            if (appeared) {
                toRemoveFrom[exPos - 1] = cell[col];
            }
        }
        for (Cell cell : toRemoveFrom) {
            for (int cand = 0; cand < 9; cand++) {
                boolean found = false;
                for (int num : nums) {
                    if (cand == num && cell.candidates[cand]) {
                        found = true;
                        break;
                    }
                }
                if (!found && cell.candidates[cand]) {
//                    System.out.println("HSC removing " + (cand + 1) + " from " + cell.pos);
                    cell.candidates[cand] = false;
                    change = true;
                }
            }
        }
    }

    public boolean[][] numInCellsCol(Cell[][] grid, int col, int... nums) {
        /*Creates a boolean[][] where each array represents the cells in the col which the number of the array appeared in.
         * Also checks that each number appears in at-least one of these cells.*/
        boolean[][] numInCells = new boolean[nums.length][9];
        boolean[] contributions = new boolean[nums.length];
        for (Cell[] cell : grid) {
            int numPos = 0;
            for (int num : nums) {
                if (cell[col].candidates[num]) {
                    numInCells[numPos][cell[col].pos / 9] = true;
                    contributions[numPos] = true;
                } else {
                    numInCells[numPos][cell[col].pos / 9] = false;
                }
                numPos++;
            }
        }
        for (boolean contribution : contributions) {
            if (!contribution) {
                allMadeContribution = false;
                break;
            }
        }
        return numInCells;
    }

    public void hiddenSubsetCol(Cell[][] grid, int col) {
        /*For every amount of candidates from 4 to 2, check for every combination of numbers (where no digit is repeated in the combination
         * and every combination is unique ie 1234 == 1243) whether between the combination they only appear in an amount of cells in the column
         * equal in size to the combination. If this is the case and each number made a contribution of a new cell to which they appeared then
         * remove every other candidate from the group of cells.*/
        for (int amount = 4; amount >= 2; amount--) {
            for (int num1 = 0; num1 < 9; num1++) {
                for (int num2 = num1 + 1; num2 < 9; num2++) {
                    if (amount == 2) {
                        allMadeContribution = true;
                        if (unionOfCandidates(numInCellsCol(grid, col, num1, num2)) && allMadeContribution) {
//                            System.out.println("HSC hidden pair " + (num1 + 1) + "," + (num2 + 1) + " in col " + col);
                            removeForHiddenSubsetCol(grid, col, num1, num2);
                        }
                    } else {
                        for (int num3 = num2 + 1; num3 < 9; num3++) {
                            if (amount == 3) {
                                allMadeContribution = true;
                                if (unionOfCandidates(numInCellsCol(grid, col, num1, num2, num3)) && allMadeContribution) {
//                                    System.out.println("HSC hidden triple " + (num1 + 1) + "," + (num2 + 1) + "," + (num3 + 1) + " in col " + col);
                                    removeForHiddenSubsetCol(grid, col, num1, num2, num3);
                                }
                            } else {
                                for (int num4 = num3 + 1; num4 < 9; num4++) {
                                    allMadeContribution = true;
                                    if (unionOfCandidates(numInCellsCol(grid, col, num1, num2, num3, num4)) && allMadeContribution) {
//                                        System.out.println("HSC hidden quad " + (num1 + 1) + "," + (num2 + 1) + "," + (num3 + 1) + "," + (num4 + 1) + " in col " + col);
                                        removeForHiddenSubsetCol(grid, col, num1, num2, num3, num4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void removeForHiddenSubsetRow(Cell[][] grid, int row, int... nums) {
        /*Finds every cell in the row which which contains any one of the candidates then removes every other candidate of that cell.*/
        Cell[] toRemoveFrom = new Cell[nums.length];
        int exPos = 0;
        for (Cell cell : grid[row]) {
            boolean appeared = false;
            for (int num : nums) {
                if (cell.candidates[num]) {
                    if (!appeared) {
                        exPos++;
                        appeared = true;
                    }
                }
            }
            if (appeared) {
                toRemoveFrom[exPos - 1] = cell;
            }
        }
        for (Cell cell : toRemoveFrom) {
            for (int cand = 0; cand < 9; cand++) {
                boolean found = false;
                for (int num : nums) {
                    if (cand == num && cell.candidates[cand]) {
                        found = true;
                        break;
                    }
                }
                if (!found && cell.candidates[cand]) {
//                    System.out.println("HSR removing " + (cand + 1) + " from " + cell.pos);
                    cell.candidates[cand] = false;
                    change = true;
                }
            }
        }
    }

    public boolean[][] numInCellsRow(Cell[][] grid, int row, int... nums) {
        /*Creates a boolean[][] where each array represents the cells in the row which the number of the array appeared in.
         * Also checks that each number appears in at-least one of these cells.*/
        boolean[][] numInCells = new boolean[nums.length][9];
        boolean[] contributions = new boolean[nums.length];
        for (Cell cell : grid[row]) {
            int numPos = 0;
            for (int num : nums) {
                if (cell.candidates[num]) {
                    numInCells[numPos][cell.pos % 9] = true;
                    contributions[numPos] = true;
                } else {
                    numInCells[numPos][cell.pos % 9] = false;
                }

                numPos++;
            }
        }
        for (boolean contribution : contributions) {
            if (!contribution) {
                allMadeContribution = false;
                break;
            }
        }
        return numInCells;
    }

    public void hiddenSubsetRow(Cell[][] grid, int row) {
        /*For every amount of candidates from 4 to 2, check for every combination of numbers (where no digit is repeated in the combination
         * and every combination is unique ie 1234 == 1243) whether between the combination they only appear in an amount of cells in the row
         * equal in size to the combination. If this is the case and each number made a contribution of a new cell to which they appeared then
         * remove every other candidate from the group of cells.*/
        for (int amount = 4; amount >= 2; amount--) {
            for (int num1 = 0; num1 < 9; num1++) {
                for (int num2 = num1 + 1; num2 < 9; num2++) {
                    if (amount == 2) {
                        allMadeContribution = true;
                        if (unionOfCandidates(numInCellsRow(grid, row, num1, num2)) && allMadeContribution) {
//                            System.out.println("HSR hidden pair " + (num1 + 1) + "," + (num2 + 1) + " in row " + row);
                            removeForHiddenSubsetRow(grid, row, num1, num2);
                        }
                    } else {
                        for (int num3 = num2 + 1; num3 < 9; num3++) {
                            if (amount == 3) {
                                allMadeContribution = true;
                                if (unionOfCandidates(numInCellsRow(grid, row, num1, num2, num3)) && allMadeContribution) {
//                                    System.out.println("HSR hidden triple " + (num1 + 1) + "," + (num2 + 1) + "," + (num3 + 1) + " in row " + row);
                                    removeForHiddenSubsetRow(grid, row, num1, num2, num3);
                                }
                            } else {
                                for (int num4 = num3 + 1; num4 < 9; num4++) {
                                    allMadeContribution = true;
                                    if (unionOfCandidates(numInCellsRow(grid, row, num1, num2, num3, num4)) && allMadeContribution) {
//                                        System.out.println("HSR hidden quad " + (num1 + 1) + "," + (num2 + 1) + "," + (num3 + 1) + "," + (num4 + 1) + " in row " + row);
                                        removeForHiddenSubsetRow(grid, row, num1, num2, num3, num4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void removeCandInBox(Cell[][] grid, int box, int[] cands, Cell[] exceptions) {
        /*For every cell in the box, if it is not an exception. Remove all cands from the cand array from that cell.*/
        boolean madeChange = false;
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.box == box) {
                    boolean isExcept = false;
                    for (Cell ex : exceptions) {
                        if (cell.pos == ex.pos) {
                            isExcept = true;
                            break;
                        }
                    }
                    if (!isExcept) {
                        for (int cand : cands) {
                            if (cell.ans == 0 && cell.candidates[cand]) {
                                System.out.println("NSB removing " + (cand + 1) + " from " + cell.pos);
                                cell.candidates[cand] = false;
                                change = true;
                                madeChange = true;
                            }
                        }
                    }
                }
            }
        }
        if (madeChange) {
            System.out.println("NSB size " + cands.length + " box " + box + " cands");
            for (int cand : cands) {
                System.out.println(cand + 1);
            }
            System.out.println("exceptions");
            for (Cell exception : exceptions) {
                System.out.println(exception.pos);
            }
        }
    }

    public void nakedSubsetBox(Cell[][] grid, int box) {
        /*For size 4 to 2 go along the column and create a list of each cell with number of candidates equal to that size.*/
        int maxSize = 4;
        for (int size = maxSize; size > 1; size--) {
            ArrayList<Cell> initialCells = new ArrayList<>();
            for (Cell[] cells : grid) {
                for (Cell cell : cells) {
                    if (cell.box == box) {
                        int candCount = 0;
                        for (int cand = 0; cand < 9; cand++) {
                            if (cell.candidates[cand]) {
                                candCount++;
                            }
                        }
                        if (candCount == size) {
                            initialCells.add(cell);
                        }
                    }
                }
            }
            /*For every cell in the initial cells, fill the array with which candidates it has*/
            int[] cands = new int[size];
            for (Cell cell : initialCells) {
                int candIndex = 0;
                for (int i = 0; i < 9; i++) {
                    if (cell.candidates[i]) {
                        cands[candIndex] = i;
                        candIndex++;
                    }
                }
                ArrayList<Cell> subset = new ArrayList<>();
                subset.add(cell);
                /*For every other cell in the same column, if the cell has no candidates belonging to the initial cell
                 * add that cell to the subset of cells list.*/
                for (Cell[] cells : grid) {
                    for (Cell cellC : cells) {
                        if (cellC.box == box) {
                            if (cellC.ans == 0 && cellC.pos != cell.pos) {
                                boolean isSubset = true;
                                for (int cand = 0; cand < 9; cand++) {
                                    if (cellC.candidates[cand] && !cell.candidates[cand]) {
                                        isSubset = false;
                                        break;
                                    }
                                }
                                if (isSubset) {
                                    subset.add(cellC);
                                }
                            }
                        }
                    }
                }
                /*If the subset contains as many cells as candidates we are looking for. Remove each candidate from every other
                 * cell in the column.*/
                if (subset.size() == size) {
                    Cell[] exceptions = subset.toArray(new Cell[0]);
                    removeCandInBox(grid, box, cands, exceptions);
                }
            }
        }
    }

    public void removeCandInCol(Cell[][] grid, int col, int[] cands, Cell[] exceptions) {
        /*For every cell in the column, if it is not an exception. Remove all cands from the cand array from that cell.*/
        boolean madeChange = false;
        for (Cell[] cell : grid) {
            boolean isExcept = false;
            for (Cell ex : exceptions) {
                if (cell[col].pos == ex.pos) {
                    isExcept = true;
                    break;
                }
            }
            if (!isExcept) {
                for (int cand : cands) {
                    if (cell[col].ans == 0 && cell[col].candidates[cand]) {
                        System.out.println("NSC removing " + (cand + 1) + " from " + cell[col].pos);
                        cell[col].candidates[cand] = false;
                        change = true;
                        madeChange = true;
                    }
                }
            }
        }
        if (madeChange) {
            System.out.println("NSC size " + cands.length + " col " + col + " cands");
            for (int cand : cands) {
                System.out.println(cand + 1);
            }
            System.out.println("exceptions");
            for (Cell exception : exceptions) {
                System.out.println(exception.pos);
            }
        }
    }

    public void nakedSubsetCol(Cell[][] grid, int col) {
        /*For size 4 to 2 go along the column and create a list of each cell with number of candidates equal to that size.*/
        int maxSize = 4;
        for (int size = maxSize; size > 1; size--) {
            ArrayList<Cell> initialCells = new ArrayList<>();
            for (Cell[] cell : grid) {
                int candCount = 0;
                for (int cand = 0; cand < 9; cand++) {
                    if (cell[col].candidates[cand]) {
                        candCount++;
                    }
                }
                if (candCount == size) {
                    initialCells.add(cell[col]);
                }
            }
            /*For every cell in the initial cells, fill the array with which candidates it has*/
            int[] cands = new int[size];
            for (Cell cell : initialCells) {
                int candIndex = 0;
                for (int i = 0; i < 9; i++) {
                    if (cell.candidates[i]) {
                        cands[candIndex] = i;
                        candIndex++;
                    }
                }
                ArrayList<Cell> subset = new ArrayList<>();
                subset.add(cell);
                /*For every other cell in the same column, if the cell has no candidates belonging to the initial cell
                 * add that cell to the subset of cells list.*/
                for (Cell[] cellC : grid) {
                    if (cellC[col].ans == 0 && cellC[col].pos != cell.pos) {
                        boolean isSubset = true;
                        for (int cand = 0; cand < 9; cand++) {
                            if (cellC[col].candidates[cand] && !cell.candidates[cand]) {
                                isSubset = false;
                                break;
                            }
                        }
                        if (isSubset) {
                            subset.add(cellC[col]);
                        }
                    }
                }
                /*If the subset contains as many cells as candidates we are looking for. Remove each candidate from every other
                 * cell in the column.*/
                if (subset.size() == size) {
                    Cell[] exceptions = subset.toArray(new Cell[0]);
                    removeCandInCol(grid, col, cands, exceptions);
                }
            }
        }

    }

    public void removeCandInRow(Cell[][] grid, int row, int[] cands, Cell[] exceptions) {
        /*For every cell in the row, if it is not an exception. Remove all cands from the cand array from that cell.*/
        boolean madeChange = false;
        for (Cell cell : grid[row]) {
            boolean isExcept = false;
            for (Cell ex : exceptions) {
                if (cell.pos == ex.pos) {
                    isExcept = true;
                    break;
                }
            }
            if (!isExcept) {
                for (int cand : cands) {
                    if (cell.ans == 0 && cell.candidates[cand]) {
                        System.out.println("NSR removing " + (cand + 1) + " from " + cell.pos);
                        cell.candidates[cand] = false;
                        change = true;
                        madeChange = true;
                    }
                }
            }
        }
        if (madeChange) {
            System.out.println("NSR size " + cands.length + " row " + row + " cands");
            for (int cand : cands) {
                System.out.println(cand + 1);
            }
            System.out.println("exceptions");
            for (Cell exception : exceptions) {
                System.out.println(exception.pos);
            }
        }
    }

    public void nakedSubsetRow(Cell[][] grid, int row) {
        /*For size 4 to 2 go along the row and create a list of each cell with number of candidates equal to that size.*/
        int maxSize = 4;
        for (int size = maxSize; size > 1; size--) {
            ArrayList<Cell> initialCells = new ArrayList<>();
            for (Cell cell : grid[row]) {
                int candCount = 0;
                for (int cand = 0; cand < 9; cand++) {
                    if (cell.candidates[cand]) {
                        candCount++;
                    }
                }
                if (candCount == size) {
                    initialCells.add(cell);
                }
            }
            /*For every cell in the initial cells, fill the array with which candidates it has*/
            int[] cands = new int[size];
            for (Cell cell : initialCells) {
                int candIndex = 0;
                for (int i = 0; i < 9; i++) {
                    if (cell.candidates[i]) {
                        cands[candIndex] = i;
                        candIndex++;
                    }
                }
                ArrayList<Cell> subset = new ArrayList<>();
                subset.add(cell);
                /*For every other cell in the same row, if the cell has no candidates belonging to the initial cell
                 * add that cell to the subset of cells list.*/
                for (Cell cellC : grid[row]) {
                    if (cellC.ans == 0 && cellC.pos != cell.pos) {
                        boolean isSubset = true;
                        for (int cand = 0; cand < 9; cand++) {
                            if (cellC.candidates[cand] && !cell.candidates[cand]) {
                                isSubset = false;
                                break;
                            }
                        }
                        if (isSubset) {
                            subset.add(cellC);
                        }
                    }
                }
                /*If the subset contains as many cells as candidates we are looking for. Remove each candidate from every other
                 * cell in the row.*/
                if (subset.size() == size) {
                    Cell[] exceptions = subset.toArray(new Cell[0]);
                    removeCandInRow(grid, row, cands, exceptions);
                }
            }
        }
    }

    public void blockBlockColInteraction(Cell[][] grid, Cell cell) {
        /*For each candidate, if the candidate appears in two columns only of one box, and the same two columns only in another box
         * remove the candidates from those same two columns in the third box.*/
        for (int cand = 0; cand < 9; cand++) {
            /*colWithinBox stores which of the 3 columns within each of the 3 boxes in the column of boxes the candidate appears in
             * index 0-2 being the 3 columns of the first box, 3-5 being the 3 columns of the second box and 6-8 being the 3 columns
             * of the third and final box.*/
            boolean[] colWithinBox = new boolean[]{false, false, false,
                    false, false, false,
                    false, false, false};
            boolean ansFoundInBox = false;
            for (Cell[] cells : grid) {
                for (Cell cellC : cells) {
                    /*If a cell (cellC) has the answer equal to the candidate we are searching for, break the loop and set a boolean
                     * to know no to try and remove candidates at the end.*/
                    if ((cellC.box % 3) == (cell.box % 3) && cellC.ans == (cand + 1)) {
                        ansFoundInBox = true;
                        break;
                    }
                    if (cellC.ans == 0 && cellC.candidates[cand] && (cellC.box % 3) == (cell.box % 3)) {
                        /*cellC.box / 3 tells you which box in the column of boxes the cell belongs to. cellC.col % 3 tells
                         * you which column within that box the cell belongs to. Stores this in colWithinBox to later look at
                         * which combination of columns we can use to remove from which box.*/
                        if (cellC.box / 3 == 0) {
                            if (cellC.col % 3 == 0) {
                                colWithinBox[0] = true;
                            } else if (cellC.col % 3 == 1) {
                                colWithinBox[1] = true;
                            } else if (cellC.col % 3 == 2) {
                                colWithinBox[2] = true;
                            }
                        } else if (cellC.box / 3 == 1) {
                            if (cellC.col % 3 == 0) {
                                colWithinBox[3] = true;
                            } else if (cellC.col % 3 == 1) {
                                colWithinBox[4] = true;
                            } else if (cellC.col % 3 == 2) {
                                colWithinBox[5] = true;
                            }
                        } else if (cellC.box / 3 == 2) {
                            if (cellC.col % 3 == 0) {
                                colWithinBox[6] = true;
                            } else if (cellC.col % 3 == 1) {
                                colWithinBox[7] = true;
                            } else if (cellC.col % 3 == 2) {
                                colWithinBox[8] = true;
                            }
                        }
                    }
                }
            }
            if (!ansFoundInBox) {
                /*countColOccur keeps track of how many times the candidate occurred in each of the 3 columns within the box.*/
                int[] countColOccur = new int[]{0, 0, 0};
                /*boxCol stores the first and second column the candidate occurs in within the box. The index is as follows:
                 * {(box1,col1),(box1,col2),(box2,col1),(box2,col2),(box3,col1),(box3,col2)}. If it occurs within a third column
                 * the value for one is overwritten.*/
                int[] boxCol = new int[]{0, 0, 0, 0, 0, 0};
                /*This loop iterates through each value of colWithinBox and counts the number of times the candidate appeared
                 * in a different column per box. countColOccur[0] is the count for the first box and so on.*/
                for (int i = 0; i < 9; i++) {
                    if (i < 3 && colWithinBox[i]) {
                        if (countColOccur[0] == 0) {
                            boxCol[0] = i % 3;
                        } else {
                            boxCol[1] = i % 3;
                        }
                        countColOccur[0]++;
                    } else if (i >= 3 && i < 6 && colWithinBox[i]) {
                        if (countColOccur[1] == 0) {
                            boxCol[2] = i % 3;
                        } else {
                            boxCol[3] = i % 3;
                        }
                        countColOccur[1]++;
                    } else if (i >= 6 && colWithinBox[i]) {
                        if (countColOccur[2] == 0) {
                            boxCol[4] = i % 3;
                        } else {
                            boxCol[5] = i % 3;
                        }
                        countColOccur[2]++;
                    }
                }
                /*If there a two boxes where the candidate only appeared in one or two columns*/
                if (countColOccur[0] < 3 && countColOccur[0] > 0 && countColOccur[1] < 3 && countColOccur[1] > 0) {
                    /*If the columns they appeared in are the same then remove them from the same columns in the third box.*/
                    if (boxCol[0] == boxCol[2] && boxCol[1] == boxCol[3]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 2 are the same so remove from box 3");
                        removeForBlockBlockColInteraction(grid, 2, cell.box % 3, boxCol[0], boxCol[1], cand);
                    }
                } else if (countColOccur[0] < 3 && countColOccur[0] > 0 && countColOccur[2] < 3 && countColOccur[2] > 0) {
                    if (boxCol[0] == boxCol[4] && boxCol[1] == boxCol[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 3 are the same so remove from box 2");
                        removeForBlockBlockColInteraction(grid, 1, cell.box % 3, boxCol[0], boxCol[1], cand);
                    }
                } else if (countColOccur[1] < 3 && countColOccur[1] > 0 && countColOccur[2] < 3 && countColOccur[2] > 0) {
                    if (boxCol[2] == boxCol[4] && boxCol[3] == boxCol[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 2 and box 3 are the same so remove from box 1");
                        removeForBlockBlockColInteraction(grid, 0, cell.box % 3, boxCol[2], boxCol[3], cand);
                    }
                }
            }
        }
    }

    public void removeForBlockBlockColInteraction(Cell[][] grid, int box, int boxCol, int col1, int col2, int cand) {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                /*If the cell is in the box within the boxCol we are removing from ((cell.box / 3) == box)
                 * and that box is in the same boxCol we are removing from ((cell.box % 3) == boxCol)*/
                if (cell.ans == 0 && cell.box / 3 == box && cell.candidates[cand] && cell.box % 3 == boxCol) {
                    /*If the cell is in either of the columns we are removing from remove the candidate.*/
                    if (cell.col % 3 == col1 || cell.col % 3 == col2) {
//                        System.out.println("BlockBlockColInteraction removing " + (cand + 1) + " from " + cellC.pos);
                        cell.candidates[cand] = false;
                        change = true;
                    }
                }
            }
        }
    }

    public void blockBlockRowInteraction(Cell[][] grid, Cell cell) {
        /*For each candidate, if the candidate appears in two rows only of one box, and the same two rows only in another box
         * remove the candidates from those same two rows in the third box.*/
        for (int cand = 0; cand < 9; cand++) {
            /*rowWithinBox stores which of the 3 rows within each of the 3 boxes in the row of boxes the candidate appears in
             * index 0-2 being the 3 rows of the first box, 3-5 being the 3 rows of the second box and 6-8 being the 3 rows
             * of the third and final box.*/
            boolean[] rowWithinBox = new boolean[]{false, false, false,
                    false, false, false,
                    false, false, false};
            boolean ansFoundInBox = false;
            for (Cell[] cells : grid) {
                for (Cell cellC : cells) {
                    /*If a cell (cellC) has the answer equal to the candidate we are searching for, break the loop and set a boolean
                     * to know no to try and remove candidates at the end.*/
                    if ((cellC.box / 3) == (cell.box / 3) && cellC.ans == (cand + 1)) {
                        ansFoundInBox = true;
                        break;
                    }
                    if (cellC.ans == 0 && cellC.candidates[cand] && (cellC.box / 3) == (cell.box / 3)) {
                        /*cellC.box / 3 tells you which box in the row of boxes the cell belongs to. cellC.row % 3 tells
                         * you which row within that box the cell belongs to. Stores this in rowWithinBox to later look at
                         * which combination of rows we can use to remove from which box.*/
                        if (cellC.box % 3 == 0) {
                            if (cellC.row % 3 == 0) {
                                rowWithinBox[0] = true;
                            } else if (cellC.row % 3 == 1) {
                                rowWithinBox[1] = true;
                            } else if (cellC.row % 3 == 2) {
                                rowWithinBox[2] = true;
                            }
                        } else if (cellC.box % 3 == 1) {
                            if (cellC.row % 3 == 0) {
                                rowWithinBox[3] = true;
                            } else if (cellC.row % 3 == 1) {
                                rowWithinBox[4] = true;
                            } else if (cellC.row % 3 == 2) {
                                rowWithinBox[5] = true;
                            }
                        } else if (cellC.box % 3 == 2) {
                            if (cellC.row % 3 == 0) {
                                rowWithinBox[6] = true;
                            } else if (cellC.row % 3 == 1) {
                                rowWithinBox[7] = true;
                            } else if (cellC.row % 3 == 2) {
                                rowWithinBox[8] = true;
                            }
                        }
                    }
                }
            }
            if (!ansFoundInBox) {
                /*countRowOccur keeps track of how many times the candidate occurred in each of the 3 rows within the box.*/
                int[] countRowOccur = new int[]{0, 0, 0};
                /*boxRow stores the first and second row the candidate occurs in within the box. The index is as follows:
                 * {(box1,row1),(box1,row2),(box2,row1),(box2,row2),(box3,row1),(box3,row2)}. If it occurs within a third row
                 * the value for one is overwritten.*/
                int[] boxRow = new int[]{0, 0, 0, 0, 0, 0};
                /*This loop iterates through each value of rowWithinBox and counts the number of times the candidate appeared
                 * in a different row per box. countRowOccur[0] is the count for the first box and so on.*/
                for (int i = 0; i < 9; i++) {
                    if (i < 3 && rowWithinBox[i]) {
                        if (countRowOccur[0] == 0) {
                            boxRow[0] = i % 3;
                        } else {
                            boxRow[1] = i % 3;
                        }
                        countRowOccur[0]++;
                    } else if (i >= 3 && i < 6 && rowWithinBox[i]) {
                        if (countRowOccur[1] == 0) {
                            boxRow[2] = i % 3;
                        } else {
                            boxRow[3] = i % 3;
                        }
                        countRowOccur[1]++;
                    } else if (i >= 6 && rowWithinBox[i]) {
                        if (countRowOccur[2] == 0) {
                            boxRow[4] = i % 3;
                        } else {
                            boxRow[5] = i % 3;
                        }
                        countRowOccur[2]++;
                    }
                }
                /*If there a two boxes where the candidate only appeared in one or two rows*/
                if (countRowOccur[0] < 3 && countRowOccur[0] > 0 && countRowOccur[1] < 3 && countRowOccur[1] > 0) {
                    /*If the rows they appeared in are the same then remove them from the same rows in the third box.*/
                    if (boxRow[0] == boxRow[2] && boxRow[1] == boxRow[3]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 2 are the same so remove from box 3");
                        removeForBlockBlockRowInteraction(grid, 2, cell.box / 3, boxRow[0], boxRow[1], cand);
                    }
                } else if (countRowOccur[0] < 3 && countRowOccur[0] > 0 && countRowOccur[2] < 3 && countRowOccur[2] > 0) {
                    if (boxRow[0] == boxRow[4] && boxRow[1] == boxRow[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 3 are the same so remove from box 2");
                        removeForBlockBlockRowInteraction(grid, 1, cell.box / 3, boxRow[0], boxRow[1], cand);
                    }
                } else if (countRowOccur[1] < 3 && countRowOccur[1] > 0 && countRowOccur[2] < 3 && countRowOccur[2] > 0) {
                    if (boxRow[2] == boxRow[4] && boxRow[3] == boxRow[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 2 and box 3 are the same so remove from box 1");
                        removeForBlockBlockRowInteraction(grid, 0, cell.box / 3, boxRow[2], boxRow[3], cand);
                    }
                }
            }
        }
    }

    public void removeForBlockBlockRowInteraction(Cell[][] grid, int box, int boxRow, int row1, int row2, int cand) {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                /*If the cell is in the box within the boxRow we are removing from ((cell.box % 3) == box)
                 * and that box is in the same boxRow we are removing from ((cell.box / 3) == boxRow)*/
                if (cell.ans == 0 && cell.box % 3 == box && cell.candidates[cand] && cell.box / 3 == boxRow) {
                    if (cell.row % 3 == row1 || cell.row % 3 == row2) {
//                        System.out.println("BlockBlockRowInteraction removing " + (cand + 1) + " from " + cellC.pos);
                        cell.candidates[cand] = false;
                        change = true;
                    }
                }
            }
        }
    }

    public void rowBlockInteraction(Cell[][] grid, Cell cell) {
        /*If a candidate only appears in one row in a box. Remove the candidate from every cell in the row outside of that box.*/
        Cell simCell = grid[0][0];
        /*For every candidate*/
        for (int cand = 0; cand < 9; cand++) {
            boolean foundInBox = false;
            /*If a cell does not contain the candidate continue*/
            if (!cell.candidates[cand]) {
                continue;
            }
            /*For every other cell*/
            for (Cell[] cells : grid) {
                for (Cell cellC : cells) {
                    /*If a cell in the same box has the candidate as its answer mark foundInBox as true and break the loop.
                     * Else if a cell within the same box and not in the same row has the same candidate, also mark foundInBox
                     * as true and break the loop.
                     * Else if any other cell in the same box and same row has the candidate make this the similar cell.*/
                    if (cellC.box == cell.box && cellC.ans == (cand + 1)) {
                        foundInBox = true;
                        break;
                    } else if (cellC.pos != cell.pos && cellC.ans == 0 && cellC.box == cell.box && cellC.candidates[cand] && cellC.row != cell.row) {
                        foundInBox = true;
                        break;
                    } else if (cellC.pos != cell.pos && cellC.row == cell.row && cellC.box == cell.box && cellC.candidates[cand]) {
                        simCell = cellC;
                    }
                }
            }
            if (!foundInBox) {
                /*Remove the candidate from every other cell in the row that is not in the same box as the original cell.*/
                removeCandInRowNotBox(grid, cell, cand, simCell);
            }
        }
    }

    public void removeCandInRowNotBox(Cell[][] grid, Cell cell, int cand, Cell simCell) {
        /*For every cell in the row*/
        for (Cell cells : grid[cell.row]) {
            /*If the cell has the candidate and is not in the same box, remove the candidate from the cell*/
            if (cells.candidates[cand] && cells.box != cell.box) {
//                System.out.println("Removing " + (cand + 1) + " from " + cells.pos);
                cells.candidates[cand] = false;
                change = true;
            }
        }
    }

    public void colBlockInteraction(Cell[][] grid, Cell cell) {
        /*If a candidate only appears in one column in a box. Remove the candidate from every cell in the column outside of that box.*/
        Cell simCell = grid[0][0];
        /*For every candidate*/
        for (int cand = 0; cand < 9; cand++) {
            boolean foundInBox = false;
            /*If a cell does not contain the candidate continue*/
            if (!cell.candidates[cand]) {
                continue;
            }
            /*For every other cell*/
            for (Cell[] cells : grid) {
                for (Cell cellC : cells) {
                    /*If a cell in the same box has the candidate as its answer mark foundInBox as true and break the loop.
                     * Else if a cell within the same box and not in the same column has the same candidate, also mark foundInBox
                     * as true and break the loop.
                     * Else if any other cell in the same box and same column has the candidate make this the similar cell.*/
                    if (cellC.box == cell.box && cellC.ans == (cand + 1)) {
                        foundInBox = true;
                    } else if (cellC.pos != cell.pos && cellC.ans == 0 && cellC.box == cell.box && cellC.candidates[cand] && cellC.col != cell.col) {
                        foundInBox = true;
                    } else if (cellC.pos != cell.pos && cellC.col == cell.col && cellC.box == cell.box && cellC.candidates[cand]) {
                        simCell = cellC;
                    }
                }
            }
            if (!foundInBox) {
                /*Remove the candidate from every other cell in the column that is not in the same box as the original cell.*/
                removeCandInColNotBox(grid, cell, cand, simCell);
            }

        }
    }

    public void removeCandInColNotBox(Cell[][] grid, Cell cell, int cand, Cell simCell) {
        /*For every cell in the column*/
        for (Cell[] cells : grid) {
            /*If the cell has the candidate and is not in the same box, remove the candidate from the cell*/
            if (cells[cell.col].candidates[cand] && cells[cell.col].box != cell.box) {
//                System.out.println("Removing " + (cand + 1) + " from " + cells[cell.col].pos);
                cells[cell.col].candidates[cand] = false;
                change = true;
            }
        }
    }

    public void printGrid(Cell[][] grid) {
        /*Prints out every answer in the puzzle. If the answer is 0 (has no answer) print +*/
        System.out.println();
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.pos % 3 == 0 && cell.pos % 9 != 0) {
                    System.out.print(" ");
                }
                if (cell.pos % 27 == 0 && cell.pos != 0) {
                    System.out.println();
                }
                if (cell.ans == 0) {
                    System.out.print("+");
                } else {
                    System.out.print(cell.ans);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void initialise(Cell[][] grid, int[] puzzle) {
        /*Takes a array of integers as the puzzle and creates a cell with all the values needed to solve the puzzle.*/
        int pos = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (puzzle[pos] == 0) {
                    lastEmpty = pos;
                }
                grid[row][col] = new Cell(pos, puzzle[pos], row, col);
                pos++;
            }
        }
    }

    public void soleCandidate(Cell[][] grid, Cell cell) {
        /*If any cell in the row, column or box has the candidate as its answer. Remove that candidate from the cell.*/
        for (int cand = 0; cand < 9; cand++) {
            if (cell.candidates[cand] && checkHouses(grid, cell, cand)) {
//                System.out.println("Removing from " + cell.pos + " candidate " + (cand + 1));
                cell.candidates[cand] = false;
                change = true;
            }
        }
    }

    public void uniqueCandidate(Cell[][] grid, Cell cell) {
        /*If the candidate does not appear as a candidate in the same row, column or box. Set the answer of that cell to the
         * candidate.*/
        for (int cand = 0; cand < 9; cand++) {
            if (cell.candidates[cand] && !checkHousesCand(grid, cell, cand)) {
//                System.out.println("UC Setting ans for cell " + cell.pos + " to " + (cand + 1));
                cell.ans = (cand + 1);
                cell.clearC();
                change = true;
            }
        }
    }

    public boolean checkHousesCand(Cell[][] grid, Cell cell, int cand) {
        /*If the candidate appears as a candidate in the same row, column or box. Return true.*/
        for (Cell[] cells : grid) {
            for (Cell cellC : cells) {
                if ((cellC.candidates[cand] || cellC.ans == cand + 1) && cellC.pos != cell.pos && (cellC.row == cell.row || cellC.col == cell.col || cellC.box == cell.box)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkHouses(Cell[][] grid, Cell cell, int cand) {
        /*If any cell in the row, column or box has the candidate as its answer. Return true.*/
        for (Cell[] cells : grid) {
            for (Cell cellC : cells) {
                if (cellC.ans == (cand + 1) && (cellC.row == cell.row || cellC.col == cell.col || cellC.box == cell.box)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printAllCands(Cell[][] grid, int... indexes) {
        /*For every cell if they are any of the cells in the given indexes, print all their candidates.
         * If no indexes are given as an argument, print the candidates for every cell in the grid.*/
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (indexes.length == 0) {
                    cell.printC();
                } else {
                    for (int i : indexes) {
                        if (cell.pos == i) {
                            cell.printC();
                        }
                    }
                }
            }
        }
    }

    public Cell[][] solve(Cell[][] grid) {
        change = false;
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    soleCandidate(grid, cell);
                    uniqueCandidate(grid, cell);
                    rowBlockInteraction(grid, cell);
                    colBlockInteraction(grid, cell);
                    blockBlockRowInteraction(grid, cell);
                    blockBlockColInteraction(grid, cell);
                    cell.findAns();
                }
                if (cell.pos % 3 == 2 && cell.row % 3 == 2) {
                    nakedSubsetBox(grid, cell.box);
                    hiddenSubsetBox(grid, cell.box);
                }
                if (cell.pos % 9 == 8) {
                    nakedSubsetRow(grid, cell.row);
                    hiddenSubsetRow(grid, cell.row);
                }
                if (cell.pos / 9 == 8) {
                    nakedSubsetCol(grid, cell.col);
                    hiddenSubsetCol(grid, cell.col);
                }
                if (cell.pos == 80) {
                    xWingRow(grid);
                    xWingCol(grid);
                }
            }
        }
        if (!change) {
            return grid;
        }
        passes++;
        return solve(grid);
    }

//    public static void main(String[] args) {
////        initialise(grid, HCPuzzles.puzzle);
////        initialise(grid, HCPuzzles.automorphic);
////        initialise(grid, HCPuzzles.medium);
////        initialise(grid, HCPuzzles.hardestPuzzle);
////        initialise(grid, HCPuzzles.rowBlockTest);
////        initialise(grid, HCPuzzles.blockBlockRowTest);
////        initialise(grid, HCPuzzles.blockBlockColTest);
////        initialise(grid, HCPuzzles.empty);
////        initialise(grid, HCPuzzles.xWingRow);
////        initialise(grid, HCPuzzles.xWingCol);
//
//        initialise(grid, HCPuzzles.generated2);
//        printGrid(grid);
////        solve(grid);
//        backTrack(grid);
////        everySolution(grid);
//        printGrid(grid);
////        printAllCands();
////        System.out.println(passes);
//
//
//    }

    public int lastEmpty;

    public boolean isSolved(Cell[][] grid) {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void backTrack(Cell[][] grid) {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    // for every possible candidate.
                    for (int cand = 0; cand < 9; cand++) {
                        if (!checkHouses(grid, cell, cand)) {
                            // set the cells answer to that candidate.
                            cell.ans = cand + 1;
                            // see if it leads to a solution with that candidate
                            backTrack(grid);
                            // if it is solved return.
//                            if (grid[lastEmpty / 9][lastEmpty % 9].ans != 0) {
//                                return;
//                            }
                            if(isSolved(grid)) {
                                return;
                            }
                        }
                    }
                    // if no candidate matches set the answer back to 0 and return.
                    cell.ans = 0;
                    return;
                }
            }
        }
    }
}