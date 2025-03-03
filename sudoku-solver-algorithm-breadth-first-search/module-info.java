module sudoku.solver.algorithm.breadth.first.search {
    requires sudoku.solver.algorithm.api; // module name like in module name in file

    provides my.yevhen_khyzha.sudoku_solver.algorithm.api.Algorithm
            with my.yevhen_khyzha.sudoku_solver.algorithm.breadth_first_search.BreadthFirstSearchAlgorithm;
}