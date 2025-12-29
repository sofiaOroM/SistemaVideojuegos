/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.CompraDTO;
import dto.VideojuegoDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class CompraModel {

    private final VideojuegoModel videojuegoModel = new VideojuegoModel();

    public int insertar(CompraDTO c) throws Exception {

        Connection conn = new ConnectionManager().conectar();
        conn.setAutoCommit(false);

        try {
            // Obtener videojuego
            VideojuegoDTO juego = videojuegoModel.obtenerPorId(c.getIdVideojuego());
            BigDecimal precio = juego.getPrecio();
            String clasificacion = juego.getClasificacion();
            int idEmpresa = juego.getIdEmpresa();

            // Obtener fecha nacimiento usuario
            Date fechaNacimiento = obtenerFechaNacimiento(conn, c.getIdUsuario());

            // Validar edad
            int edad = calcularEdad(fechaNacimiento, c.getFechaCompra());
            validarEdad(edad, clasificacion);

            // Obtener cartera
            CarteraData cartera = obtenerCartera(conn, c.getIdUsuario());

            if (cartera.saldo.compareTo(precio) < 0) {
                throw new Exception("Saldo insuficiente para realizar la compra");
            }

            // Insertar compra
            int idCompra = insertarCompra(conn, c, precio);

            //  Determinar comisión
            ComisionData comision = obtenerComision(conn, idEmpresa);

            // Insertar compra_comision
            insertarCompraComision(conn, idCompra, precio, comision);

            // Descontar saldo
            descontarCartera(conn, cartera.idCartera, precio);

            conn.commit();
            return idCompra;

        } catch (Exception e) {
            conn.rollback();
            throw e;
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
    
     private Date obtenerFechaNacimiento(Connection conn, int idUsuario) throws Exception {
        String sql = "SELECT fecha_nacimiento FROM usuario WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDate("fecha_nacimiento");
            }
            throw new Exception("Usuario no encontrado");
        }
    }

    private int calcularEdad(Date nacimiento, Date compra) {
        LocalDate n = nacimiento.toLocalDate();
        LocalDate c = compra.toLocalDate();
        return Period.between(n, c).getYears();
    }

    private void validarEdad(int edad, String clasificacion) throws Exception {
        switch (clasificacion) {
            case "T":
                if (edad < 13)
                    throw new Exception("El usuario no cumple la edad mínima (13+)");
                break;
            case "M":
                if (edad < 16)
                    throw new Exception("El usuario no cumple la edad mínima (16+)");
                break;
            default:
                // E → cualquier edad
                break;
        }
    }

    private CarteraData obtenerCartera(Connection conn, int idUsuario) throws Exception {
        String sql = "SELECT id_cartera, saldo FROM cartera WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CarteraData(
                        rs.getInt("id_cartera"),
                        rs.getBigDecimal("saldo")
                );
            }
            throw new Exception("El usuario no tiene cartera");
        }
    }

    private int insertarCompra(Connection conn, CompraDTO c, BigDecimal precio) throws Exception {
        String sql = "INSERT INTO compras (id_usuario, id_videojuego, fecha_compra, precio_pagado) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getIdUsuario());
            ps.setInt(2, c.getIdVideojuego());
            ps.setDate(3, c.getFechaCompra());
            ps.setBigDecimal(4, precio);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private ComisionData obtenerComision(Connection conn, int idEmpresa) throws Exception {

        // Buscar comisión de empresa vigente
        String sqlEmpresa = "SELECT id_comision_empresa, porcentaje FROM empresa_comision WHERE id_empresa = ? "
                + "AND CURRENT_DATE >= fecha_inicio AND (fecha_fin IS NULL OR CURRENT_DATE <= fecha_fin) LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sqlEmpresa)) {
            ps.setInt(1, idEmpresa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ComisionData(
                        rs.getInt("id_comision_empresa"),
                        rs.getBigDecimal("porcentaje"),
                        true
                );
            }
        }

        //  Comisión global
        String sqlGlobal = "SELECT id_comision, porcentaje FROM comision_global WHERE CURRENT_DATE >= fecha_inicio "
                + "AND (fecha_fin IS NULL OR CURRENT_DATE <= fecha_fin) LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sqlGlobal)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ComisionData(
                        rs.getInt("id_comision"),
                        rs.getBigDecimal("porcentaje"),
                        false
                );
            }
        }

        throw new Exception("No existe comisión vigente");
    }

    private void insertarCompraComision(Connection conn, int idCompra, BigDecimal precio, ComisionData c) throws Exception {

        BigDecimal monto = precio
                .multiply(c.porcentaje)
                .divide(BigDecimal.valueOf(100));

        String sql = "INSERT INTO compra_comision (id_compra, id_comision_empresa, id_comision_global, monto_comision, porcentaje_usado) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCompra);

            if (c.esEmpresa) {
                ps.setInt(2, c.idComision);
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setNull(2, Types.INTEGER);
                ps.setInt(3, c.idComision);
            }

            ps.setBigDecimal(4, monto);
            ps.setBigDecimal(5, c.porcentaje);
            ps.executeUpdate();
        }
    }

    private void descontarCartera(Connection conn, int idCartera, BigDecimal precio) throws Exception {
        String sql = "UPDATE cartera SET saldo = saldo - ? WHERE id_cartera=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, precio);
            ps.setInt(2, idCartera);
            ps.executeUpdate();
        }
    }

    private static class CarteraData {
        int idCartera;
        BigDecimal saldo;

        CarteraData(int idCartera, BigDecimal saldo) {
            this.idCartera = idCartera;
            this.saldo = saldo;
        }
    }

    private static class ComisionData {
        int idComision;
        BigDecimal porcentaje;
        boolean esEmpresa;

        ComisionData(int idComision, BigDecimal porcentaje, boolean esEmpresa) {
            this.idComision = idComision;
            this.porcentaje = porcentaje;
            this.esEmpresa = esEmpresa;
        }
    }  
}
