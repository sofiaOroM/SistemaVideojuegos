/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dto.BibliotecaDTO;
import models.BibliotecaModel;
import java.util.List;

/**
 *
 * @author sofia
 */
public class BibliotecaService {

    private final BibliotecaModel model = new BibliotecaModel();

    public List<BibliotecaDTO> obtenerPorUsuario(int idUsuario) throws Exception {
        return model.obtenerPorUsuario(idUsuario);
    }
}