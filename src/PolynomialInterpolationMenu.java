import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PolynomialInterpolationMenu {
    public static void displayPolynomialInterpolationMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== Pilih Metode Input =====");
        System.out.println("1. Input dari keyboard");
        System.out.println("2. Input dari file");
        System.out.print("Pilihan Anda: ");
        int choice = scanner.nextInt();

        double[][] points = null;
        double[] xEstimate = null;

        if (choice == 1) {
            System.out.print("Masukkan jumlah titik: ");
            int n = scanner.nextInt();
            points = new double[n][2];
            
            for (int i = 0; i < n; i++) {
                System.out.print("Masukkan titik ke-" + (i+1) + " (x y): ");
                points[i][0] = scanner.nextDouble();
                points[i][1] = scanner.nextDouble();
            }

            System.out.print("Masukkan nilai x yang akan ditaksir: ");
            xEstimate = new double[1];
            xEstimate[0] = scanner.nextDouble();
        } else if (choice == 2) {
            System.out.print("Masukkan nama file: ");
            scanner.nextLine();
            String filename = scanner.nextLine();
            
            try {
                ArrayList<double[]> pointsList = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length == 2) {
                        pointsList.add(new double[]{
                            Double.parseDouble(parts[0]),
                            Double.parseDouble(parts[1])
                        });
                    } else if (parts.length == 1) {
                        xEstimate = new double[1];
                        xEstimate[0] = Double.parseDouble(parts[0]);
                    }
                }
                reader.close();
                
                points = new double[pointsList.size()][2];
                for (int i = 0; i < pointsList.size(); i++) {
                    points[i] = pointsList.get(i);
                }
            } catch (Exception e) {
                System.out.println("Error membaca file: " + e.getMessage());
                return;
            }
        }

        if (points != null && xEstimate != null) {
            int n = points.length - 1;  // digit tertinggi dari polinom interpolasi
            double[][] augmentedMatrix = new double[n + 1][n + 2];
            
            // matrix augmented
            for (int i = 0; i <= n; i++) {
                double x = points[i][0];
                double xPower = 1.0;
                for (int j = 0; j <= n; j++) {
                    augmentedMatrix[i][j] = xPower;
                    xPower = xPower * x;
                }
                augmentedMatrix[i][n + 1] = points[i][1];
            }

            // eliminasi gauss
            GaussElimination.gaussElimination(augmentedMatrix);
            double[] coefficients = GaussElimination.backSubstitution(augmentedMatrix);

            // format polynomial string
            StringBuilder result = new StringBuilder();
            result.append("Fungsi : f(x) = ");
            
            // mulai dari digit tertinggi
            boolean isFirst = true;
            for (int i = n; i >= 0; i--) {
                if (!isFirst) {
                    result.append(" + ");
                }
                
                if (i == 0) {
                    result.append(String.format("%.4f", coefficients[i]));
                } else {
                    result.append(String.format("%.20f", coefficients[i]));
                    result.append("*x^").append(i);
                }
                isFirst = false;
            }
            
            // estimasi f(x)
            double x = xEstimate[0];
            double estimate = coefficients[0];
            double xPower = x;
            for (int i = 1; i <= n; i++) {
                estimate += coefficients[i] * xPower;
                xPower *= x;
            }
            
            result.append("\nTaksiran f(").append(String.format("%.2f", x))
                  .append(") = ").append(String.format("%.6f", estimate));
            System.out.println("\n=============== HASIL ==================");
            System.out.println(result.toString());
            // opsi save
            System.out.print("\nApakah ingin disimpan dalam file? (y/n): ");
            if (scanner.next().toLowerCase().charAt(0) == 'y') {
                System.out.print("Masukkan nama file: ");
                scanner.nextLine();
                String outputFile = scanner.nextLine();
                try {
                    FileWriter writer = new FileWriter(outputFile);
                    writer.write(result.toString());
                    writer.close();
                    System.out.println("Hasil berhasil disimpan ke " + outputFile);
                } catch (IOException e) {
                    System.out.println("Error menyimpan file: " + e.getMessage());
                }
            }
        }
    }
}
