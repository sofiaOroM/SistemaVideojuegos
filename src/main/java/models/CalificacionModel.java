/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.CalificacionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

/**
 *
 * @author sofia
 */
public class CalificacionModel {

    public int insertar(CalificacionDTO c) throws Exception {
        String sql = "INSERT INTO calificacion(id_usuario, id_videojuego, calificacion, fecha_calificacion) "
                + " VALUES (?,?,?,NOW())";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getIdUsuario());
            ps.setInt(2, c.getIdVideojuego());
            ps.setInt(3, c.getCalificacion());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int idCalificacion = rs.getInt(1);
            return idCalificacion;
        } finally {
            conn.close();
        }
    }

    public String listar(int idVideojuego) throws Exception {

        String sql = "SELECT id_usuario, calificacion FROM calificacion WHERE id_videojuego = ?";

        JsonArray array = new JsonArray();

        try (Connection conn = new ConnectionManager().conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVideojuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("idUsuario", rs.getInt("id_usuario"));
                obj.addProperty("calificacion", rs.getInt("calificacion"));
                array.add(obj);
            }
        }
        return array.toString();
    }

    public String promedio(int idVideojuego) throws Exception {

        String sql = "SELECT AVG(calificacion) promedio FROM calificacion WHERE id_videojuego = ?";

        try (Connection conn = new ConnectionManager().conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVideojuego);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return "{\"promedio\":" + rs.getDouble("promedio") + "}";
            }
            return "{\"promedio\":0}";
        }
    }
}
