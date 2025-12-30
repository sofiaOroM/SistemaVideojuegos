/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.BibliotecaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class BibliotecaModel {

    public List<BibliotecaDTO> obtenerPorUsuario(int idUsuario) throws Exception {

        String sql = "SELECT c.id_compra, c.fecha_compra, c.precio_pagado, "
                + "v.id_videojuego, v.titulo_videojuego, v.descripcion, v.imagen_principal, v.clasificacion "
                + "FROM compras c JOIN videojuego v ON v.id_videojuego = c.id_videojuego WHERE c.id_usuario = ?";

        List<BibliotecaDTO> lista = new ArrayList<>();
        Connection conn = new ConnectionManager().conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BibliotecaDTO b = new BibliotecaDTO();
                b.setIdCompra(rs.getInt("id_compra"));
                b.setIdVideojuego(rs.getInt("id_videojuego"));
                b.setTitulo(rs.getString("titulo_videojuego"));
                b.setDescripcion(rs.getString("descripcion"));
                b.setClasificacion(rs.getString("clasificacion"));
                b.setImagenPrincipal(rs.getBytes("imagen_principal"));
                b.setFechaCompra(rs.getDate("fecha_compra"));
                b.setPrecioPagado(rs.getBigDecimal("precio_pagado"));
                lista.add(b);
            }
        } finally {
            conn.close();
        }

        return lista;
    }
}
