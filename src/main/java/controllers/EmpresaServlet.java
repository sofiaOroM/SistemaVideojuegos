/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.EmpresaDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import service.EmpresaService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/empresas")
public class EmpresaServlet extends HttpServlet {

    private final EmpresaService service = new EmpresaService();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException, ServletException {
        setResponseHeaders(resp);

        try {
            String nombre = req.getParameter("nombre");
            String descripcion = req.getParameter("descripcion");

            if(nombre == null || nombre.isEmpty())
                throw new Exception("Nombre obligatorio");

            Part logoPart = req.getPart("logo");
            byte[] logo = null;
            if(logoPart != null && logoPart.getSize() > 0){
                logo = logoPart.getInputStream().readAllBytes();
            }

            EmpresaDTO e = new EmpresaDTO();
            e.setNombreEmpresa(nombre);
            e.setDescripcion(descripcion);
            e.setLogo(logo);

            service.crear(e);

            resp.getWriter().write("{\"message\":\"Empresa creada\"}");

        } catch (Exception ex) {
            sendError(resp, 400, ex.getMessage());
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        EmpresaDTO empresa = new EmpresaDTO();
        empresa.setIdEmpresa(Integer.parseInt(req.getParameter("id")));
        empresa.setNombreEmpresa(req.getParameter("nombre"));

        try {
            service.actualizar(empresa);
            resp.setStatus(200);
        } catch (Exception ex) {
            resp.sendError(400);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            service.eliminar(Integer.parseInt(req.getParameter("id")));
            resp.setStatus(204);
        } catch (Exception e) {
            resp.sendError(400);
        }
    }

    protected void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    protected void sendError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        resp.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}
