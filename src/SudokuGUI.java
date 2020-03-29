import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SudokuGUI extends JFrame {

    public JTextField[][] uiCells = new JTextField[9][9];

    public JButton[][] cellButtons = new JButton[9][9];

    public JLayeredPane[][] cellPanes = new JLayeredPane[9][9];

    public JPanel[][] candPanes = new JPanel[9][9];

    public boolean[][] setByButton = new boolean[9][9];

    public void placePuzzle(int[] puzzle) {
        int pos = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (puzzle[pos] != 0) {
                    uiCells[row][col].setText(String.valueOf(puzzle[pos]));
                } else {
                    uiCells[row][col].setText("");
                }
                pos++;
            }
        }
    }

    public void clear() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                uiCells[row][col].setText("");
            }
        }
    }

    public void toggleAllCands() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if(!setByButton[row][col]) {
                    toggleCands(row, col);
                }
                else if(setByButton[row][col]) {
                    setByButton[row][col] = false;
                }
            }
        }
    }

    public void toggleCands(int row, int col) {
        if (candPanes[row][col].isVisible()) {
            candPanes[row][col].setVisible(false);
            cellButtons[row][col].setVisible(false);

        } else {
            candPanes[row][col].setVisible(true);
            cellButtons[row][col].setVisible(true);
        }
    }

    public void initialise() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int index = row * 9 + col;
                if (uiCells[row][col].getText().equals("")) {
                    Solver.grid[row][col] = new Cell(index, 0, row, col);
                } else {
                    int ans = Integer.parseInt(uiCells[row][col].getText());
                    Solver.grid[row][col] = new Cell(index, ans, row, col);
                }
            }
        }
    }

    public void solve() {
        Solver.solve(Solver.grid);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (Solver.grid[row][col].ans == 0) {
                    uiCells[row][col].setText("");
                } else {
                    String ans = Integer.toString(Solver.grid[row][col].ans);
                    uiCells[row][col].setText(ans);
                }
            }
        }
    }

    public void createCandPanel(int row, int col) {
        cellPanes[row][col] = new JLayeredPane();

        candPanes[row][col] = new JPanel(new GridLayout(3, 3));
        candPanes[row][col].setBorder(BorderFactory.createLineBorder(Color.GRAY));
        candPanes[row][col].setVisible(false);
        JButton[] candButtons = new JButton[9];
        for (int cand = 1; cand <= 9; cand++) {
            candButtons[cand - 1] = new JButton(String.valueOf(cand));
            candButtons[cand - 1].setFont(new Font("Tahoma", Font.PLAIN, 10));
            candButtons[cand - 1].setMargin(new Insets(0, 0, 0, 0));
            candPanes[row][col].add(candButtons[cand - 1]);
        }
        candPanes[row][col].setBounds(8, 10, 65, 65);
        cellPanes[row][col].add(candPanes[row][col], 1, 0);
    }

    public void createTextField(int row, int col) {
        uiCells[row][col] = new JTextField();
        uiCells[row][col].setHorizontalAlignment(SwingConstants.CENTER);
        uiCells[row][col].setFont(new Font("Tahoma", Font.PLAIN, 20));
        uiCells[row][col].setText(String.valueOf(row) + col);

        uiCells[row][col].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cellButtons[row][col].setVisible(true);
                super.mouseEntered(e);
            }
        });

        uiCells[row][col].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if(!candPanes[row][col].isVisible()) {
                    cellButtons[row][col].setVisible(false);
                }
                super.mouseExited(e);
            }
        });
        uiCells[row][col].setBounds(0, 0, 80, 80);
        cellPanes[row][col].add(uiCells[row][col], 0, 0);
    }

    public void createCandButton(int row, int col) {
        cellButtons[row][col] = new JButton();

        cellButtons[row][col].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cellButtons[row][col].setVisible(true);
                super.mouseEntered(e);
            }
        });

        cellButtons[row][col].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if(!candPanes[row][col].isVisible()) {
                    cellButtons[row][col].setVisible(false);
                }
                super.mouseExited(e);
            }
        });

        cellButtons[row][col].setVisible(false);
        cellButtons[row][col].setBounds(0, 0, 16, 10);

        setByButton[row][col] = false;

        cellButtons[row][col].addActionListener(e -> {
            setByButton[row][col] = !setByButton[row][col];
                    toggleCands(row, col);
                }
        );

        cellPanes[row][col].add(cellButtons[row][col], 2, 0);
    }

    public SudokuGUI() {

        setTitle("Sam Darling Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 830, 800);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                JPanel box = new JPanel(new GridLayout(3, 3));
                box.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                for (int boxCell = 0; boxCell < 9; boxCell++) {

                    int row = boxRow * 3 + (boxCell / 3);
                    int col = boxCol * 3 + (boxCell % 3);

                    createCandPanel(row, col);

                    createTextField(row, col);

                    createCandButton(row, col);

                    box.add(cellPanes[row][col]);
                }
                gridPanel.add(box);
            }
        }
        contentPane.add(gridPanel, BorderLayout.CENTER);

        JPanel westPanel = new JPanel();
        contentPane.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();
        contentPane.add(eastPanel, BorderLayout.EAST);
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        JButton btnSolve = new JButton("Solve");
        btnSolve.addActionListener(e -> {
                    initialise();
                    solve();
                }
        );
        eastPanel.add(btnSolve);

        JButton btnPuzzle = new JButton("Puzzle");
        btnPuzzle.addActionListener(e -> {
//                    placePuzzle(Solver.puzzle);
                    placePuzzle(Solver.automorphic);

                }
        );
        eastPanel.add(btnPuzzle);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
                    clear();
                }
        );
        eastPanel.add(btnClear);

        JButton btnShowAllCand = new JButton("Toggle all cands");
        btnShowAllCand.addActionListener(e -> {
                    toggleAllCands();
                }
        );
        eastPanel.add(btnShowAllCand);

    }
}