<h1 align="center" style="font-size: 3.5em;">Kalkulator Matriks Reeves</h1>

<div align="center">
  <img src="readme/Matriks%20Reeves%20Logo.jpg" alt="Logo Matriks Reeves" width="200"/>
  <br><br>
  <h2 align="center" style="font-size: 2em; font-weight: bold;">Tugas Besar 1 IF2123 - Aljabar Linier dan Geometri</h2>
  <h3 align="center" style="font-size: 1.75em; font-weight: bold;">Institut Teknologi Bandung</h3>
  <h3 align="center" style="font-size: 1.75em; font-weight: bold;">Semester I Tahun 2024/2025</h3>
</div>

## Deskripsi Singkat
Matriks Reeves merupakan program kalkulator yang dirancang untuk menyelesaikan berbagai perhitungan matematika, meliputi Sistem Persamaan Linier (SPL), Determinan, Interpolasi Polinomial, Regresi Linier Berganda, dan Interpolasi Bicubic Spline. Program ini menyediakan beberapa metode penyelesaian SPL, termasuk Metode Eliminasi Gauss, Gauss-Jordan, Matriks Balikan, dan Kaidah Cramer.

## Anggota Matriks Reeves
<div align="center">
  <img src="readme/Anggota%20Matrix%20Reeves.jpg" alt="Anggota Matrix Reeves" width="400"/>

| NIM      | Nama                    |
|----------|-------------------------|
| 13523136 | Danendra Shafi Athallah |
| 13523141 | Jovandra Otniel P.S.    |
| 13523151 | Ardell Aghna Mahendra   |
</div>

## Fitur Program

### 1. Penyelesaian Sistem Persamaan Linier (SPL)
- Metode Eliminasi Gauss
- Metode Eliminasi Gauss-Jordan
- Metode Matriks Balikan
- Kaidah Cramer

### 2. Perhitungan Determinan
- Metode Ekspansi Kofaktor
- Metode Reduksi Baris (OBE)

### 3. Operasi Matriks
- Perhitungan Matriks Balikan (Invers)
- Aritmatika Matriks
  - Penjumlahan
  - Pengurangan
  - Perkalian

### 4. Interpolasi
- Interpolasi Polinomial
- Interpolasi Bicubic Spline
- Regresi Linier dan Kuadratik Berganda

### 5. Pengolahan Gambar
- Peningkatan Kualitas Gambar dengan Interpolasi

## Struktur Direktori
```
kalkulator-matriks-reeves/
├── bin/                            # File hasil kompilasi
│   ├── AritmatikaMatriks.class     # Operasi aritmatika matriks
│   ├── AritmatikaMatriksMenu.class # Menu operasi aritmatika
│   ├── BasicOperationMatrix.class  # Operasi dasar matriks
│   ├── BicubicSplineInterpolation.class  # Interpolasi bicubic spline
│   ├── CramerRule.class           # Implementasi kaidah Cramer
│   ├── DeterminantCalculator.class # Perhitungan determinan
│   ├── DeterminantMenu.class      # Menu perhitungan determinan
│   ├── DeterminantOperations.class # Operasi determinan
│   ├── GaussElimination.class     # Eliminasi Gauss
│   ├── GaussJordanElimination.class # Eliminasi Gauss-Jordan
│   ├── ImageInterpolator.class    # Interpolasi gambar
│   ├── IntroCalculator.class      # Tampilan awal kalkulator
│   ├── InverseMatrix.class        # Perhitungan matriks balikan
│   ├── InverseMenu.class          # Menu matriks balikan
│   ├── LinearEquationInputSystem.class # Input sistem persamaan linear
│   ├── Main.class                 # Program utama
│   ├── MatrixInputSystem.class    # Sistem input matriks
│   ├── PolynomialInterpolationMenu.class # Menu interpolasi polinomial
│   ├── Regresi.class             # Perhitungan regresi
│   ├── RegresiMenu.class         # Menu regresi
│   └── SPLMenu.class             # Menu SPL
│
├── doc/                           # Dokumentasi
│   ├── README.md                 # Dokumentasi utama
│   └── readme/                   # Folder aset dokumentasi
│       ├── Anggota Matrix Reeves.jpg  # Foto anggota tim
│       └── Matriks Reeves Logo.jpg    # Logo program
│
├── src/                          
│   ├── AritmatikaMatriks.java    # Implementasi operasi aritmatika
│   ├── AritmatikaMatriksMenu.java # Implementasi menu aritmatika
│   ├── BasicOperationMatrix.java  # Implementasi operasi dasar
│   ├── BicubicSplineInterpolation.java # Implementasi interpolasi bicubic
│   ├── CramerRule.java           # Implementasi kaidah Cramer
│   ├── DeterminantCalculator.java # Implementasi perhitungan determinan
│   ├── DeterminantMenu.java      # Implementasi menu determinan
│   ├── DeterminantOperations.java # Implementasi operasi determinan
│   ├── GaussElimination.java     # Implementasi eliminasi Gauss
│   ├── GaussJordanElimination.java # Implementasi Gauss-Jordan
│   ├── ImageInterpolator.java    # Implementasi interpolasi gambar
│   ├── IntroCalculator.java      # Implementasi tampilan awal
│   ├── InverseMatrix.java        # Implementasi matriks balikan
│   ├── InverseMenu.java          # Implementasi menu inverse
│   ├── LinearEquationInputSystem.java # Implementasi input SPL
│   ├── MatrixInputSystem.java    # Implementasi input matriks
│   ├── MatrixUtility.java        # Utilitas operasi matriks
│   ├── PolynomialInterpolationMenu.java # Implementasi menu interpolasi
│   ├── Regresi.java             # Implementasi perhitungan regresi
│   ├── RegresiMenu.java         # Implementasi menu regresi
│   ├── SPLMenu.java             # Implementasi menu SPL
│   └── main.java                 # Program utama
│
├── test/   
│   ├── Bicubic1.txt             # Data uji interpolasi bicubic 1
│   ├── Bicubic2.txt             # Data uji interpolasi bicubic 2
│   ├── Bicubic3.txt             # Data uji interpolasi bicubic 3
│   ├── Bicubic4.txt             # Data uji interpolasi bicubic 4
│   ├── InterpolA.txt            # Data uji interpolasi A
│   ├── SPL1a.txt                # Data uji SPL metode 1a
│   ├── SPL1b.txt                # Data uji SPL metode 1b
│   ├── SPL1c.txt                # Data uji SPL metode 1c
│   ├── SPL1d10.txt              # Data uji SPL metode 1d (10x10)
│   ├── SPL1d6.txt               # Data uji SPL metode 1d (6x6)
│   ├── SPL2a.txt                # Data uji SPL metode 2a
│   ├── SPL2b.txt                # Data uji SPL metode 2b
│   ├── SPL3a.txt                # Data uji SPL metode 3a
│   ├── SPL3b.txt                # Data uji SPL metode 3b
│   ├── SoalNo4.txt              # Data uji soal nomor 4
│   └── SoalRegresi.txt          # Data uji regresi
│
├── LICENSE                       
└── README.md          
```

## Testing

### Prasyarat
- Java Development Kit (JDK)
- Java Runtime Environment (JRE)

### Step by step
1. Unduh seluruh berkas pada folder `src`

2. Buka terminal atau command prompt, navigasi ke direktori utama program
   ```bash
   cd <path>
   ```

3. Lakukan kompilasi semua berkas Java dengan perintah:
   ```bash
   javac -d bin src/*.java
   ```

4. Jalankan program dengan perintah:
   ```bash
   cd bin
   ```
   lalu:
   ```bash
   java main
   ```

6. Pilih menu fungsi/fitur yang diinginkan

7. Pilih cara input (keyboard/file)

8. Pilih menu output yang diinginkan

9. Program selesai ketika memilih keluar


## Format File Masukan
- File teks (.txt)
- Format sesuai dengan jenis operasi yang dipilih
- Contoh file uji tersedia di folder `test`

## Lisensi
Kode dalam proyek ini dilisensikan di bawah lisensi MIT.

---
**Catatan:** Program ini dibuat sebagai bagian dari Tugas Besar 1 IF2123 - Aljabar Linier dan Geometri, Institut Teknologi Bandung, Semester I Tahun 2024/2025.
