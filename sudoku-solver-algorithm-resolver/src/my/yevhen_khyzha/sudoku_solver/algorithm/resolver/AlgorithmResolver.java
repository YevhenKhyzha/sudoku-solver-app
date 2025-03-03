package my.yevhen_khyzha.sudoku_solver.algorithm.resolver;

import my.yevhen_khyzha.sudoku_solver.algorithm.api.Algorithm;
import my.yevhen_khyzha.sudoku_solver.algorithm.api.annotation.AlgorithmName;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class AlgorithmResolver {

    public static List<Algorithm> findAllAlgorithms() {
        List<Algorithm> algorithms = new ArrayList<>();
        ServiceLoader<Algorithm> loader = ServiceLoader.load(Algorithm.class);
        for (Algorithm algorithm : loader)
            algorithms.add(algorithm);
        return algorithms;
    }

    public static Algorithm findAlgorithmBy(String algorithmName) {
        assert algorithmName != null;
        ServiceLoader<Algorithm> loader = ServiceLoader.load(Algorithm.class);
        for (Algorithm algorithm : loader) {
            if (algorithm.getClass().isAnnotationPresent(AlgorithmName.class)) {
                AlgorithmName annotation = algorithm.getClass().getAnnotation(AlgorithmName.class);
                if (algorithmName.equals(annotation.name())) {
                    return algorithm;
                }
            }
        }
        return null;
    }
}
