import java.io.FileWriter;
import java.io.IOException;


public class AritmatikaMatriksMenu {
    public static void displayAritmatikaMatriksMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║               Aritmatika Matriks              ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Perjumlahan                               ║");
        System.out.println("║  2. Pengurangan                               ║");
        System.out.println("║  3. Perkalian                                 ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih metode (1-3): ");
        int subChoice = Integer.parseInt(readLine());
        while (subChoice < 1 || subChoice > 3) {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            System.out.print("Pilih metode (1-3): ");
            subChoice = Integer.parseInt(readLine());
        }
        switch (subChoice) {
            case 1:
                System.out.println("Anda memilih: Penjumlahan Matriks");
                System.out.print("Berapa banyak matriks yang ingin dijumlahkan? ");
                int matrixCountAdd = Integer.parseInt(readLine());
                if (matrixCountAdd < 2) {
                    System.out.println("Anda harus memasukkan minimal dua matriks.");
                    return;
                }
                double[][] resultMatrixAdd = inputMatrix();
                for (int i = 2; i <= matrixCountAdd; i++) {
                    System.out.println("Masukkan Matriks Ke-" + i + ":");
                    double[][] nextMatrixAdd = inputMatrix();
                    resultMatrixAdd = AritmatikaMatriks.addMatrices(resultMatrixAdd, nextMatrixAdd);
                }
                StringBuilder resultAdd = new StringBuilder("Hasil penjumlahan matriks:\n");
                for (int i = 0; i < resultMatrixAdd.length; i++) {
                    for (int j = 0; j < resultMatrixAdd[i].length; j++) {
                        resultAdd.append(String.format("%.2f ", resultMatrixAdd[i][j]));
                    } resultAdd.append("\n"); 
                }
                System.out.println(resultAdd.toString());
                System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n) ");
                String saveChoice = readLine();
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(resultAdd.toString());
                }
                break;

            case 2:
                System.out.println("Anda memilih: Pengurangan Matriks");
                System.out.print("Berapa banyak matriks yang ingin dikurangkan? ");
                int matrixMin = Integer.parseInt(readLine());
                if (matrixMin < 2) {
                    System.out.println("Anda harus memasukkan minimal dua matriks.");
                    return;
                }
                double[][] resultMatrixMin = inputMatrix();
                for (int i = 2; i <= matrixMin; i++) {
                    System.out.println("Masukkan Matriks Ke-" + i + ":");
                    double[][] nextMatrixMin = inputMatrix();
                    resultMatrixMin = AritmatikaMatriks.minusMatrices(resultMatrixMin, nextMatrixMin);
                }
                StringBuilder resultMin = new StringBuilder("Hasil pengurangan matriks:\n");
                for (int i = 0; i < resultMatrixMin.length; i++) {
                    for (int j = 0; j < resultMatrixMin[i].length; j++) {
                        resultMin.append(String.format("%.2f ", resultMatrixMin[i][j]));
                    } resultMin.append("\n"); 
                }
                System.out.println(resultMin.toString());
                System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n) ");
                saveChoice = readLine();
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(resultMin.toString());
                }
                break;

            case 3:
                System.out.println("Anda memilih: Perkalian Matriks");
                System.out.print("Berapa banyak matriks yang ingin dikalikan? ");
                int matrixCountMulti = Integer.parseInt(readLine());
                if (matrixCountMulti < 2) {
                    System.out.println("Anda harus memasukkan minimal dua matriks.");
                    return;
                }
                double[][] resultMatrixMulti = inputMatrix();
                for (int i = 2; i <= matrixCountMulti; i++) {
                    System.out.println("Masukkan Matriks Ke-" + i + ":");
                    double[][] nextMatrixMulti = inputMatrix();
                    if (resultMatrixMulti[0].length != nextMatrixMulti.length) {
                        System.out.println("Dimensi matriks tidak cocok untuk perkalian.");
                        return;
                    }
                    resultMatrixMulti = AritmatikaMatriks.multiplyMatrices(resultMatrixMulti, nextMatrixMulti);
                }
                StringBuilder resultMulti = new StringBuilder("Hasil perkalian matriks:\n");
                for (int i = 0; i < resultMatrixMulti.length; i++) {
                    for (int j = 0; j < resultMatrixMulti[i].length; j++) {
                        resultMulti.append(String.format("%.2f ", resultMatrixMulti[i][j]));
                    } resultMulti.append("\n"); 
                }
                System.out.print(resultMulti.toString());
                System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n) ");
                saveChoice = readLine();
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(resultMulti.toString());
                }
                break;

            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }

    private static double[][] inputMatrix() {
        System.out.print("Masukkan jumlah baris: ");
        int rows = Integer.parseInt(readLine());
        System.out.print("Masukkan jumlah kolom: ");
        int cols = Integer.parseInt(readLine());
        double[][] matrix = new double[rows][cols];
        System.out.println("Masukkan elemen matriks:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Matriks[" + (i + 1) + "][" + (j + 1) + "]: ");
                matrix[i][j] = Double.parseDouble(readLine());
            }
        }
        return matrix;
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
        } return input.toString().trim();
    }
}