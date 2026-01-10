# 🚗 Sistem Manajemen Penjualan Sparepart Mobil

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Java Swing](https://img.shields.io/badge/Java_Swing-4A90E2?style=for-the-badge&logo=java&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

**Aplikasi Desktop Cerdas untuk Manajemen Inventori & Penjualan Sparepart**

[📖 Dokumentasi](#-daftar-isi) | [⚙️ Instalasi](#-instalasi--konfigurasi) | [🚀 Fitur](#-fitur-unggulan) | [📊 Demo](#-demo-screenshots)

</div>

---

## 📋 Daftar Isi

- [🌟 Overview](#-overview)
- [✨ Fitur Unggulan](#-fitur-unggulan)
- [🎯 Keunggulan Sistem](#-keunggulan-sistem)
- [🏗️ Arsitektur Sistem](#️-arsitektur-sistem)
- [🗄️ Struktur Database](#️-struktur-database)
- [⚡ Instalasi & Konfigurasi](#-instalasi--konfigurasi)
- [📊 Demo Screenshots](#-demo-screenshots)
- [🔄 Alur Kerja Sistem](#-alur-kerja-sistem)
- [📁 Struktur Project](#-struktur-project)
- [🤝 Kontribusi](#-kontribusi)
- [📄 Lisensi](#-lisensi)

---

## 🌟 Overview

Aplikasi **Sistem Penjualan Sparepart** adalah solusi desktop komprehensif yang dirancang khusus untuk bengkel atau toko sparepart kendaraan. Dengan antarmuka intuitif berbasis Java Swing dan backend MySQL, sistem ini mengotomatisasi seluruh proses bisnis mulai dari manajemen stok hingga transaksi penjualan.

### 🎯 Tujuan Utama

✅ **Otomatisasi Penuh** proses penjualan dan manajemen stok  
✅ **Keamanan Data** dengan autentikasi petugas dan transaksi database yang konsisten  
✅ **Efisiensi Operasional** dengan pengurangan kesalahan manual  
✅ **Laporan Real-time** ketersediaan stok dan penjualan

---

## ✨ Fitur Unggulan

### 🔧 Manajemen Data

| Fitur | Deskripsi | Ikon |
|-------|-----------|------|
| CRUD Sparepart | Tambah, lihat, edit, hapus data sparepart | 📦 |
| CRUD Pelanggan | Kelola database pelanggan | 👥 |
| CRUD Penjualan | Kelola transaksi penjualan | 💰 |
| Manajemen Petugas | Kelola akun dan level akses petugas | 🔐 |
| Pencarian Cerdas | Temukan data dengan cepat | 🔍 |

### 📊 Manajemen Stok Otomatis

| Aksi | Perilaku Sistem | Status Stok |
|------|----------------|-------------|
| Penjualan Baru | Stok otomatis berkurang | ⬇️ -beli |
| Hapus Penjualan | Stok otomatis kembali | ⬆️ +beli |
| Edit Penjualan | Stok disesuaikan otomatis | 🔄 Selisih |
| Stok Habis | Peringatan otomatis | 🚫 Validasi |

### 🛡️ Fitur Keamanan

✅ Sistem Login dengan level akses (Admin)  
✅ Transaksi Atomic (Commit/Rollback)  
✅ Validasi Input real-time  
✅ PreparedStatement anti SQL Injection  
✅ Konsistensi Data terjamin

---

## 🎯 Keunggulan Sistem

### ⚡ Performansi

- Real-time update stok
- Minimal operasi database
- Optimasi query dengan index
- Interface yang responsif dan user-friendly

### 🔄 Logika Stok Cerdas

```java
// Contoh logika update stok
public void updateStok(int idSparepart, int perubahan) {
    // Stok baru = Stok lama ± perubahan
    String sql = "UPDATE sparepart SET stok = stok + ? WHERE id_sparepart = ?";
    // Menggunakan transaksi untuk menjaga konsistensi
}
```

---

## 🏗️ Arsitektur Sistem

```
┌─────────────────────────────────────────────┐
│            PRESENTATION LAYER               │
│   Java Swing GUI (Form, Table, Dialog)     │
│   - FrameLogin, FrameMenu, FramePenjualan   │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│            BUSINESS LAYER                   │
│   Logic: Stok, Validasi, Transaksi         │
│   - Koneksi Database, CRUD Operations       │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│            DATA ACCESS LAYER                │
│   JDBC Connection, PreparedStatement        │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│            DATABASE LAYER                   │
│   MySQL/MariaDB - Database: sparepart       │
└─────────────────────────────────────────────┘
```

---

## 🗄️ Struktur Database

### 📦 Tabel sparepart

```sql
CREATE TABLE `sparepart` (
  `id_sparepart` int(3) NOT NULL AUTO_INCREMENT,
  `nama_sparepart` varchar(30) NOT NULL,
  `satuan` varchar(20) NOT NULL,
  `stok` varchar(30) NOT NULL,
  `harga_satuan` int(10) NOT NULL,
  PRIMARY KEY (`id_sparepart`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

**Kolom:**
- `id_sparepart`: ID unik sparepart (Auto Increment)
- `nama_sparepart`: Nama produk sparepart
- `satuan`: Satuan produk (Pcs, Galon, dll)
- `stok`: Jumlah stok tersedia
- `harga_satuan`: Harga per satuan produk

### 👥 Tabel pelanggan

```sql
CREATE TABLE `pelanggan` (
  `id_pelanggan` int(3) NOT NULL AUTO_INCREMENT,
  `nama` varchar(30) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  PRIMARY KEY (`id_pelanggan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

**Kolom:**
- `id_pelanggan`: ID unik pelanggan (Auto Increment)
- `nama`: Nama pelanggan
- `alamat`: Alamat pelanggan

### 💰 Tabel penjualan

```sql
CREATE TABLE `penjualan` (
  `id_penjualan` int(3) NOT NULL AUTO_INCREMENT,
  `id_pelanggan` int(3) NOT NULL,
  `id_sparepart` int(3) NOT NULL,
  `beli` int(3) NOT NULL,
  `subtotal` int(10) NOT NULL,
  PRIMARY KEY (`id_penjualan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

**Kolom:**
- `id_penjualan`: ID unik transaksi (Auto Increment)
- `id_pelanggan`: Foreign key ke tabel pelanggan
- `id_sparepart`: Foreign key ke tabel sparepart
- `beli`: Jumlah barang dibeli
- `subtotal`: Total harga (harga_satuan × beli)

### 🔐 Tabel petugas

```sql
CREATE TABLE `petugas` (
  `id_petugas` int(3) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nama_petugas` varchar(30) NOT NULL,
  `level` varchar(10) NOT NULL,
  PRIMARY KEY (`id_petugas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

**Kolom:**
- `id_petugas`: ID unik petugas (Auto Increment)
- `username`: Username untuk login
- `password`: Password petugas
- `nama_petugas`: Nama lengkap petugas
- `level`: Level akses (admin, kasir, dll)

---

## ⚡ Instalasi & Konfigurasi

### 📋 Prasyarat

- ☕ Java Development Kit (JDK) 8 atau lebih tinggi
- 🗄️ MySQL Server 5.7+ / MariaDB 10.4+
- 💻 IDE (NetBeans, IntelliJ IDEA, atau Eclipse)
- 🔌 MySQL Connector/J (JDBC Driver)

### 🚀 Langkah Instalasi

#### 1. Clone/Download Project

```bash
git clone https://github.com/username/penjualansparepart.git
cd penjualansparepart
```

#### 2. Setup Database

```bash
# Login ke MySQL
mysql -u root -p

# Buat database
CREATE DATABASE sparepart;

# Import database
mysql -u root -p sparepart < sparepart.sql
```

Atau import melalui phpMyAdmin:
1. Buka phpMyAdmin
2. Buat database baru bernama `sparepart`
3. Import file `sparepart.sql`

#### 3. Konfigurasi Koneksi Database

Edit file koneksi di package `conn/koneksi.java`:

```java
public class koneksi {
    private static Connection conn;
    
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/sparepart";
            String user = "root";
            String password = ""; // Sesuaikan dengan password MySQL Anda
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            
            System.out.println("Koneksi Berhasil");
        } catch (Exception e) {
            System.out.println("Koneksi Gagal: " + e.getMessage());
        }
        return conn;
    }
}
```

#### 4. Struktur Project

```
PenjualanSparePart/
├── build/              # Compiled classes
├── nbproject/          # NetBeans project files
├── src/                # Source code
│   ├── conn/          # Database connection
│   │   └── koneksi.java
│   └── view/          # GUI Forms
│       ├── FrameLogin.form
│       ├── FrameLogin.java
│       ├── FrameMenu.form
│       ├── FrameMenu.java
│       ├── FramePelanggan.form
│       ├── FramePelanggan.java
│       ├── FramePenjualan.form
│       ├── FramePenjualan.java
│       ├── FramePetugas.form
│       ├── FramePetugas.java
│       ├── FrameSparepart.form
│       └── FrameSparepart.java
├── test/              # Test files
├── dist/              # Distributable files
├── images/            # Image resources
│   ├── exit2.png
│   ├── file.jpg
│   ├── toko.png
│   └── user.png
├── build.xml
├── manifest.mf
├── sparepart.sql      # Database dump
└── README.md
```

#### 5. Menjalankan Aplikasi

**Via NetBeans:**
1. Buka NetBeans IDE
2. File → Open Project
3. Pilih folder `PenjualanSparePart`
4. Klik kanan project → Run

**Via Command Line:**

```bash
# Compile
javac -cp ".;lib/mysql-connector-java.jar" src/view/*.java

# Run
java -cp ".;lib/mysql-connector-java.jar;build/classes" view.FrameLogin
```

#### 6. Login Default

```
Username: admin
Password: 123
Level: admin
```

---

## 📊 Demo Screenshots

### 🔐 Form Login

```
┌─────────────────────────────────────────────┐
│         SISTEM PENJUALAN SPAREPART          │
├─────────────────────────────────────────────┤
│                                             │
│   Username: [________________]              │
│   Password: [________________]              │
│                                             │
│         [LOGIN]        [CANCEL]             │
└─────────────────────────────────────────────┘
```

### 🖥️ Dashboard Menu Utama

```
┌─────────────────────────────────────────────┐
│      SISTEM PENJUALAN SPAREPART MOBIL       │
├─────────────────────────────────────────────┤
│ [📦] Master Sparepart  [👥] Data Pelanggan │
│ [💰] Transaksi         [🔐] Data Petugas   │
│                [🚪] Logout                 │
└─────────────────────────────────────────────┘
```

### 📦 Form Sparepart

```java
// Validasi stok sebelum penjualan
if (Integer.parseInt(stok) < Integer.parseInt(beli)) {
    JOptionPane.showMessageDialog(null, 
        "❌ Stok tidak mencukupi!\n" +
        "Stok tersedia: " + stok + "\n" +
        "Jumlah dibeli: " + beli,
        "Peringatan Stok",
        JOptionPane.WARNING_MESSAGE);
    return;
}
```

---

## 🔄 Alur Kerja Sistem

### 1. Proses Login

```
Input Credentials → Validasi Database → Cek Level → Redirect ke Menu
```

### 2. Proses Penjualan

```
Pilih Pelanggan → Pilih Sparepart → Input Jumlah → 
Validasi Stok → Hitung Subtotal → Insert Penjualan → 
Update Stok → Commit
```

### 3. Proses Update Stok

```
Ambil Data Penjualan Lama → Kembalikan Stok Lama → 
Update Data Penjualan → Kurangi Stok Baru → Commit
```

### 4. Proses Delete Penjualan

```
Ambil Data Penjualan → Kembalikan Stok → 
Delete Penjualan → Commit
```

---

## 📁 Struktur Project Detail

### Package Structure

```
src/
├── conn/
│   └── koneksi.java              # Database connection handler
│
└── view/
    ├── FrameLogin.java           # Login form & authentication
    ├── FrameMenu.java            # Main dashboard/menu
    ├── FrameSparepart.java       # Sparepart CRUD operations
    ├── FramePelanggan.java       # Customer management
    ├── FramePenjualan.java       # Sales transaction
    └── FramePetugas.java         # Staff/user management
```

### Resources

```
images/
├── exit2.png                      # Exit/logout icon
├── file.jpg                       # File icon
├── toko.png                       # Store/shop icon
└── user.png                       # User/profile icon
```

### Database

```
sparepart.sql                      # Full database dump with sample data
```

---

## 🔧 Fitur-Fitur Utama

### 1. Manajemen Sparepart
- ➕ Tambah data sparepart baru
- ✏️ Edit informasi sparepart
- 🗑️ Hapus data sparepart
- 🔍 Pencarian sparepart
- 📊 Monitoring stok real-time

### 2. Manajemen Pelanggan
- ➕ Registrasi pelanggan baru
- ✏️ Update data pelanggan
- 🗑️ Hapus data pelanggan
- 📋 Daftar pelanggan lengkap

### 3. Transaksi Penjualan
- 🛒 Input transaksi penjualan
- 💵 Kalkulasi otomatis subtotal
- 📉 Update stok otomatis
- 🧾 History transaksi
- ✏️ Edit transaksi
- 🗑️ Batal transaksi dengan pengembalian stok

### 4. Manajemen Petugas
- 👤 Tambah akun petugas
- 🔐 Pengaturan level akses
- ✏️ Update profil petugas
- 🔒 Keamanan password

---

## 🤝 Kontribusi

### Cara Berkontribusi

1. 🍴 Fork repository ini
2. 🔱 Buat branch fitur baru (`git checkout -b fitur-unggulan`)
3. 💾 Commit perubahan (`git commit -m 'Menambahkan fitur X'`)
4. 📤 Push ke branch (`git push origin fitur-unggulan`)
5. 🔃 Buat Pull Request

### Pedoman Kode

- ✅ Gunakan Java naming conventions
- 📝 Tambahkan komentar untuk logika kompleks
- 🧪 Test fitur sebelum commit
- 📚 Update dokumentasi jika ada perubahan

### Yang Bisa Dikontribusikan

- 🐛 Bug fixes
- ✨ Fitur baru
- 📖 Perbaikan dokumentasi
- 🎨 Perbaikan UI/UX
- ⚡ Optimasi performa

---

## 📄 Lisensi

```
MIT License

Copyright (c) 2024 Sistem Penjualan Sparepart

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

<div align="center">

## 🚀 Siap Mengotomatisasi Bisnis Anda?

[![Download Project](https://img.shields.io/badge/%F0%9F%93%A5_Download_Project-blue?style=for-the-badge)](#)
[![Laporkan Bug](https://img.shields.io/badge/%F0%9F%90%9B_Laporkan_Bug-red?style=for-the-badge)](#)
[![Request Fitur](https://img.shields.io/badge/%F0%9F%92%A1_Request_Fitur-green?style=for-the-badge)](#)

### ⭐ Jangan lupa beri bintang jika project ini membantu!

</div>

---

## 🛠️ Troubleshooting

### Masalah Koneksi Database
```
Error: Unable to connect to database
```
**Solusi:**
- Pastikan MySQL/MariaDB sudah berjalan
- Cek username dan password di `koneksi.java`
- Verifikasi nama database sudah benar (`sparepart`)

### Masalah JDBC Driver
```
Error: ClassNotFoundException: com.mysql.jdbc.Driver
```
**Solusi:**
- Download MySQL Connector/J
- Tambahkan ke Library project
- Atau letakkan di folder `lib/`

### Masalah Login
```
Username atau password salah
```
**Solusi:**
- Gunakan kredensial default: `admin` / `123`
- Atau cek tabel `petugas` di database

---

## 📞 Dukungan & Kontak

Pertanyaan atau masalah?


- 💬 Issues: [GitHub Issues](#)
- 📚 Dokumentasi: [Wiki](#)

---

