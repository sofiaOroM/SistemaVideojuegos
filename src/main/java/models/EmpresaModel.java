/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.EmpresaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

/**
 *
 * @author sofia
 */
public class EmpresaModel {

    public void insertarEmpresa(EmpresaDTO e) throws Exception {
        String sql = "INSERT INTO empresa(nombre_empresa, descripcion, logo) VALUES (?,?,?)";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNombreEmpresa());
            ps.setString(2, e.getDescripcion());
            if (e.getLogo() != null) {
                ps.setBytes(3, e.getLogo());
            } else {
                ps.setNull(3, Types.BLOB);
            }
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }

    public void actualizarEmpresa(EmpresaDTO e, Connection conn) throws Exception {
        String sql = "UPDATE empresa SET Nombre_empresa=?, Descripcion=? WHERE Id_empresa=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, e.getNombreEmpresa());
        ps.setString(2, e.getDescripcion());
        ps.setInt(3, e.getIdEmpresa());
        ps.executeUpdate();
    }

    public void eliminarEmpresa(int id, Connection conn) throws Exception {
        String sql = "DELETE FROM empresa WHERE Id_empresa=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
