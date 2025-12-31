/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Reportes.controller;

import Reportes.dto.ReporteGananciasGlobalDTO;
import Reportes.service.ReportesAdminService;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


/**
 *
 * @author sofia
 *
 */
@WebServlet("/api/reportes/admin/ganancias")
public class ReportesAdminServlet extends HttpServlet {

    private ReportesAdminService service = new ReportesAdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            List<ReporteGananciasGlobalDTO> data = service.generarReporteGanancias();

            JRBeanCollectionDataSource ds
                    = new JRBeanCollectionDataSource(data);

            Map<String, Object> params = new HashMap<>();
            params.put("ds", ds);
            params.put("logoEmpresa", getServletContext().getResourceAsStream("/Reportes/Jasper/img/logoEmpresa.png"));
            params.put("imagenAlternativa", getServletContext().getResourceAsStream("/Reportes/Jasper/img/imagenAlternativa.png"));
            System.out.println("Parametros definidos");
            InputStream reporte = getServletContext()
                    .getResourceAsStream("/Reportes/Jasper/admin/ReporteGananciasGlobales.jasper");
            System.out.println("generando reporte");
            System.out.println("Reporte: " + reporte);
System.out.println("Logo: " + params.get("logoEmpresa"));
System.out.println("Imagen alt: " + params.get("imagenAlternativa"));

            JasperPrint jp = JasperFillManager.fillReport(reporte, params, new JREmptyDataSource());

            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=ganancias_globales.pdf");
            System.out.println("reporte generado");
            JasperExportManager.exportReportToPdfStream(jp, resp.getOutputStream());
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error generando reporte" + e);
        }
    }
}
