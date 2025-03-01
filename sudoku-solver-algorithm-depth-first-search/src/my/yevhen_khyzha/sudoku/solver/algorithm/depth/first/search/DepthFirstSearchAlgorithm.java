package my.yevhen_khyzha.sudoku.solver.algorithm.depth.first.search;

import my.yevhen_khyzha.sudoku.solver.algorithm.api.Algorithm;
import my.yevhen_khyzha.sudoku.solver.algorithm.api.PuzzleStateObject;
import my.yevhen_khyzha.sudoku.solver.algorithm.api.annotation.AlgorithmName;

import java.util.Stack;

@AlgorithmName(name = DepthFirstSearchAlgorithm.NAME)
public class DepthFirstSearchAlgorithm extends Algorithm {

    public static final String NAME = "DFS";

    @Override
    public String getAlgorithmName() {
        return NAME;
    }

    @Override
    public int[][] resolvePuzzle(int[][] sudokuPuzzle) {
        boolean puzzleSolved = false;
        int[][] clonedSudokuPuzzle = null;

        Stack<PuzzleStateObject> puzzleStateObjectsStack = new Stack<>();

        // the first puzzle state objects stack member, have always be present in
        int[][] clonedFirstSudokuPuzzle = cloneSudokuPuzzle(sudokuPuzzle);

        int[] emptyFirstCell = findFirstEmptyCellCoordinates(clonedFirstSudokuPuzzle);

        Stack<Integer> possibleValuesForFirstEmptyCell = findPossibleValuesForCell(
                clonedFirstSudokuPuzzle,
                emptyFirstCell[0],
                emptyFirstCell[1]
        );

        puzzleStateObjectsStack.push(
                new PuzzleStateObject(
                    clonedFirstSudokuPuzzle,
                    possibleValuesForFirstEmptyCell,
                    emptyFirstCell[0],
                    emptyFirstCell[1]
                )
        );

        while (!puzzleSolved) {
            // peek from puzzle state objects stack
            PuzzleStateObject puzzleStateObject = puzzleStateObjectsStack.peek();

            // get objects from pojo to work with
            int[][] puzzleFromStack = puzzleStateObject.getSudokuPuzzle();

            // possible values in cell position
            Stack<Integer> possibleValuesForCell = puzzleStateObject.getPossibleValues();

            // cell position
            int i = puzzleStateObject.getIi();
            int j = puzzleStateObject.getJj();

            // clone puzzle to work with
            clonedSudokuPuzzle = cloneSudokuPuzzle(puzzleFromStack);

            // get first possible value and put it into cloned puzzle to empty cell with i j coordinates
            if (!possibleValuesForCell.empty()) {
                int firstPossibleValueForCell = possibleValuesForCell.pop();
                clonedSudokuPuzzle[i][j] = firstPossibleValueForCell;
            } else {
                puzzleStateObjectsStack.pop();
                continue;
            }

            // find first empty cell in puzzle
            int[] emptyCellArray = findFirstEmptyCellCoordinates(clonedSudokuPuzzle); // todo use method which returns next coordinates with which program worked before

            if (emptyCellArray != null) {
                Stack<Integer> possibleValuesForEmptyCell = findPossibleValuesForCell(
                        clonedSudokuPuzzle,
                        emptyCellArray[0],
                        emptyCellArray[1]
                );

                if (!possibleValuesForEmptyCell.empty()) {
                    puzzleStateObjectsStack.push(
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
                } else {
                    puzzleStateObjectsStack.pop();
                }
            }

            puzzleSolved = isAlgorithmSolvedPuzzle(clonedSudokuPuzzle);
        }

        System.out.println("Number of try: " + tryCount);

        return clonedSudokuPuzzle;
    }
}
