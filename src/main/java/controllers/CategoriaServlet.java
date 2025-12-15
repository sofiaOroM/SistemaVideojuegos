/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.CategoriaDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoriaService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/categorias")
public class CategoriaServlet extends HttpServlet {

    private final CategoriaService service = new CategoriaService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        CategoriaDTO c = new CategoriaDTO();
        c.setNombreCategoria(req.getParameter("nombre"));

        try {
            service.crear(c);
            resp.setStatus(201);
        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        CategoriaDTO c = new CategoriaDTO();
        c.setIdCategoria(Integer.parseInt(req.getParameter("id")));
        c.setNombreCategoria(req.getParameter("nombre"));

        try {
            service.actualizar(c);
            resp.setStatus(200);
        } catch (Exception e) {
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