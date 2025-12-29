/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ComentarioService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/comentario/*")
public class ComentarioServlet extends HttpServlet {

    private final ComentarioService comentarioService = new ComentarioService();

    protected void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo(); // /{id}/estado
            if (path == null || !path.endsWith("/estado")) {
                throw new Exception("Ruta inválida");
            }

            int idVideojuego = Integer.parseInt(path.split("/")[1]);
            boolean activo = comentarioService.obtenerEstado(idVideojuego);

            resp.getWriter().write("{\"activo\":" + activo + "}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        setResponseHeaders(resp);
        try {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            comentarioService.actualizar(id);
            resp.getWriter().write("{\"message\":\"Videojuego eliminado\"}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }
}
