package my.yevhen_khyzha.sudoku_solver.algorithm.api;

import java.util.Stack;

public record PuzzleStateObject(
        int[][] sudokuPuzzle,
        Stack<Integer> possibleValues,
        int ii,
        int jj
) { }
