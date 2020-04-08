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
        FileManager fileManager = new FileManager();
        Generator generator = new Generator(fileManager);

        Cell[][] grid = new Cell[9][9];

        generator.generateSet(grid);
    }
}