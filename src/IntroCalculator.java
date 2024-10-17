import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class IntroCalculator {
    public static void main(String[] args) {
        int choice;
        do {
            displayMainMenu();
            choice = getMenuChoice();
            processMainMenuChoice(choice);
        } while (choice != 9);
    }

    private static void displayMainMenu() {
        System.out.println("\n\n");
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║           KALKULATOR MATRIKS REEVES           ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Sistem Persamaan Linier                   ║");
        System.out.println("║  2. Determinan                                ║");
        System.out.println("║  3. Matriks balikan                           ║");
        System.out.println("║  4. Interpolasi Polinom                       ║");
        System.out.println("║  5. Interpolasi Bicubic Spline                ║");
        System.out.println("║  6. Regresi linier dan kuadratik berganda     ║");
        System.out.println("║  7. Interpolasi Gambar (Bonus)                ║");
        System.out.println("║  8. Aritmatika Matriks                        ║");
        System.out.println("║  9. Keluar                                    ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih menu (1-9): ");
    }

    public static int getMenuChoice() {
        int choice = 0;
        boolean validInput = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!validInput) {
            try {
                String input = reader.readLine().trim();
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 9) {
                    validInput = true;
                } else {
                    System.out.println("Input tidak valid. Masukkan angka antara 1-9.");
                    System.out.print("Pilih menu (1-9): ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka antara 1-9.");
                System.out.print("Pilih menu (1-9): ");
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan input/output: " + e.getMessage());
                System.exit(1);
            }
        }
        return choice;
    }

    private static void processMainMenuChoice(int choice) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        switch (choice) {
            case 1:
                System.out.println("Anda memilih: Sistem Persamaan Linier");
                SPLMenu.displaySPLMenu();
                break;
            case 2:
                System.out.println("Anda memilih: Determinan");
                DeterminantMenu.displayDeterminantMenu();
                break;
            case 3:
                System.out.println("Anda memilih: Matriks balikan");
                InverseMenu.displayInverseMenu();
                break;
            case 4:
                System.out.println("Anda memilih: Interpolasi Polinom");
                PolynomialInterpolationMenu.displayPolynomialInterpolationMenu();
                break;
            case 5:
                System.out.println("Anda memilih: Interpolasi Bicubic Spline");
                BicubicSplineInterpolation.performBicubicSplineInterpolation();
                break;
            case 6:
                System.out.println("Anda memilih: Regresi linier dan kuadratik berganda");
                // Panggil metode yang sesuai
                break;
            case 7:
                System.out.println("Anda memilih: Interpolasi Gambar (Bonus)");
                // Panggil metode yang sesuai
                break;
            case 8:
                System.out.println("Anda memilih: Aritmatika Matriks");
                // Panggil metode yang sesuai
                break;
            case 9:
                System.out.println("~~ Terima kasih telah menggunakan Matriks Reeves. Sampai jumpa! ~~");
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
        if (choice != 9) {
            System.out.println("\nTekan Enter untuk kembali ke menu utama!");
            try {
                reader.readLine(); // Menunggu user menekan Enter
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan input/output: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
