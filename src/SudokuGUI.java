import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;

public class SudokuGUI extends JFrame {

    public Solver solver;
    public Generator generator;
    public FileManager fileManager;

    Cell[][] grid = new Cell[9][9];

    JTextArea text = new JTextArea();

    public JTextField[][] uiCells = new JTextField[9][9];
    public PlainDocument[][] doc = new PlainDocument[9][9];
    public JLayeredPane[][] cellPanes = new JLayeredPane[9][9];
    public JPanel[][] candPanes = new JPanel[9][9];
    public JButton[][][] candButtons = new JButton[9][9][9];

    public Color myGreen = new Color(0, 130, 0);

    public boolean showing = false;

    public JComboBox<String> difficultySelect;

    public SudokuGUI(Solver solver, Generator generator, FileManager fileManager) {
        this.solver = solver;
        solver.setTextArea(text);
        this.generator = generator;
        this.fileManager = fileManager;



        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int pos = row * 9 + col;
                grid[row][col] = new Cell(pos, 0, row, col);
            }
        }

        setTitle("Sam Darling Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 670);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
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
        gridPanel.setBounds(15, 15, 600, 600);
        contentPane.add(gridPanel);

        JPanel eastPanel = new JPanel(null);
        eastPanel.setBounds(600, 0, 600, 600);
        contentPane.add(eastPanel);

        createSolveButton(eastPanel);
        createClearButton(eastPanel);
        createEditCandsButton(eastPanel);
        createDifficultySelect(eastPanel);
        createGenerateSet(eastPanel);
        createOutputScrollPane(eastPanel);

/*        JButton btnNextStep = new JButton("Next Step");
        btnNextStep.addActionListener(e -> {

                }
        );
        btnNextStep.setBounds(30, 120, 155, 20);
        eastPanel.add(btnNextStep);

        JButton btnHint = new JButton("Hint");
        btnHint.addActionListener(e -> {

                }
        );
        btnHint.setBounds(215, 120, 155, 20);
        eastPanel.add(btnHint);*/
    }

    public void toggleCandPanes() {
        /*Toggles whether the candidate panes buttons are clickable*/
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

    public void placePuzzle() {
        /*Places puzzle into the grid.*/
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col].ans != 0) {
                    uiCells[row][col].setText(String.valueOf(grid[row][col].ans));
                } else {
                    uiCells[row][col].setText("");
                }
            }
        }
    }

    public void clear() throws BadLocationException {
        /*Clears all values from the grid.*/
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col].ans = 0;
                doc[row][col].remove(0, 1);
            }
        }
    }

    public void initialise() {
        /*Initialises the cells from the UI grid into the Cell[][] grid.*/
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int pos = row * 9 + col;
                if (uiCells[row][col].getText().equals("")) {
                    grid[row][col] = new Cell(pos, 0, row, col);
                } else {
                    int ans = Integer.parseInt(uiCells[row][col].getText());
                    grid[row][col] = new Cell(pos, ans, row, col);
                }
            }
        }
    }

    public void solve() {
        /*If there's multiple solutions inform the user.*/
        if (generator.numberOfSolutions(grid) > 1) {
            text.append("Multiple solutions found. Here is one." + "\n");
        }

        /*Solve the puzzle.*/
        solver.solve(grid);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col].ans == 0) {
                    uiCells[row][col].setText("");
                } else {
                    String ans = Integer.toString(grid[row][col].ans);
                    uiCells[row][col].setText(ans);
                }
            }
        }
    }

    public void createSolveButton(JPanel eastPanel) {
        /*Creates the solve button.*/
        JButton btnSolve = new JButton("Solve");
        btnSolve.addActionListener(e -> {
                    text.setText(null);
                    initialise();
                    solve();
                }
        );
        btnSolve.setBounds(30, 15, 155, 20);
        eastPanel.add(btnSolve);
    }

    public void createClearButton(JPanel eastPanel) {
        /*Creates the clear button.*/
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
                    try {
                        text.setText(null);
                        clear();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        btnClear.setBounds(215, 15, 155, 20);
        eastPanel.add(btnClear);
    }

    public void createEditCandsButton(JPanel eastPanel) {
        /*Creates the edit cands button.*/
        JButton btnEditCands = new JButton("Candidates");
        btnEditCands.addActionListener(e -> {
                    toggleCandPanes();
                }
        );
        btnEditCands.setBounds(30, 50, 155, 20);
        eastPanel.add(btnEditCands);
    }

    public void createDifficultySelect(JPanel eastPanel) {
        /*Creates the drop down combo box for difficulty*/
        String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultySelect = new JComboBox<>(difficulties);
        difficultySelect.setBounds(215, 50, 155, 20);
        eastPanel.add(difficultySelect);
        /*Creates the button to get a random pre-generated puzzle.*/
        JButton btnGetRandom = new JButton("Random Puzzle");
        btnGetRandom.addActionListener(e -> {
                    try {
                        text.setText(null);
                        clear();
                        String selected = difficulties[difficultySelect.getSelectedIndex()];
                        grid = fileManager.readRandomPuzzle(selected);
                        placePuzzle();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        btnGetRandom.setBounds(215, 85, 155, 20);
        eastPanel.add(btnGetRandom);
    }

    public void createGenerateSet(JPanel eastPanel) {
        /*Generates a set of buttons for the whole range of difficulties.*/
        JButton btnGenSet = new JButton("Generate Set");
        btnGenSet.addActionListener(e -> {
                    generator.generateSet(100);
                }
        );
        btnGenSet.setBounds(30, 85, 155, 20);
        eastPanel.add(btnGenSet);
    }

    public void createOutputScrollPane(JPanel eastPanel) {
        /*Creates the output scroll pane.*/
        text.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(text);
        outputScroll.setBounds(30, 200, 540, 400);
        eastPanel.add(outputScroll);
    }

    public void createCandPanel(int row, int col) {
        /*Creates the candidate button panel for every cell in the grid.*/
        candPanes[row][col] = new JPanel(new GridLayout(3, 3));
        candPanes[row][col].setBorder(BorderFactory.createLineBorder(Color.GRAY));
        candPanes[row][col].setVisible(true);
        for (int cand = 0; cand < 9; cand++) {
            candButtons[row][col][cand] = new JButton(String.valueOf(cand + 1));
            candButtons[row][col][cand].setFont(new Font("Tahoma", Font.PLAIN, 10));
            candButtons[row][col][cand].setMargin(new Insets(0, 0, 0, 0));
            candButtons[row][col][cand].setBorderPainted(false);
            candButtons[row][col][cand].setFocusPainted(false);
            candButtons[row][col][cand].setBackground(Color.WHITE);

            int finalCand = cand;
            candButtons[row][col][cand].addActionListener(e -> {
                if (candButtons[row][col][finalCand].getBackground().equals(myGreen)) {
                    candButtons[row][col][finalCand].setBackground(Color.RED);
                } else if (candButtons[row][col][finalCand].getBackground() == Color.RED) {
                    candButtons[row][col][finalCand].setBackground(Color.WHITE);
                } else {
                    candButtons[row][col][finalCand].setBackground(myGreen);
                }
            });


            candPanes[row][col].add(candButtons[row][col][cand]);
        }
        candPanes[row][col].setBackground(Color.WHITE);
        candPanes[row][col].setBounds(0, 0, cellPanes[row][col].getWidth(), cellPanes[row][col].getHeight());
        cellPanes[row][col].add(candPanes[row][col], 0, 0);
    }

    public void createTextField(int row, int col) {
        /*Creates the text field for each cell in the grid.*/
        uiCells[row][col] = new JTextField();
        doc[row][col] = (PlainDocument) uiCells[row][col].getDocument();
        doc[row][col].setDocumentFilter(new MyIntFilter(row, col));
        uiCells[row][col].setHorizontalAlignment(SwingConstants.CENTER);
        uiCells[row][col].setFont(new Font("Tahoma", Font.PLAIN, 20));
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
                    if (uiCells[row][col].isVisible() && showing) {
                        candButtons[row][col][cand].setBorderPainted(true);
                    }
                }
                if (uiCells[row][col].isVisible() && showing) {
                    uiCells[row][col].setVisible(false);
                }
            }
        }
    }
}