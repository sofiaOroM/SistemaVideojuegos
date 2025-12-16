/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.VideojuegoDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import service.VideojuegoService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/videojuegos/*")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB
public class VideojuegoServlet extends HttpServlet {

    private final VideojuegoService service = new VideojuegoService();
    private final Gson gson = new Gson();

    private void setResponseHeaders(HttpServletResponse resp){
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
            VideojuegoDTO v = new VideojuegoDTO();

            v.setTitulo(req.getParameter("titulo"));
            v.setDescripcion(req.getParameter("descripcion"));
            v.setPrecio(new BigDecimal(req.getParameter("precio")));
            v.setClasificacion(req.getParameter("clasificacion"));
            v.setRequisitos(req.getParameter("requisitos"));
            v.setFechaLanzamiento(Date.valueOf(req.getParameter("fechaLanzamiento")));
            v.setActivo(Boolean.parseBoolean(req.getParameter("activo")));
            v.setIdEmpresa(Integer.parseInt(req.getParameter("idEmpresa")));

            String[] catIds = req.getParameterValues("categorias");
            if(catIds != null){
                List<Integer> categorias = new ArrayList<>();
                for(String c : catIds) categorias.add(Integer.valueOf(c));
                v.setCategorias(categorias);
            }

            Part filePart = req.getPart("imagenPrincipal");
            if(filePart != null && filePart.getSize() > 0){
                try(InputStream is = filePart.getInputStream()){
                    v.setImagenPrincipal(is.readAllBytes());
                }
            } else {
                throw new Exception("Imagen principal obligatoria");
            }

            int id = service.crear(v);
            resp.getWriter().write("{\"message\":\"Videojuego creado\",\"id\":"+id+"}");
        } catch(Exception e){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
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
                VideojuegoDTO v = service.obtener(id);
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            VideojuegoDTO v = new VideojuegoDTO();

            v.setIdVideojuego(Integer.parseInt(req.getParameter("idVideojuego")));
            v.setTitulo(req.getParameter("titulo"));
            v.setDescripcion(req.getParameter("descripcion"));
            v.setPrecio(new BigDecimal(req.getParameter("precio")));
            v.setClasificacion(req.getParameter("clasificacion"));
            v.setRequisitos(req.getParameter("requisitos"));
            v.setFechaLanzamiento(Date.valueOf(req.getParameter("fechaLanzamiento")));
            v.setActivo(Boolean.parseBoolean(req.getParameter("activo")));
            v.setIdEmpresa(Integer.parseInt(req.getParameter("idEmpresa")));

            String[] catIds = req.getParameterValues("categorias");
            if(catIds != null){
                List<Integer> categorias = new ArrayList<>();
                for(String c : catIds) categorias.add(Integer.valueOf(c));
                v.setCategorias(categorias);
            }

            Part filePart = req.getPart("imagenPrincipal");
            if(filePart != null && filePart.getSize() > 0){
                try(InputStream is = filePart.getInputStream()){
                    v.setImagenPrincipal(is.readAllBytes());
                }
            }

            service.actualizar(v);
            resp.getWriter().write("{\"message\":\"Videojuego actualizado\"}");
        } catch(Exception e){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            service.eliminar(id);
            resp.getWriter().write("{\"message\":\"Videojuego eliminado\"}");
        } catch(Exception e){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
        }
    }
}