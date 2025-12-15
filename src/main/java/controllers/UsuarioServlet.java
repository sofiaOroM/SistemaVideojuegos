/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.UsuarioDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UsuarioService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/usuarios/*")
@MultipartConfig(maxFileSize = 1024*1024*5)
public class UsuarioServlet extends HttpServlet {

    private final UsuarioService service = new UsuarioService();

    protected void setResponseHeaders(HttpServletResponse resp) {
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
            String data = req.getParameter("data");
            if(data == null) throw new Exception("Campo 'data' obligatorio");
            JSONObject json = new JSONObject(data);

            UsuarioDTO u = new UsuarioDTO();
            u.setRol(json.getString("rol"));
            u.setCorreoElectronico(json.getString("correo"));
            u.setPassword(json.getString("password"));
            u.setFechaNacimiento(Date.valueOf(json.getString("fechaNacimiento")));
            if("gamer".equals(u.getRol())){
                u.setNickname(json.optString("nickname"));
                u.setTelefono(json.optString("telefono"));
                u.setPais(json.optString("pais"));
            } else if("empresa".equals(u.getRol())){
                u.setNombreUsuario(json.optString("nombre"));
                u.setIdEmpresa(json.getInt("idEmpresa"));
            }

            Part avatarPart = req.getPart("avatar");
            if(avatarPart != null && avatarPart.getSize() > 0)
                u.setAvatar(avatarPart.getInputStream().readAllBytes());

            int id = service.crearUsuario(u);
            resp.getWriter().write("{\"message\":\"Usuario creado\",\"id\":"+id+"}");
        } catch(Exception e){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if(path != null && path.length() > 1){
                int id = Integer.parseInt(path.substring(1));
                UsuarioDTO u = service.obtenerUsuario(id);
                if(u == null){
                    resp.setStatus(404);
                    resp.getWriter().write("{\"error\":\"Usuario no encontrado\"}");
                    return;
                }
                resp.getWriter().write(new JSONObject(u).toString());
            } else {
                List<UsuarioDTO> lista = service.obtenerTodos();
                JSONArray arr = new JSONArray(lista);
                resp.getWriter().write(arr.toString());
            }
        } catch(Exception e){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if(path == null || path.length() <= 1) throw new Exception("ID obligatorio en la URL");
            int id = Integer.parseInt(path.substring(1));

            String data = req.getParameter("data");
            if(data == null) throw new Exception("Campo 'data' obligatorio");
            JSONObject json = new JSONObject(data);

            UsuarioDTO u = new UsuarioDTO();
            u.setIdUsuario(id);
            u.setNombreUsuario(json.optString("nombreUsuario"));
            u.setCorreoElectronico(json.optString("correo")); // opcional, inmutable si gamer
            u.setPassword(json.optString("password"));
            u.setFechaNacimiento(Date.valueOf(json.getString("fechaNacimiento")));
            u.setNickname(json.optString("nickname"));
            u.setTelefono(json.optString("telefono"));
            u.setPais(json.optString("pais"));
            u.setRol(json.optString("rol"));
            if(json.has("idEmpresa")) u.setIdEmpresa(json.getInt("idEmpresa"));

            Part avatarPart = req.getPart("avatar");
            if(avatarPart != null && avatarPart.getSize() > 0)
                u.setAvatar(avatarPart.getInputStream().readAllBytes());

            service.actualizarUsuario(u);
            resp.getWriter().write("{\"message\":\"Usuario actualizado\"}");
        } catch(Exception e){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if(path == null || path.length() <= 1) throw new Exception("ID obligatorio en la URL");
            int id = Integer.parseInt(path.substring(1));
            service.eliminarUsuario(id);
            resp.getWriter().write("{\"message\":\"Usuario eliminado\"}");
        } catch(Exception e){
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
        }
    }
}