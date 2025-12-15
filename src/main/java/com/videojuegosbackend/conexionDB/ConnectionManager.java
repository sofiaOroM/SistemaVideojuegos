/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegosbackend.conexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sofia
 */
public class ConnectionManager {

    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/empvideodb";
    private String usuario = "root";
    private String password = "sofia2808";

    public Connection conectar() {
        try {
            // Establecer la conexión
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión exitosa");
            return conn;
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void desconectar(Connection c) {
        if (c != null) {
            try {
                c.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
