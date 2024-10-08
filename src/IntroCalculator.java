//Masukin librarynya disini

public class IntroCalculator {
    public static void main(String[] args) {
        int choice;
        do {
            displayMainMenu();
            choice = getMenuChoice();
            processMainMenuChoice(choice);
        } while (choice != 8);
    }

    private static void displayMainMenu() {
        System.out.println("\n\n");
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║             KALKULATOR MATRIKS                ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Sistem Persamaan Linier                   ║");
        System.out.println("║  2. Determinan                                ║");
        System.out.println("║  3. Matriks balikan                           ║");
        System.out.println("║  4. Interpolasi Polinom                       ║");
        System.out.println("║  5. Interpolasi Bicubic Spline                ║");
        System.out.println("║  6. Regresi linier dan kuadratik berganda     ║");
        System.out.println("║  7. Interpolasi Gambar (Bonus)                ║");
        System.out.println("║  8. Keluar                                    ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih menu (1-8): ");
    }

    private static int getMenuChoice() {
        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            int input = readSingleChar();
            if (input >= '1' && input <= '8') {
                choice = input - '0';
                validInput = true;
            } else {
                System.out.println("Input tidak valid. Masukkan angka antara 1-8.");
            }
            clearInputBuffer();
        }
        return choice;
    }

    private static int readSingleChar() {
        int input = 0;
        try {
            input = System.in.read();
        } catch (java.io.IOException e) {
            return -1;
        }
        return input;
    }
    private static void clearInputBuffer() {
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (java.io.IOException e) {
        }
    }
    private static void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Anda memilih: Sistem Persamaan Linier");
                displaySubMenu();
                break;
            case 2:
                System.out.println("Anda memilih: Determinan");
                break;
            case 3:
                System.out.println("Anda memilih: Matriks balikan");
                break;
            case 4:
                System.out.println("Anda memilih: Interpolasi Polinom");
                break;
            case 5:
                System.out.println("Anda memilih: Interpolasi Bicubic Spline");
                break;
            case 6:
                System.out.println("Anda memilih: Regresi linier dan kuadratik berganda");
                break;
            case 7:
                System.out.println("Anda memilih: Interpolasi Gambar (Bonus)");
                break;
            case 8:
                System.out.println("Terima kasih telah menggunakan kalkulator kami. Sampai jumpa!");
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
        if (choice != 8) {
            System.out.println("\nTekan Enter untuk kembali ke menu utama...");
            clearInputBuffer();
        }
    }

    private static void displaySubMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║         SISTEM PERSAMAAN LINIER               ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Metode eliminasi Gauss                    ║");
        System.out.println("║  2. Metode eliminasi Gauss-Jordan             ║");
        System.out.println("║  3. Metode matriks balikan                    ║");
        System.out.println("║  4. Kaidah Cramer                             ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih metode (1-4): ");
        int subChoice = getMenuChoice();
        System.out.println("Anda memilih metode " + subChoice);
    }
}

