package org.learn;

import java.util.ArrayList;
import java.util.List;

public class Exercise_2 {
    // 1. Робота з масивами
    public static class ArrayOperations {
        public static void main(String[] args) {
            int[] a = {82, 31, 99, 100, -28, 56, -16, 89, 40, 86, -39, 9, 7, 57, -51, -24, 49, 64, 62, -93, -25, -95, -6, -97, -55};

            // (1) Знаходить всi елементи масиву кратнi 11 та їхнi iндекси
            findMultiplesOf11(a);

            // (2) Знаходить найбiльший вiд’ємний елемент масиву
            findMaxNegativeElement(a);

            // (3) Мiняє мiсцями елементи a[7] i a[12]
            swapElements(a, 7, 12);
            System.out.println("Array after swapping a[7] and a[12]: " + arrayToString(a));

            // (4) Циклiчно зсуває елементи масиву на одну позицiю лiворуч
            leftShiftArray(a);
            System.out.println("Array after left shift: " + arrayToString(a));
        }

        // (1) Знаходить всi елементи масиву кратнi 11 та їхнi iндекси
        public static void findMultiplesOf11(int[] array) {
            List<Integer> multiples = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();

            for (int i = 0; i < array.length; i++) {
                if (array[i] % 11 == 0) {
                    multiples.add(array[i]);
                    indices.add(i);
                }
            }

            System.out.println("Elements multiples of 11: " + multiples);
            System.out.println("Indices of elements multiples of 11: " + indices);
        }

        // (2) Знаходить найбiльший вiд’ємний елемент масиву
        public static void findMaxNegativeElement(int[] array) {
            int maxNegative = Integer.MIN_VALUE;
            boolean found = false;

            for (int value : array) {
                if (value < 0 && value > maxNegative) {
                    maxNegative = value;
                    found = true;
                }
            }

            if (found) {
                System.out.println("Maximum negative element: " + maxNegative);
            } else {
                System.out.println("No negative elements found.");
            }
        }

        // (3) Мiняє мiсцями елементи a[7] i a[12]
        public static void swapElements(int[] array, int index1, int index2) {
            int temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }

        // (4) Циклiчно зсуває елементи масиву на одну позицiю лiворуч
        public static void leftShiftArray(int[] array) {
            int firstElement = array[0];
            System.arraycopy(array, 1, array, 0, array.length - 1);
            array[array.length - 1] = firstElement;
        }

        // Допоміжний метод для перетворення масиву в строку
        public static String arrayToString(int[] array) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                if (i < array.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    // 2. Робота з двовимiрними масивами
    public static class MatrixOperations {
        public static void main(String[] args) {
            int[][] a = {
                    {1, 3, -95, -19, 12},
                    {-9, -3, 0, -1, 7},
                    {3, -14, 6, 5, 2},
                    {19, 6, 312, 1, 2},
                    {12, 11, 4, -9, 35}
            };

            // (1) Cформувати i вивести матрицю елементiв двовимiрного масиву.
            printMatrix(a);

            // (2) Знайти максимальний елемент в кожному стовпчику матрицi.
            findMaxInColumns(a);

            // (3) Знайти мiнiмальний елемент в кожному рядку матрицi.
            findMinInRows(a);

            // (4) Знайти суму найменшого з максимальних елементiв по стовпчикам
            // та найбiльшого з мiнiмальних елементiв по рядкам.
            findSpecialSum(a);
        }

        // (1) Вивести матрицю
        public static void printMatrix(int[][] matrix) {
            System.out.println("Matrix:");
            for (int[] row : matrix) {
                for (int element : row) {
                    System.out.print(element + "\t");
                }
                System.out.println();
            }
        }

        // (2) Знайти максимальний елемент в кожному стовпчику
        public static void findMaxInColumns(int[][] matrix) {
            System.out.println("Max elements in each column:");
            for (int col = 0; col < matrix[0].length; col++) {
                int max = Integer.MIN_VALUE;
                for (int row = 0; row < matrix.length; row++) {
                    if (matrix[row][col] > max) {
                        max = matrix[row][col];
                    }
                }
                System.out.println("Column " + (col + 1) + ": " + max);
            }
        }

        // (3) Знайти мiнiмальний елемент в кожному рядку
        public static void findMinInRows(int[][] matrix) {
            System.out.println("Min elements in each row:");
            for (int row = 0; row < matrix.length; row++) {
                int min = Integer.MAX_VALUE;
                for (int col = 0; col < matrix[row].length; col++) {
                    if (matrix[row][col] < min) {
                        min = matrix[row][col];
                    }
                }
                System.out.println("Row " + (row + 1) + ": " + min);
            }
        }

        // (4) Знайти суму найменшого з максимальних елементiв по стовпчикам
        // та найбiльшого з мiнiмальних елементiв по рядкам
        public static void findSpecialSum(int[][] matrix) {
            int[] maxInColumns = new int[matrix[0].length];
            int[] minInRows = new int[matrix.length];

            // Знаходимо максимальні елементи у стовпцях
            for (int col = 0; col < matrix[0].length; col++) {
                int max = Integer.MIN_VALUE;
                for (int row = 0; row < matrix.length; row++) {
                    if (matrix[row][col] > max) {
                        max = matrix[row][col];
                    }
                }
                maxInColumns[col] = max;
            }

            // Знаходимо мінімальні елементи у рядках
            for (int row = 0; row < matrix.length; row++) {
                int min = Integer.MAX_VALUE;
                for (int col = 0; col < matrix[row].length; col++) {
                    if (matrix[row][col] < min) {
                        min = matrix[row][col];
                    }
                }
                minInRows[row] = min;
            }

            // Знаходимо найменший з максимальних елементів по стовпцях
            int minOfMaxInColumns = Integer.MAX_VALUE;
            for (int max : maxInColumns) {
                if (max < minOfMaxInColumns) {
                    minOfMaxInColumns = max;
                }
            }

            // Знаходимо найбільший з мінімальних елементів по рядках
            int maxOfMinInRows = Integer.MIN_VALUE;
            for (int min : minInRows) {
                if (min > maxOfMinInRows) {
                    maxOfMinInRows = min;
                }
            }

            // Обчислюємо суму
            int specialSum = minOfMaxInColumns + maxOfMinInRows;
            System.out.println("Sum of the smallest of the maximum elements in columns and the largest of the minimum elements in rows: " + specialSum);
        }
    }
}
