/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dto.CalificacionDTO;
import models.CalificacionModel;

/**
 *
 * @author sofia
 */
public class CalificacionService {

    private CalificacionModel calificacionModel = new CalificacionModel();

    public int crearCalificacion(CalificacionDTO calificacion) throws Exception {
        int idCalificacion = calificacionModel.insertar(calificacion);
        return idCalificacion;
    }

    public String listarPorVideojuego(int idVideojuego) throws Exception {
        return calificacionModel.listar(idVideojuego);
    }

    public String obtenerPromedio(int idVideojuego) throws Exception {
        return calificacionModel.promedio(idVideojuego);
    }

}
