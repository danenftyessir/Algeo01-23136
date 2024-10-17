import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeterminantCalculator {

    // Metode untuk menghitung determinan dengan default metode reduksi baris
    public static double calculateDeterminant(double[][] matrix) {
        return calculateDeterminant(matrix, "row reduction");
    }

    // Metode untuk menghitung determinan dengan pilihan metode
    public static double calculateDeterminant(double[][] matrix, String method) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matriks tidak boleh null atau kosong.");
        }
        if (!isSquareMatrix(matrix)) {
            throw new IllegalArgumentException("Matriks harus persegi untuk menghitung determinan.");
        }

        if (method.equalsIgnoreCase("cofactor")) {
            return calculateDeterminantByCofactorExpansion(matrix);
        } else if (method.equalsIgnoreCase("row reduction")) {
            return calculateDeterminantByRowReduction(matrix);
        } else {
            throw new IllegalArgumentException("Metode tidak dikenal. Gunakan 'cofactor' atau 'row reduction'.");
        }
    }

    // Metode untuk mengecek apakah matriks persegi
    private static boolean isSquareMatrix(double[][] matrix) {
        int i = 0;
        while (i < matrix.length) {
            if (matrix[i].length != matrix.length) {
                return false;
            }
            i = i + 1;
        }
        return true;
    }

    // Metode Ekspansi Kofaktor tanpa fungsi tambahan
    private static double calculateDeterminantByCofactorExpansion(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        double det = 0;
        double sign = 1; // Inisialisasi tanda
        int j = 0;
        while (j < n) {
            double[][] minor = getMinor(matrix, 0, j);
            double cofactor = sign * matrix[0][j] * calculateDeterminantByCofactorExpansion(minor);
            det = det + cofactor;
            sign = 0 - sign; // Mengubah tanda untuk iterasi berikutnya
            j = j + 1;
        }
        return det;
    }

    private static double[][] getMinor(double[][] matrix, int row, int col) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];
        int r = 0;
        int i = 0;
        while (i < n) {
            if (i != row) {
                int c = 0;
                int j = 0;
                while (j < n) {
                    if (j != col) {
                        minor[r][c] = matrix[i][j];
                        c = c + 1;
                    }
                    j = j + 1;
                }
                r = r + 1;
            }
            i = i + 1;
        }
        return minor;
    }

    // Metode Reduksi Baris
    private static double calculateDeterminantByRowReduction(double[][] matrix) {
        double[][] temp = copyMatrix(matrix);
        double det = 1;
        int swaps = 0;
        int i = 0;
        while (i < temp.length) {
            int maxRow = findMaxPivotRow(temp, i);
            if (maxRow != i) {
                swapRows(temp, i, maxRow);
                swaps = swaps + 1;
            }
            if (temp[i][i] == 0) {
                return 0; // Matriks singular
            }
            int j = i + 1;
            while (j < temp.length) {
                double factor = temp[j][i] / temp[i][i];
                int k = i;
                while (k < temp.length) {
                    temp[j][k] = temp[j][k] - factor * temp[i][k];
                    k = k + 1;
                }
                j = j + 1;
            }
            i = i + 1;
        }

        i = 0;
        while (i < temp.length) {
            det = det * temp[i][i];
            i = i + 1;
        }

        if (swaps % 2 == 0) {
            return det;
        } else {
            return 0 - det;
        }
    }

    private static int findMaxPivotRow(double[][] matrix, int col) {
        int maxRow = col;
        int i = col + 1;
        while (i < matrix.length) {
            if (absoluteValue(matrix[i][col]) > absoluteValue(matrix[maxRow][col])) {
                maxRow = i;
            }
            i = i + 1;
        }
        return maxRow;
    }

    private static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private static double[][] copyMatrix(double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        double[][] copy = new double[n][m];
        int i = 0;
        while (i < n) {
            int j = 0;
            while (j < m) {
                copy[i][j] = matrix[i][j];
                j = j + 1;
            }
            i = i + 1;
        }
        return copy;
    }

    // Fungsi untuk menghitung nilai absolut
    private static double absoluteValue(double value) {
        if (value < 0) {
            return 0 - value;
        } else {
            return value;
        }
    }

    // Metode untuk matriks int (memanggil versi double)
    public static double calculateDeterminant(int[][] matrix) {
        return calculateDeterminant(convertToDoubleMatrix(matrix));
    }
    public static double calculateDeterminant(int[][] matrix, String method) {
        return calculateDeterminant(convertToDoubleMatrix(matrix), method);
    }
    private static double[][] convertToDoubleMatrix(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        double[][] doubleMatrix = new double[n][m];
        int i = 0;
        while (i < n) {
            int j = 0;
            while (j < m) {
                doubleMatrix[i][j] = matrix[i][j];
                j = j + 1;
            }
            i = i + 1;
        }
        return doubleMatrix;
    }

    // Metode Input dari Keyboard (menggunakan input per baris, dipisahkan oleh spasi)
    public static double[][] readMatrixFromKeyboard(Scanner scanner) {
        System.out.print("Masukkan ukuran matriks (n x n): ");
        int n = Integer.parseInt(scanner.nextLine());
        double[][] matrix = new double[n][n];

        System.out.println("Masukkan elemen-elemen matriks:");
        int i = 0;
        while (i < n) {
            System.out.print("Baris " + (i + 1) + " (masukkan " + n + " angka, dipisahkan oleh spasi): ");
            String[] rowValues = scanner.nextLine().trim().split("\\s+");
            if (rowValues.length != n) {
                System.out.println("Jumlah elemen tidak sesuai. Harus " + n + " elemen per baris.");
                i = i - 1; // Mengulangi iterasi untuk baris ini
            } else {
                int j = 0;
                boolean validRow = true;
                while (j < n) {
                    try {
                        matrix[i][j] = Double.parseDouble(rowValues[j]);
                    } catch (NumberFormatException e) {
                        System.out.println("Input tidak valid. Harap masukkan angka.");
                        validRow = false;
                        break;
                    }
                    j = j + 1;
                }
                if (!validRow) {
                    i = i - 1; // Mengulangi iterasi untuk baris ini
                }
            }
            i = i + 1;
        }
        return matrix;
    }

    // Metode Input dari File
    public static double[][] readMatrixFromFile(String filename) throws IOException {
        Scanner scanner = new Scanner(new File(filename));
        int n = scanner.nextInt();
        double[][] matrix = new double[n][n];
        int i = 0;
        while (i < n) {
            int j = 0;
            while (j < n) {
                if (scanner.hasNextDouble()) {
                    matrix[i][j] = scanner.nextDouble();
                } else {
                    throw new IOException("Format file tidak sesuai.");
                }
                j = j + 1;
            }
            i = i + 1;
        }
        scanner.close();
        return matrix;
    }

    // Metode Output ke File
    public static void writeDeterminantToFile(double determinant, String filename) throws IOException {
    // Memastikan nama file memiliki ekstensi .txt
    if (!filename.endsWith(".txt")) {
        filename = filename + ".txt";
    }
    FileWriter writer = new FileWriter(filename);
    writer.write("Determinan matriks adalah: " + determinant);
    writer.close();
    }
}
