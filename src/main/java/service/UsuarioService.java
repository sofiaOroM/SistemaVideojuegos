/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.UsuarioDTO;
import java.sql.Connection;
import models.CarteraModel;
import models.UsuarioModel;
import util.PasswordUtil;

/**
 *
 * @author sofia
 */
public class UsuarioService {

    private final UsuarioModel usuario = new UsuarioModel();
    private final CarteraModel cartera = new CarteraModel();

    public void crearGamer(UsuarioDTO u) throws Exception {

        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();

        try {
            conn.setAutoCommit(false);

            u.setRol("gamer");
            u.setPassword(PasswordUtil.encriptar(u.getPassword()));

            int idUsuario = usuario.insertarUsuario(conn, u);
            cartera.crearCartera(conn, idUsuario);

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            cm.desconectar(conn);
        }
    }

    public void actualizarUsuario(UsuarioDTO u) throws Exception {
        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();
        usuario.actualizarUsuario(conn, u);
        cm.desconectar(conn);
    }

    public void eliminarUsuario(int idUsuario) throws Exception {

        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();

        try {
            conn.setAutoCommit(false);
            cartera.eliminarPorUsuario(conn, idUsuario);
            usuario.eliminarUsuario(conn, idUsuario);
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            cm.desconectar(conn);
        }
    }
}
