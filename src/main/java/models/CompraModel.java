/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.CompraDTO;
import java.math.BigDecimal;
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
public class CompraModel {

    VideojuegoModel videojuego = new VideojuegoModel();

    public int insertar(CompraDTO c) throws Exception {
        BigDecimal precioPagado = videojuego.obtenerPorId(c.getIdVideojuego()).getPrecio();

        String sql = "INSERT INTO compras(Id_usuario, Id_videojuego, Fecha_compra, Precio_pagado) VALUES (?,?,?,?)";
        Connection conn = new ConnectionManager().conectar();
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, c.getIdUsuario());
                ps.setInt(2, c.getIdVideojuego());
                ps.setDate(3, c.getFechaCompra());
                ps.setBigDecimal(4, precioPagado);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int idCompra = rs.getInt(1);
                conn.commit();
                return idCompra;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public CompraDTO obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM compras WHERE Id_compra=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CompraDTO c = new CompraDTO();
                c.setIdCompra(rs.getInt("id_compra"));
                c.setIdUsuario(rs.getInt("id_usuario"));
                c.setIdVideojuego(rs.getInt("id_videojuego"));
                c.setFechaCompra(rs.getDate("fecha_compra"));
                c.setPrecioPagado(rs.getInt("precio_pagado"));

                return c;
            }
            return null;
        } finally {
            conn.close();
        }
    }

    public List<CompraDTO> obtenerTodos() throws Exception {
        String sql = "SELECT * FROM compras";
        Connection conn = new ConnectionManager().conectar();
        List<CompraDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CompraDTO c = new CompraDTO();
                c.setIdCompra(rs.getInt("id_compra"));
                c.setIdUsuario(rs.getInt("id_usuario"));
                c.setIdVideojuego(rs.getInt("id_videojuego"));
                c.setFechaCompra(rs.getDate("fecha_compra"));
                c.setPrecioPagado(rs.getInt("precio_pagado"));
                lista.add(c);
            }
            return lista;
        } finally {
            conn.close();
        }
    }
}
