/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.EmpresaDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import service.EmpresaService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/empresas/*")
@MultipartConfig(maxFileSize = 1024*1024*5)
public class EmpresaServlet extends HttpServlet {

    private final EmpresaService service = new EmpresaService();

    private void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        setResponseHeaders(resp);
        try {
            String nombre = req.getParameter("nombre");
            String descripcion = req.getParameter("descripcion");

            Part logoPart = req.getPart("logo");
            byte[] logo = null;
            if(logoPart != null && logoPart.getSize() > 0)
                logo = logoPart.getInputStream().readAllBytes();

            EmpresaDTO e = new EmpresaDTO();
            e.setNombreEmpresa(nombre);
            e.setDescripcion(descripcion);
            e.setLogo(logo);

            int id = service.crear(e);
            resp.getWriter().write("{\"message\":\"Empresa creada\",\"id\":"+id+"}");
        } catch(Exception ex){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+ex.getMessage()+"\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if(path != null && path.length() > 1){
                int id = Integer.parseInt(path.substring(1));
                EmpresaDTO e = service.obtener(id);
                if(e == null){
                    resp.setStatus(404);
                    resp.getWriter().write("{\"error\":\"Empresa no encontrada\"}");
                    return;
                }
                resp.getWriter().write(new JSONObject(e).toString());
            } else {
                List<EmpresaDTO> lista = service.obtenerTodos();
                JSONArray arr = new JSONArray(lista);
                resp.getWriter().write(arr.toString());
            }
        } catch(Exception ex){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+ex.getMessage()+"\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if(path == null || path.length() <= 1) throw new Exception("ID obligatorio en la URL");
            int id = Integer.parseInt(path.substring(1));

            String nombre = req.getParameter("nombre");
            String descripcion = req.getParameter("descripcion");
            Part logoPart = req.getPart("logo");
            byte[] logo = null;
            if(logoPart != null && logoPart.getSize() > 0)
                logo = logoPart.getInputStream().readAllBytes();

            EmpresaDTO e = new EmpresaDTO();
            e.setIdEmpresa(id);
            e.setNombreEmpresa(nombre);
            e.setDescripcion(descripcion);
            e.setLogo(logo);

            service.actualizar(e);
            resp.getWriter().write("{\"message\":\"Empresa actualizada\"}");
        } catch(Exception ex){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+ex.getMessage()+"\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if(path == null || path.length() <= 1) throw new Exception("ID obligatorio en la URL");
            int id = Integer.parseInt(path.substring(1));
            service.eliminar(id);
            resp.getWriter().write("{\"message\":\"Empresa eliminada\"}");
        } catch(Exception ex){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+ex.getMessage()+"\"}");
        }
    }
}