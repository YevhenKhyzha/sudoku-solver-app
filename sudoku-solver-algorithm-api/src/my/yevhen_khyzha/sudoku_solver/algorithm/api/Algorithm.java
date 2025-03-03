package my.yevhen_khyzha.sudoku_solver.algorithm.api;

import java.util.List;
import java.util.Stack;

public abstract class Algorithm {

    protected int tryCount = 0;
    protected int lapsCount = 0;
    protected int findingValuesCount = 0;

    public abstract String getAlgorithmName();

    public abstract int[][] resolvePuzzle(int[][] sudokuPuzzle);

    public int[] findFirstEmptyCellCoordinates(int[][] sudokuPuzzle) {
        for (int i = 0; i < sudokuPuzzle.length; i++) {
            for (int j = 0; j < sudokuPuzzle[i].length; j++) {
                if (sudokuPuzzle[i][j] == 0) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public int[] findFirstEmptyCellCoordinates(int[][] sudokuPuzzle, int i, int j) { // todo
        for (int ii = i; ii < sudokuPuzzle.length; ii++) {
            for (int jj = j; jj < sudokuPuzzle[ii].length; jj++) {
                if (sudokuPuzzle[ii][jj] == 0) {
                    return new int[]{ii,jj};
                }
            }
        }
        return null;
    }

    private boolean findInSquare(int searchingValue, int[][] sudokuPuzzle, int rowNumber, int columnNumber) {
        for (int i = 3 * (rowNumber / 3); i <= 3 * (rowNumber / 3) + 2; i++) {
            for (int j = 3 * (columnNumber / 3); j <= 3 * (columnNumber / 3) + 2; j++) {
                if (sudokuPuzzle[i][j] == searchingValue) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean findInRow(int searchingValue, int[][] sudokuPuzzle, int rowNumber) {
        for (int j = 0; j < sudokuPuzzle[rowNumber].length; j++) {
            if (sudokuPuzzle[rowNumber][j] == searchingValue) {
                return true;
            }
        }
        return false;
    }

    private boolean findInColumn(int searchingValue, int[][] sudokuPuzzle, int columnNumber) {
        for (int i = 0; i < sudokuPuzzle.length; i++) {
            if (sudokuPuzzle[i][columnNumber] == searchingValue) {
                return true;
            }
        }
        return false;
    }

    protected boolean findInAllPossiblePlaces(
            int searchingValue,
            int[][] sudokuPuzzle,
            int rowNumber,
            int columnNumber) {

        return findInRow(searchingValue, sudokuPuzzle, rowNumber)
                | findInColumn(searchingValue, sudokuPuzzle, columnNumber)
                | findInSquare(searchingValue, sudokuPuzzle, rowNumber, columnNumber);
    }

    protected boolean findUnambiguousForCell(int[][] sudokuPuzzle, int i, int j) {
        Stack<Integer> possibleValues = findPossibleValuesForCell(sudokuPuzzle, i, j);
        if (possibleValues.size() == 1) {
            findingValuesCount++;
            return true;
        }
        return false;
    }

    protected boolean findUnambiguousForCell(int[][] sudokuPuzzle, int i, int j, List<Integer> possibleValuesList) {
        List<Integer> possibleValues = findPossibleValuesForCell(sudokuPuzzle, i, j, possibleValuesList);
        if (possibleValues.size() == 1) {
            sudokuPuzzle[i][j] = possibleValues.get(0);
            possibleValues.clear();
            findingValuesCount++;
            return true;
        }
        return false;
    }

    protected List<Integer> findPossibleValuesForCell(
            int[][] sudokuPuzzle,
            int i,
            int j,
            List<Integer> possibleValuesList) {

        possibleValuesList.clear();
        for (int possibleValue = 1; possibleValue <= 9; possibleValue++) {
            tryCount++;
            if (!findInAllPossiblePlaces(possibleValue, sudokuPuzzle, i, j)) {
                possibleValuesList.add(possibleValue);
            }
        }
        return possibleValuesList;
    }

    protected Stack<Integer> findPossibleValuesForCell(int[][] sudokuPuzzle, int i, int j) {
        Stack<Integer> possibleValues = new Stack<>();
        for (int possibleValue = 1; possibleValue <= 9; possibleValue++) {
            tryCount++;
            if (!findInAllPossiblePlaces(possibleValue, sudokuPuzzle, i, j)) {
                possibleValues.push(possibleValue);
            }
        }
        return possibleValues;
    }

    // methods for check puzzle right resolving
    public boolean isAlgorithmSolvedPuzzle(int[][] sudokuArray) {
        for (int i = 0; i < sudokuArray.length; i++) {
            for (int j = 0; j < sudokuArray[i].length; j++) {
                if (sudokuArray[i][j] == 0 | checkInAllPossiblePlaces(sudokuArray[i][j], sudokuArray, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkInAllPossiblePlaces(int findingValue, int[][] sudokuArray, int i, int j) {
        return checkInRow(findingValue, sudokuArray, i, j)
                | checkInColumn(findingValue, sudokuArray, i, j)
                | checkInSquare(findingValue, sudokuArray, i, j);
    }

    private boolean checkInRow(int searchingValue, int[][] sudokuPuzzle, int rowNumber, int columnNumber) {
        for (int j = 0; j < sudokuPuzzle[rowNumber].length; j++) {
            if (sudokuPuzzle[rowNumber][j] == searchingValue && j != columnNumber) {
                return true;
            }
        }
        return false;
    }

    private boolean checkInColumn(int searchingValue, int[][] sudokuPuzzle, int rowNumber, int columnNumber) {
        for (int i = 0; i < sudokuPuzzle.length; i++) {
            if (sudokuPuzzle[i][columnNumber] == searchingValue && i != rowNumber) {
                return true;
            }
        }
        return false;
    }

    private boolean checkInSquare(int searchingValue, int[][] sudokuPuzzle, int rowNumber, int columnNumber) {
        for (int i = 3 * (rowNumber / 3); i <= 3 * (rowNumber / 3) + 2; i++) {
            for (int j = 3 * (columnNumber / 3); j <= 3 * (columnNumber / 3) + 2; j++) {
                if (sudokuPuzzle[i][j] == searchingValue && i != rowNumber && j != columnNumber) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isTheSameValuePresentInPossiblePlace_checkInAllPuzzle(int[][] sudokuPuzzle) {
        for (int i = 0; i < sudokuPuzzle.length; i++) {
            for (int j = 0; j < sudokuPuzzle[i].length; j++) {
                if (!isPresentInCell(sudokuPuzzle, i, j)) {
                    checkInAllPossiblePlaces(sudokuPuzzle[i][j], sudokuPuzzle, i, j);
                }
            }
        }
        return false;
    }

    protected boolean isPresentInCell(int[][] sudokuPuzzle, int i, int j) {
        return sudokuPuzzle[i][j] != 0;
    }

    protected int[][] cloneSudokuPuzzle(int[][] sudokuPuzzle) {
        int[][] clonedPuzzle = sudokuPuzzle.clone();
        for (int i = 0; i < clonedPuzzle.length; i++) {
            clonedPuzzle[i] = sudokuPuzzle[i].clone();
        }
        return clonedPuzzle;
    }
}
