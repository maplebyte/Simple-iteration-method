package com.karazina.simple_iterations.math;


import com.karazina.simple_iterations.exception.SolverException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Scanner;

public class Matrix {
    private final int amountOfEquations;
    private final double[][] matrix;

    /**
     * @param matrix Коефіцієнти матриці
     * @param amountOfEquations Кількість рівнянь
     */
    private Matrix(double[][] matrix, int amountOfEquations) {
        this.matrix = matrix;
        this.amountOfEquations = amountOfEquations;
    }

    public static Matrix load(Path path) {
        try (InputStream is = new FileInputStream(path.toFile()); Scanner scanner = new Scanner(is)){
            int dimension = scanner.nextInt();
            double[][] data = new double[dimension][dimension + 1];
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j <= dimension; j++) {
                    data[i][j] = scanner.nextDouble();
                }
            }
            return new Matrix(data, dimension);
        } catch (IOException e) {
            throw new SolverException("Unable to load matrix!", e);
        }
    }

    public int getAmountOfEquations() {
        return amountOfEquations;
    }

    public double get(int x, int y) {
        if (x >= amountOfEquations || x < 0 || y > amountOfEquations || y < 0) {
            throw new SolverException("Wrong bounds: {x: " + x + ", y: " + y + "}");
        }
        return matrix[x][y];
    }

    public void set(int x, int y, double val) {
        if (x >= amountOfEquations || x < 0 || y > amountOfEquations || y < 0) {
            throw new SolverException("Invalid bounds: {x: " + x + ", y: " + y + "}");
        }
        matrix[x][y] = val;
    }

    public void setMatrix(double[][] matrix) {
        if (matrix.length != amountOfEquations || matrix[0].length != amountOfEquations + 1) {
            throw new SolverException("Invalid data.");
        }
        for (int i = 0; i < amountOfEquations; i++) {
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, amountOfEquations + 1);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amountOfEquations; i++) {
            for (int j = 0; j <= amountOfEquations; j++) {
                sb.append(matrix[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
