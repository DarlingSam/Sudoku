public class Sudoku {
    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//                    try {
//                        Solver solver = new Solver();
//                        HCPuzzles hcPuzzles = new HCPuzzles();
//                        Generator generator = new Generator();
//                        SudokuGUI frame = new SudokuGUI();
//                        frame.setVisible(true);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//        );

        Solver solver = new Solver();
        HCPuzzles hcPuzzles = new HCPuzzles();
        Generator generator = new Generator();

        Cell[][] grid = new Cell[9][9];

        generator.initialiseEmptyGrid(grid);
        solver.printGrid(grid);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
        generator.randomlyFill(grid);
        solver.printGrid(grid);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
        generator.removeValues(grid, 57);
        solver.printGrid(grid);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
        solver.backTrack(grid);
        solver.printGrid(grid);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||");


    }
}