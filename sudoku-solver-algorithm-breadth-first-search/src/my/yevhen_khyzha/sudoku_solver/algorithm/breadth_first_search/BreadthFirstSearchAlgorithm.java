package my.yevhen_khyzha.sudoku_solver.algorithm.breadth_first_search;

import my.yevhen_khyzha.sudoku_solver.algorithm.api.Algorithm;
import my.yevhen_khyzha.sudoku_solver.algorithm.api.PuzzleStateObject;
import my.yevhen_khyzha.sudoku_solver.algorithm.api.annotation.AlgorithmName;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

@AlgorithmName(name = BreadthFirstSearchAlgorithm.NAME)
public class BreadthFirstSearchAlgorithm extends Algorithm {

    public static final String NAME = "BFS";

    @Override
    public String getAlgorithmName() {
        return NAME;
    }

    @Override
    public int[][] resolvePuzzle(int[][] sudokuPuzzle) {
        boolean puzzleSolved = false;
        int[][] clonedSudokuPuzzle = null;

        Queue<PuzzleStateObject> puzzleStateObjectsQueue = new LinkedList<>();

        // the first puzzleStateObjectsQueue member, have always be present in
        int[][] clonedFirstSudokuPuzzle = cloneSudokuPuzzle(sudokuPuzzle);

        int[] emptyFirstCell = findFirstEmptyCellCoordinates(clonedFirstSudokuPuzzle);

        Stack<Integer> possibleValuesForFirstEmptyCell = findPossibleValuesForCell(
                clonedFirstSudokuPuzzle,
                emptyFirstCell[0],
                emptyFirstCell[1]
        );

        puzzleStateObjectsQueue.add(
                new PuzzleStateObject(
                        clonedFirstSudokuPuzzle,
                        possibleValuesForFirstEmptyCell,
                        emptyFirstCell[0],
                        emptyFirstCell[1]
                )
        );

        while (!puzzleSolved) {
            // extract with removing element from puzzle state objects queue
            PuzzleStateObject puzzleStateObject = puzzleStateObjectsQueue.poll();

            if (puzzleStateObject == null) {
                break;
            }

            // get objects from pojo to work with
            int[][] puzzleFromQueue = puzzleStateObject.sudokuPuzzle();

            Stack<Integer> possibleValuesForCell = puzzleStateObject.possibleValues();

            int i = puzzleStateObject.ii();
            int j = puzzleStateObject.jj();

            // get first possible value and put it into cloned puzzle to coordinates
            for (int possibleValueForCell : possibleValuesForCell) {
                // clone puzzle to work with
                clonedSudokuPuzzle = cloneSudokuPuzzle(puzzleFromQueue);

                clonedSudokuPuzzle[i][j] = possibleValueForCell;

                // find first empty cell in puzzle
                int[] emptyCellArray = findFirstEmptyCellCoordinates(clonedSudokuPuzzle); // todo use method which returns next coordinates with which program worked before

                if (emptyCellArray != null) {
                    Stack<Integer> possibleValuesForEmptyCell = findPossibleValuesForCell(
                            clonedSudokuPuzzle,
                            emptyCellArray[0],
                            emptyCellArray[1]
                    );

                    if (!possibleValuesForEmptyCell.isEmpty()) {
                        puzzleStateObjectsQueue.add(
                                new PuzzleStateObject(
                                        clonedSudokuPuzzle,
                                        possibleValuesForEmptyCell,
                                        emptyCellArray[0],
                                        emptyCellArray[1]
                                )
                        );
                    }
                } else {
                    tryCount++;
                    puzzleSolved = isAlgorithmSolvedPuzzle(clonedSudokuPuzzle);
                    if (puzzleSolved) {
                        break;
                    }
                }
            }

            puzzleSolved = isAlgorithmSolvedPuzzle(clonedSudokuPuzzle);
        }

        System.out.println("Number of try: " + tryCount);

        return clonedSudokuPuzzle;
    }
}
