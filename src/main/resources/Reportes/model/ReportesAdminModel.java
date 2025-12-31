/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes.model;

import Reportes.dto.ReporteGananciasGlobalDTO;
import com.videojuegosbackend.conexionDB.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author sofia
 */
public class ReportesAdminModel {

    public ReporteGananciasGlobalDTO obtenerGananciasGlobales() throws Exception {
        System.out.println("Ahora en model");
        String sql = "SELECT SUM(c.precio_pagado) AS total_ingresos, "
                + "SUM(c.precio_pagado * (cc.porcentaje_usado / 100)) AS ganancia_plataforma, "
                + "SUM(c.precio_pagado * (1 - cc.porcentaje_usado / 100)) AS ganancia_empresas "
                + "FROM compras c JOIN compra_comision cc ON c.id_compra = cc.id_compra";

        Connection conn = new ConnectionManager().conectar();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ReporteGananciasGlobalDTO dto = new ReporteGananciasGlobalDTO();

        if (rs.next()) {
            dto.setTotalIngresos(rs.getBigDecimal("total_ingresos"));
            dto.setGananciaPlataforma(rs.getBigDecimal("ganancia_plataforma"));
            dto.setGananciaEmpresas(rs.getBigDecimal("ganancia_empresas"));
        }

        conn.close();
        System.out.println(dto);
        return dto;
    }
}
