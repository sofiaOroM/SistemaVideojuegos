/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.VideojuegoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import service.VideojuegoService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/videojuegos")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class VideojuegoServlet extends HttpServlet {

    private final VideojuegoService service = new VideojuegoService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            VideojuegoDTO v = construirVideojuego(req);
            List<Integer> categorias = obtenerCategorias(req);
            byte[] imagen = obtenerImagen(req);

            service.crearVideojuego(v, categorias, imagen);
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            VideojuegoDTO v = construirVideojuego(req);
            v.setIdVideojuego(Integer.parseInt(req.getParameter("idVideojuego")));

            List<Integer> categorias = obtenerCategorias(req);
            byte[] imagen = obtenerImagen(req);

            service.actualizarVideojuego(v, categorias, imagen);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            int id = Integer.parseInt(req.getParameter("idVideojuego"));
            service.eliminarVideojuego(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
    }

    private VideojuegoDTO construirVideojuego(HttpServletRequest req) {

        VideojuegoDTO v = new VideojuegoDTO();
        v.setTitulo(req.getParameter("titulo"));
        v.setDescripcion(req.getParameter("descripcion"));
        v.setPrecio(new BigDecimal(req.getParameter("precio")));
        v.setClasificacion(req.getParameter("clasificacion"));
        v.setIdEmpresa(Integer.parseInt(req.getParameter("idEmpresa")));
        v.setActivo(true);
        return v;
    }

    private List<Integer> obtenerCategorias(HttpServletRequest req) {
        String[] ids = req.getParameterValues("categorias");
        return Arrays.stream(ids).map(Integer::parseInt).toList();
    }

    private byte[] obtenerImagen(HttpServletRequest req) throws IOException, ServletException {
        Part part = req.getPart("imagen");
        if (part == null || part.getSize() == 0) return null;
        return part.getInputStream().readAllBytes();
    }
}
