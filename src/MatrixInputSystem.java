public class MatrixInputSystem {
    private double[][] matrix_det;
    public double[][] getMatrix() {
        return this.matrix_det;
    }
    public void inputMatrix() {
        System.out.print("Masukkan jumlah baris: ");
        int rows = readInt();
        System.out.print("Masukkan jumlah kolom: ");
        int cols = readInt();
        this.matrix_det = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Masukkan elemen baris %d kolom %d: ", i + 1, j + 1);
                this.matrix_det[i][j] = readDouble();
            }
        }

        System.out.println("Matriks yang dimasukkan:");
        for (double[] row : matrix_det) {
            for (double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

    }

    private static int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(readLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: Input tidak valid. Masukkan angka bulat.");
            }
        }
    }
    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(readLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Input tidak valid. Masukkan angka.");
            }
        }
    }

    private static String readLine() {
        StringBuilder input = new StringBuilder();
        int c;
        while (true) {
            try {
                c = System.in.read();
                if (c == -1 || c == '\n') break;
                input.append((char)c);
            } catch (Exception e) {
                System.out.println("Error: Terjadi kesalahan saat membaca input.");
                return "";
            }
        }
        return input.toString().trim();
    }
}
