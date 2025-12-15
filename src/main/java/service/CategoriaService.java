/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dto.CategoriaDTO;
import java.util.List;
import models.CategoriaModel;

/**
 *
 * @author sofia
 */
public class CategoriaService {

    private final CategoriaModel categoria = new CategoriaModel();

    public int crear(CategoriaDTO c) throws Exception {
        if (c.getNombreCategoria() == null || c.getNombreCategoria().isEmpty()) {
            throw new Exception("Nombre obligatorio");
        }
        return categoria.insertar(c);
    }

    public CategoriaDTO obtener(int id) throws Exception {
        return categoria.obtenerPorId(id);
    }

    public List<CategoriaDTO> obtenerTodos() throws Exception {
        return categoria.obtenerTodos();
    }

    public void actualizar(CategoriaDTO c) throws Exception {
        if (c.getNombreCategoria() == null || c.getNombreCategoria().isEmpty()) {
            throw new Exception("Nombre obligatorio");
        }
        categoria.actualizar(c);
    }

    public void eliminar(int id) throws Exception {
        categoria.eliminar(id);
    }
}