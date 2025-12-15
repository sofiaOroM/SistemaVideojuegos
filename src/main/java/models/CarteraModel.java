/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author sofia
 */
public class CarteraModel {

    public void crearCartera(Connection conn, int idUsuario) throws Exception {

        String sql = "INSERT INTO cartera (Id_usuario, Saldo) VALUES (?, ?)";

        if (conn != null) {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idUsuario);
                ps.setBigDecimal(2, BigDecimal.ZERO);
                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error al crear cartera");
            }
        }
    }

    public void eliminarPorUsuario(Connection conn, int idUsuario) throws Exception {

        String sql = "DELETE FROM cartera WHERE Id_usuario=?";

        if (conn != null) {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idUsuario);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error al eliminar cartera");
            }
        }
    }

}
