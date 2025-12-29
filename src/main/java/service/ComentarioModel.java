/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author sofia
 */
public class ComentarioModel {

    public void activarComentario(int idVideojuego, Connection conn) throws Exception {

        String sql = "INSERT INTO visibilidad_comentario(id_videojuego, activo) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVideojuego);
            ps.setBoolean(2, true);
            ps.executeUpdate();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
    }

    public boolean obtenerEstado(int idVideojuego) throws Exception {
        String sql = "SELECT activo FROM visibilidad_comentario WHERE id_videojuego = ?";
        try (Connection conn = new ConnectionManager().conectar(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVideojuego);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("activo");
            }
            return true;
        }
    }

    public void actualizar(int idVideojuego) throws Exception {

        String sqlSelect = "SELECT activo FROM visibilidad_comentario WHERE id_videojuego = ?";
        String sqlUpdate = "UPDATE visibilidad_comentario SET activo = ? WHERE id_videojuego = ?";

        Connection conn = new ConnectionManager().conectar();
        try {
            conn.setAutoCommit(false);
            boolean estadoActual;
            try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect)) {
                psSelect.setInt(1, idVideojuego);

                try (ResultSet rs = psSelect.executeQuery()) {
                    if (!rs.next()) {
                        throw new Exception("No existe visibilidad para el videojuego");
                    }
                    estadoActual = rs.getBoolean("activo");
                }
            }

            boolean nuevoEstado = !estadoActual;

            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setBoolean(1, nuevoEstado);
                psUpdate.setInt(2, idVideojuego);
                psUpdate.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

}
