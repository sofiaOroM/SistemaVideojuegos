/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.UsuarioDTO;
import java.sql.Connection;
import java.util.List;
import models.CarteraModel;
import models.UsuarioModel;
import util.PasswordUtil;

/**
 *
 * @author sofia
 */
public class UsuarioService {

    private final UsuarioModel model = new UsuarioModel();

    public int crearUsuario(UsuarioDTO u) throws Exception {
        if (u.getCorreoElectronico() == null || u.getCorreoElectronico().isEmpty()) {
            throw new Exception("Correo obligatorio");
        }
        if (u.getPassword() == null || u.getPassword().isEmpty()) {
            throw new Exception("Password obligatorio");
        }
        if (u.getRol() == null || u.getRol().isEmpty()) {
            throw new Exception("Rol obligatorio");
        }

        u.setPassword(PasswordUtil.encriptar(u.getPassword()));
        int id = model.insertar(u);

        // Si es gamer, crear cartera automáticamente
        if ("gamer".equals(u.getRol())) {
            new CarteraService().crearCartera(u);
        }
        return id;
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
