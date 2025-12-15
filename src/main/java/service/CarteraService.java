/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author sofia
 */
public class CarteraService {

        public void crearCartera(int idUsuario, Connection conn) throws Exception {
        String sql = "INSERT INTO cartera (id_usuario, saldo) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setBigDecimal(2, new BigDecimal("0.00"));
            ps.executeUpdate();
        }
    }
}
