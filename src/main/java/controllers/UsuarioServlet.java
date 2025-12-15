/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import service.UsuarioService;

/**
 *
 * @author sofia
 */
@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioService service = new UsuarioService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setCorreoElectronico(req.getParameter("correo"));
        usuario.setPassword(req.getParameter("password"));
        usuario.setNickname(req.getParameter("nickname"));
        usuario.setFechaNacimiento(Date.valueOf(req.getParameter("fechaNacimiento")));

        try {
            service.crearGamer(usuario);
            resp.setStatus(201);
        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setIdUsuario(Integer.parseInt(req.getParameter("id")));
        usuario.setNickname(req.getParameter("nickname"));
        usuario.setTelefono(req.getParameter("telefono"));

        try {
            service.actualizarUsuario(usuario);
            resp.setStatus(200);
        } catch (Exception e) {
            resp.sendError(400);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            service.eliminarUsuario(Integer.parseInt(req.getParameter("id")));
            resp.setStatus(204);
        } catch (Exception e) {
            resp.sendError(400);
        }
    }
}
