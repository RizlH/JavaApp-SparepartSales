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
✅ **Keamanan Data** dengan transaksi database yang konsisten  
✅ **Efisiensi Operasional** dengan pengurangan kesalahan manual  
✅ **Laporan Real-time** ketersediaan stok

---

## ✨ Fitur Unggulan

### 🔧 Manajemen Data

| Fitur | Deskripsi | Ikon |
|-------|-----------|------|
| CRUD Sparepart | Tambah, lihat, edit, hapus data sparepart | 📦 |
| CRUD Pelanggan | Kelola database pelanggan | 👥 |
| CRUD Penjualan | Kelola transaksi penjualan | 💰 |
| Pencarian Cerdas | Temukan data dengan cepat | 🔍 |

### 📊 Manajemen Stok Otomatis

| Aksi | Perilaku Sistem | Status Stok |
|------|----------------|-------------|
| Penjualan Baru | Stok otomatis berkurang | ⬇️ -beli |
| Hapus Penjualan | Stok otomatis kembali | ⬆️ +beli |
| Edit Penjualan | Stok disesuaikan otomatis | 🔄 Selisih |
| Stok Habis | Peringatan otomatis | 🚫 Validasi |

### 🛡️ Fitur Keamanan

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
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│            BUSINESS LAYER                   │
│   Logic: Stok, Validasi, Transaksi         │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│            DATA ACCESS LAYER                │
│   JDBC, PreparedStatement, Connection      │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│            DATABASE LAYER                   │
│   MySQL dengan Relasi Tabel                │
└─────────────────────────────────────────────┘
```

---

## 🗄️ Struktur Database

### 📦 Tabel sparepart

```sql
CREATE TABLE sparepart (
    id_sparepart INT PRIMARY KEY AUTO_INCREMENT,
    nama_sparepart VARCHAR(100) NOT NULL,
    stok INT DEFAULT 0,
    harga INT NOT NULL CHECK (harga >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_nama (nama_sparepart)
) ENGINE=InnoDB;
```

### 👥 Tabel pelanggan

```sql
CREATE TABLE pelanggan (
    id_pelanggan INT PRIMARY KEY AUTO_INCREMENT,
    nama VARCHAR(100) NOT NULL,
    alamat TEXT NOT NULL,
    no_telp VARCHAR(15),
    email VARCHAR(100),
    INDEX idx_nama (nama)
) ENGINE=InnoDB;
```

### 💰 Tabel penjualan

```sql
CREATE TABLE penjualan (
    id_penjualan INT PRIMARY KEY AUTO_INCREMENT,
    id_pelanggan INT NOT NULL,
    id_sparepart INT NOT NULL,
    beli INT NOT NULL CHECK (beli > 0),
    subtotal INT NOT NULL,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pelanggan) 
        REFERENCES pelanggan(id_pelanggan) 
        ON DELETE RESTRICT,
    FOREIGN KEY (id_sparepart) 
        REFERENCES sparepart(id_sparepart) 
        ON DELETE RESTRICT,
    INDEX idx_tanggal (tanggal),
    INDEX idx_pelanggan (id_pelanggan)
) ENGINE=InnoDB;
```

---

## ⚡ Instalasi & Konfigurasi

### 📋 Prasyarat

- Java Development Kit (JDK) 8 atau lebih tinggi
- MySQL Server 5.7+
- IDE (NetBeans, IntelliJ IDEA, atau Eclipse)
- MySQL Connector/J (JDBC Driver)

### 🚀 Langkah Instalasi

#### 1. Setup Database

```bash
# Import database
mysql -u root -p < database_setup.sql
```

#### 2. Konfigurasi Koneksi

```java
// File: DatabaseConfig.java
public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/sparepart_db";
    private static final String USER = "root";
    private static final String PASS = "password";
    
    public static Connection getConnection() {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
```

#### 3. Struktur Project

```
sparepart-sales-system/
├── src/
│   ├── model/          # Model classes
│   ├── dao/            # Data Access Objects
│   ├── service/        # Business logic
│   ├── ui/             # Swing interfaces
│   └── util/           # Utilities
├── lib/                # External libraries
├── docs/               # Documentation
└── README.md
```

#### 4. Menjalankan Aplikasi

```bash
# Compile
javac -cp ".;lib/mysql-connector-java.jar" src/*.java

# Run
java -cp ".;lib/mysql-connector-java.jar" src.MainApp
```

---

## 📊 Demo Screenshots

### 🖥️ Dashboard Utama

```
┌─────────────────────────────────────────────┐
│   SISTEM PENJUALAN SPAREPART MOBIL         │
├─────────────────────────────────────────────┤
│  [📊] Dashboard        [📦] Sparepart      │
│  [👥] Pelanggan        [💰] Penjualan      │
│  [📈] Laporan          [⚙️] Pengaturan     │
└─────────────────────────────────────────────┘
```

### 📦 Form Sparepart

```java
// Input validation example
if (stok < beli) {
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

### 1. Proses Penjualan

```
User Input → Validasi Stok → Update Penjualan → Update Stok → Commit
```

### 2. Proses Update/Delete

```
Ambil Data Lama → Kembalikan Stok → Update/Delete → Kurangi Stok Baru → Commit
```

---

## 📁 Struktur Project

### Core Packages

```java
com.sparepart.sales
├── Main.java                    // Entry point
├── config/
│   └── DatabaseConfig.java      // Database connection
├── models/
│   ├── Sparepart.java          // Sparepart entity
│   ├── Customer.java           // Customer entity
│   └── Sale.java               // Sale entity
├── dao/
│   ├── SparepartDAO.java       // CRUD operations
│   ├── CustomerDAO.java
│   └── SaleDAO.java
├── services/
│   ├── StockService.java       // Stock management
│   └── TransactionService.java // Sale transactions
├── ui/
│   ├── MainFrame.java          // Main window
│   ├── SparepartForm.java      // Sparepart form
│   ├── CustomerForm.java       // Customer form
│   └── SaleForm.java           // Sale form
└── utils/
    ├── Validator.java          // Input validation
    └── Formatter.java          // Data formatting
```

---

## 🤝 Kontribusi

### Cara Berkontribusi

1. Fork repository
2. Buat branch fitur baru (`git checkout -b fitur-unggulan`)
3. Commit perubahan (`git commit -m 'Menambahkan fitur X'`)
4. Push ke branch (`git push origin fitur-unggulan`)
5. Buat Pull Request

### Pedoman Kode

- Gunakan Java naming conventions
- Tambahkan komentar Javadoc
- Test sebelum commit
- Update documentation

---

## 📄 Lisensi

```
MIT License

Copyright (c) 2024 Sistem Penjualan Sparepart

Izin diberikan, secara gratis, kepada siapa pun yang memperoleh salinan
perangkat lunak ini dan file dokumentasi terkait untuk mengolahnya tanpa batasan.
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

## 📞 Dukungan & Kontak

Pertanyaan atau masalah?

- 📧 Email: support@sparepartsales.com
- 💬 Issues: [GitHub Issues](#)
- 📚 Dokumentasi: [Wiki](#)

---

