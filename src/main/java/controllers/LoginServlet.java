/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import dto.UsuarioDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import service.UsuarioService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    private UsuarioService service = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            BufferedReader reader = req.getReader();
            Gson gson = new Gson();

            UsuarioDTO requestUser = gson.fromJson(reader, UsuarioDTO.class);

            if (requestUser == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"JSON inválido o vacío\"}");
                return;
            }

            UsuarioDTO user = service.login(
                    requestUser.getCorreoElectronico(),
                    requestUser.getPassword()
            );

            if (user == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"mensaje\":\"Correo o contraseña incorrectos\"}");
                return;
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(user));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Error interno en el servidor\"}");
        }
    }
}