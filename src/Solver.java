import java.util.ArrayList;

public class Solver {

    // Cell[row][col]
    public static Cell[][] grid = new Cell[9][9];

    private static boolean change;

    private static int passes = 0;

    public static int[] puzzle = {0, 6, 0, 3, 0, 0, 8, 0, 4,
            5, 3, 7, 0, 9, 0, 0, 0, 0,
            0, 4, 0, 0, 0, 6, 3, 0, 7,
            0, 9, 0, 0, 5, 1, 2, 3, 8,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            7, 1, 3, 6, 2, 0, 0, 4, 0,
            3, 0, 6, 4, 0, 0, 0, 1, 0,
            0, 0, 0, 0, 6, 0, 5, 2, 3,
            1, 0, 2, 0, 0, 9, 0, 8, 0};

    public static int[] rowBlockTest = {0, 0, 0, 0, 7, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 2, 0, 1, 0, 0, 0,
            5, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 9, 0, 6, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static int[] blockBlockRowTest = {0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 8, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            2, 9, 0, 0, 1, 4, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 8, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static int[] hardestPuzzle = {8, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 3, 6, 0, 0, 0, 0, 0,
            0, 7, 0, 0, 9, 0, 2, 0, 0,
            0, 5, 0, 0, 0, 7, 0, 0, 0,
            0, 0, 0, 0, 4, 5, 7, 0, 0,
            0, 0, 0, 1, 0, 0, 0, 3, 0,
            0, 0, 1, 0, 0, 0, 0, 6, 8,
            0, 0, 8, 5, 0, 0, 0, 1, 0,
            0, 9, 0, 0, 0, 0, 4, 0, 0};

    public static int[] blockBlockColTest = {0, 0, 0, 0, 2, 0, 0, 0, 0,
            0, 0, 0, 0, 9, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 8, 0,
            0, 8, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 4, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static int[] medium = {0, 4, 0, 0, 0, 2, 0, 1, 9,
            0, 0, 0, 3, 5, 1, 0, 8, 6,
            3, 1, 0, 0, 9, 4, 7, 0, 0,
            0, 9, 4, 0, 0, 0, 0, 0, 7,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            2, 0, 0, 0, 0, 0, 8, 9, 0,
            0, 0, 9, 5, 2, 0, 0, 4, 1,
            4, 2, 0, 1, 6, 9, 0, 0, 0,
            1, 6, 0, 8, 0, 0, 0, 7, 0};

    public static int[] automorphic = {0, 0, 0, 2, 1, 0, 0, 0, 0,
            0, 0, 7, 3, 0, 0, 0, 0, 0,
            0, 5, 8, 0, 0, 0, 0, 0, 0,
            4, 3, 0, 0, 0, 0, 0, 0, 0,
            2, 0, 0, 0, 0, 0, 0, 0, 8,
            0, 0, 0, 0, 0, 0, 0, 7, 6,
            0, 0, 0, 0, 0, 0, 2, 5, 0,
            0, 0, 0, 0, 0, 7, 3, 0, 0,
            0, 0, 0, 0, 9, 8, 0, 0, 0};

    public static int[] hiddenTriple = {5, 2, 8, 6, 0, 0, 0, 4, 9,
            1, 3, 6, 4, 9, 0, 0, 2, 5,
            7, 9, 4, 2, 0, 5, 6, 3, 0,
            0, 0, 0, 1, 0, 0, 2, 0, 0,
            0, 0, 7, 8, 2, 6, 3, 0, 0,
            0, 0, 2, 5, 0, 9, 0, 6, 0,
            2, 4, 0, 3, 0, 0, 9, 7, 6,
            9, 0, 9, 7, 0, 2, 4, 1, 3,
            0, 7, 0, 9, 0, 4, 5, 8, 2};

    public static int[] empty = {0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,};

    public static void setCandsForHiddenTriple(int col) {
        // Col needs to be 4
        for (Cell[] cell : grid) {
            if (cell[col].pos / 9 == 0) {
                cell[col].candidates[1] = false;
                cell[col].candidates[3] = false;
                cell[col].candidates[4] = false;
                cell[col].candidates[5] = false;
                cell[col].candidates[7] = false;
                cell[col].candidates[8] = false;
            }
            if (cell[col].pos / 9 == 1) {
                cell[col].candidates[0] = false;
                cell[col].candidates[1] = false;
                cell[col].candidates[2] = false;
                cell[col].candidates[3] = false;
                cell[col].candidates[4] = false;
                cell[col].candidates[5] = false;
                cell[col].candidates[6] = false;
                cell[col].candidates[7] = false;
                cell[col].candidates[8] = false;
            }
            if (cell[col].pos / 9 == 2) {
                cell[col].candidates[1] = false;
                cell[col].candidates[2] = false;
                cell[col].candidates[3] = false;
                cell[col].candidates[4] = false;
                cell[col].candidates[5] = false;
                cell[col].candidates[6] = false;
                cell[col].candidates[8] = false;
            }
            if (cell[col].pos / 9 == 3) {
                cell[col].candidates[0] = false;
                cell[col].candidates[1] = false;
                cell[col].candidates[4] = false;
                cell[col].candidates[5] = false;
                cell[col].candidates[7] = false;
                cell[col].candidates[8] = false;
            }
            if (cell[col].pos / 9 == 4) {
                cell[col].candidates[0] = false;
                cell[col].candidates[1] = false;
                cell[col].candidates[2] = false;
                cell[col].candidates[3] = false;
                cell[col].candidates[4] = false;
                cell[col].candidates[5] = false;
                cell[col].candidates[6] = false;
                cell[col].candidates[7] = false;
                cell[col].candidates[8] = false;
            }
            if (cell[col].pos / 9 == 5) {
                cell[col].candidates[0] = false;
                cell[col].candidates[1] = false;
                cell[col].candidates[4] = false;
                cell[col].candidates[5] = false;
                cell[col].candidates[7] = false;
                cell[col].candidates[8] = false;
            }
            if (cell[col].pos / 9 == 6) {
                cell[col].candidates[1] = false;
                cell[col].candidates[2] = false;
                cell[col].candidates[3] = false;
                cell[col].candidates[5] = false;
                cell[col].candidates[6] = false;
                cell[col].candidates[8] = false;
            }
            if (cell[col].pos / 9 == 7) {
                cell[col].candidates[0] = false;
                cell[col].candidates[1] = false;
                cell[col].candidates[2] = false;
                cell[col].candidates[3] = false;
                cell[col].candidates[6] = false;
                cell[col].candidates[7] = false;
                cell[col].candidates[8] = false;
            }
            if (cell[col].pos / 9 == 8) {
                cell[col].candidates[1] = false;
                cell[col].candidates[2] = false;
                cell[col].candidates[3] = false;
                cell[col].candidates[4] = false;
                cell[col].candidates[6] = false;
                cell[col].candidates[7] = false;
                cell[col].candidates[8] = false;
            }


            if (cell[col].pos / 9 == 100) {
                cell[col].candidates[0] = false;
                cell[col].candidates[1] = false;
                cell[col].candidates[2] = false;
                cell[col].candidates[3] = false;
                cell[col].candidates[4] = false;
                cell[col].candidates[5] = false;
                cell[col].candidates[6] = false;
                cell[col].candidates[7] = false;
                cell[col].candidates[8] = false;
            }
        }
    }

    public static boolean unionOfCandidates(boolean[][] numInCells, int amount) {
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

        return count == amount;
    }

    public static boolean allMadeContribution;

    public static Cell[] exceptionsBox(int box, int... nums) {
        Cell[] exceptions = new Cell[nums.length];
        int exPos = 0;
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if(cell.box == box) {
                    boolean placed = false;
                    for (int num : nums) {
                        if (!placed && cell.candidates[num]) {
                            exceptions[exPos] = cell;
                            placed = true;
                            exPos++;
                        }
                    }
                }
            }
        }
        return exceptions;
    }

    public static boolean[][] numInCellsBox(int box, int... nums) {
        boolean[][] numInCells = new boolean[nums.length][9];
        // numPos is which index of the 4 numbers it is
        // num in nums is the exact number we are checking
        // cell[col].pos is which cell we are checking

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
                            // figure out how to get pos in box
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

        for(boolean contribution : contributions) {
            if (!contribution) {
                allMadeContribution = false;
                break;
            }
        }

        return numInCells;
    }

    public static void hiddenSubsetBox(int box) {
        /* For each number create a boolean array for which cells they appear in. (can just use the cells candidate array). for every combination of 4 numbers
         * see if the union of cells they appear in results in just 4 cells.*/

        for(int amount = 4; amount >= 2; amount--) {
            for (int num1 = 0; num1 < 9; num1++) {
                for (int num2 = num1 + 1; num2 < 9; num2++) {
                    if(amount == 2) {
                        allMadeContribution = true;
                        if(unionOfCandidates(numInCellsBox(box, num1, num2), amount) && allMadeContribution) {
//                            System.out.println("Hello 2");
//                            System.out.println((num1+1) + "," + (num2+1));
                            int[] cands = new int[] {num1, num2};

                            removeCandInBox(box, cands, exceptionsBox(box, num1, num2));
                        }
                    }
                    else {
                        for (int num3 = num2 + 1; num3 < 9; num3++) {
                            if(amount == 3) {
                                allMadeContribution = true;
                                if(unionOfCandidates(numInCellsBox(box, num1, num2, num3), amount) && allMadeContribution) {
//                                    System.out.println("Hello 3");
//                                    System.out.println((num1+1) + "," + (num2+1) + "," + (num3+1));
                                    int[] cands = new int[] {num1, num2, num3};

                                    removeCandInBox(box, cands, exceptionsBox(box, num1, num2, num3));
                                }
                            }
                            else {
                                for (int num4 = num3 + 1; num4 < 9; num4++) {
                                    // create boolean array for each number of which cells they appear in
                                    // create method which unions the arrays and returns true if the boolean array result has only 4 true's
                                    allMadeContribution = true;
                                    if(unionOfCandidates(numInCellsBox(box, num1, num2, num3, num4), amount) && allMadeContribution) {
//                                        System.out.println("Hello 4");
//                                        System.out.println((num1+1) + "," + (num2+1) + "," + (num3+1) + "," + (num4+1));
                                        int[] cands = new int[] {num1, num2, num3, num4};

                                        removeCandInBox(box, cands, exceptionsBox(box, num1, num2, num3, num4));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Cell[] exceptionsCol(int col, int... nums) {
        Cell[] exceptions = new Cell[nums.length];
        int exPos = 0;
        for (Cell[] cell : grid) {
            boolean placed = false;
            for(int num : nums) {
                if(!placed && cell[col].candidates[num]) {
                    exceptions[exPos] = cell[col];
                    placed = true;
                    exPos++;
                }
            }
        }
        return exceptions;
    }

    public static boolean[][] numInCellsCol(int col, int... nums) {
        boolean[][] numInCells = new boolean[nums.length][9];
        // numPos is which index of the 4 numbers it is
        // num in nums is the exact number we are checking
        // cell[col].pos is which cell we are checking

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

        for(boolean contribution : contributions) {
            if (!contribution) {
                allMadeContribution = false;
                break;
            }
        }

        return numInCells;
    }

    public static void hiddenSubsetCol(int col) {
        /* For each number create a boolean array for which cells they appear in. (can just use the cells candidate array). for every combination of 4 numbers
         * see if the union of cells they appear in results in just 4 cells.*/

        for(int amount = 4; amount >= 2; amount--) {
            for (int num1 = 0; num1 < 9; num1++) {
                for (int num2 = num1 + 1; num2 < 9; num2++) {
                    if(amount == 2) {
                        allMadeContribution = true;
                        if(unionOfCandidates(numInCellsCol(col, num1, num2), amount) && allMadeContribution) {
//                            System.out.println("Hello 2");
//                            System.out.println((num1+1) + "," + (num2+1));
                            int[] cands = new int[] {num1, num2};

                            removeCandInCol(col, cands, exceptionsCol(col, num1, num2));
                        }
                    }
                    else {
                        for (int num3 = num2 + 1; num3 < 9; num3++) {
                            if(amount == 3) {
                                allMadeContribution = true;
                                if(unionOfCandidates(numInCellsCol(col, num1, num2, num3), amount) && allMadeContribution) {
//                                    System.out.println("Hello 3");
//                                    System.out.println((num1+1) + "," + (num2+1) + "," + (num3+1));
                                    int[] cands = new int[] {num1, num2, num3};

                                    removeCandInCol(col, cands, exceptionsCol(col, num1, num2, num3));
                                }
                            }
                            else {
                                for (int num4 = num3 + 1; num4 < 9; num4++) {
                                    // create boolean array for each number of which cells they appear in
                                    // create method which unions the arrays and returns true if the boolean array result has only 4 true's
                                    allMadeContribution = true;
                                    if(unionOfCandidates(numInCellsCol(col, num1, num2, num3, num4), amount) && allMadeContribution) {
//                                        System.out.println("Hello 4");
//                                        System.out.println((num1+1) + "," + (num2+1) + "," + (num3+1) + "," + (num4+1));
                                        int[] cands = new int[] {num1, num2, num3, num4};

                                        removeCandInCol(col, cands, exceptionsCol(col, num1, num2, num3, num4));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Cell[] exceptionsRow(int row, int... nums) {
        Cell[] exceptions = new Cell[nums.length];
        int exPos = 0;
        for (Cell cell : grid[row]) {
            boolean placed = false;
            for(int num : nums) {
                if(!placed && cell.candidates[num]) {
                    exceptions[exPos] = cell;
                    placed = true;
                    exPos++;
                }
            }
        }
        return exceptions;
    }

    public static boolean[][] numInCellsRow(int row, int... nums) {
        boolean[][] numInCells = new boolean[nums.length][9];
        // numPos is which index of the 4 numbers it is
        // num in nums is the exact number we are checking
        // cell[col].pos is which cell we are checking

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

        for(boolean contribution : contributions) {
            if (!contribution) {
                allMadeContribution = false;
                break;
            }
        }

        return numInCells;
    }

    public static void hiddenSubsetRow(int row) {
        /* For each number create a boolean array for which cells they appear in. (can just use the cells candidate array). for every combination of 4 numbers
         * see if the union of cells they appear in results in just 4 cells.*/

        for(int amount = 4; amount >= 2; amount--) {
            for (int num1 = 0; num1 < 9; num1++) {
                for (int num2 = num1 + 1; num2 < 9; num2++) {
                    if(amount == 2) {
                        allMadeContribution = true;
                        if(unionOfCandidates(numInCellsRow(row, num1, num2), amount) && allMadeContribution) {
//                            System.out.println("Hello 2");
//                            System.out.println((num1+1) + "," + (num2+1));
                            int[] cands = new int[] {num1, num2};

                            removeCandInRow(row, cands, exceptionsRow(row, num1, num2));
                        }
                    }
                    else {
                        for (int num3 = num2 + 1; num3 < 9; num3++) {
                            if(amount == 3) {
                                allMadeContribution = true;
                                if(unionOfCandidates(numInCellsRow(row, num1, num2, num3), amount) && allMadeContribution) {
//                                    System.out.println("Hello 3");
//                                    System.out.println((num1+1) + "," + (num2+1) + "," + (num3+1));
                                    int[] cands = new int[] {num1, num2, num3};

                                    removeCandInRow(row, cands, exceptionsRow(row, num1, num2, num3));
                                }
                            }
                            else {
                                for (int num4 = num3 + 1; num4 < 9; num4++) {
                                    // create boolean array for each number of which cells they appear in
                                    // create method which unions the arrays and returns true if the boolean array result has only 4 true's
                                    allMadeContribution = true;
                                    if(unionOfCandidates(numInCellsRow(row, num1, num2, num3, num4), amount) && allMadeContribution) {
//                                        System.out.println("Hello 4");
//                                        System.out.println((num1+1) + "," + (num2+1) + "," + (num3+1) + "," + (num4+1));
                                        int[] cands = new int[] {num1, num2, num3, num4};

                                        removeCandInRow(row, cands, exceptionsRow(row, num1, num2, num3, num4));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void nakedSubsetBox(int box) {
        // size is the number of cands we're looking for. we start at max size 4 down to 2
        int maxSize = 4;


        // Go through every cell in the column and if it has the correct amount of candidates add it to initial cells to be worked on later
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

            int[] cands = new int[size];

            /*For every cell in the initial cells, go through the rest of the cells in the column and create a subset where every cell
             * only has candidates belonging to the set of candidates of the initial cell (It doesn't have to contain all the candidates)
             * If the subset of cells is equal to the size of the amount of candidates the initial cell has then we remove every candidate
             * of the initial cell from all cells except the subset and initial cell.*/

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

                Cell[] exceptions = subset.toArray(new Cell[0]);

                if (subset.size() == size) {
                    removeCandInBox(box, cands, exceptions);
                }
            }
        }
    }

    public static void removeCandInBox(int box, int[] cands, Cell[] exceptions) {
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

    public static void nakedSubsetCol(int col) {
        /* Go along the row and make a list of every cell with 4 candidates.
         * Once the cells are found, for each cell try find every cell up to a max of 4 which contains any subset of these candidates
         * If it doesn't work for 4 try for 3, then 2.
         * Every time you find a perfect set of cells remove the 4, 3 or 2 candidates from every cell in the row except for the subset. */

        // size is the number of cands we're looking for. we start at max size 4 down to 2
        int maxSize = 4;


        // Go through every cell in the column and if it has the correct amount of candidates add it to initial cells to be worked on later
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


            int[] cands = new int[size];

            /*For every cell in the initial cells, go through the rest of the cells in the column and create a subset where every cell
             * only has candidates belonging to the set of candidates of the initial cell (It doesn't have to contain all the candidates)
             * If the subset of cells is equal to the size of the amount of candidates the initial cell has then we remove every candidate
             * of the initial cell from all cells except the subset and initial cell.*/

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

                Cell[] exceptions = subset.toArray(new Cell[0]);

                if (subset.size() == size) {
                    removeCandInCol(col, cands, exceptions);
                }
            }
        }

    }

    public static void removeCandInCol(int col, int[] cands, Cell[] exceptions) {
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

    public static void nakedSubsetRow(int row) {
        /* Go along the row and make a list of every cell with 4 candidates.
         * Once the cells are found, for each cell try find every cell up to a max of 4 which contains any subset of these candidates
         * If it doesn't work for 4 try for 3, then 2.
         * Every time you find a perfect set of cells remove the 4, 3 or 2 candidates from every cell in the row except for the subset. */

        // size is the number of cands we're looking for. we start at max size 4 down to 2
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

                Cell[] exceptions = subset.toArray(new Cell[0]);

                if (subset.size() == size) {
                    removeCandInRow(row, cands, exceptions);
                }
            }
        }
    }

    public static void removeCandInRow(int row, int[] cands, Cell[] exceptions) {
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

    public static void blockBlockColInteraction(Cell cell) {
        // find two cells in one box with common cand. then find another pair in the same row with same cand.
        // afterwards find another pair in the same columns as the first in the same box with same cand.
        // then find another pair in the second box in the same columns as that boxes pair.
        // All the while if we ever come across a cell with the same candidate in a position we do not want
        // we stop looking.
        // If its successful remove the cand in the third box in the two rows

        for (int cand = 0; cand < 9; cand++) {

            // if it appears in two boxes in the same two rows only then remove it from the third box in the same two rows

            boolean[] boxRowShit = new boolean[]{false, false, false,
                    false, false, false,
                    false, false, false};

            boolean foundInBoxRow = false;

            for (Cell[] cells : grid) {
                for (Cell cellC : cells) {

                    if ((cellC.box % 3) == (cell.box % 3) && cellC.ans == (cand + 1)) {
                        foundInBoxRow = true;
                    }

                    // (cellC.box / 3) == (cell.box / 3) ensures we only check in the same col of boxes
//                    if (cellC.ans == 0 && cellC.pos != cell.pos && cellC.candidates[cand] && (cellC.box % 3) == (cell.box % 3)) {
                    if (cellC.ans == 0 && cellC.candidates[cand] && (cellC.box % 3) == (cell.box % 3)) {
                        // for each box count the number of rows the cand occurs in.
                        // if at the end it only occurs in the same two rows of two boxes then remove them from the second

                        //Add something here to make it always start from the first row in the block

                        if (cellC.box / 3 == 0) {
                            if (cellC.col % 3 == 0) {
                                boxRowShit[0] = true;
                            } else if (cellC.col % 3 == 1) {
                                boxRowShit[1] = true;
                            } else if (cellC.col % 3 == 2) {
                                boxRowShit[2] = true;
                            }
                        } else if (cellC.box / 3 == 1) {
                            if (cellC.col % 3 == 0) {
                                boxRowShit[3] = true;
                            } else if (cellC.col % 3 == 1) {
                                boxRowShit[4] = true;
                            } else if (cellC.col % 3 == 2) {
                                boxRowShit[5] = true;
                            }
                        } else if (cellC.box / 3 == 2) {
                            if (cellC.col % 3 == 0) {
                                boxRowShit[6] = true;
                            } else if (cellC.col % 3 == 1) {
                                boxRowShit[7] = true;
                            } else if (cellC.col % 3 == 2) {
                                boxRowShit[8] = true;
                            }
                        }
                    }
                }
            }


            if (!foundInBoxRow) {
                // Count row occur counts the number of rows a cand occurs in per box. The index of the array being the number of box in the row of boxes
                int[] countRowOccur = new int[]{0, 0, 0};

                // Box row stores the first and second row the cand occurs in, in the box. This is useless if the number of rows it occurs in, in the box is greater than 3
                // The index goes box 1 row 1 -> box 1 row 2 -> ... -> box 3 row 1 -> box 3 row 2
                int[] boxRow = new int[]{0, 0, 0, 0, 0, 0};

                // this counts the number of different rows the cand appears in each box
                for (int i = 0; i < 9; i++) {
                    if (i < 3 && boxRowShit[i]) {
                        if (countRowOccur[0] == 0) {
                            boxRow[0] = i % 3;
                        } else {
                            boxRow[1] = i % 3;
                        }
                        countRowOccur[0]++;
                    } else if (i >= 3 && i < 6 && boxRowShit[i]) {
                        if (countRowOccur[1] == 0) {
                            boxRow[2] = i % 3;
                        } else {
                            boxRow[3] = i % 3;
                        }
                        countRowOccur[1]++;
                    } else if (i >= 6 && boxRowShit[i]) {
                        if (countRowOccur[2] == 0) {
                            boxRow[4] = i % 3;
                        } else {
                            boxRow[5] = i % 3;
                        }
                        countRowOccur[2]++;
                    }
                }

                if (countRowOccur[0] < 3 && countRowOccur[0] > 0 && countRowOccur[1] < 3 && countRowOccur[1] > 0) {
                    if (boxRow[0] == boxRow[2] && boxRow[1] == boxRow[3]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 2 are the same so remove from box 3");

                        removeForBlockBlockColInteraction(2, cell.box % 3, boxRow[0], boxRow[1], cand);
                    }
                } else if (countRowOccur[0] < 3 && countRowOccur[0] > 0 && countRowOccur[2] < 3 && countRowOccur[2] > 0) {
                    if (boxRow[0] == boxRow[4] && boxRow[1] == boxRow[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 3 are the same so remove from box 2");

                        removeForBlockBlockColInteraction(1, cell.box % 3, boxRow[0], boxRow[1], cand);
                    }
                } else if (countRowOccur[1] < 3 && countRowOccur[1] > 0 && countRowOccur[2] < 3 && countRowOccur[2] > 0) {
                    if (boxRow[2] == boxRow[4] && boxRow[3] == boxRow[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 2 and box 3 are the same so remove from box 1");

                        removeForBlockBlockColInteraction(0, cell.box % 3, boxRow[2], boxRow[3], cand);
                    }
                }
            }
        }
    }

    public static void removeForBlockBlockColInteraction(int box, int boxRow, int row1, int row2, int cand) {
        for (Cell[] cells : grid) {
            for (Cell cellC : cells) {
                if (cellC.ans == 0 && cellC.box / 3 == box && cellC.candidates[cand] && cellC.box % 3 == boxRow) {
                    if (cellC.col % 3 == row1 || cellC.col % 3 == row2) {
//                        System.out.println("BlockBlockColInteraction removing " + (cand + 1) + " from " + cellC.pos);
                        cellC.candidates[cand] = false;
                        change = true;
                    }
                }
            }
        }
    }

    public static void blockBlockRowInteraction(Cell cell) {
        // find two cells in one box with common cand. then find another pair in the same row with same cand.
        // afterwards find another pair in the same columns as the first in the same box with same cand.
        // then find another pair in the second box in the same columns as that boxes pair.
        // All the while if we ever come across a cell with the same candidate in a position we do not want
        // we stop looking.
        // If its successful remove the cand in the third box in the two rows

        for (int cand = 0; cand < 9; cand++) {

            // if it appears in two boxes in the same two rows only then remove it from the third box in the same two rows

            boolean[] boxRowShit = new boolean[]{false, false, false,
                    false, false, false,
                    false, false, false};

            boolean foundInBoxRow = false;

            for (Cell[] cells : grid) {
                for (Cell cellC : cells) {

                    if ((cellC.box / 3) == (cell.box / 3) && cellC.ans == (cand + 1)) {
                        foundInBoxRow = true;
                    }

                    // (cellC.box / 3) == (cell.box / 3) ensures we only check in the same row of boxes
//                    if (cellC.ans == 0 && cellC.pos != cell.pos && cellC.candidates[cand] && (cellC.box / 3) == (cell.box / 3)) {
                    if (cellC.ans == 0 && cellC.candidates[cand] && (cellC.box / 3) == (cell.box / 3)) {
                        // for each box count the number of rows the cand occurs in.
                        // if at the end it only occurs in the same two rows of two boxes then remove them from the second

                        //Add something here to make it always start from the first row in the block

                        if (cellC.box % 3 == 0) {
                            if (cellC.row % 3 == 0) {
                                boxRowShit[0] = true;
                            } else if (cellC.row % 3 == 1) {
                                boxRowShit[1] = true;
                            } else if (cellC.row % 3 == 2) {
                                boxRowShit[2] = true;
                            }
                        } else if (cellC.box % 3 == 1) {
                            if (cellC.row % 3 == 0) {
                                boxRowShit[3] = true;
                            } else if (cellC.row % 3 == 1) {
                                boxRowShit[4] = true;
                            } else if (cellC.row % 3 == 2) {
                                boxRowShit[5] = true;
                            }
                        } else if (cellC.box % 3 == 2) {
                            if (cellC.row % 3 == 0) {
                                boxRowShit[6] = true;
                            } else if (cellC.row % 3 == 1) {
                                boxRowShit[7] = true;
                            } else if (cellC.row % 3 == 2) {
                                boxRowShit[8] = true;
                            }
                        }
                    }
                }
            }


            if (!foundInBoxRow) {
                // Count row occur counts the number of rows a cand occurs in per box. The index of the array being the number of box in the row of boxes
                int[] countRowOccur = new int[]{0, 0, 0};

                // Box row stores the first and second row the cand occurs in, in the box. This is useless if the number of rows it occurs in, in the box is greater than 3
                // The index goes box 1 row 1 -> box 1 row 2 -> ... -> box 3 row 1 -> box 3 row 2
                int[] boxRow = new int[]{0, 0, 0, 0, 0, 0};

                // this counts the number of different rows the cand appears in each box
                for (int i = 0; i < 9; i++) {
                    if (i < 3 && boxRowShit[i]) {
                        if (countRowOccur[0] == 0) {
                            boxRow[0] = i % 3;
                        } else {
                            boxRow[1] = i % 3;
                        }
                        countRowOccur[0]++;
                    } else if (i >= 3 && i < 6 && boxRowShit[i]) {
                        if (countRowOccur[1] == 0) {
                            boxRow[2] = i % 3;
                        } else {
                            boxRow[3] = i % 3;
                        }
                        countRowOccur[1]++;
                    } else if (i >= 6 && boxRowShit[i]) {
                        if (countRowOccur[2] == 0) {
                            boxRow[4] = i % 3;
                        } else {
                            boxRow[5] = i % 3;
                        }
                        countRowOccur[2]++;
                    }
                }

//                if (cell.pos == 54 && cand == 6) {
//                    printAllCands(54);
//                    for (int i = 0; i < 6; i++) {
//                        System.out.println(boxRow[i]);
//                    }
//                    System.out.println("count row occur");
//                    for (int i = 0; i < 3; i++) {
//                        System.out.println(countRowOccur[i]);
//                    }
//                }


                if (countRowOccur[0] < 3 && countRowOccur[0] > 0 && countRowOccur[1] < 3 && countRowOccur[1] > 0) {
                    if (boxRow[0] == boxRow[2] && boxRow[1] == boxRow[3]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 2 are the same so remove from box 3");

                        removeForBlockBlockRowInteraction(2, cell.box / 3, boxRow[0], boxRow[1], cand);

                    }
                } else if (countRowOccur[0] < 3 && countRowOccur[0] > 0 && countRowOccur[2] < 3 && countRowOccur[2] > 0) {
                    if (boxRow[0] == boxRow[4] && boxRow[1] == boxRow[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 3 are the same so remove from box 2");

                        removeForBlockBlockRowInteraction(1, cell.box / 3, boxRow[0], boxRow[1], cand);

                    }
                } else if (countRowOccur[1] < 3 && countRowOccur[1] > 0 && countRowOccur[2] < 3 && countRowOccur[2] > 0) {
                    if (boxRow[2] == boxRow[4] && boxRow[3] == boxRow[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 2 and box 3 are the same so remove from box 1");

                        removeForBlockBlockRowInteraction(0, cell.box / 3, boxRow[2], boxRow[3], cand);

                    }
                }
            }
        }
    }

    public static void removeForBlockBlockRowInteraction(int box, int boxRow, int row1, int row2, int cand) {
        for (Cell[] cells : grid) {
            for (Cell cellC : cells) {
                if (cellC.ans == 0 && cellC.box % 3 == box && cellC.candidates[cand] && cellC.box / 3 == boxRow) {
                    if (cellC.row % 3 == row1 || cellC.row % 3 == row2) {
//                        System.out.println("BlockBlockRowInteraction removing " + (cand + 1) + " from " + cellC.pos);
                        cellC.candidates[cand] = false;
                        change = true;
                    }
                }
            }
        }
    }

    public static void rowBlockInteraction(Cell cell) {
        // for every cell in the same row in the same block, if all other cells in the block except for those cells don't contain
        // the same candidate. Then remove the candidate from every cell in the same row but not in the same block
        Cell simCell = grid[0][0];
        for (int cand = 0; cand < 9; cand++) {
            boolean foundInBox = false;
            if (!cell.candidates[cand]) {
                continue;
            }
            for (Cell[] cells : grid) {
                for (Cell cellC : cells) {
                    if (cellC.box == cell.box && cellC.ans == (cand + 1)) {
                        foundInBox = true;
                    } else if (cellC.pos != cell.pos && cellC.ans == 0 && cellC.box == cell.box && cellC.candidates[cand] && cellC.row != cell.row) {
                        foundInBox = true;
                    } else if (cellC.pos != cell.pos && cellC.row == cell.row && cellC.box == cell.box && cellC.candidates[cand]) {
                        simCell = cellC;
                    }
                }
            }
            if (!foundInBox) {
                removeCandInRowNotBox(cell, cand, simCell);
            }
        }
    }

    public static void removeCandInRowNotBox(Cell cell, int cand, Cell simCell) {
        boolean firstTime = true;
        for (Cell cells : grid[cell.row]) {
            if (cells.candidates[cand] && cells.box != cell.box) {
                if (firstTime) {
//                    System.out.println("Row block due to cell " + cell.pos + " and sim Cell " + simCell.pos);
                    firstTime = false;
                }
//                System.out.println("Removing " + (cand + 1) + " from " + cells.pos);
                cells.candidates[cand] = false;
                change = true;
            }
        }
    }

    public static void colBlockInteraction(Cell cell) {
        Cell simCell = grid[0][0];
        for (int cand = 0; cand < 9; cand++) {
            boolean foundInBox = false;
            if (!cell.candidates[cand]) {
                continue;
            }
            for (Cell[] cells : grid) {
                for (Cell cellC : cells) {
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
                removeCandInColNotBox(cell, cand, simCell);
            }

        }
    }

    public static void removeCandInColNotBox(Cell cell, int cand, Cell simCell) {
        boolean firstTime = true;
        for (Cell[] cells : grid) {
            if (cells[cell.col].candidates[cand] && cells[cell.col].box != cell.box) {
                if (firstTime) {
//                    System.out.println("Col block due to cell " + cell.pos + " and sim Cell " + simCell.pos);
                    firstTime = false;
                    if (cell.pos == 49 && simCell.pos == 40) {
                        cell.printC();
                        simCell.printC();
                    }
                }
//                System.out.println("Removing " + (cand + 1) + " from " + cells[cell.col].pos);
                cells[cell.col].candidates[cand] = false;
                change = true;
            }
        }
    }

    public static void printGrid() {
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
    }

    public static void initialise(int[] puzzle) {
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

    public static void soleCandidate(Cell cell) {
        for (int cand = 0; cand < 9; cand++) {
            if (cell.candidates[cand] && checkHouses(cell, cand)) {
//                System.out.println("Removing from " + cell.pos + " candidate " + (cand + 1));
                cell.candidates[cand] = false;
                change = true;
            }
        }
    }

    public static void uniqueCandidate(Cell cell) {
        for (int cand = 0; cand < 9; cand++) {
            if (cell.candidates[cand] && !checkHousesCand(cell, cand)) {
//                System.out.println("UC Setting ans for cell " + cell.pos + " to " + (cand + 1));
                cell.ans = (cand + 1);
                cell.clearC();
                change = true;
            }
        }
    }

    public static boolean checkHousesCand(Cell cell, int cand) {
        for (Cell[] cells : grid) {
            for (Cell cellC : cells) {
                if ((cellC.candidates[cand] || cellC.ans == cand + 1) && cellC.pos != cell.pos && (cellC.row == cell.row || cellC.col == cell.col || cellC.box == cell.box)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkHouses(Cell cell, int cand) {
        for (Cell[] cells : grid) {
            for (Cell cellC : cells) {
                if (cellC.ans == (cand + 1) && (cellC.row == cell.row || cellC.col == cell.col || cellC.box == cell.box)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printAllCands(int... indexes) {
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

    public static Cell[][] solve(Cell[][] grid) {
        change = false;

        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    soleCandidate(cell);
                    uniqueCandidate(cell);
                    rowBlockInteraction(cell);
                    colBlockInteraction(cell);
                    blockBlockRowInteraction(cell);
                    blockBlockColInteraction(cell);
                    cell.findAns();

                }
                if (cell.pos % 3 == 2 && cell.row % 3 == 2) {
                    nakedSubsetBox(cell.box);
                    hiddenSubsetBox(cell.box);
                }
                if (cell.pos % 9 == 8) {
                    nakedSubsetRow(cell.row);
                    hiddenSubsetRow(cell.row);
                }
                if (cell.pos / 9 == 8) {
                    nakedSubsetCol(cell.col);
                    hiddenSubsetCol(cell.col);
                }
            }
        }

        if (!change) {
            return grid;
        }
        passes++;
        return solve(grid);
    }

    public static void main(String[] args) {
//        initialise(puzzle);
//        initialise(automorphic);
        initialise(medium);
//        initialise(hardestPuzzle);
//        initialise(rowBlockTest);
//        initialise(blockBlockRowTest);
//        initialise(blockBlockColTest);
//        initialise(empty);

        printGrid();
        solve(grid);
//        backTrack(grid);
        printGrid();

        System.out.println(passes);
//        printAllCands(15, 16, 17);

    }

    private static int lastEmpty;

    private static boolean isSolved(Cell[][] grid) {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void backTrack(Cell[][] grid) {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans == 0) {
                    for (int cand = 0; cand < 9; cand++) {
                        if (!checkHouses(cell, cand)) {
                            cell.ans = cand + 1;
                            backTrack(grid);
                            if (grid[lastEmpty / 9][lastEmpty % 9].ans != 0) {
                                return;
                            }
//                            if (isSolved(grid)) {
//                                return;
//                            }
                            // if we want to find multiple solutions just add code here to add the solution to an arraylist
                            // don't return. and it should go on to find all solutions.
                        }
                    }
                    cell.ans = 0;
                    return;
                }
            }
        }
    }

}