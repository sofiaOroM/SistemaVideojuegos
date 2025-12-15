/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.CategoriaDTO;
import java.sql.Connection;
import models.CategoriaModel;

/**
 *
 * @author sofia
 */
public class CategoriaService {

    private final CategoriaModel categoria = new CategoriaModel();

    public void crear(CategoriaDTO c) throws Exception {

        if (c.getNombreCategoria() == null || c.getNombreCategoria().isBlank()) {
            throw new Exception("Nombre de categoría obligatorio");
        }

        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();
        categoria.insertarCategoria(c, conn);
        cm.desconectar(conn);
    }

    public void actualizar(CategoriaDTO c) throws Exception {
        if (c.getIdCategoria() <= 0) {
            throw new Exception("ID inválido");
        }
        crear(c);
    }

    public void eliminar(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID inválido");
        }

        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();
        categoria.eliminarCategoria(id, conn);
        cm.desconectar(conn);
    }
}
