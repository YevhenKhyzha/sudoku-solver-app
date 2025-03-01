execute in sudoku-solver-service root location script to compile modules and create jars: 

javac -d sudoku-solver-algorithm-api sudoku-solver-algorithm-api/src/my/yevhen_khyzha/sudoku/solver/algorithm/api/*.java sudoku-solver-algorithm-api/src/my/yevhen_khyzha/sudoku/solver/algorithm/api/annotation/*.java sudoku-solver-algorithm-api/module-info.java & jar -cvf mods/sudoku-solver-algorithm-api.jar -C sudoku-solver-algorithm-api/ . & javac -p mods -d sudoku-solver-algorithm-unambiguous sudoku-solver-algorithm-unambiguous/src/my/yevhen_khyzha/sudoku/solver/algorithm/unambiguous/*.java sudoku-solver-algorithm-unambiguous/module-info.java & jar -cvf mods/sudoku-solver-algorithm-unambiguous.jar -C sudoku-solver-algorithm-unambiguous/ . & javac -p mods -d sudoku-solver-algorithm-depth-first-search sudoku-solver-algorithm-depth-first-search/src/my/yevhen_khyzha/sudoku/solver/algorithm/depth/first/search/*.java sudoku-solver-algorithm-depth-first-search/module-info.java & jar -cvf mods/sudoku-solver-algorithm-depth-first-search.jar -C sudoku-solver-algorithm-depth-first-search/ . & javac -p mods -d sudoku-solver-algorithm-breadth-first-search sudoku-solver-algorithm-breadth-first-search/src/my/yevhen_khyzha/sudoku/solver/algorithm/breadth/first/search/*.java sudoku-solver-algorithm-breadth-first-search/module-info.java & jar -cvf mods/sudoku-solver-algorithm-breadth-first-search.jar -C sudoku-solver-algorithm-breadth-first-search/ . & javac -p mods -d sudoku-solver-algorithm-resolver sudoku-solver-algorithm-resolver/src/my/yevhen_khyzha/sudoku/solver/algorithm/resolver/*.java sudoku-solver-algorithm-resolver/module-info.java & jar -cvf mods/sudoku-solver-algorithm-resolver.jar -C sudoku-solver-algorithm-resolver/ . & javac -p mods -d sudoku-solver-launcher sudoku-solver-launcher/src/my/yevhen_khyzha/sudoku/solver/*.java sudoku-solver-launcher/module-info.java & jar -cvf mods/sudoku-solver-launcher.jar -C sudoku-solver-launcher/ .

execute launch script with input values:

java -p mods -m sudoku.solver.launcher/my.yevhen_khyzha.sudoku.solver.SudokuSolverLauncher C:\\Users\\yevhe\\Documents\\sudoku\\sudoku_1.txt C:\\Users\\yevhe\\Documents\\sudokuout\\sudokuout.html DFS

Task:

+-------+-------+-------+
| 9     | 7 6   |     1 |
| 7 6 2 |       |   9   |
|       | 8   9 |     7 |
+-------+-------+-------+
|     3 |     7 | 4     |
| 6     |       |     2 |
|     5 | 2     | 1     |
+-------+-------+-------+
|       | 4   3 |       |
|       |       | 7 3 8 |
| 2     |   7 6 |     5 |
+-------+-------+-------+

+---------+---------+---------+
| 9  8  4 | 7  6  2 | 3  5  1 |
| 7  6  2 | 5  3  1 | 8  9  4 |
| 3  5  1 | 8  4  9 | 6  2  7 |
+---------+---------+---------+
| 1  2  3 | 6  5  7 | 4  8  9 |
| 6  4  9 | 3  1  8 | 5  7  2 |
| 8  7  5 | 2  9  4 | 1  6  3 |
+---------+---------+---------+
| 5  9  7 | 4  8  3 | 2  1  6 |
| 4  1  6 | 9  2  5 | 7  3  8 |
| 2  3  8 | 1  7  6 | 9  4  5 |
+---------+---------+---------+

Requirements Specification
You have to develop an application that can solve a Sudoku puzzle. The application should be an ordinary console Java application without any GUI. In other words, neither Swing, nor JEE Web container is to be used. The application should be started using the following command line:
java -jar sudoku-solver.jar -in <puzzle-file> -out <solution-file> -alg <ALGORITHM>
Obviously, the application JAR file must be a runnable fat JAR containing all dependencies. The input file (i.e. the file containing the puzzle to be solved) should be a simple ASCII file following the structure depicted by the following snippet:

+-------+-------+-------+
| 9     | 7 6   |     1 |
| 7 6 2 |       |   9   |
|       | 8   9 |     7 |
+-------+-------+-------+
|     3 |     7 | 4     |
| 6     |       |     2 |
|     5 | 2     | 1     |
+-------+-------+-------+
|       | 4   3 |       |
|       |       | 7 3 8 |
| 2     |   7 6 |     5 |
+-------+-------+-------+

The output file (i.e. the file containing the solution) should be an HTML file. The HTML should meet the following requirements:
It must distinguish predefined cells (i.e. the cells whose values were defined in the original puzzle) from those cells whose values were completed by the program. Predefined cells should use bold font, other cells should use normal font.
Pretty styling (colors, sophisticated table borders etc.) is nice to have, it is not required. In practical terms, do not spend time with nice styling if the other requirements have not been met yet.
Besides the Sudoku grid displaying the solution, output file has to provide additional information:
Number of undefined cells in the original puzzle.
The search algorithm used to find the solution.
Search outcome indicating whether the search has found a solution or not. As mentioned later in this document, the application has to support several search algorithms. Some search algorithms are not able to deal with ambiguity, so the outcome of a search can also be some sort of dead end (blind alley). In addition, it is also possible to specify a puzzle which does not have any solution, regardless of the algorithm used. Therefore, the search outcome has to distinguish the following cases: a) solution has been found; b) the algorithm used has not found any solution, but some other algorithm might be able to solve the puzzle; c) the original puzzle cannot be solved at all, regardless of the algorithm (the snippet in appendix A illustrates such assignment).
Duration of the search in milliseconds.
Number of cell values tried during the search. The brute force algorithms mentioned below will try many cell values, most likely much more than the number of undefined cells in the original assignment.
The application should have a template for the output HTML. The template should be part of the runnable JAR file.
The -alg command line argument specifies the algorithm to be used to solve the given puzzle.

The application has to be based on the Spring framework. Annotation-based auto-wiring has to be used. As already mentioned, the application must support several search algorithms. These algorithms are required:
breadth-first search (brute force algorithm based on queue)
depth-first search (brute force algorithm based on stack)
an algorithm searching for unambiguous candidate values for cells; an unambiguous candidate value is the only value applicable to a cell (see appendix B)
In addition, the architecture of the application must easily allow to add completely new search algorithms without any modification of the existing source code.
I do not expect you to implement unit tests for each and every class, this is not required. However, I expect you to implement automated tests that will test the entire search. These tests are expected to read the puzzle from a string, write the solution as plain ASCII to another string, and verify whether the actual solution is the expected solution. The expected solution is to be specified in the same ASCII format as the original puzzle (see appendix C). These tests are to be based on JUnit. The test suite has to cover all search algorithms as well as all possible search outcomes (solution found, both dead ends).
When designing the class/component responsible for reading of puzzles, keep in mind that you have to support both files and in-memory representations. The java.io package provides some abstractions that might help to address this requirement without code duplication. One of the benefits of such design is the fact that the above mentioned automated tests will also cover the class/component responsible for reading of puzzles.
Appendix A
The following snippet illustrates a puzzle that cannot be solved, regardless of the search algorithm. The cell in the upper left corner is empty (i.e. undefined), but no value is applicable to the cell as all values are already present in the first row, the first column, or the upper left region. Such an assignment should lead to search outcome “assignment dead end”.
+-------+-------+-------+
|       | 3 9   |     1 |
| 7 6 2 |       |       |
|   8   |       |       |
+-------+-------+-------+
|       |       |       |
| 6     |       |       |
|       |       |       |
+-------+-------+-------+
| 5     |       |       |
|       |       |       |
| 4     |       |       |
+-------+-------+-------+
Appendix B
The following snippet illustrates unambiguous candidate values. The value 4 is the only applicable value for the cell in the upper left corner, so it is the unambiguous candidate.
+-------+-------+-------+
|       | 3 9   |     1 |
| 7 6 2 |       |       |
|       |       |       |
+-------+-------+-------+
|       |       |       |
| 6     |       |       |
|       |       |       |
+-------+-------+-------+
| 5     |       |       |
|       |       |       |
| 8     |       |       |
+-------+-------+-------+
Appendix C
The following snippets illustrate an original puzzle as well as the expected solution for the original puzzle. These formats are to be used in the automated test cases.
