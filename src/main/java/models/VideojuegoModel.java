/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.VideojuegoDTO;
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
public class VideojuegoModel {

    public int insertar(VideojuegoDTO v) throws Exception {
        String sql = "INSERT INTO videojuego(Titulo_videojuego, Descripcion, Precio_videojuego, Clasificacion, "
                + "Requisitos, Imagen_principal, Fecha_lanzamiento, Activo, Id_empresa) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection conn = new ConnectionManager().conectar();
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, v.getTitulo());
                ps.setString(2, v.getDescripcion());
                ps.setBigDecimal(3, v.getPrecio());
                ps.setString(4, v.getClasificacion());
                ps.setString(5, v.getRequisitos());
                ps.setBytes(6, v.getImagenPrincipal());
                ps.setDate(7, v.getFechaLanzamiento());
                ps.setBoolean(8, v.isActivo());
                ps.setInt(9, v.getIdEmpresa());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int idVideojuego = rs.getInt(1);

                if (v.getCategorias() != null) {
                    String sqlCat = "INSERT INTO videojuego_categoria(Id_videojuego, Id_categoria) VALUES (?,?)";
                    try (PreparedStatement psCat = conn.prepareStatement(sqlCat)) {
                        for (Integer idCat : v.getCategorias()) {
                            psCat.setInt(1, idVideojuego);
                            psCat.setInt(2, idCat);
                            psCat.addBatch();
                        }
                        psCat.executeBatch();
                    }
                }

                conn.commit();
                return idVideojuego;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public VideojuegoDTO obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM videojuego WHERE Id_videojuego=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                VideojuegoDTO v = new VideojuegoDTO();
                v.setIdVideojuego(rs.getInt("id_videojuego"));
                v.setTitulo(rs.getString("titulo_videojuego"));
                v.setDescripcion(rs.getString("descripcion"));
                v.setPrecio(rs.getBigDecimal("precio_videojuego"));
                v.setClasificacion(rs.getString("clasificacion"));
                v.setRequisitos(rs.getString("requisitos"));
                v.setImagenPrincipal(rs.getBytes("imagen_principal"));
                v.setFechaLanzamiento(rs.getDate("fecha_lanzamiento"));
                v.setActivo(rs.getBoolean("activo"));
                v.setIdEmpresa(rs.getInt("id_empresa"));

                // Obtener categorías
                String sqlCat = "SELECT id_categoria FROM videojuego_categoria WHERE id_videojuego=?";
                try (PreparedStatement psCat = conn.prepareStatement(sqlCat)) {
                    psCat.setInt(1, id);
                    ResultSet rsCat = psCat.executeQuery();
                    List<Integer> categorias = new ArrayList<>();
                    while (rsCat.next()) {
                        categorias.add(rsCat.getInt("id_categoria"));
                    }
                    v.setCategorias(categorias);
                }

                return v;
            }
            return null;
        } finally {
            conn.close();
        }
    }

    public List<VideojuegoDTO> obtenerTodos() throws Exception {
        String sql = "SELECT * FROM videojuego";
        Connection conn = new ConnectionManager().conectar();
        List<VideojuegoDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VideojuegoDTO v = new VideojuegoDTO();
                v.setIdVideojuego(rs.getInt("id_videojuego"));
                v.setTitulo(rs.getString("titulo_videojuego"));
                v.setDescripcion(rs.getString("descripcion"));
                v.setPrecio(rs.getBigDecimal("precio_videojuego"));
                v.setClasificacion(rs.getString("clasificacion"));
                v.setRequisitos(rs.getString("requisitos"));
                v.setImagenPrincipal(rs.getBytes("imagen_principal"));
                v.setFechaLanzamiento(rs.getDate("fecha_lanzamiento"));
                v.setActivo(rs.getBoolean("activo"));
                v.setIdEmpresa(rs.getInt("id_empresa"));
                lista.add(v);
            }
            return lista;
        } finally {
            conn.close();
        }
    }

    public void actualizar(VideojuegoDTO v) throws Exception {
        String sql = "UPDATE videojuego SET Titulo_videojuego=?, Descripcion=?, Precio_videojuego=?, Clasificacion=?, "
                + "Requisitos=?, Imagen_principal=?, Fecha_lanzamiento=?, Activo=?, Id_empresa=? WHERE Id_videojuego=?";
        Connection conn = new ConnectionManager().conectar();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, v.getTitulo());
                ps.setString(2, v.getDescripcion());
                ps.setBigDecimal(3, v.getPrecio());
                ps.setString(4, v.getClasificacion());
                ps.setString(5, v.getRequisitos());
                ps.setBytes(6, v.getImagenPrincipal());
                ps.setDate(7, v.getFechaLanzamiento());
                ps.setBoolean(8, v.isActivo());
                ps.setInt(9, v.getIdEmpresa());
                ps.setInt(10, v.getIdVideojuego());
                ps.executeUpdate();

                // Actualizar categorías: eliminar todas y volver a insertar
                String sqlDel = "DELETE FROM videojuego_categoria WHERE Id_videojuego=?";
                try (PreparedStatement psDel = conn.prepareStatement(sqlDel)) {
                    psDel.setInt(1, v.getIdVideojuego());
                    psDel.executeUpdate();
                }

                if (v.getCategorias() != null) {
                    String sqlCat = "INSERT INTO videojuego_categoria(Id_videojuego, Id_categoria) VALUES (?,?)";
                    try (PreparedStatement psCat = conn.prepareStatement(sqlCat)) {
                        for (Integer idCat : v.getCategorias()) {
                            psCat.setInt(1, v.getIdVideojuego());
                            psCat.setInt(2, idCat);
                            psCat.addBatch();
                        }
                        psCat.executeBatch();
                    }
                }

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void eliminar(int id) throws Exception {
        String sqlDel = "DELETE FROM videojuego_categoria WHERE Id_videojuego=?";
        String sql = "DELETE FROM videojuego WHERE Id_videojuego=?";
        Connection conn = new ConnectionManager().conectar();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement psDel = conn.prepareStatement(sqlDel); 
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                psDel.setInt(1, id);
                psDel.executeUpdate();

                ps.setInt(1, id);
                ps.executeUpdate();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}
