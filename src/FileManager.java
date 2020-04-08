import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class FileManager {
    public void write(String difficulty, Cell[][] grid) {
        File file = new File("C:\\Users\\samda\\Desktop\\" + difficulty + ".txt");
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    bufferedWriter.write(Integer.toString(grid[row][col].ans));
                }
            }
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Random random = new Random();

    public Cell[][] readRandomPuzzle(String difficulty) {
        File file = new File("C:\\Users\\samda\\Desktop\\" + difficulty + ".txt");
        Cell[][] puzzle = new Cell[9][9];
        try {
            int lines = lineCount(difficulty);
            if (lines == 0) {
                System.out.println("This file is empty.");
            } else {
                int randomLine = random.nextInt(lines);
                String puzzleString = Files.readAllLines(Paths.get(String.valueOf(file))).get(randomLine);
                int pos = 0;
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        int number = puzzleString.charAt(pos) - '0';
                        puzzle[row][col] = new Cell(pos, number, row, col);
                        pos++;
                    }
                }
                return puzzle;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return puzzle;
    }

    public int lineCount(String difficulty) {
        int lines = 0;
        try {
            File file = new File("C:\\Users\\samda\\Desktop\\" + difficulty + ".txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            while (reader.readLine() != null) {
                lines++;
            }
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
