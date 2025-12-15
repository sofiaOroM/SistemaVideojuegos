/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.EmpresaDTO;
import java.sql.Connection;
import models.EmpresaModel;

/**
 *
 * @author sofia
 */
public class EmpresaService {

    private final EmpresaModel empresa = new EmpresaModel();

    public void crear(EmpresaDTO empresaDto) throws Exception {
        if (empresaDto.getNombreEmpresa() == null || empresaDto.getNombreEmpresa().isEmpty()) {
            throw new Exception("Nombre obligatorio");
        }
        empresa.insertarEmpresa(empresaDto);
    }

    public void actualizar(EmpresaDTO empresaDto) throws Exception {
        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();
        empresa.actualizarEmpresa(empresaDto, conn);
        cm.desconectar(conn);
    }

    public void eliminar(int idEmpresa) throws Exception {
        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();
        empresa.eliminarEmpresa(idEmpresa, conn);
        cm.desconectar(conn);
    }
}
