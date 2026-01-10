/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import conn.koneksi;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author USER
 */
public class FramePenjualan extends javax.swing.JFrame {
    Connection connection;
    DefaultTableModel model;
    
    public FramePenjualan() {
        initComponents();
        connection = koneksi.getConnection();
     
        // buat txt gk bisa di edit
        txtHargaSatuan.setEditable(false);
        txtTotal.setEditable(false);
        
        // panggil method baca isi item ComboBox pelanggan
        loadPelanggan();
        // panggil method baca isi item ComboBox sparepart
        loadSparepart();
        // panggil method
        loadTable();
    }

    
    // method baca record di tabel prodi
    private void loadTable(){
        model = new DefaultTableModel();
        model.addColumn("ID");               // 0
        model.addColumn("Pelanggan");        // 1
        model.addColumn("Sparepart");        // 2
        model.addColumn("Beli");             // 3
        model.addColumn("Harga");            // 4
        model.addColumn("Total");            // 5

        try {
            String sql = """
                SELECT pj.id_penjualan, p.nama, s.nama_sparepart,
                    pj.beli, s.harga_satuan, pj.subtotal
                FROM penjualan pj
                JOIN pelanggan p ON pj.id_pelanggan = p.id_pelanggan
                JOIN sparepart s ON pj.id_sparepart = s.id_sparepart
            """;

            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()){
                model.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getInt(6)
                });
            }
            table.setModel(model);

            // sembunyikan kolom ID
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    
    // Method ambil data dari tabel pelanggan untuk isi ComboBox
    private void loadPelanggan() {
        try {
            String sql = "SELECT id_pelanggan, nama FROM pelanggan";
            var rs = connection.createStatement().executeQuery(sql);
                    
            cmbPelanggan.removeAllItems();
            while (rs.next()){
                cmbPelanggan.addItem(
                    rs.getInt("id_pelanggan") + " - " + rs.getString("nama")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method ambil data dari tabel sparepart untuk isi ComboBox
    private void loadSparepart() {
        try {
            String sql = "SELECT id_sparepart, nama_sparepart FROM sparepart";
            var rs = connection.createStatement().executeQuery(sql);
                    
            cmbSparepart.removeAllItems();
            while (rs.next()){
                cmbSparepart.addItem(
                    rs.getInt("id_sparepart") + " - " + rs.getString("nama_sparepart")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // cek stok
    private boolean cekStok(int idSparepart, int beli){
        try {
            String sql = "SELECT stok FROM sparepart WHERE id_sparepart=?";
            var ps = connection.prepareStatement(sql);
            ps.setInt(1, idSparepart);
            var rs = ps.executeQuery();
            
            if (rs.next()) {
                int stok = Integer.parseInt(rs.getString("stok"));
                return stok >= beli;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getBeliLama(int idPenjualan) throws SQLException {
        String sql = "SELECT beli FROM penjualan WHERE id_penjualan=?";
        var ps = connection.prepareStatement(sql);
        ps.setInt(1, idPenjualan);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    private void tambahStok(int idSparepart, int qty) throws SQLException {
        String sql = "UPDATE sparepart SET stok = stok + ? WHERE id_sparepart=?";
        var ps = connection.prepareStatement(sql);
        ps.setInt(1, qty);
        ps.setInt(2, idSparepart);
        ps.executeUpdate();
    }

    
    private void kurangiStok(int idSparepart, int beli)throws Exception {
        String sql = "UPDATE sparepart SET stok = stok - ? WHERE id_sparepart = ?";
        var ps = connection.prepareStatement(sql);
        ps.setInt(1, beli);
        ps.setInt(2, idSparepart);
        ps.executeUpdate();

    }
    
    // Method reset isian data di textfield dan nonaktifkan button simpan
    private void resetForm() {
        txtBeli.setText("");
        txtHargaSatuan.setText("");
        txtTotal.setText("");
    }
    
    private void cari(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Nama Sparepart");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");
        
        try {
            String keyword = txtCari.getText();
            
            String sql = """
                SELECT pj.id_penjualan, p.nama, s.nama_sparepart, pj.beli, s.harga_satuan, pj.subtotal
                FROM penjualan pj
                JOIN pelanggan p ON pj.id_pelanggan = p.id_pelanggan
                JOIN sparepart s ON pj.id_sparepart = s.id_sparepart
                WHERE p.nama LIKE ? OR s.nama_sparepart LIKE ?
            """;
            
            var ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            var rs = ps.executeQuery();
            
            while (rs.next()){
                model.addRow(new Object []{
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6),
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtHargaSatuan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbPelanggan = new javax.swing.JComboBox<>();
        cmbSparepart = new javax.swing.JComboBox<>();
        txtBeli = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Penjualan");
        setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setText("Data Penjualan Toko Sparepart - \"Serang Jaya\"");

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel2.setText("Pilih Pelanggan");

        jLabel3.setText("Pilih Sparepart Yang Ingin Dibeli");

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel5.setText("Jumlah Beli");

        jLabel6.setText("Harga Satuan");

        jLabel7.setText("Total yang Harus Dibayar");

        cmbPelanggan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbSparepart.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSparepartActionPerformed(evt);
            }
        });

        txtBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBeliActionPerformed(evt);
            }
        });
        txtBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBeliKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addComponent(txtHargaSatuan)
                    .addComponent(cmbPelanggan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbSparepart, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBeli)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTotal)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHargaSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUbah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Cari Data");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nama Pelanggan", "Nama Sparepart", "Jumlah Beli", "Harga Satuan", "Total"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtCari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(402, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
        txtCari.setText("");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        if (!txtCari.getText().trim().isEmpty()) {
            model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            // panggil method
            cari();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Masukan nama mahasiswa yang ingin dicari!",
                "Notifikasi",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.getSelectedRow();

        cmbPelanggan.setSelectedItem(
            table.getValueAt(row, 1).toString()
        );
        cmbSparepart.setSelectedItem(
            table.getValueAt(row, 2).toString()
        );
        txtBeli.setText(table.getValueAt(row, 3).toString());
        txtHargaSatuan.setText(table.getValueAt(row, 4).toString());
        txtTotal.setText(table.getValueAt(row, 5).toString());
        
        cmbPelanggan.setEnabled(false);
        cmbSparepart.setEnabled(false);
        btnSimpan.setEnabled(false);
    }                                  
    
    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        resetForm();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        int idPenjualan = (int) table.getValueAt(row, 0);

        try {
            connection.setAutoCommit(false);

            int idSparepart = Integer.parseInt(
                cmbSparepart.getSelectedItem().toString().split(" - ")[0]
            );

            int beliBaru = Integer.parseInt(txtBeli.getText());
            int beliLama = getBeliLama(idPenjualan);

            int selisih = beliBaru - beliLama;

            if (selisih > 0) {
                // beli naik → stok berkurang
                if (!cekStok(idSparepart, selisih)) {
                    JOptionPane.showMessageDialog(this, "Stok tidak mencukupi!");
                    return;
                }
                kurangiStok(idSparepart, selisih);
            } else if (selisih < 0) {
                // beli turun → stok bertambah
                tambahStok(idSparepart, Math.abs(selisih));
            }

            String sql = """
                UPDATE penjualan
                SET beli=?, subtotal=?
                WHERE id_penjualan=?
            """;

            var ps = connection.prepareStatement(sql);
            ps.setInt(1, beliBaru);
            ps.setInt(2, Integer.parseInt(txtTotal.getText()));
            ps.setInt(3, idPenjualan);
            ps.executeUpdate();

            connection.commit();

            JOptionPane.showMessageDialog(this, "Data berhasil diubah");
            resetForm();
            loadTable();
            cmbPelanggan.setEnabled(true);
            cmbSparepart.setEnabled(true);
            btnSimpan.setEnabled(true);

        } catch (Exception e) {
            try { connection.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengubah data!");
        } finally {
            try { connection.setAutoCommit(true); } catch (Exception e) {}
        }
    }                                       

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        int idPenjualan = (int) table.getValueAt(row, 0);

        try {
            connection.setAutoCommit(false);

            // ambil data lama
            int beliLama = getBeliLama(idPenjualan);

            int idSparepart = Integer.parseInt(
                cmbSparepart.getSelectedItem().toString().split(" - ")[0]
            );

            // stok balik
            tambahStok(idSparepart, beliLama);

            // hapus penjualan
            String sql = "DELETE FROM penjualan WHERE id_penjualan=?";
            var ps = connection.prepareStatement(sql);
            ps.setInt(1, idPenjualan);
            ps.executeUpdate();

            connection.commit();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
            resetForm();
            loadTable();
            cmbPelanggan.setEnabled(true);
            cmbSparepart.setEnabled(true);
            btnSimpan.setEnabled(true);

        } catch (Exception e) {
            try { connection.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menghapus data!");
        } finally {
            try { connection.setAutoCommit(true); } catch (Exception e) {}
        }
    }                                        

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try {
            connection.setAutoCommit(false);
            
            int idPelanggan = Integer.parseInt(
                cmbPelanggan.getSelectedItem().toString().split(" - ")[0]
            );
            int idSparepart = Integer.parseInt(
                cmbSparepart.getSelectedItem().toString().split(" - ")[0]
            );
            int beli = Integer.parseInt(txtBeli.getText());
            int total = Integer.parseInt(txtTotal.getText());
            
            // cek stok
            if (!cekStok(idSparepart, beli)){
                connection.rollback();
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi");
                return;
            }
            
            String sql = "INSERT INTO penjualan (id_pelanggan, id_sparepart, beli, subtotal) VALUES (?,?,?,?)";
            var ps = connection.prepareStatement(sql);
            ps.setInt(1, idPelanggan);
            ps.setInt(2, idSparepart);
            ps.setInt(3, beli);
            ps.setInt(4, total);
            ps.executeUpdate();
            
            //kurangi stok
            kurangiStok(idSparepart, beli);
            
            connection.commit();
            JOptionPane.showMessageDialog(this, "Data penjualan berhasil disimpan & stok berhasil diperbarui");
            loadTable();
            resetForm();

        } catch (Exception e){
            try {
                connection.rollback();
            } catch (Exception ex){}
            JOptionPane.showMessageDialog(this, "Gagal simpan! "+ e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {}
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBeliActionPerformed
    // Method auto Harga saat Sparepart dipilih
    private void cmbSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSparepartActionPerformed
        if (cmbSparepart.getSelectedItem() == null){
            return;
        }
        try {
            String item = cmbSparepart.getSelectedItem().toString();
            String id = item.split(" - ")[0];
            
            String sql = "SELECT harga_satuan FROM sparepart WHERE id_sparepart=?";
            var ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            var rs = ps.executeQuery();
            
            if (rs.next()){
                txtHargaSatuan.setText(rs.getString("harga_satuan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cmbSparepartActionPerformed

    private void txtBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBeliKeyReleased
        try {
            int beli = Integer.parseInt(txtBeli.getText());
            int harga = Integer.parseInt(txtHargaSatuan.getText());
            txtTotal.setText(String.valueOf(beli * harga));
        } catch (Exception e) {
            txtTotal.setText("0");
        }
    }//GEN-LAST:event_txtBeliKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FramePenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FramePenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cmbPelanggan;
    private javax.swing.JComboBox<String> cmbSparepart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtBeli;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHargaSatuan;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
