/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.ComentarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

/**
 *
 * @author sofia
 */
public class ComentarioModel {

    public int insertar(ComentarioDTO c) throws Exception {
        String sql = "INSERT INTO comentario(id_usuario, id_videojuego, id_comentario_padre, comentario, fecha_comentario) "
                + "VALUES (?,?,?,?,NOW())";

        Connection conn = new ConnectionManager().conectar();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, c.getIdUsuario());
        ps.setInt(2, c.getIdVideojuego());

        if (c.getIdComentarioPadre() != null) {
            ps.setInt(3, c.getIdComentarioPadre());
        } else {
            ps.setNull(3, Types.INTEGER);
        }

        ps.setString(4, c.getComentario());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int idComentario = rs.getInt(1);

        rs.close();
        ps.close();
        conn.close();

        return idComentario;
    }

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
        try (Connection conn = new ConnectionManager().conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

    public String listarPorVideojuego(int idVideojuego) throws Exception {
        String sql = "SELECT id_comentario, id_usuario, comentario, fecha_comentario "
                + "FROM comentario WHERE id_videojuego = ? ORDER BY fecha_comentario DESC";

        JsonArray array = new JsonArray();

        try (Connection conn = new ConnectionManager().conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVideojuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("idComentario", rs.getInt("id_comentario"));
                obj.addProperty("idUsuario", rs.getInt("id_usuario"));
                obj.addProperty("comentario", rs.getString("comentario"));
                obj.addProperty("fechaComentario", rs.getDate("fecha_comentario").toString());
                array.add(obj);
            }
        }
        return array.toString();
    }

}
