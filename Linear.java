public class Linear {
    public static String[] parametricSolutions(double[][] matrix, int rank) {
        int rows = matrix.length;
        int cols = matrix[0].length - 1;  // kolom terakhir adalah hasil (right-hand side)
        String[] solutions = new String[rows];
        int[] freeVariables = new int[rows];
        double[] solution = new double[rows];

        // Inisialisasi semua variabel bebas
        for (int i = 0; i < cols; i++) {
            freeVariables[i] = 1;
        }

        // Identifikasi variabel terikat dan keluarkan dari daftar variabel bebas
        for (int i = 0; i < rank; i++) {
            int pivotCol = -1;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    pivotCol = j;
                    break;
                }
            }
            if (pivotCol != -1) {
                freeVariables[pivotCol] = 0; // Hapus variabel terikat dari daftar variabel bebas
            }
        }

        // Bangun solusi parametrik untuk variabel terikat
        for (int i = rank - 1; i >= 0; i--) {
            int pivotCol = -1;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    pivotCol = j;
                    break;
                }
            }

            if (pivotCol != -1) {
                StringBuilder equation = new StringBuilder("x" + (pivotCol + 1) + " = ");
                double constant = matrix[i][cols]; // Nilai di kolom paling kanan

                // Kurangi kontribusi dari variabel bebas pada baris ini
                for (int j = pivotCol + 1; j < cols; j++) {
                    if (matrix[i][j] != 0) {
                        equation.append(String.format("%.2f", -matrix[i][j]) + " * x" + (j + 1) + " ");
                    }
                }
                equation.append(String.format("+ %.2f", constant));
                solutions[pivotCol] = equation.toString();
                solution[pivotCol] = constant;
            }
        }

        // Tambahkan solusi untuk variabel bebas
        for (int i = 0; i < cols; i++) {
            if (freeVariables[i] == 1) {
                solutions[i] = "x" + (i + 1) + " = x" + (i + 1);
            }
        }

        return solutions;
    }

    public static void main(String[] args) {
        double[][] matrix = {
            {0,1,0,0,1,0,2},
            {0, 0, 0,1,1,0,-1},
            {0,1,0,0,0,1,1},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0}
        };

        int rank = calculateRank(matrix);
        String[] solutions = parametricSolutions(matrix, rank);

        System.out.println("Solusi Parametrik:");
        for (String solution : solutions) {
            if (solution != null) {
                System.out.println(solution);
            }
        }
    }

    public static int calculateRank(double[][] matrix) {
        int rank = 0;
        int rows = matrix.length;
        int cols = matrix[0].length - 1;

        for (int i = 0; i < rows; i++) {
            boolean nonZeroRow = false;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    nonZeroRow = true;
                    break;
                }
            }
            if (nonZeroRow) {
                rank++;
            }
        }

        return rank;
    }
}
