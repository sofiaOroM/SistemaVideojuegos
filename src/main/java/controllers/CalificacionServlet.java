/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import dto.CalificacionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.sql.Date;
import service.CalificacionService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/calificaciones/*")
public class CalificacionServlet extends HttpServlet {

    private final CalificacionService calificacionService = new CalificacionService();

    protected void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);

        try {
            BufferedReader reader = req.getReader();
            Gson gson = new Gson();

            CalificacionDTO calificacion = gson.fromJson(reader, CalificacionDTO.class);

            if (calificacion.getIdUsuario() == 0 || calificacion.getIdVideojuego() == 0) {
                throw new Exception("Datos obligatorios incompletos");
            }

            calificacionService.crearCalificacion(calificacion);

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
            String path = req.getPathInfo(); // /{id} o /{id}/promedio
            String[] parts = path.split("/");

            int idVideojuego = Integer.parseInt(parts[1]);

            if (parts.length == 3 && parts[2].equals("promedio")) {
                resp.getWriter().write(
                        calificacionService.obtenerPromedio(idVideojuego)
                );
            } else {
                resp.getWriter().write(
                        calificacionService.listarPorVideojuego(idVideojuego)
                );
            }

        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
