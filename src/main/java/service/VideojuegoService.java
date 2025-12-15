/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import dto.VideojuegoDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import models.VideojuegoCategoriaModel;
import models.VideojuegoModel;

/**
 *
 * @author sofia
 */
public class VideojuegoService {

    private final VideojuegoModel videojuego = new VideojuegoModel();
    private final VideojuegoCategoriaModel categoria = new VideojuegoCategoriaModel();

    public void crearVideojuego(VideojuegoDTO v, List<Integer> categorias, byte[] imagen)
            throws Exception {

        validar(v);

        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();

        try {
            conn.setAutoCommit(false);

            int idVideojuego = videojuego.insertarYRetornarId(v, conn);

            if (imagen != null) {
                videojuego.actualizarImagen(conn, idVideojuego, imagen);
            }

            categoria.insertarCategorias(conn, idVideojuego, categorias);

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            cm.desconectar(conn);
        }
    }

    public void actualizarVideojuego(VideojuegoDTO v, List<Integer> categorias, byte[] imagen)
            throws Exception {

        if (v.getIdVideojuego() <= 0)
            throw new Exception("ID de videojuego inválido");

        validar(v);

        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();

        try {
            conn.setAutoCommit(false);

            videojuego.actualizarVideojuego(v, conn);
            categoria.eliminarPorVideojuego(conn, v.getIdVideojuego());
            categoria.insertarCategorias(conn, v.getIdVideojuego(), categorias);

            if (imagen != null) {
                videojuego.actualizarImagen(conn, v.getIdVideojuego(), imagen);
            }

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            cm.desconectar(conn);
        }
    }

    public void eliminarVideojuego(int idVideojuego) throws Exception {

        ConnectionManager cm = new ConnectionManager();
        Connection conn = cm.conectar();

        try {
            conn.setAutoCommit(false);
            categoria.eliminarPorVideojuego(conn, idVideojuego);
            videojuego.eliminarVideojuego(idVideojuego, conn);
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            cm.desconectar(conn);
        }
    }

    private void validar(VideojuegoDTO v) throws Exception {

        if (v.getTitulo() == null || v.getTitulo().length() < 3)
            throw new Exception("Título inválido");

        if (v.getPrecio() == null || v.getPrecio().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("Precio inválido");

        if (!List.of("E","T","M").contains(v.getClasificacion()))
            throw new Exception("Clasificación inválida");

        if (v.getIdEmpresa() <= 0)
            throw new Exception("Empresa obligatoria");
    }
}