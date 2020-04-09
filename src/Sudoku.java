import java.awt.*;

public class Sudoku {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                    try {
                        Cell[][] grid = new Cell[9][9];
                        Solver solver = new Solver();
                        HCPuzzles hcPuzzles = new HCPuzzles();
                        FileManager fileManager = new FileManager();
                        Generator generator = new Generator(fileManager);
                        SudokuGUI frame = new SudokuGUI(solver, generator, fileManager);


//                        generator.generateSet(grid);
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}