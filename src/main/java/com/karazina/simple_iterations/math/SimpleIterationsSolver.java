package com.karazina.simple_iterations.math;

import com.karazina.simple_iterations.exception.SolverException;

import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * Метод простих ітерацій
 */
public class SimpleIterationsSolver {

    public static Solution getSolution(Matrix matrix, double delta) throws SolverException {

        if (delta < 0 || delta > 1) {
            throw new SolverException("Invalid accuracy!");
        }

        if (diagonalDominanceConditionMet(matrix)) {
            for (int i = 0; i < matrix.getAmountOfEquations(); i++) {
                double divisor = matrix.get(i, i);
                matrix.set(i, i, 0);
                for (int j = 0; j <= matrix.getAmountOfEquations(); j++) {
                    matrix.set(i, j, matrix.get(i, j) / divisor);
                }
            }
            double[][] coefficientMatrix = getCoefficientMatrix(matrix);
            double[] independentCoefficients = getIndependentCoefficients(matrix);
            double[] temp = new double[matrix.getAmountOfEquations()];
            double[] result = new double[matrix.getAmountOfEquations()];
            System.arraycopy(independentCoefficients, 0, result, 0, matrix.getAmountOfEquations());
            double currentAccuracy;
            long iterations = 0;
            do {
                System.arraycopy(result, 0, temp, 0, matrix.getAmountOfEquations());
                for (int i = 0; i < matrix.getAmountOfEquations(); i++) {
                    double sum = 0;
                    for (int j = 0; j < matrix.getAmountOfEquations(); j++) {
                        sum -= coefficientMatrix[i][j] * temp[j];
                    }
                    result[i] = independentCoefficients[i] + sum;
                }
                currentAccuracy = 0;
                for (int i = 0; i < matrix.getAmountOfEquations(); i++) {
                    currentAccuracy = max(currentAccuracy, abs(temp[i] - result[i]));
                }
                iterations++;
                System.out.println("Iteration " + iterations + "| " + Arrays.toString(result));
            } while (currentAccuracy > delta); //перевіряємо критерій закінчення ітераційного процесу
            double[] error = new double[matrix.getAmountOfEquations()];
            for (int i = 0; i < matrix.getAmountOfEquations(); i++) {
                error[i] = abs(temp[i] - result[i]);
            }
            return new Solution(result, iterations, error);
        } else {
            throw new SolverException("Can't solve.");
        }
    }

    private static boolean diagonalDominanceConditionMet(Matrix matrix) {
        int[] row = new int[matrix.getAmountOfEquations()];
        boolean[] flag = new boolean[matrix.getAmountOfEquations()];
        for (int i = 0; i < matrix.getAmountOfEquations(); i++) {
            double sum = 0;
            int max = 0;
            for (int j = 0; j < matrix.getAmountOfEquations(); j++) {
                if (abs(matrix.get(i, max)) < abs(matrix.get(i, j))) {
                    max = j;
                }
                sum += abs(matrix.get(i, j));
            }
            if (2 * abs(matrix.get(i, max)) > sum && !flag[max]) {
                flag[max] = true;
                row[max] = i;
            } else {
                return false;
            }
        }
        double[][] element = new double[matrix.getAmountOfEquations()][matrix.getAmountOfEquations() + 1];
        for (int i = 0; i < matrix.getAmountOfEquations(); i++) {
            for (int j = 0; j <= matrix.getAmountOfEquations(); j++) {
                element[i][j] = matrix.get(row[i], j);
            }
        }
        matrix.setMatrix(element);
        return true;
    }

    private static double[] getIndependentCoefficients(Matrix matrix) {
        double[] coefficients = new double[matrix.getAmountOfEquations()];
        for (int i = 0; i < matrix.getAmountOfEquations(); i++) {
            coefficients[i] = matrix.get(i, matrix.getAmountOfEquations());
        }
        return coefficients;
    }

    private static double[][] getCoefficientMatrix(Matrix matrix) {
        double[][] coefficients = new double[matrix.getAmountOfEquations()][matrix.getAmountOfEquations()];
        for (int i = 0; i < matrix.getAmountOfEquations(); i++) {
            for (int j = 0; j < matrix.getAmountOfEquations(); j++) {
                coefficients[i][j] = matrix.get(i, j);
            }
        }
        return coefficients;
    }

}
