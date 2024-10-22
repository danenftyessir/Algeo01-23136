import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageInterpolator {
    private Scanner scanner;

    public ImageInterpolator() {
        this.scanner = new Scanner(System.in);
    }

    public void performImageInterpolation() {
        try {
            // Membaca gambar input
            System.out.print("Masukkan path gambar input: ");
            String inputImagePath = scanner.nextLine();
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

            // Meminta faktor skala dari pengguna
            System.out.print("Masukkan faktor skala untuk lebar (contoh: 1.5): ");
            double scaleX = getValidDoubleInput(0.1, 10.0);

            System.out.print("Masukkan faktor skala untuk tinggi (contoh: 2.0): ");
            double scaleY = getValidDoubleInput(0.1, 10.0);

            // Melakukan penskalaan gambar
            BufferedImage outputImage = resizeImage(inputImage, scaleX, scaleY);

            // Menyimpan gambar output
            System.out.print("Masukkan path untuk menyimpan gambar output: ");
            String outputImagePath = scanner.nextLine();
            ImageIO.write(outputImage, "png", new File(outputImagePath));

            System.out.println("Gambar berhasil diskalakan dan disimpan di " + outputImagePath);
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private BufferedImage resizeImage(BufferedImage inputImage, double scaleX, double scaleY) {
        int originalWidth = inputImage.getWidth();
        int originalHeight = inputImage.getHeight();

        int newWidth = (int)(originalWidth * scaleX);
        int newHeight = (int)(originalHeight * scaleY);

        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        int x = 0;
        while (x < newWidth) {
            int y = 0;
            while (y < newHeight) {
                // Koordinat dalam gambar asli
                double gx = x / scaleX;
                double gy = y / scaleY;

                // Mendapatkan koordinat piksel terdekat
                int gxi = (int)gx;
                int gyi = (int)gy;

                // Mendapatkan matriks 4x4 piksel di sekitar (gxi, gyi)
                double[][] pixels = getNeighborhoodPixels(inputImage, gxi, gyi);

                // Membuat objek BicubicSplineInterpolation
                BicubicSplineInterpolation interpolator = new BicubicSplineInterpolation();
                interpolator.setInputMatrix(pixels);
                interpolator.setA(gx - gxi);
                interpolator.setB(gy - gyi);

                // Menghitung koefisien dan nilai interpolasi
                interpolator.calculateCoefficients();
                double interpolatedValue = interpolator.evaluatePolynomial();

                // Membatasi nilai piksel antara 0 dan 255
                int rgbValue = clamp((int)interpolatedValue, 0, 255);

                // Mengatur piksel pada gambar output
                int rgb = (rgbValue << 16) | (rgbValue << 8) | rgbValue;
                outputImage.setRGB(x, y, rgb);

                y = y + 1;
            }
            x = x + 1;
        }

        return outputImage;
    }

    private double[][] getNeighborhoodPixels(BufferedImage image, int x, int y) {
        double[][] pixels = new double[4][4];
        int m = 0;
        while (m < 4) {
            int n = 0;
            while (n < 4) {
                int xi = x - 1 + n;
                int yi = y - 1 + m;

                // Membatasi koordinat piksel dalam batas gambar
                xi = clamp(xi, 0, image.getWidth() - 1);
                yi = clamp(yi, 0, image.getHeight() - 1);

                // Mendapatkan nilai piksel (menggunakan komponen merah sebagai grayscale)
                int rgb = image.getRGB(xi, yi);
                int gray = (rgb >> 16) & 0xFF;

                pixels[m][n] = gray;

                n = n + 1;
            }
            m = m + 1;
        }
        return pixels;
    }

    private int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    private double getValidDoubleInput(double min, double max) {
        double input;
        while (true) {
            try {
                input = Double.parseDouble(scanner.next());
                if (input >= min && input <= max) {
                    scanner.nextLine(); // Membersihkan buffer
                    return input;
                } else {
                    System.out.print("Input harus antara " + min + " dan " + max + ". Coba lagi: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid. Masukkan angka: ");
                scanner.nextLine(); // Membersihkan buffer
            }
        }
    }
}
