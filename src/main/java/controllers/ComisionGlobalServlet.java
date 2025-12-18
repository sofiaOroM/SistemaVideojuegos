/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.ComisionGlobalDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import service.ComisionService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/comisiones/global")
public class ComisionGlobalServlet extends HttpServlet {

    private final ComisionService service = new ComisionService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            ComisionGlobalDTO c = new ComisionGlobalDTO();
            c.setPorcentaje(new BigDecimal(req.getParameter("porcentaje")));
            c.setFechaInicio(Date.valueOf(req.getParameter("fechaInicio")));
            c.setFechaFin(null);

            service.crearComisionGlobal(c);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
    }
}
