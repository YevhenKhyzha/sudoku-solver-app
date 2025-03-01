package my.yevhen_khyzha.sudoku.solver;

public class ArgsValues {

    private final String inPath;
    private final String outPath;
    private final String algorithm;

    ArgsValues(String inPath, String outPath, String algorithm) {
        this.inPath = inPath;
        this.outPath = outPath;
        this.algorithm = algorithm;
    }

    public String getInPath() {
        return inPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
