public class DeterminantOperations {

    // Metode untuk menghitung determinan
    public static double calculateDeterminant(double[][] matrix, String method) {
        // Memanggil metode dari DeterminantCalculator
        return DeterminantCalculator.calculateDeterminant(matrix, method);
    }

    // Metode untuk menyimpan hasil ke file
    public static void saveDeterminantToFile(double determinant, String filename) {
        try {
            DeterminantCalculator.writeDeterminantToFile(determinant, filename);
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
        }
    }
}
