/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.CompraDTO;
import service.CompraService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.Date;

/**
 *
 * @author sofia
 */
@WebServlet("/api/compras/*")
public class CompraServlet extends HttpServlet {

    private final CompraService service = new CompraService();

    private void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    //http://localhost:8080/VideojuegosBackend/api/compras?idUsuario=6&idVideojuego=8&fechaCompra=2025-11-11
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            CompraDTO c = new CompraDTO();

            c.setIdUsuario(Integer.parseInt(req.getParameter("idUsuario")));
            c.setIdVideojuego(Integer.parseInt(req.getParameter("idVideojuego")));
            c.setFechaCompra(Date.valueOf(req.getParameter("fechaCompra")));

            int id = service.crear(c);
            resp.getWriter().write("{\"message\":\"Compra registrada\",\"id\":" + id + "}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error desde aqui\":\"" + e.getMessage() + "\"}");
        }
    }
    
        @Override
    //http://localhost:8080/VideojuegosBackend/api/videojuegos listado general
    //http://localhost:8080/VideojuegosBackend/api/videojuegos/id uno en especifico
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if(path != null && path.length() > 1){
                int id = Integer.parseInt(path.substring(1));
                compraDTO v = service.obtener(id);
                if(v == null){
                    resp.setStatus(404);
                    resp.getWriter().write("{\"error\":\"Videojuego no encontrado\"}");
                    return;
                }
                resp.getWriter().write(gson.toJson(v));
            } else {
                resp.getWriter().write(gson.toJson(service.obtenerTodos()));
            }
        } catch(Exception e){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
        }
    }
}
