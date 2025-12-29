/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;

/**
 *
 * @author sofia
 */
public class ComentarioService {
    private ComentarioModel comentarioModel = new ComentarioModel(); 
    
    public void activarComentario(int idVideojuego, Connection conn) throws Exception {
        comentarioModel.activarComentario(idVideojuego, conn);        
    }
    
    public boolean obtenerEstado(int idVideojuego) throws Exception {
        boolean estado = comentarioModel.obtenerEstado(idVideojuego);
        return estado;
    }
    
    public void actualizar(int idVideojuego) throws Exception {
        comentarioModel.actualizar(idVideojuego);
    }
}
