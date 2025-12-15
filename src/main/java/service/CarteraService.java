/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.UsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author sofia
 */
public class CarteraService {

    public void crearCartera(UsuarioDTO u) throws Exception {
        String sql = "INSERT INTO cartera(id_usuario, saldo) VALUES (?, 0)";
        Connection conn = new ConnectionManager().conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, u.getIdUsuario());
            ps.executeUpdate();
        } finally { conn.close(); }
    }
}
