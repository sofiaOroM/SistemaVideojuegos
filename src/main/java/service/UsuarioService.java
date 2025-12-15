/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.UsuarioDTO;
import java.sql.Connection;
import java.util.List;
import models.UsuarioModel;
import util.PasswordUtil;

/**
 *
 * @author sofia
 */
public class UsuarioService {

    private final UsuarioModel model = new UsuarioModel();

    private final CarteraService carteraService = new CarteraService();

    public int crearUsuario(UsuarioDTO u) throws Exception {
        if(u.getCorreoElectronico() == null || u.getCorreoElectronico().isEmpty())
            throw new Exception("Correo obligatorio");
        if(u.getPassword() == null || u.getPassword().isEmpty())
            throw new Exception("Password obligatorio");
        if(u.getRol() == null || u.getRol().isEmpty())
            throw new Exception("Rol obligatorio");

        // Encriptar password
        u.setPassword(PasswordUtil.encriptar(u.getPassword()));

        ConnectionManager cm = new ConnectionManager();
        try(Connection conn = cm.conectar()){
            conn.setAutoCommit(false);

            // Insertar usuario
            int idUsuario = model.insertar(u, conn);

            // Crear cartera si es gamer
            if("gamer".equalsIgnoreCase(u.getRol())){
                carteraService.crearCartera(idUsuario, conn);
            }

            conn.commit();
            return idUsuario;
        } catch(Exception e){
            throw e;
        }
    }

    public UsuarioDTO obtenerUsuario(int id) throws Exception {
        return model.obtenerPorId(id);
    }

    public List<UsuarioDTO> obtenerTodos() throws Exception {
        return model.obtenerTodos();
    }

    public void actualizarUsuario(UsuarioDTO u) throws Exception {
        if (u.getPassword() != null && !u.getPassword().isEmpty()) {
            u.setPassword(PasswordUtil.encriptar(u.getPassword()));
        }
        model.actualizar(u);
    }

    public void eliminarUsuario(int id) throws Exception {
        model.eliminar(id);
    }
}
