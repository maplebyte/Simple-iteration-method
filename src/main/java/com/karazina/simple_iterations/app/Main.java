package com.karazina.simple_iterations.app;

import com.karazina.simple_iterations.math.Matrix;
import com.karazina.simple_iterations.math.SimpleIterationsSolver;
import com.karazina.simple_iterations.math.Solution;

import java.nio.file.Path;

public class Main {

    private static Matrix matrix;
    private static double delta = 0.005;
    private static Path path = Path.of("12_var.txt");

    public static void main(String[] args) {
        matrix = Matrix.load(path);
        Solution solution = SimpleIterationsSolver.getSolution(matrix, delta);
        System.out.println(solution);
    }

}
