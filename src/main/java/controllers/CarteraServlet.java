/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.CarteraDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.math.BigDecimal;
import models.CarteraModel;
import org.json.JSONObject;
import service.CarteraService;

/**
 *
 * @author sofia
 */
@WebServlet("/api/cartera/*")
public class CarteraServlet extends HttpServlet {

    private final CarteraService carteraService = new CarteraService();
    private final CarteraModel Cartera = new CarteraModel();
    private final CarteraDTO CarteraDTO = new CarteraDTO();

    private void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);
        try {
            String path = req.getPathInfo();
            if (path != null && path.length() > 1) {
                int id = Integer.parseInt(path.substring(1));
                CarteraDTO c = carteraService.obtenerCarteraUsuario(id);
                if (c == null) {
                    resp.setStatus(404);
                    resp.getWriter().write("{\"error\":\"Cartera no encontrada\"}");
                    return;
                }
                JSONObject json = carteraToJson(c);
                resp.getWriter().write(json.toString());
            }
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setResponseHeaders(resp);

        try {
            String path = req.getPathInfo(); // /8/recargar

            if (path != null && path.matches("/\\d+/recargar")) {

                int idUsuario = Integer.parseInt(path.split("/")[1]);

                BufferedReader reader = req.getReader();
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                Gson gson = new Gson();
                JsonObject body = gson.fromJson(json.toString(), JsonObject.class);

                BigDecimal cantidad = body.get("cantidad").getAsBigDecimal();

                carteraService.recargarPorUsuario(idUsuario, cantidad);

                resp.getWriter().write("{\"message\":\"Recarga exitosa\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private JSONObject carteraToJson(CarteraDTO c) {
        JSONObject json = new JSONObject();

        json.put("idCartera", c.getIdCartera());
        json.put("idUsuario", c.getIdUsuario());
        json.put("saldo", c.getSaldo());

        return json;
    }

}
