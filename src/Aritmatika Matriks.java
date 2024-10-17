import java.io.FileWriter;
import java.io.IOException;
import src.BasicOperationMatrix;

public class AritmatikaMatriks {
    // Fungsi untuk menampilkan menu Aritmatika Matriks di IntroCalculator (Main Class)
    public static void displayInverseMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║               Aritmatika Matriks              ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Perjumlahan                               ║");
        System.out.println("║  2. Pengurangan                               ║");
        System.out.println("║  3. Perkalian                                 ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih metode (1-3): ");
        int subChoice = IntroCalculator.getMenuChoice();
        System.out.println("Anda memilih metode " + subChoice);
        while (subChoice < 1 || subChoice > 3) {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            subChoice = IntroCalculator.getMenuChoice();
        }
        switch (subChoice) {
            case 1:
                System.out.println("Anda memilih: Penjumlahan Matriks");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Berapa banyak matriks yang ingin dijumlahkan? ");
                int matrixCount = scanner.nextInt();
                if (matrixCount < 2) {
                    System.out.println("Anda harus memasukkan minimal dua matriks.");
                    return;
                }
    
                MatrixInputSystem matrixInputSystem = new MatrixInputSystem();
                System.out.println("Masukkan Matriks Pertama:");
                double[][] resultMatrix = matrixInputSystem.inputMatrix();
    
                for (int i = 2; i <= matrixCount; i++) {
                    System.out.println("Masukkan Matriks Ke-" + i + ":");
                    double[][] nextMatrix = matrixInputSystem.inputMatrix();
                    resultMatrix = BasicOperationMatrix.addMatrices(resultMatrix, nextMatrix);
                }

                StringBuilder results = new StringBuilder();
                System.out.println("\nHasil Penjumlahan Matriks:");
                results.append("Hasil Penjumlahan Matriks:\n");
                for (int i = 0; i < resultMatrix.length; i++) {
                    for (int j = 0; j < resultMatrix[0].length; j++) {
                        System.out.printf("%.2f ", resultMatrix[i][j]);
                        results.append(resultMatrix[i][j]).append(" ");
                    }
                    System.out.println();
                    results.append("\n");
                }

                System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n) ");
                String saveChoice = IntroCalculator.readLine();
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(results.toString());
                }
                break;

            case 2:
                System.out.println("Anda memilih: Pengurangan Matriks");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Berapa banyak matriks yang ingin dikurangkan? ");
                int matrixCount = scanner.nextInt();
                if (matrixCount < 2) {
                    System.out.println("Anda harus memasukkan minimal dua matriks.");
                    return;
                }
    
                MatrixInputSystem matrixInputSystem = new MatrixInputSystem();
                System.out.println("Masukkan Matriks Pertama:");
                double[][] resultMatrix = matrixInputSystem.inputMatrix();
    
                for (int i = 2; i <= matrixCount; i++) {
                    System.out.println("Masukkan Matriks Ke-" + i + ":");
                    double[][] nextMatrix = matrixInputSystem.inputMatrix();
                    resultMatrix = BasicOperationMatrix.minusMatrices(resultMatrix, nextMatrix);
                }

                StringBuilder results = new StringBuilder();
                System.out.println("\nHasil Pengurangan Matriks:");
                results.append("Hasil Pengurangan Matriks:\n");
                for (int i = 0; i < resultMatrix.length; i++) {
                    for (int j = 0; j < resultMatrix[0].length; j++) {
                        System.out.printf("%.2f ", resultMatrix[i][j]);
                        results.append(resultMatrix[i][j]).append(" ");
                    }
                    System.out.println();
                    results.append("\n");
                }

                System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n) ");
                String saveChoice = IntroCalculator.readLine();
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(results.toString());
                }
                break;

                case 3:
                System.out.println("Anda memilih: Pengurangan Matriks");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Berapa banyak matriks yang ingin dikurangkan? ");
                int matrixCount = scanner.nextInt();
                if (matrixCount < 2) {
                    System.out.println("Anda harus memasukkan minimal dua matriks.");
                    return;
                }
    
                MatrixInputSystem matrixInputSystem = new MatrixInputSystem();
                System.out.println("Masukkan Matriks Pertama:");
                double[][] resultMatrix = matrixInputSystem.inputMatrix();
    
                for (int i = 2; i <= matrixCount; i++) {
                    System.out.println("Masukkan Matriks Ke-" + i + ":");
                    double[][] nextMatrix = matrixInputSystem.inputMatrix();
                    resultMatrix = BasicOperationMatrix.minusMatrices(resultMatrix, nextMatrix);
                }

                StringBuilder results = new StringBuilder();
                System.out.println("\nHasil Pengurangan Matriks:");
                results.append("Hasil Pengurangan Matriks:\n");
                for (int i = 0; i < resultMatrix.length; i++) {
                    for (int j = 0; j < resultMatrix[0].length; j++) {
                        System.out.printf("%.2f ", resultMatrix[i][j]);
                        results.append(resultMatrix[i][j]).append(" ");
                    }
                    System.out.println();
                    results.append("\n");
                }

                System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n) ");
                String saveChoice = IntroCalculator.readLine();
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(results.toString());
                }
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }

    private static void saveToFile(String results) {
        System.out.print("Masukkan nama file yang akan disimpan (tanpa ekstensi): ");
        String name = readLine();
        name += ".txt";
        try (FileWriter writer = new FileWriter(name)) {
            writer.write(results);
            System.out.printf("Berhasil disimpan ke file %s\n",name);
        } catch (IOException e) {
            System.out.println("Error: Gagal menyimpan ke file");
        }
    }

    public static String readLine() {
        StringBuilder input = new StringBuilder();
        int c;
        while (true) {
            try {
                c = System.in.read();
                if (c == -1 || c == '\n') break;
                input.append((char) c);
            } catch (Exception e) {
                System.out.println("Error: Terjadi kesalahan saat membaca input.");
                return "";
            }
        }
        return input.toString().trim();
    }
}