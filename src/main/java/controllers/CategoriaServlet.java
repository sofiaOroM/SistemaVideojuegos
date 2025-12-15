/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import dto.CategoriaDTO;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoriaService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/categorias/*")
public class CategoriaServlet extends HttpServlet {

    private final CategoriaService service = new CategoriaService();
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
        setResponseHeaders(resp);
        try {
            CategoriaDTO c = gson.fromJson(req.getReader(), CategoriaDTO.class);
            int id = service.crear(c);
            resp.getWriter().write("{\"message\":\"Categoría creada\",\"id\":" + id + "}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if (path != null && path.length() > 1) {
                int id = Integer.parseInt(path.substring(1));
                CategoriaDTO c = service.obtener(id);
                if (c == null) {
                    resp.setStatus(404);
                    resp.getWriter().write("{\"error\":\"Categoría no encontrada\"}");
                    return;
                }
                resp.getWriter().write(gson.toJson(c));
            } else {
                resp.getWriter().write(gson.toJson(service.obtenerTodos()));
            }
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            CategoriaDTO c = gson.fromJson(req.getReader(), CategoriaDTO.class);
            service.actualizar(c);
            resp.getWriter().write("{\"message\":\"Categoría actualizada\"}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            service.eliminar(id);
            resp.getWriter().write("{\"message\":\"Categoría eliminada\"}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
