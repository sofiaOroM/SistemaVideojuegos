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

/**
 *
 * @author sofia
 */
public class UsuarioModel {

    public int insertarUsuario(Connection conn, UsuarioDTO u) throws Exception {

        String sql = "INSERT INTO usuario (Nombre_usuario, Correo_electronico, Fecha_nacimiento, Password,"
                + " Nickname, Telefono, Pais, Rol, Id_empresa)"
                + " VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(
                sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, u.getNombreUsuario());
        ps.setString(2, u.getCorreoElectronico());
        ps.setDate(3, u.getFechaNacimiento());
        ps.setString(4, u.getPassword());
        ps.setString(5, u.getNickname());
        ps.setString(6, u.getTelefono());
        ps.setString(7, u.getPais());
        ps.setString(8, u.getRol());
        ps.setObject(9, u.getIdEmpresa());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    public UsuarioDTO obtenerPorId(int id, Connection conn) throws Exception {
        String sql = "SELECT * FROM usuario WHERE Id_usuario=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            UsuarioDTO u = new UsuarioDTO();
            u.setIdUsuario(rs.getInt("Id_usuario"));
            u.setCorreoElectronico(rs.getString("Correo_electronico"));
            u.setRol(rs.getString("Rol"));
            return u;
        }
        return null;
    }

    public void actualizarUsuario(Connection conn, UsuarioDTO u) throws Exception {
        String sql = "UPDATE usuario SET Nombre_usuario=?, Nickname=?, Telefono=?, Pais=? WHERE Id_usuario=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, u.getNombreUsuario());
        ps.setString(2, u.getNickname());
        ps.setString(3, u.getTelefono());
        ps.setString(4, u.getPais());
        ps.setInt(5, u.getIdUsuario());

        ps.executeUpdate();
    }

    public void eliminarUsuario(Connection conn, int idUsuario) throws Exception {
        String sql = "DELETE FROM usuario WHERE Id_usuario=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        ps.executeUpdate();
    }
}