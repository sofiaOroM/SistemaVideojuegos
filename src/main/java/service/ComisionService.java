/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.ComisionEmpresaDTO;
import dto.ComisionGlobalDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import models.ComisionEmpresaModel;
import models.ComisionGlobalModel;

/**
 *
 * @author sofia
 */
public class ComisionService {

    private final ComisionGlobalModel globalModel = new ComisionGlobalModel();
    private final ComisionEmpresaModel empresaModel = new ComisionEmpresaModel();

    public void crearComisionGlobal(ComisionGlobalDTO c) throws Exception {
        validarPorcentaje(c.getPorcentaje());

        Connection conn = new ConnectionManager().conectar();
        try {
            conn.setAutoCommit(false);

            globalModel.cerrarComisionActiva(conn);
            globalModel.insertar(c, conn);

            empresaModel.ajustarComisionesMayoresAlGlobal(c.getPorcentaje(), conn);

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public void crearComisionEmpresa(ComisionEmpresaDTO c) throws Exception {
        validarPorcentaje(c.getPorcentaje());

        ComisionGlobalDTO global = globalModel.obtenerActivaObligatoria();

        if (c.getPorcentaje().compareTo(global.getPorcentaje()) > 0) {
            throw new Exception(
                    "El porcentaje de la empresa no puede ser mayor al porcentaje global vigente ("
                    + global.getPorcentaje() + "%)"
            );
        }

        Connection conn = new ConnectionManager().conectar();
        try {
            conn.setAutoCommit(false);

            empresaModel.cerrarComisionActiva(c.getIdEmpresa(), conn);
            empresaModel.insertar(c, conn);

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public BigDecimal obtenerComisionParaEmpresa(int idEmpresa) throws Exception {
        ComisionEmpresaDTO empresa = empresaModel.obtenerActiva(idEmpresa);
        if (empresa != null) {
            return empresa.getPorcentaje();
        }

        ComisionGlobalDTO global = globalModel.obtenerActivaObligatoria();
        return global.getPorcentaje();
    }

    private void validarPorcentaje(BigDecimal p) throws Exception {
        if (p == null
                || p.compareTo(BigDecimal.ZERO) <= 0
                || p.compareTo(new BigDecimal("100")) > 0) {
            throw new Exception("Porcentaje inválido (1–100)");
        }
    }

    public void actualizarComisionGlobal(BigDecimal porcentaje) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object obtenerComisionActiva() throws Exception {
        ComisionGlobalDTO global = globalModel.obtenerActivaObligatoria();
        return global;
    }

    public List<ComisionGlobalDTO> listarTodas() throws Exception {
        return globalModel.obtenerTodos();
    }
}
