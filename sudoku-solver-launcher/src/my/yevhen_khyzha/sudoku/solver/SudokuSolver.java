package my.yevhen_khyzha.sudoku.solver;

import my.yevhen_khyzha.sudoku.solver.algorithm.api.Algorithm;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class SudokuSolver {

    SudokuSolver(String inFilePath, String outFilePath, Algorithm algorithm) {
        FileUtils fileUtils = new FileUtils(inFilePath, outFilePath);

        int[][] sudokuInArray = fileUtils.fromASCIIFileToArray();

        printArray(sudokuInArray);

        LocalDateTime localDateTimeStart = LocalDateTime.now();
        int[][] resolvedPuzzle = algorithm.resolvePuzzle(sudokuInArray);
        LocalDateTime localDateTimeEnd = LocalDateTime.now();

        System.out.println("Puzzle was " + printIsResolved(algorithm.isAlgorithmSolvedPuzzle(resolvedPuzzle))
                + " by " + algorithm.getAlgorithmName() + " algorithm");

        printTime(localDateTimeStart, localDateTimeEnd);
        printArray(resolvedPuzzle);

        fileUtils.createOutPutFile(resolvedPuzzle);
    }

    private void printArray(int[][] sudokuArray) {
        String line = "+-------+-------+-------+";
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.println(line);
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                if (sudokuArray[i][j] != 0) {
                    System.out.print(sudokuArray[i][j] + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println(line);
    }

    private void printTime(LocalDateTime dateBegin, LocalDateTime dateEnd) {
        long nanosecondsTime = dateEnd.getNano() - dateBegin.getNano();
        System.out.println("Resolving time in seconds: " + TimeUnit.NANOSECONDS.toSeconds(nanosecondsTime));
        System.out.println("Resolving time in nanoseconds: " + nanosecondsTime);
    }

    private String printIsResolved(boolean isResolved) {
        return isResolved ? "resolved" : "not resolved";
    }
}
