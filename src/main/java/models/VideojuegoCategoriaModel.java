/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author sofia
 */
public class VideojuegoCategoriaModel {

    public void insertarCategorias(Connection conn, int idVideojuego, List<Integer> categorias)
            throws Exception {

        String sql = "INSERT INTO videojuego_categoria (Id_videojuego, Id_categoria) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (Integer idCategoria : categorias) {
            ps.setInt(1, idVideojuego);
            ps.setInt(2, idCategoria);
            ps.addBatch();
        }

        ps.executeBatch();
    }

    public void eliminarPorVideojuego(Connection conn, int idVideojuego) throws Exception {
        String sql = "DELETE FROM videojuego_categoria WHERE Id_videojuego=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idVideojuego);
        ps.executeUpdate();
    }
}
