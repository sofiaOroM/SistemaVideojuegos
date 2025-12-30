/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import dto.ComentarioDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);

        try {
            BufferedReader reader = req.getReader();
            Gson gson = new Gson();

            ComentarioDTO comentario = gson.fromJson(reader, ComentarioDTO.class);

            if (comentario.getIdUsuario() == 0 || comentario.getIdVideojuego() == 0) {
                throw new Exception("Datos obligatorios incompletos");
            }

            comentarioService.crearComentario(comentario);

            resp.getWriter().write("{\"message\":\"Comentario agregado\"}");

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
            // /{idVideojuego}
            // /{idVideojuego}/estado

            if (path == null) {
                throw new Exception("Ruta inválida");
            }

            String[] parts = path.split("/");

            int idVideojuego = Integer.parseInt(parts[1]);

            //estado
            if (parts.length == 3 && parts[2].equals("estado")) {
                boolean activo = comentarioService.obtenerEstado(idVideojuego);
                resp.getWriter().write("{\"activo\":" + activo + "}");
                return;
            }

            //listar comentarios
            resp.getWriter().write(
                    comentarioService.listarPorVideojuego(idVideojuego)
            );

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
