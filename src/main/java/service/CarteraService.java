/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import models.CarteraModel;

/**
 *
 * @author sofia
 */
public class CarteraService {

    private CarteraModel carteraModel = new CarteraModel();
    
    public void crearCartera(int idUsuario, Connection conn) throws Exception {
        carteraModel.crearCartera(idUsuario, conn);
    }
    
}
