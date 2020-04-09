import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;

public class SudokuGUI extends JFrame {

    public Solver solver;
    public Generator generator;
    public FileManager fileManager;

    public JTextField[][] uiCells = new JTextField[9][9];
    public PlainDocument[][] doc = new PlainDocument[9][9];
    public JLayeredPane[][] cellPanes = new JLayeredPane[9][9];
    public JPanel[][] candPanes = new JPanel[9][9];
    public JButton[][][] candButtons = new JButton[9][9][9];

    public boolean showing = false;

    public SudokuGUI(Solver solver, Generator generator, FileManager fileManager) {
        this.solver = solver;
        this.generator = generator;
        this.fileManager = fileManager;

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

                    cellPanes[row][col] = new JLayeredPane();
                    cellPanes[row][col].setLayout(new OverlayLayout(cellPanes[row][col]));
                    cellPanes[row][col].setForeground(Color.WHITE);

                    createCandPanel(row, col);

                    createTextField(row, col);

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

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
                    try {
                        clear();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        eastPanel.add(btnClear);

        JButton btnEditCands = new JButton("Edit Candidates");
        btnEditCands.addActionListener(e -> {
                    toggleCandPanes();
                }
        );
        eastPanel.add(btnEditCands);

    }

    public void toggleCandPanes() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (showing) {
                    if (uiCells[row][col].getText().isEmpty()) {
                        uiCells[row][col].setVisible(true);
                        for (int cand = 0; cand < 9; cand++) {
                            candButtons[row][col][cand].setBorderPainted(false);
                        }
                    }
                } else {
                    if (uiCells[row][col].getText().isEmpty()) {
                        uiCells[row][col].setVisible(false);
                        for (int cand = 0; cand < 9; cand++) {
                            candButtons[row][col][cand].setBorderPainted(true);
                        }
                    }
                }
            }
        }
        showing = !showing;
    }

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

    public void clear() throws BadLocationException {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                doc[row][col].remove(0,1);
            }
        }
    }

    public void initialise() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int index = row * 9 + col;
                if (uiCells[row][col].getText().equals("")) {
                    solver.grid[row][col] = new Cell(index, 0, row, col);
                } else {
                    int ans = Integer.parseInt(uiCells[row][col].getText());
                    solver.grid[row][col] = new Cell(index, ans, row, col);
                }
            }
        }
    }

    public void solve() {
        solver.solve(solver.grid);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (solver.grid[row][col].ans == 0) {
                    uiCells[row][col].setText("");
                } else {
                    String ans = Integer.toString(solver.grid[row][col].ans);
                    uiCells[row][col].setText(ans);
                }
            }
        }
    }

    public void createCandPanel(int row, int col) {
        candPanes[row][col] = new JPanel(new GridLayout(3, 3));
        candPanes[row][col].setBorder(BorderFactory.createLineBorder(Color.GRAY));
        candPanes[row][col].setVisible(true);
        for (int cand = 0; cand < 9; cand++) {
            candButtons[row][col][cand] = new JButton(String.valueOf(cand + 1));
            candButtons[row][col][cand].setFont(new Font("Tahoma", Font.PLAIN, 10));
            candButtons[row][col][cand].setMargin(new Insets(0, 0, 0, 0));
            candButtons[row][col][cand].setBorderPainted(false);
            candButtons[row][col][cand].setFocusPainted(false);
            candButtons[row][col][cand].setContentAreaFilled(false);
            candPanes[row][col].add(candButtons[row][col][cand]);
        }
        candPanes[row][col].setBackground(Color.WHITE);
        candPanes[row][col].setBounds(0, 0, cellPanes[row][col].getWidth(), cellPanes[row][col].getHeight());
        cellPanes[row][col].add(candPanes[row][col], 0, 0);
    }

    public void createTextField(int row, int col) {
        uiCells[row][col] = new JTextField();
        doc[row][col] = (PlainDocument) uiCells[row][col].getDocument();
        doc[row][col].setDocumentFilter(new MyIntFilter(row, col));
        uiCells[row][col].setHorizontalAlignment(SwingConstants.CENTER);
        uiCells[row][col].setFont(new Font("Tahoma", Font.PLAIN, 20));
//        uiCells[row][col].setText(String.valueOf(row) + col);
        uiCells[row][col].setOpaque(false);
        uiCells[row][col].setBounds(0, 0, cellPanes[row][col].getWidth(), cellPanes[row][col].getHeight());
        cellPanes[row][col].add(uiCells[row][col], 1, 0);
    }

    public class MyIntFilter extends DocumentFilter {
        int row, col;

        MyIntFilter(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                                 AttributeSet attr) throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (test(sb.toString()) && doc.getLength() < 1) {
                super.insertString(fb, offset, string, attr);
                for (int cand = 0; cand < 9; cand++) {
                    candButtons[row][col][cand].setVisible(false);
                }
            }
        }

        private boolean test(String text) {
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (test(sb.toString()) && doc.getLength() < 1) {
                super.replace(fb, offset, length, text, attrs);
                for (int cand = 0; cand < 9; cand++) {
                    candButtons[row][col][cand].setVisible(false);
                }
            }
        }

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset);

            if (test(sb.toString())) {
                super.remove(fb, offset, length);
                for (int cand = 0; cand < 9; cand++) {
                    candButtons[row][col][cand].setVisible(true);
                    if(uiCells[row][col].isVisible() && showing) {
                        candButtons[row][col][cand].setBorderPainted(true);
                    }
                }
                if(uiCells[row][col].isVisible() && showing) {
                    uiCells[row][col].setVisible(false);
                }
            }
        }
    }
}