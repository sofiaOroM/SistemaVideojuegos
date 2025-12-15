/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.CategoriaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class CategoriaModel {

    public int insertar(CategoriaDTO c) throws Exception {
        String sql = "INSERT INTO categoria(nombre_categoria) VALUES (?)";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNombreCategoria());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } finally {
            conn.close();
        }
    }

    public CategoriaDTO obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM categoria WHERE id_categoria=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CategoriaDTO c = new CategoriaDTO();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNombreCategoria(rs.getString("nombre_categoria"));
                return c;
            }
            return null;
        } finally {
            conn.close();
        }
    }

    public List<CategoriaDTO> obtenerTodos() throws Exception {
        String sql = "SELECT * FROM categoria";
        Connection conn = new ConnectionManager().conectar();
        List<CategoriaDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoriaDTO c = new CategoriaDTO();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(c);
            }
            return lista;
        } finally {
            conn.close();
        }
    }

    public void actualizar(CategoriaDTO c) throws Exception {
        String sql = "UPDATE categoria SET nombre_categoria=? WHERE id_categoria=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombreCategoria());
            ps.setInt(2, c.getIdCategoria());
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM categoria WHERE id_categoria=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }
}
