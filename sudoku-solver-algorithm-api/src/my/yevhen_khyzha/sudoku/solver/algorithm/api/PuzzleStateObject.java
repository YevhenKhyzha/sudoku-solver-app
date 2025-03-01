package my.yevhen_khyzha.sudoku.solver.algorithm.api;

import java.util.Stack;

public class PuzzleStateObject {

    private final int[][] sudokuPuzzle;
    private final Stack<Integer> possibleValues;
    private final int ii;
    private final int jj;

    public PuzzleStateObject(int[][] sudokuPuzzle, Stack<Integer> possibleValues, int ii, int jj) {
        this.sudokuPuzzle = sudokuPuzzle;
        this.possibleValues = possibleValues;
        this.ii = ii;
        this.jj = jj;
    }

    public int[][] getSudokuPuzzle() {
        return sudokuPuzzle;
    }

    public Stack<Integer> getPossibleValues() {
        return possibleValues;
    }

    public int getIi() {
        return ii;
    }

    public int getJj() {
        return jj;
    }
}
