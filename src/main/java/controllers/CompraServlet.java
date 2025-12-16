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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        System.out.println("id usuario " + req.getParameter("idUsuario"));
        System.out.println("id videojuego " + req.getParameter("idVideojuego"));
        System.out.println("fecha " + req.getParameter("fechaCompra"));

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
}
