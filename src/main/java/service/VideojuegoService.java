/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dto.VideojuegoDTO;
import java.util.List;
import models.VideojuegoModel;

/**
 *
 * @author sofia
 */
public class VideojuegoService {

    private final VideojuegoModel videojuego = new VideojuegoModel();

    public int crear(VideojuegoDTO v) throws Exception {
        if (v.getTitulo() == null || v.getTitulo().isEmpty()) {
            throw new Exception("Título obligatorio");
        }
        if (v.getImagenPrincipal() == null) {
            throw new Exception("Imagen principal obligatoria");
        }
        return videojuego.insertar(v);
    }

    public VideojuegoDTO obtener(int id) throws Exception {
        return videojuego.obtenerPorId(id);
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
