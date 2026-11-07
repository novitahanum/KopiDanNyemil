# Kopi dan Nyemil ☕

Aplikasi pemesanan kopi dan camilan sederhana untuk tugas Pemrograman Mobile. Project dibuat menggunakan **Android Studio, Kotlin, dan Android Views**. Aplikasi tidak memakai database atau layanan backend; data akun dan keranjang hanya disimpan sementara selama aplikasi berjalan, sesuai ketentuan tugas.

## Fitur

1. **Splash / Welcome**: halaman pembuka dan tombol mulai.
2. **Sign Up / pilihan akun**: pengguna dapat memilih Register atau Login.
3. **Login**: input username dan password, dilengkapi validasi kolom.
4. **Register**: input nama lengkap, username, dan password. Password minimal 4 karakter.
5. **Home**: sapaan pengguna dan lima menu makanan/minuman.
6. **Profile**: pengguna dapat mengubah dan menyimpan nama tampilan.
7. **Order / Alamat Pengiriman**: menambahkan produk, mengatur jumlah, melihat total, dan mengisi alamat.
8. **Konfirmasi Pesanan**: menampilkan nomor pesanan, daftar pesanan, alamat, dan total.

## Daftar menu dummy

| Menu | Harga |
|---|---:|
| Kopi Susu Gula Aren | Rp22.000 |
| Donat Cokelat | Rp12.000 |
| Sourdough Loaf | Rp35.000 |
| Cappuccino | Rp25.000 |
| Matcha Latte | Rp27.000 |

## Teknologi

- Bahasa: Kotlin
- IDE: Android Studio
- Tampilan: Android Views yang dibuat dari Kotlin (tanpa Jetpack Compose)
- Minimum Android: API 23
- Compile / Target SDK: API 36
- Android Gradle Plugin: 9.3.1
- Kotlin Gradle Plugin: 2.4.21
- Gradle: 9.5.0
- Tidak membutuhkan database maupun API internet.

## Cara memasukkan project ke Android Studio

Paket ini berisi source code dan file konfigurasi project. Untuk memastikan Gradle Wrapper yang dibuat oleh Android Studio tetap tersedia, ikuti panduan pada [`PANDUAN_STEP_BY_STEP.md`](PANDUAN_STEP_BY_STEP.md): buat project **Empty Views Activity** terlebih dahulu, lalu salin file dari paket ini ke project tersebut. Setelah itu pasang Android SDK Platform 36, lakukan Gradle Sync, pilih emulator/HP, dan tekan **Run ▶**.

## Catatan simulasi

- Karena tugas tidak meminta database, akun dan keranjang tidak tersimpan setelah aplikasi ditutup.
- Untuk akun yang baru didaftarkan pada sesi aplikasi yang sama, gunakan username dan password yang sama saat login.
- Untuk demonstrasi login tanpa register, isi username dan password apa saja (tidak boleh kosong). Ini hanya simulasi, bukan autentikasi sungguhan.
- Pesanan hanya menghasilkan halaman konfirmasi di dalam aplikasi. Tidak ada pembayaran atau pengiriman sungguhan.
- Screenshot aplikasi dapat dimasukkan ke folder `screenshots/`, lalu ditautkan di bagian dokumentasi ini.

## Screenshot aplikasi

Tambahkan hasil screenshot emulator/HP ke folder `screenshots/`. Contoh format penyisipan gambar:

```md
![Halaman Home](screenshots/home.png)
![Halaman Order](screenshots/order.png)
![Halaman Konfirmasi](screenshots/confirmation.png)
```

## Struktur project

```text
KopiDanNyemil/
├── app/
│   └── src/main/
│       ├── java/com/example/kopidannyemil/MainActivity.kt
│       ├── res/values/styles.xml
│       └── AndroidManifest.xml
├── gradle/wrapper/gradle-wrapper.properties
├── .gitignore
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── README.md
└── PANDUAN_STEP_BY_STEP.md
```
