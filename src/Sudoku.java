import java.awt.*;

public class Sudoku {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                    try {
                        Solver solver = new Solver();
                        SudokuGUI frame = new SudokuGUI();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}