/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.EmpresaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class EmpresaModel {

    public int insertar(EmpresaDTO e) throws Exception {
        String sql = "INSERT INTO empresa(nombre_empresa, descripcion, logo) VALUES (?,?,?)";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNombreEmpresa());
            ps.setString(2, e.getDescripcion());
            if (e.getLogo() != null) {
                ps.setBytes(3, e.getLogo());
            } else {
                ps.setNull(3, Types.BLOB);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } finally {
            conn.close();
        }
    }

    public EmpresaDTO obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM empresa WHERE Id_empresa=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                EmpresaDTO e = new EmpresaDTO();
                e.setIdEmpresa(rs.getInt("Id_empresa"));
                e.setNombreEmpresa(rs.getString("Nombre_empresa"));
                e.setDescripcion(rs.getString("Descripcion"));
                e.setLogo(rs.getBytes("Logo"));
                return e;
            }
            return null;
        } finally {
            conn.close();
        }
    }

    public List<EmpresaDTO> obtenerTodos() throws Exception {
        String sql = "SELECT * FROM empresa WHERE estadoEmpresa=1";
        Connection conn = new ConnectionManager().conectar();
        List<EmpresaDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmpresaDTO e = new EmpresaDTO();
                e.setIdEmpresa(rs.getInt("Id_empresa"));
                e.setNombreEmpresa(rs.getString("Nombre_empresa"));
                e.setDescripcion(rs.getString("Descripcion"));
                e.setLogo(rs.getBytes("Logo"));
                lista.add(e);
            }
            return lista;
        } finally {
            conn.close();
        }
    }

    public void actualizar(EmpresaDTO e) throws Exception {
        String sql = "UPDATE empresa SET Nombre_empresa=?, Descripcion=?, Logo=? WHERE Id_empresa=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNombreEmpresa());
            ps.setString(2, e.getDescripcion());
            if (e.getLogo() != null) {
                ps.setBytes(3, e.getLogo());
            } else {
                ps.setNull(3, Types.BLOB);
            }
            ps.setInt(4, e.getIdEmpresa());
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }

    public void eliminar(int id) throws Exception {
        VideojuegoModel videojuego = new VideojuegoModel();
        videojuego.eliminar(id);
        String sql = "UPDATE empresa SET estadoEmpresa=0 WHERE Id_empresa=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }
}
