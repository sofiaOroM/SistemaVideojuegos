/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.ComisionGlobalDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author sofia
 */
public class ComisionGlobalModel {

    public ComisionGlobalDTO obtenerActiva() throws Exception {
        String sql = "SELECT * FROM comision_global WHERE fecha_inicio <= CURDATE() "
                + "AND (fecha_fin IS NULL OR fecha_fin >= CURDATE()) LIMIT 1";

        try (Connection conn = new ConnectionManager().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                ComisionGlobalDTO c = new ComisionGlobalDTO();
                c.setIdComision(rs.getInt("id_comision"));
                c.setPorcentaje(rs.getBigDecimal("porcentaje"));
                c.setFechaInicio(rs.getDate("fecha_inicio"));
                c.setFechaFin(rs.getDate("fecha_fin"));
                return c;
            }
            return null;
        }
    }

    public void cerrarComisionActiva(Connection conn) throws Exception {
        String sql = "UPDATE comision_global SET fecha_fin = CURDATE() - "
                + "INTERVAL 1 DAY WHERE fecha_fin IS NULL";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    public void insertar(ComisionGlobalDTO c, Connection conn) throws Exception {
        String sql = "INSERT INTO comision_global(porcentaje, fecha_inicio, fecha_fin) "
                + "VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, c.getPorcentaje());
            ps.setDate(2, c.getFechaInicio());
            ps.setDate(3, c.getFechaFin());
            ps.executeUpdate();
        }
    }
}
