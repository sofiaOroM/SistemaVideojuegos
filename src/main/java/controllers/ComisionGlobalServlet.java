/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.ComisionGlobalDTO;
import com.google.gson.Gson;
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
    private final Gson gson = new Gson();

    private void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);

        try {
            String path = req.getPathInfo();

            if (path != null && path.equals("activa")) {
                resp.getWriter().write(
                        gson.toJson(service.obtenerComisionActiva())
                );
            } else {
                resp.getWriter().write(
                        gson.toJson(service.listarTodas())
                );
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);

        try {
            ComisionGlobalDTO dto = gson.fromJson(req.getReader(), ComisionGlobalDTO.class);

            service.actualizarComisionGlobal(dto.getPorcentaje());

            resp.getWriter().write("{\"message\":\"Comisión global actualizada\"}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
