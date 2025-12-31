/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes.service;

import Reportes.dto.ReporteGananciasGlobalDTO;
import Reportes.model.ReportesAdminModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class ReportesAdminService {

    private ReportesAdminModel AdminModel = new ReportesAdminModel();

    public List<ReporteGananciasGlobalDTO> generarReporteGanancias() throws Exception {
        List<ReporteGananciasGlobalDTO> lista = new ArrayList<>();
        System.out.println("Listar para ganancias reportes en servicio");
        lista.add(AdminModel.obtenerGananciasGlobales());
        System.out.println("lista:"+ lista);
        return lista;
    }
}
