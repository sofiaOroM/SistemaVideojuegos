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
import service.EmpresaService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/empresas")
public class EmpresaServlet extends HttpServlet {

    private final EmpresaService service = new EmpresaService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        EmpresaDTO empresa = new EmpresaDTO();
        empresa.setNombreEmpresa(req.getParameter("nombre"));
        empresa.setDescripcion(req.getParameter("descripcion"));

        try {
            service.crear(empresa);
            resp.setStatus(201);
        } catch (Exception ex) {
            resp.sendError(400);
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
}