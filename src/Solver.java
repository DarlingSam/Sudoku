public class Solver {
    public static int row = 9, col = 9;

    public static Cell[][] grid = new Cell[row][col];

    private static boolean change;

    private static int passes = 0;

    private static int[] puzzle = {0, 6, 0, 3, 0, 0, 8, 0, 4,
            5, 3, 7, 0, 9, 0, 0, 0, 0,
            0, 4, 0, 0, 0, 6, 3, 0, 7,
            0, 9, 0, 0, 5, 1, 2, 3, 8,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            7, 1, 3, 6, 2, 0, 0, 4, 0,
            3, 0, 6, 4, 0, 0, 0, 1, 0,
            0, 0, 0, 0, 6, 0, 5, 2, 3,
            1, 0, 2, 0, 0, 9, 0, 8, 0};

    private static int[] rowBlockTest = {0, 0, 0, 0, 7, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 2, 0, 1, 0, 0, 0,
            5, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 9, 0, 6, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static int[] blockBlockRowTest = {0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 8, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,

            0, 0, 0, 0, 0, 0, 0, 0, 0,
            2, 9, 0, 0, 1, 4, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,

            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 8, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static int[] hardestPuzzle = {8, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 3, 6, 0, 0, 0, 0, 0,
            0, 7, 0, 0, 9, 0, 2, 0, 0,
            0, 5, 0, 0, 0, 7, 0, 0, 0,
            0, 0, 0, 0, 4, 5, 7, 0, 0,
            0, 0, 0, 1, 0, 0, 0, 3, 0,
            0, 0, 1, 0, 0, 0, 0, 6, 8,
            0, 0, 8, 5, 0, 0, 0, 1, 0,
            0, 9, 0, 0, 0, 0, 4, 0, 0};


    private static int[] blockBlockColTest = {0, 0, 0, 0, 2, 0, 0, 0, 0,
            0, 0, 0, 0, 9, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 8, 0,

            0, 8, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 4, 0, 0, 0, 0,

            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static int[] medium = {0, 4, 0, 0, 0, 2, 0, 1, 9,
            0, 0, 0, 3, 5, 1, 0, 8, 6,
            3, 1, 0, 0, 9, 4, 7, 0, 0,
            0, 9, 4, 0, 0, 0, 0, 0, 7,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            2, 0, 0, 0, 0, 0, 8, 9, 0,
            0, 0, 9, 5, 2, 0, 0, 4, 1,
            4, 2, 0, 1, 6, 9, 0, 0, 0,
            1, 6, 0, 8, 0, 0, 0, 7, 0};

    private static int[] automorphic = {0, 0, 0, 2, 1, 0, 0, 0, 0,
            0, 0, 7, 3, 0, 0, 0, 0, 0,
            0, 5, 8, 0, 0, 0, 0, 0, 0,
            4, 3, 0, 0, 0, 0, 0, 0, 0,
            2, 0, 0, 0, 0, 0, 0, 0, 8,
            0, 0, 0, 0, 0, 0, 0, 7, 6,
            0, 0, 0, 0, 0, 0, 2, 5, 0,
            0, 0, 0, 0, 0, 7, 3, 0, 0,
            0, 0, 0, 0, 9, 8, 0, 0, 0};

    public static void nakedSubset(Cell cell) {
        int candCount = 0;
        for(boolean cand : cell.candidates) {
            if(cand) {
                candCount++;
            }
        }

        if(candCount > 2) {
            return;
        }

        for(Cell[] cells : grid) {
            for(Cell cellC : cells) {

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
                        System.out.println("BlockBlockColInteraction removing " + (cand + 1) + " from " + cellC.pos);
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

                        removeForBlockBlockRowInteraction(2, cell.box / 3, boxRow[0], boxRow[1], cand, cell);

                    }
                } else if (countRowOccur[0] < 3 && countRowOccur[0] > 0 && countRowOccur[2] < 3 && countRowOccur[2] > 0) {
                    if (boxRow[0] == boxRow[4] && boxRow[1] == boxRow[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 1 and box 3 are the same so remove from box 2");

                        removeForBlockBlockRowInteraction(1, cell.box / 3, boxRow[0], boxRow[1], cand, cell);

                    }
                } else if (countRowOccur[1] < 3 && countRowOccur[1] > 0 && countRowOccur[2] < 3 && countRowOccur[2] > 0) {
                    if (boxRow[2] == boxRow[4] && boxRow[3] == boxRow[5]) {
//                        System.out.println(cell.pos + " " + (cand + 1));
//                        System.out.println("box 2 and box 3 are the same so remove from box 1");

                        removeForBlockBlockRowInteraction(0, cell.box / 3, boxRow[2], boxRow[3], cand, cell);

                    }
                }
            }
        }
    }

    public static void removeForBlockBlockRowInteraction(int box, int boxRow, int row1, int row2, int cand, Cell cell) {
        for (Cell[] cells : grid) {
            for (Cell cellC : cells) {
                if (cellC.ans == 0 && cellC.box % 3 == box && cellC.candidates[cand] && cellC.box / 3 == boxRow) {
                    if (cellC.row % 3 == row1 || cellC.row % 3 == row2) {
                        System.out.println("BlockBlockRowInteraction removing " + (cand + 1) + " from " + cellC.pos);
//                        System.out.println(cell.pos);
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
                    System.out.println("Row block due to cell " + cell.pos + " and sim Cell " + simCell.pos);
                    firstTime = false;
                }
                System.out.println("Removing " + (cand + 1) + " from " + cells.pos);
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
                    System.out.println("Col block due to cell " + cell.pos + " and sim Cell " + simCell.pos);
                    firstTime = false;
                    if (cell.pos == 49 && simCell.pos == 40) {
                        cell.printC();
                        simCell.printC();
                    }
                }
                System.out.println("Removing " + (cand + 1) + " from " + cells[cell.col].pos);
                cells[cell.col].candidates[cand] = false;
                change = true;
            }
        }
    }

    public static void soleCandidate(Cell cell) {
        removeCandInRow(cell);
        removeCandInCol(cell);
        removeCandInBox(cell);
    }

    public static void uniqueCandidate(Cell cell) {
        for (int cand = 0; cand < 9; cand++) {
            if (cell.candidates[cand]) {
                if (!checkForCandInRow(cell, cand) || !checkForCandInCol(cell, cand) || !checkForCandInBox(cell, cand)) {
                    cell.ans = (cand + 1);
                    System.out.println("UC Setting ans for cell " + cell.pos + " to " + (cand + 1));
                    cell.clearC();
                    change = true;
                }
            }
        }
    }

    public static boolean checkForCandInRow(Cell cell, int cand) {
        for (Cell cells : grid[cell.row]) {
            if (cells.pos != cell.pos && (cells.candidates[cand] || cells.ans == cand + 1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkForCandInCol(Cell cell, int cand) {
        for (Cell[] cells : grid) {
            if (cells[cell.col].pos != cell.pos && (cells[cell.col].candidates[cand] || cells[cell.col].ans == cand + 1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkForCandInBox(Cell cell, int cand) {
        for (Cell[] cells : grid) {
            for (Cell cell2 : cells) {
                if (cell2.pos != cell.pos && (cell2.candidates[cand] || cell2.ans == cand + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeCandInCol(Cell cell) {
        for (Cell[] cells : grid) {
            if (cells[cell.col].candidates[cell.ans - 1]) {
//                System.out.println("Removing from " + cells[cell.col].pos + " candidate " + cell.ans);
                cells[cell.col].candidates[cell.ans - 1] = false;
                change = true;
            }
        }
    }

    public static void removeCandInRow(Cell cell) {
        for (Cell cells : grid[cell.row]) {
            if (cells.candidates[cell.ans - 1]) {
//                System.out.println("Removing from " + cells.pos + " candidate " + cell.ans);
                cells.candidates[cell.ans - 1] = false;
                change = true;
            }
        }
    }

    public static void removeCandInBox(Cell cell) {
        for (Cell[] cells : grid) {
            for (Cell cell2 : cells) {
                if (cell2.box == cell.box && cell2.candidates[cell.ans - 1]) {
//                    System.out.println("Removing from " + cell2.pos + " candidate " + cell.ans);
                    cell2.candidates[cell.ans - 1] = false;
                    change = true;
                }
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
                if(cell.ans == 0) {
                    System.out.print("+");
                }
                else {
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
                grid[row][col] = new Cell(pos, puzzle[pos], row, col);
                pos++;
            }
        }
    }

    public static Cell[][] solve(Cell[][] grid) {
        change = false;
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.ans != 0) {
                    soleCandidate(cell);
                } else {
                    rowBlockInteraction(cell);
                    colBlockInteraction(cell);
                    blockBlockRowInteraction(cell);
                    blockBlockColInteraction(cell);
                    uniqueCandidate(cell);
                    cell.findAns();

                }
            }
        }

        if (!change) {
            return grid;
        }
        passes++;
        return solve(grid);
    }

    public static void printAllCands(int...indexes) {
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

    public static void main(String[] args) {
//        initialise(puzzle);
        initialise(automorphic);
//        initialise(medium);
//        initialise(hardestPuzzle);
//        initialise(rowBlockTest);
//        initialise(blockBlockRowTest);
//        initialise(blockBlockColTest);

        printGrid();
        solve(grid);
        printGrid();


        System.out.println(passes);
//        printAllCands();
//        grid[4][4].printC();
    }
}
