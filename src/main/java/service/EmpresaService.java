/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dto.EmpresaDTO;
import java.util.List;
import models.EmpresaModel;

/**
 *
 * @author sofia
 */
public class EmpresaService {

    private final EmpresaModel model = new EmpresaModel();

    public int crear(EmpresaDTO e) throws Exception {
        if (e.getNombreEmpresa() == null || e.getNombreEmpresa().isEmpty()) {
            throw new Exception("Nombre obligatorio");
        }
        return model.insertar(e);
    }

    public EmpresaDTO obtener(int id) throws Exception {
        return model.obtenerPorId(id);
    }

    public List<EmpresaDTO> obtenerTodos() throws Exception {
        return model.obtenerTodos();
    }

    public void actualizar(EmpresaDTO e) throws Exception {
        if (e.getNombreEmpresa() == null || e.getNombreEmpresa().isEmpty()) {
            throw new Exception("Nombre obligatorio");
        }
        model.actualizar(e);
    }

    public void eliminar(int id) throws Exception {
        model.eliminar(id);
    }
}
