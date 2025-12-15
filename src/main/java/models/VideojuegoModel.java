/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dto.VideojuegoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author sofia
 */
public class VideojuegoModel {

    public void insertarVideojuego(VideojuegoDTO videojuego, Connection conn) throws Exception {

        String sql = "INSERT INTO videojuego(Titulo_videojuego, Descripcion, Precio_videojuego,"
                + "Clasificacion, Requisitos, Imagen_principal, Fecha_lanzamiento, "
                + "Activo, Id_empresa) VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, videojuego.getTitulo());
        ps.setString(2, videojuego.getDescripcion());
        ps.setBigDecimal(3, videojuego.getPrecio());
        ps.setString(4, videojuego.getClasificacion());
        ps.setString(5, videojuego.getRequisitos());
        ps.setBytes(6, videojuego.getImagenPrincipal());
        ps.setDate(7, videojuego.getFechaLanzamiento());
        ps.setBoolean(8, videojuego.isActivo());
        ps.setInt(9, videojuego.getIdEmpresa());

        ps.executeUpdate();
    }

    public void actualizarVideojuego(VideojuegoDTO videojuego, Connection conn) throws Exception {

        String sql = "UPDATE videojuego SET Titulo_videojuego=?, Descripcion=?, Precio_videojuego=?,"
                + " Clasificacion=?, Requisitos=?, Fecha_lanzamiento=?, Activo=?"
                + " WHERE Id_videojuego=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, videojuego.getTitulo());
        ps.setString(2, videojuego.getDescripcion());
        ps.setBigDecimal(3, videojuego.getPrecio());
        ps.setString(4, videojuego.getClasificacion());
        ps.setString(5, videojuego.getRequisitos());
        ps.setDate(6, videojuego.getFechaLanzamiento());
        ps.setBoolean(7, videojuego.isActivo());
        ps.setInt(8, videojuego.getIdVideojuego());

        ps.executeUpdate();
    }

    public void actualizarImagen(Connection conn, int idVideojuego, byte[] imagen)
            throws Exception {

        String sql = "UPDATE videojuego SET Imagen_principal=? WHERE Id_videojuego=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setBytes(1, imagen);
        ps.setInt(2, idVideojuego);
        ps.executeUpdate();
    }

    public void eliminarVideojuego(int id, Connection conn) throws Exception {
        String sql = "DELETE FROM videojuego WHERE Id_videojuego=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public int insertarYRetornarId(VideojuegoDTO v, Connection conn) throws Exception {

        String sql = "INSERT INTO videojuego (Titulo_videojuego, Descripcion, Precio_videojuego, Clasificacion,"
                + " Requisitos, Fecha_lanzamiento, Activo, Id_empresa)"
                + " VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(
                sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, v.getTitulo());
        ps.setString(2, v.getDescripcion());
        ps.setBigDecimal(3, v.getPrecio());
        ps.setString(4, v.getClasificacion());
        ps.setString(5, v.getRequisitos());
        ps.setDate(6, v.getFechaLanzamiento());
        ps.setBoolean(7, v.isActivo());
        ps.setInt(8, v.getIdEmpresa());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }
}
