/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.CarteraDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author sofia
 */
public class CarteraModel {

    public void crearCartera(int idUsuario, Connection conn) throws Exception {

        String sql = "INSERT INTO cartera (id_usuario, saldo) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setBigDecimal(2, new BigDecimal("0.00"));
            ps.executeUpdate();
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

    public CarteraDTO obtenerCarteraUsuario(int idUsuario) throws Exception {
        String sql = "SELECT * FROM cartera WHERE Id_usuario=?";

        CarteraDTO c = new CarteraDTO();
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c.setIdCartera(rs.getInt("id_cartera"));
                c.setIdUsuario(rs.getInt("id_usuario"));
                c.setSaldo(rs.getBigDecimal("saldo"));

            }
            return c;
        } finally {
            conn.close();
        }
    }

    public void recargarPorUsuario(int idUsuario, BigDecimal cantidad) throws Exception {
        String sql = "UPDATE cartera SET saldo = saldo + ? WHERE id_usuario = ?";

        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, cantidad);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }

}
