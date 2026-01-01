/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Reportes.controller;

import Reportes.dto.RankingUsuarioDTO;
import Reportes.dto.ReporteIngresosEmpresaDTO;
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
@WebServlet("/api/reportes/admin/rankingUsuario")
public class ReporteRankingUsuarioServlet extends HttpServlet {

    private final ReportesAdminService service = new ReportesAdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            List<RankingUsuarioDTO> ranking = service.generarReporteRankingUsuario();

            JRBeanCollectionDataSource ds
                    = new JRBeanCollectionDataSource(ranking);

            InputStream reporte = getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "Reportes/Jasper/admin/ReporteRankingUsuario.jasper"
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
            /*InputStream primerPosicion = getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "Reportes/Jasper/img/primeraPosicion.png"
                    );
            InputStream segundaPosicion = getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "Reportes/Jasper/img/segundaPosicion.png"
                    );
            InputStream terceraPosicion = getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "Reportes/Jasper/img/terceraPosicion.png"
                    );*/

            if (reporte == null || logo == null || imgAlt == null /*|| primerPosicion == null || segundaPosicion == null || terceraPosicion == null*/) {
                throw new RuntimeException("No se encontró algún recurso Jasper");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("ds", ds);
            params.put("logoEmpresa", logo);
            params.put("imagenAlternativa", imgAlt);
            /*params.put("primerPosicion", primerPosicion);
            params.put("segundaPosicion", segundaPosicion);
            params.put("terceraPosicion", terceraPosicion);*/

            JasperPrint jp = JasperFillManager.fillReport(
                    reporte,
                    params,
                    new JREmptyDataSource()
            );

            resp.setContentType("application/pdf");
            resp.setHeader(
                    "Content-Disposition",
                    "attachment; filename=ingresos_por_empresa.pdf"
            );

            JasperExportManager.exportReportToPdfStream(
                    jp,
                    resp.getOutputStream()
            );

        } catch (Exception e) {
            resp.reset();
            resp.setStatus(500);
            resp.getWriter().write(
                    "Error generando reporte: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
