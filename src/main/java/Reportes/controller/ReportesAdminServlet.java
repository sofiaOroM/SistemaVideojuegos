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
/*@WebServlet("/api/reportes/admin/ganancias")
public class ReportesAdminServlet extends HttpServlet {

    private ReportesAdminService service = new ReportesAdminService();

    //con este si genera el reporte pero no se muestra la tabla
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            List<ReporteGananciasGlobalDTO> data = service.generarReporteGanancias();

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);

            InputStream reporte = getClass()
                    .getClassLoader()
                    .getResourceAsStream("/Reportes/Jasper/admin/ReporteGananciasGlobales.jasper");

            InputStream logo = getClass()
                    .getClassLoader()
                    .getResourceAsStream("/Reportes/Jasper/img/logoEmpresa.png");

            InputStream imgAlt = getClass()
                    .getClassLoader()
                    .getResourceAsStream("/Reportes/Jasper/img/imagenAlternativa.png");

            if (reporte == null || logo == null || imgAlt == null) {
                throw new RuntimeException("No se encontró algún recurso Jasper");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("logoEmpresa", logo);
            params.put("imagenAlternativa", imgAlt);

            JasperPrint jp = JasperFillManager.fillReport(
                    reporte,
                    params,
                    ds
            );

            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=ganancias_globales.pdf");

            JasperExportManager.exportReportToPdfStream(jp, resp.getOutputStream());

        } catch (Exception e) {
            resp.reset();
            resp.setStatus(500);
            resp.setContentType("text/plain");
            resp.getWriter().write("Error generando reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}*/

@WebServlet("/api/reportes/admin/ganancias")
public class ReportesAdminServlet extends HttpServlet {

    private ReportesAdminService service = new ReportesAdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            List<ReporteGananciasGlobalDTO> data =
                    service.generarReporteGanancias();

            JRBeanCollectionDataSource ds =
                    new JRBeanCollectionDataSource(data);

            InputStream reporte = getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "Reportes/Jasper/admin/ReporteGananciasGlobales.jasper"
                    );

            InputStream logo = getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "Reportes/Jasper/img/logoEmpresa.png"
                    );

            InputStream imgAlt = getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "Reportes/Jasper/img/imagenAlternativa.png"
                    );

            if (reporte == null || logo == null || imgAlt == null) {
                throw new RuntimeException("No se encontró algún recurso Jasper");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("ds", ds);
            params.put("logoEmpresa", logo);
            params.put("imagenAlternativa", imgAlt);

            JasperPrint jp = JasperFillManager.fillReport(
                    reporte,
                    params,
                    new JREmptyDataSource()
            );

            resp.setContentType("application/pdf");
            resp.setHeader(
                    "Content-Disposition",
                    "attachment; filename=ganancias_globales.pdf"
            );

            JasperExportManager.exportReportToPdfStream(
                    jp,
                    resp.getOutputStream()
            );

        } catch (Exception e) {
            resp.reset();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain");
            resp.getWriter().write(
                    "Error generando reporte: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}

