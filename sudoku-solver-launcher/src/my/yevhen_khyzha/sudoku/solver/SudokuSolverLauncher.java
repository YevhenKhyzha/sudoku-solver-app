package my.yevhen_khyzha.sudoku.solver;

import my.yevhen_khyzha.sudoku.solver.algorithm.api.Algorithm;
import my.yevhen_khyzha.sudoku.solver.algorithm.resolver.AlgorithmResolver;

public class SudokuSolverLauncher {

    public static void main(String[] args) {
        ArgsValues argsValues = getValuesFromArgs(args);

        Algorithm algorithm = AlgorithmResolver.findAlgorithmBy(argsValues.getAlgorithm());
        assert algorithm != null;

        new SudokuSolver(argsValues.getInPath(), argsValues.getOutPath(), algorithm);
    }

    private static ArgsValues getValuesFromArgs(String[] args) {
        String inputFilePath = args[0];
        assert inputFilePath != null;

        String outputFilePath = args[1];
        assert outputFilePath != null;

        String algorithmString = args[2];
        assert algorithmString != null;

        return new ArgsValues(inputFilePath, outputFilePath, algorithmString);
    }
}
