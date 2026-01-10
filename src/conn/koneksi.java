/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class koneksi {
    private static Connection connection = null;
        public static Connection getConnection(){
            if (connection != null)
                return connection;
            else {
                String dbUrl = "jdbc:mysql://localhost:3306/sparepart?user=root&password=";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection(dbUrl);
                } catch (ClassNotFoundException | SQLException e) {
                }
                return connection;
            }
        }
    }

