package my.yevhen_khyzha.sudoku_solver.launcher;

import java.io.*;

public class FileUtils {

    private final String inFilePath;
    private final String outFilePath;

    private static final int NON_READABLE_LINE_PERIOD = 4;
    private static final int NON_READABLE_CHARACTER_IN_LINE = 8;
    private static final int TRI = 3; // TODO: change name
    private static final String EMPTY_PLACE = " ";

    FileUtils(String inFilePath, String outFilePath) {
        this.inFilePath = inFilePath;
        this.outFilePath = outFilePath;
    }

    public int[][] fromASCIIFileToArray() {
        File inFile = getFileWithPuzzle(inFilePath);
        int[][] sudokuArray = new int[9][9];
        try {
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            int lineNumberInFile = 0;
            int lineInArray = 0;
            String line;
            while ((line = br.readLine()) != null) {
                int columnInArray = 0;
                if (lineInArray > 8) {
                    break;
                }
                if (lineNumberInFile == 0 || lineNumberInFile % NON_READABLE_LINE_PERIOD == 0) {
                    lineNumberInFile++;
                    continue;
                }
                // work with one line
                for (int numberPlaceInLine = 2; numberPlaceInLine <= 22; numberPlaceInLine = numberPlaceInLine + 2) {
                    if (numberPlaceInLine % NON_READABLE_CHARACTER_IN_LINE == 0) {
                        continue;
                    }
                    if (!EMPTY_PLACE.equals(String.valueOf(line.charAt(numberPlaceInLine)))) {
                        sudokuArray[lineInArray][columnInArray] = Integer
                                .parseInt(String.valueOf(line.charAt(numberPlaceInLine)));
                    } else {
                        sudokuArray[lineInArray][columnInArray] = 0;
                    }
                    columnInArray++;
                }
                lineInArray++;
                lineNumberInFile++;
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("File " + inFilePath + " is not found");
        }
        return sudokuArray;
    }

    private File getFileWithPuzzle(String path) {
        File file = null;
        try {
            file = new File(path);
        } catch (Exception e) {
            System.out.println("File with path : " + path + " not found! " + e.getMessage());
        }
        return file;
    }

    public void createOutPutFile(int[][] sudokuArray) {
        Cell[][] resolvedSudokuArray = putNumberArrayIntoCellArray(sudokuArray);
        try {
            File file = new File(outFilePath);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("<html>");
            bw.write("<body>");
            bw.write("<table>");
            for (int i = 0; i < 9; i++) {
//                if (i % TRI == 0) {
//                    bw.write("<tr><td>----------------</td></tr>");
//                }
                bw.write("<tr>");
                for (int j = 0; j < 9; j++) {
                    if (j == 0 || j % TRI == 0) {
                        bw.write("<td>|</td>");
                    }
                    bw.write("<td>"
                            + makeColorCell(
                                    resolvedSudokuArray[i][j].getNumber(), resolvedSudokuArray[i][j].isPredefined())
                            + "</td>");
                }
                bw.write("<td>|</td>");
                bw.write("</tr>");
            }
            bw.write("</table>");
            bw.write("</body>");
            bw.write("</html>");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Cell[][] putNumberArrayIntoCellArray(int[][] sudokuArray) {
        Cell[][] cells = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell(sudokuArray[i][j], sudokuArray[i][j] != 0);
            }
        }
        return cells;
    }

    private String makeColorCell(int number, boolean isPredefined) {
        return isPredefined
                ? "<font color = \"RED\">" + number + "</font>"
                : "<font color = \"BLUE\">" + number + "</font>";
    }
}
