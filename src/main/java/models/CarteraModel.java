/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    
    public void recargar(int id, BigDecimal cantidad) throws Exception{
        String sql = "UPDATE cartera SET saldo=? WHERE id_cartera=?";
        
        Connection conn = new ConnectionManager().conectar();
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setBigDecimal(2, cantidad);
            
        }
    }

}
