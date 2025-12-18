/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.ComisionEmpresaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author sofia
 */
public class ComisionEmpresaModel {

    public ComisionEmpresaDTO obtenerActiva(int idEmpresa) throws Exception {
        String sql = "SELECT * FROM empresa_comision WHERE id_empresa = ? "
                + "AND fecha_inicio <= CURDATE() AND (fecha_fin IS NULL OR "
                + "fecha_fin >= CURDATE()) LIMIT 1";

        try (Connection conn = new ConnectionManager().conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ComisionEmpresaDTO c = new ComisionEmpresaDTO();
                    c.setIdComisionEmpresa(rs.getInt("id_comision_empresa"));
                    c.setIdEmpresa(rs.getInt("id_empresa"));
                    c.setPorcentaje(rs.getBigDecimal("porcentaje"));
                    c.setFechaInicio(rs.getDate("fecha_inicio"));
                    c.setFechaFin(rs.getDate("fecha_fin"));
                    return c;
                }
            }
        }
        return null;
    }

    public void cerrarComisionActiva(int idEmpresa, Connection conn) throws Exception {
        String sql = "UPDATE empresa_comision fecha_fin = CURDATE() - INTERVAL 1 DAY "
                + "WHERE id_empresa = ? AND fecha_fin IS NULL";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEmpresa);
            ps.executeUpdate();
        }
    }

    public void insertar(ComisionEmpresaDTO c, Connection conn) throws Exception {
        String sql = "INSERT INTO empresa_comision(id_empresa, porcentaje, fecha_inicio, fecha_fin) "
                + "VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getIdEmpresa());
            ps.setBigDecimal(2, c.getPorcentaje());
            ps.setDate(3, c.getFechaInicio());
            ps.setDate(4, c.getFechaFin());
            ps.executeUpdate();
        }
    }
}

