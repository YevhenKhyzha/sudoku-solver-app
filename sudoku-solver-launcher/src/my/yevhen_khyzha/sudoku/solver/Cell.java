package my.yevhen_khyzha.sudoku.solver;

public class Cell {

    private final int number;
    private final boolean isPredefined;

    Cell(int number, boolean isPredefined) {
        this.number = number;
        this.isPredefined = isPredefined;
    }

    public int getNumber() {
        return number;
    }

    public boolean isPredefined() {
        return isPredefined;
    }
}
