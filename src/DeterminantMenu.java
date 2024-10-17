import java.util.Scanner;

public class DeterminantMenu {

    public static void displayDeterminantMenu() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\n===== Menu Determinan =====");
            System.out.println("Pilih metode input matriks:");
            System.out.println("1. Input melalui keyboard");
            System.out.println("2. Input melalui file");
            System.out.print("Pilihan Anda: ");
            String inputChoice = scanner.nextLine();
            double[][] matrix;
            if (inputChoice.equals("1")) {
                // Input melalui keyboard
                matrix = DeterminantCalculator.readMatrixFromKeyboard(scanner);
            } else if (inputChoice.equals("2")) {
                // Input melalui file
                System.out.print("Masukkan nama file (dengan ekstensi): ");
                String filename = scanner.nextLine();
                matrix = DeterminantCalculator.readMatrixFromFile(filename);
            } else {
                System.out.println("Pilihan tidak valid. Kembali ke menu utama.");
                scanner.close();
                return;
            }

            System.out.println("\nPilih metode penghitungan determinan:");
            System.out.println("1. Ekspansi Kofaktor");
            System.out.println("2. Reduksi Baris (OBE)");
            System.out.print("Pilihan Anda: ");
            String methodChoice = scanner.nextLine();

            String method;
            if (methodChoice.equals("1")) {
                method = "cofactor";
            } else if (methodChoice.equals("2")) {
                method = "row reduction";
            } else {
                System.out.println("Pilihan tidak valid. Menggunakan metode 'row reduction' secara default.");
                method = "row reduction";
            }

            // Memanggil DeterminantOperations untuk menghitung determinan
            double determinant = DeterminantOperations.calculateDeterminant(matrix, method);

            // Tampilkan hasil
            System.out.println("\nDeterminan matriks adalah: " + determinant);

            // Opsi menyimpan hasil
            System.out.print("Apakah Anda ingin menyimpan hasil ke file? (y/n): ");
            String saveChoice = scanner.nextLine();
            if (saveChoice.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file untuk menyimpan hasil: ");
                String outputFilename = scanner.nextLine();
                DeterminantOperations.saveDeterminantToFile(determinant, outputFilename);
                System.out.println("Hasil telah disimpan ke dalam file " + outputFilename);
            }

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}
