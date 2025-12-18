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
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
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
    //http://localhost:8080/VideojuegosBackend/api/usuarios?rol=gamer
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        setResponseHeaders(resp);
        try {
            String rol = req.getParameter("rol");
            String correo = req.getParameter("correoElectronico");
            String password = req.getParameter("password");
            String fechaNacimientoStr = req.getParameter("fechaNacimiento");

            if (rol == null || rol.isEmpty()) {
                throw new Exception("Campo 'rol' obligatorio");
            }
            if (correo == null || correo.isEmpty()) {
                throw new Exception("Campo 'correoElectronico' obligatorio");
            }
            if (password == null || password.isEmpty()) {
                throw new Exception("Campo 'password' obligatorio");
            }
            if (fechaNacimientoStr == null || fechaNacimientoStr.isEmpty()) {
                throw new Exception("Campo 'fechaNacimiento' obligatorio");
            }

            UsuarioDTO u = new UsuarioDTO();
            u.setRol(rol);
            u.setCorreoElectronico(correo);
            u.setPassword(password);
            u.setFechaNacimiento(Date.valueOf(fechaNacimientoStr));

            // Campos específicos según el rol
            if ("gamer".equalsIgnoreCase(rol)) {
                String nickname = req.getParameter("nickname");
                String telefono = req.getParameter("telefono");
                String pais = req.getParameter("pais");

                if (nickname == null || nickname.isEmpty()) {
                    throw new Exception("Campo 'nickname' obligatorio para gamers");
                }

                u.setNickname(nickname);
                u.setTelefono(telefono);
                u.setPais(pais);
            } else if ("empresa".equalsIgnoreCase(rol)) {
                String nombreUsuario = req.getParameter("nombreUsuario");
                String idEmpresaStr = req.getParameter("idEmpresa");

                if (nombreUsuario == null || nombreUsuario.isEmpty()) {
                    throw new Exception("Campo 'nombreUsuario' obligatorio para empresas");
                }
                if (idEmpresaStr == null || idEmpresaStr.isEmpty()) {
                    throw new Exception("Campo 'idEmpresa' obligatorio para empresas");
                }

                u.setNombreUsuario(nombreUsuario);
                u.setIdEmpresa(Integer.parseInt(idEmpresaStr));
            } else if ("admin".equalsIgnoreCase(rol)) {
                String nombreUsuario = req.getParameter("nombreUsuario");

                if (nombreUsuario == null || nombreUsuario.isEmpty()) {
                    throw new Exception("Campo 'nombreUsuario' obligatorio para empresas");
                }
                u.setNombreUsuario(nombreUsuario);
            } else {
                throw new Exception("Rol inválido: solo 'gamer' o 'empresa' son permitidos");
            }

            Part avatarPart = req.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0) {
                u.setAvatar(avatarPart.getInputStream().readAllBytes());
            }

            int id = service.crearUsuario(u);

            resp.setStatus(201);
            resp.getWriter().write("{\"message\":\"Usuario creado\",\"id\":" + id + "}");
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
                UsuarioDTO u = service.obtenerUsuario(id);
                if (u == null) {
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
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if (path == null || path.length() <= 1) {
                throw new Exception("ID obligatorio en la URL");
            }
            int id = Integer.parseInt(path.substring(1));

            String data = req.getParameter("data");
            if (data == null) {
                throw new Exception("Campo 'data' obligatorio");
            }
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
            if (json.has("idEmpresa")) {
                u.setIdEmpresa(json.getInt("idEmpresa"));
            }

            Part avatarPart = req.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0) {
                u.setAvatar(avatarPart.getInputStream().readAllBytes());
            }

            service.actualizarUsuario(u);
            resp.getWriter().write("{\"message\":\"Usuario actualizado\"}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if (path == null || path.length() <= 1) {
                throw new Exception("ID obligatorio en la URL");
            }
            int id = Integer.parseInt(path.substring(1));
            service.eliminarUsuario(id);
            resp.getWriter().write("{\"message\":\"Usuario eliminado\"}");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
