/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.UsuarioDTO;

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
public class UsuarioModel {

    public int insertar(UsuarioDTO u, Connection conn) throws Exception {
        String sql = "INSERT INTO usuario "
                + "(Nombre_usuario, Correo_electronico, Fecha_nacimiento, Password, "
                + "Nickname, Telefono, Pais, Rol, Id_empresa, Avatar) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";

        int idUsuario = 0;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getCorreoElectronico());
            ps.setDate(3, u.getFechaNacimiento());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getNickname());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getPais());
            ps.setString(8, u.getRol());
            if (u.getIdEmpresa() != null) {
                ps.setInt(9, u.getIdEmpresa());
            } else {
                ps.setNull(9, Types.INTEGER);
            }

            if (u.getAvatar() != null) {
                ps.setBytes(10, u.getAvatar());
            } else {
                ps.setNull(10, Types.BLOB);
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idUsuario = rs.getInt(1);
                u.setIdUsuario(idUsuario);
            }
        }

        return idUsuario;
    }

    public UsuarioDTO obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM usuario WHERE Id_usuario=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UsuarioDTO u = new UsuarioDTO();
                u.setIdUsuario(rs.getInt("Id_usuario"));
                u.setNombreUsuario(rs.getString("Nombre_usuario"));
                u.setCorreoElectronico(rs.getString("Correo_electronico"));
                u.setFechaNacimiento(rs.getDate("Fecha_nacimiento"));
                u.setNickname(rs.getString("Nickname"));
                u.setTelefono(rs.getString("Telefono"));
                u.setPais(rs.getString("Pais"));
                u.setRol(rs.getString("Rol"));
                u.setIdEmpresa(rs.getInt("Id_empresa") == 0 ? null : rs.getInt("Id_empresa"));
                u.setAvatar(rs.getBytes("Avatar"));
                return u;
            }
            return null;
        } finally {
            conn.close();
        }
    }

    public List<UsuarioDTO> obtenerTodos() throws Exception {
        String sql = "SELECT * FROM usuario";
        Connection conn = new ConnectionManager().conectar();
        List<UsuarioDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioDTO u = new UsuarioDTO();
                u.setIdUsuario(rs.getInt("Id_usuario"));
                u.setNombreUsuario(rs.getString("Nombre_usuario"));
                u.setCorreoElectronico(rs.getString("Correo_electronico"));
                u.setFechaNacimiento(rs.getDate("Fecha_nacimiento"));
                u.setNickname(rs.getString("Nickname"));
                u.setTelefono(rs.getString("Telefono"));
                u.setPais(rs.getString("Pais"));
                u.setRol(rs.getString("Rol"));
                u.setIdEmpresa(rs.getInt("Id_empresa") == 0 ? null : rs.getInt("Id_empresa"));
                u.setAvatar(rs.getBytes("Avatar"));
                lista.add(u);
            }
            return lista;
        } finally {
            conn.close();
        }
    }

    public void actualizar(UsuarioDTO u) throws Exception {
        String sql = "UPDATE usuario SET Nombre_usuario=?, Fecha_nacimiento=?, Password=?, "
                + "Nickname=?, Telefono=?, Pais=?, Rol=?, Id_empresa=?, Avatar=? WHERE Id_usuario=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombreUsuario());
            ps.setDate(2, u.getFechaNacimiento());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getNickname());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getPais());
            ps.setString(7, u.getRol());
            if (u.getIdEmpresa() != null) {
                ps.setInt(8, u.getIdEmpresa());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            if (u.getAvatar() != null) {
                ps.setBytes(9, u.getAvatar());
            } else {
                ps.setNull(9, Types.BLOB);
            }
            ps.setInt(10, u.getIdUsuario());
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM usuario WHERE Id_usuario=?";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }
}
