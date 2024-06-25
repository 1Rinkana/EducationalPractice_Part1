package org.learn;

import java.util.Arrays;

public class Exercise_1 {
    public static class Part_1 { // Для вирішення кубічного рівняння можна використати метод Ньютона-Рафсона
        // Вихідна функція f(x) = x^3 - 2.75x^2 + 5.5x - 3
        public static double f(double x) {
            return Math.pow(x, 3) - 2.75 * Math.pow(x, 2) + 5.5 * x - 3;
        }

        // Похідна функції f'(x) = 3x^2 - 5.5x + 5.5
        public static double fPrime(double x) {
            return 3 * Math.pow(x, 2) - 5.5 * x + 5.5;
        }

        // Метод Ньютона-Рафсона
        public static double newtonRaphson(double initialGuess, double tolerance, int maxIterations) {
            double x = initialGuess;
            for (int i = 0; i < maxIterations; i++) {
                double fx = f(x);
                double fpx = fPrime(x);

                // Якщо похідна дорівнює нулю, метод не працює
                if (fpx == 0) {
                    throw new ArithmeticException("Поділ на нуль при обчисленні дотичної");
                }

                // Нове значення x
                double x1 = x - fx / fpx;

                // Якщо різниця між новими та старими значеннями менша від заданого допустимого значення, повертаємо результат
                if (Math.abs(x1 - x) < tolerance) {
                    return x1;
                }

                x = x1;
            }

            throw new ArithmeticException("Метод Ньютона-Рафсона не зійшовся за задану кількість ітерацій");
        }

        public static void main(String[] args) {
            double initialGuess = 1.0;  // Початкове наближення
            double tolerance = 1e-7;    // Припустима похибка
            int maxIterations = 1000;   // Максимальна кількість ітерацій

            try {
                double root = newtonRaphson(initialGuess, tolerance, maxIterations);
                System.out.println("Знайдений корінь: " + root);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static class Part_2 {
        // 1. Матричний метод
        public static class MatrixMethod {
            public static void main(String[] args) {
                double[][] A = {
                        {19, 5, -1},
                        {2, -17, -1},
                        {3, -2, 14}
                };
                double[] B = {7, 4, 11};

                double[] solution = solveByMatrixMethod(A, B);
                System.out.println("Рішення матричним методом: " + Arrays.toString(solution));
            }

            public static double[] solveByMatrixMethod(double[][] A, double[] B) {
                int n = A.length;
                double[][] inverseA = invertMatrix(A);
                double[] result = new double[n];

                for (int i = 0; i < n; i++) {
                    result[i] = 0;
                    for (int j = 0; j < n; j++) {
                        result[i] += inverseA[i][j] * B[j];
                    }
                }
                return result;
            }

            public static double[][] invertMatrix(double[][] A) {
                int n = A.length;
                double[][] augmentedMatrix = new double[n][2 * n];

                // Створення доповненої матриці [A|I]
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        augmentedMatrix[i][j] = A[i][j];
                    }
                    augmentedMatrix[i][i + n] = 1;
                }

                // Виконати елімінацію Гауса
                for (int i = 0; i < n; i++) {
                    // Змусити діагональ містити всі одиниці
                    double diagElement = augmentedMatrix[i][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix[i][j] /= diagElement;
                    }

                    // Змусити інші рядки містити 0
                    for (int k = 0; k < n; k++) {
                        if (k != i) {
                            double factor = augmentedMatrix[k][i];
                            for (int j = 0; j < 2 * n; j++) {
                                augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                            }
                        }
                    }
                }

                // Вилучення оберненої матриці з доповненої матриці
                double[][] inverse = new double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        inverse[i][j] = augmentedMatrix[i][j + n];
                    }
                }
                return inverse;
            }
        }

        // 2. Метод Крамера
        public static class CramersRule {
            public static void main(String[] args) {
                double[][] A = {
                        {19, 5, -1},
                        {2, -17, -1},
                        {3, -2, 14}
                };
                double[] B = {7, 4, 11};

                double[] solution = solveByCramersRule(A, B);
                System.out.println("Рішення методом Крамера: " + Arrays.toString(solution));
            }

            public static double[] solveByCramersRule(double[][] A, double[] B) {
                int n = A.length;
                double[] solution = new double[n];

                double detA = determinant(A);
                for (int i = 0; i < n; i++) {
                    double[][] Ai = replaceColumn(A, B, i);
                    solution[i] = determinant(Ai) / detA;
                }

                return solution;
            }

            public static double determinant(double[][] matrix) {
                int n = matrix.length;
                if (n == 1) {
                    return matrix[0][0];
                }
                double det = 0;
                for (int k = 0; k < n; k++) {
                    double[][] subMatrix = new double[n - 1][n - 1];
                    for (int i = 1; i < n; i++) {
                        for (int j = 0, col = 0; j < n; j++) {
                            if (j == k) continue;
                            subMatrix[i - 1][col++] = matrix[i][j];
                        }
                    }
                    det += Math.pow(-1, k) * matrix[0][k] * determinant(subMatrix);
                }
                return det;
            }

            public static double[][] replaceColumn(double[][] matrix, double[] column, int colIndex) {
                int n = matrix.length;
                double[][] newMatrix = new double[n][n];

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        newMatrix[i][j] = (j == colIndex) ? column[i] : matrix[i][j];
                    }
                }

                return newMatrix;
            }
        }

        // 3. Метод Гауса
        public static class GaussMethod {
            public static void main(String[] args) {
                double[][] A = {
                        {19, 5, -1},
                        {2, -17, -1},
                        {3, -2, 14}
                };
                double[] B = {7, 4, 11};

                double[] solution = solveByGauss(A, B);
                System.out.println("Рішення методом Гауса: " + Arrays.toString(solution));
            }

            public static double[] solveByGauss(double[][] A, double[] B) {
                int n = A.length;
                double[][] augmentedMatrix = new double[n][n + 1];

                // Створюємо розширену матрицю [A|B]
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        augmentedMatrix[i][j] = A[i][j];
                    }
                    augmentedMatrix[i][n] = B[i];
                }

                // Прямий хід методу Гауса
                for (int i = 0; i < n; i++) {
                    // Пошук найбільшого елемента для стійкості
                    int maxRow = i;
                    for (int k = i + 1; k < n; k++) {
                        if (Math.abs(augmentedMatrix[k][i]) > Math.abs(augmentedMatrix[maxRow][i])) {
                            maxRow = k;
                        }
                    }

                    // Перестановка рядків
                    double[] temp = augmentedMatrix[i];
                    augmentedMatrix[i] = augmentedMatrix[maxRow];
                    augmentedMatrix[maxRow] = temp;

                    // Приведення до трикутного вигляду
                    for (int k = i + 1; k < n; k++) {
                        double factor = augmentedMatrix[k][i] / augmentedMatrix[i][i];
                        for (int j = i; j < n + 1; j++) {
                            augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                        }
                    }
                }

                // Зворотний хід методу Гауса
                double[] solution = new double[n];
                for (int i = n - 1; i >= 0; i--) {
                    solution[i] = augmentedMatrix[i][n] / augmentedMatrix[i][i];
                    for (int k = i - 1; k >= 0; k--) {
                        augmentedMatrix[k][n] -= augmentedMatrix[k][i] * solution[i];
                    }
                }

                return solution;
            }
        }

        //4. Блокова структура Given..Find
        public class GivenFind {
            public static void main(String[] args) {
                double[][] A = {
                        {19, 5, -1},
                        {2, -17, -1},
                        {3, -2, 14}
                };
                double[] B = {7, 4, 11};

                // Given
                double[][] givenMatrix = A;
                double[] givenVector = B;

                // Find
                double[] solution = solveLinearSystem(givenMatrix, givenVector);
                System.out.println("Рішеня блоковою структурою Given..Find: " + Arrays.toString(solution));
            }

            public static double[] solveLinearSystem(double[][] A, double[] B) {
                return solveByGauss(A, B); // Використовуємо метод Гауса для розв'язання
            }

            public static double[] solveByGauss(double[][] A, double[] B) {
                int n = A.length;
                double[][] augmentedMatrix = new double[n][n + 1];

                // Створюємо розширену матрицю [A|B]
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        augmentedMatrix[i][j] = A[i][j];
                    }
                    augmentedMatrix[i][n] = B[i];
                }

                // Прямий хід методу Гауса
                for (int i = 0; i < n; i++) {
                    // Пошук найбільшого елемента для стійкості
                    int maxRow = i;
                    for (int k = i + 1; k < n; k++) {
                        if (Math.abs(augmentedMatrix[k][i]) > Math.abs(augmentedMatrix[maxRow][i])) {
                            maxRow = k;
                        }
                    }

                    // Перестановка рядків
                    double[] temp = augmentedMatrix[i];
                    augmentedMatrix[i] = augmentedMatrix[maxRow];
                    augmentedMatrix[maxRow] = temp;

                    // Приведення до трикутного вигляду
                    for (int k = i + 1; k < n; k++) {
                        double factor = augmentedMatrix[k][i] / augmentedMatrix[i][i];
                        for (int j = i; j < n + 1; j++) {
                            augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                        }
                    }
                }

                // Зворотний хід методу Гауса
                double[] solution = new double[n];
                for (int i = n - 1; i >= 0; i--) {
                    solution[i] = augmentedMatrix[i][n] / augmentedMatrix[i][i];
                    for (int k = i - 1; k >= 0; k--) {
                        augmentedMatrix[k][n] -= augmentedMatrix[k][i] * solution[i];
                    }
                }

                return solution;
            }
        }
    }
}
