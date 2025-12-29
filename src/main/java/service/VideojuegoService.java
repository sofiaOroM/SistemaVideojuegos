/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.VideojuegoDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.VideojuegoModel;

import dto.VideojuegoDTO;

/**
 *
 * @author sofia
 */
public class VideojuegoService {

    private final VideojuegoModel videojuego = new VideojuegoModel();
    private final ComentarioService comentarioService = new ComentarioService();

    public int crear(VideojuegoDTO v) throws Exception {
        if (v.getTitulo() == null || v.getTitulo().isEmpty()) {
            throw new Exception("Título obligatorio");
        }
        if (v.getImagenPrincipal() == null) {
            throw new Exception("Imagen principal obligatoria");
        }
        ConnectionManager cm = new ConnectionManager();
        try (Connection conn = cm.conectar()) {
            conn.setAutoCommit(false);
            System.out.println("conexion:" + conn);
            int idVideojuego = videojuego.insertar(v, conn);
            System.out.println("conexion:" + conn);
            comentarioService.activarComentario(idVideojuego, conn);
            System.out.println("conexion:" + conn);
            conn.commit();
            conn.setAutoCommit(true);
            conn.close();
            return idVideojuego;
        } catch (Exception e) {
            throw e;
        }
    }

    public VideojuegoDTO obtener(int id) throws Exception {
        return videojuego.obtenerPorId(id);
    }

    public List<VideojuegoDTO> obtenerPorEmpresa(int idEmpresa) throws Exception {
        return videojuego.obtenerPorEmpresa(idEmpresa);
    }

    public List<VideojuegoDTO> obtenerTodos() throws Exception {
        return videojuego.obtenerTodos();
    }

    public void actualizar(VideojuegoDTO v) throws Exception {
        if (v.getTitulo() == null || v.getTitulo().isEmpty()) {
            throw new Exception("Título obligatorio");
        }
        if (v.getImagenPrincipal() == null) {
            throw new Exception("Imagen principal obligatoria");
        }
        videojuego.actualizar(v);
    }

    public void eliminar(int id) throws Exception {
        videojuego.eliminar(id);
    }
}