/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dto.CompraDTO;
import models.CompraModel;

/**
 *
 * @author sofia
 */
public class CompraService {

    private final CompraModel compra = new CompraModel();

    public int crear(CompraDTO c) throws Exception {
        return compra.insertar(c);
    }
}
