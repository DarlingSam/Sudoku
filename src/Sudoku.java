import java.awt.*;

public class Sudoku {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                    try {
                        Solver solver = new Solver();
                        FileManager fileManager = new FileManager();
                        Generator generator = new Generator(fileManager, solver);
                        SudokuGUI frame = new SudokuGUI(solver, generator, fileManager);
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}