/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes.service;

import Reportes.dto.RankingUsuarioDTO;
import Reportes.dto.ReporteGananciasGlobalDTO;
import Reportes.dto.ReporteIngresosEmpresaDTO;
import Reportes.model.ReportesAdminModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class ReportesAdminService {

    private final ReportesAdminModel AdminModel = new ReportesAdminModel();

    public List<ReporteGananciasGlobalDTO> generarReporteGanancias() throws Exception {
        List<ReporteGananciasGlobalDTO> lista = new ArrayList<>();
        System.out.println("Listar para ganancias reportes en servicio");
        lista.add(AdminModel.obtenerGananciasGlobales());
        System.out.println("lista:" + lista);
        return lista;
    }

    public List<ReporteIngresosEmpresaDTO> generarReporteIngresosEmpresa() throws Exception {
        return AdminModel.obtenerIngresosPorEmpresa();
    }

    public List<RankingUsuarioDTO> generarReporteRankingUsuario() throws Exception {
        return AdminModel.obtenerRankingUsuario();
    }
}
