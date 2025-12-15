/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dto.CategoriaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author sofia
 */
public class CategoriaModel {

    public void insertarCategoria(CategoriaDTO c, Connection conn) throws Exception {
        String sql = "INSERT INTO categoria (Nombre_categoria) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, c.getNombreCategoria());
        ps.executeUpdate();
    }

    public void actualizarCategoria(CategoriaDTO c, Connection conn) throws Exception {
        String sql = "UPDATE categoria SET Nombre_categoria=? WHERE Id_categoria=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, c.getNombreCategoria());
        ps.setInt(2, c.getIdCategoria());
        ps.executeUpdate();
    }

    public void eliminarCategoria(int id, Connection conn) throws Exception {
        String sql = "DELETE FROM categoria WHERE Id_categoria=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
