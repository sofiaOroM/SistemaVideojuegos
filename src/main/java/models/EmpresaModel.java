/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dto.EmpresaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author sofia
 */
public class EmpresaModel {

    public void insertarEmpresa(EmpresaDTO e, Connection conn) throws Exception {
        String sql = "INSERT INTO empresa (Nombre_empresa, Descripcion) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, e.getNombreEmpresa());
        ps.setString(2, e.getDescripcion());
        ps.executeUpdate();
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