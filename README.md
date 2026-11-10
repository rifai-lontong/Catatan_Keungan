📱 Catatan Keuangan - Tugas 2 (Android Development)
Aplikasi Android untuk mencatat dan memantau keuangan harian secara real-time. Proyek ini dikembangkan menggunakan Kotlin dan Jetpack Compose dengan menerapkan prinsip UI modern dan interaktif.

✨ Fitur Utama (Tugas 2):
Navigasi Layar Dinamis (FinanceAppNavigator): Berpindah mulus antara MainScreen (ringkasan saldo & riwayat transaksi) dan AddTransactionScreen (form tambah data).

Validasi Input Ketat: Dilengkapi sistem validasi yang mencegah penyimpanan data jika kolom nama transaksi kosong atau nominal bernilai nol/negatif, disertai pesan error dinamis.

Logika Pemasukan & Pengeluaran: Pengguna dapat memilih jenis transaksi (Pemasukan/Pengeluaran) dengan kalkulasi saldo otomatis secara real-time.

Manajemen State: Menggunakan mutableStateListOf untuk memperbarui daftar riwayat transaksi secara langsung di dalam aplikasi.



*Foto
<img width="250" height="400" alt="Screenshot_2026-11-09-23-20-55-213_com example myapplication" src="https://github.com/user-attachments/assets/c0f8f27f-b637-4edb-afa5-c760d0ed0229" />
<img width="250" height="400" alt="Screenshot_2026-11-09-23-21-02-329_com google android googlequicksearchbox" src="https://github.com/user-attachments/assets/44cc1fd6-0d72-4148-b7e0-2eacac76a56f" />
<img width="250" height="400" alt="Screenshot_2026-11-09-23-20-38-053_com example myapplication" src="https://github.com/user-attachments/assets/0cc74cf3-cc07-4dc9-80ba-f7b0dd847fd7" />

## 🎥 Demo Aplikasi
https://github.com/user-attachments/assets/a67cca0d-5d08-4278-88b1-1e145be4aca6

🚀 Cara Menjalankan Aplikasi (Running the App)
Untuk menjalankan dan menguji aplikasi Catatan Keuangan ini langsung dari komputer atau perangkatmu, ikuti langkah-langkah berikut:

Prasyarat Sistem:
Terinstal Android Studio (disarankan versi terbaru yang mendukung Jetpack Compose dan Kotlin).

Perangkat Android fisik (dengan USB Debugging aktif) atau Android Emulator (AVD) yang sudah dikonfigurasi.

Langkah-langkah:
Clone Repository:
Clone repository ini ke komputer lokal menggunakan perintah Git atau unduh sebagai ZIP:

Bash
git clone https://github.com/rifai-lontong/Catatan-Keuangan.git
Buka Proyek di Android Studio:

Buka Android Studio, pilih Open, lalu arahkan ke folder proyek Catatan Keuangan yang sudah di-clone.

Tunggu proses Gradle Sync selesai hingga semua dependensi terunduh dengan baik.

Hubungkan Perangkat / Emulator:

Sambungkan HP Android fisik via kabel USB ke komputer, atau nyalakan Device Manager di Android Studio untuk menjalankan Emulator.

Jalankan Aplikasi:

Klik tombol Run (ikon segitiga hijau ▶) di bagian atas bilah alat Android Studio (atau tekan Shift + F10 di keyboard).

Tunggu beberapa saat hingga proses build selesai dan aplikasi otomatis terbuka di perangkat atau emulator pilihanmu.





🛠️ Tech Stack:
Bahasa: Kotlin

UI Framework: Jetpack Compose (Material 3)

Version Control: Git & GitHub
