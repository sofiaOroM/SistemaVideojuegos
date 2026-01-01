/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes.model;

import Reportes.dto.ReporteGananciasGlobalDTO;
import Reportes.dto.ReporteIngresosEmpresaDTO;
import com.videojuegosbackend.conexionDB.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class ReportesAdminModel {

    public ReporteGananciasGlobalDTO obtenerGananciasGlobales() throws Exception {
        System.out.println("Ahora en model");
        String sql = "SELECT ROUND(SUM(c.precio_pagado),2) AS total_ingresos, "
                + "ROUND(SUM(c.precio_pagado * (cc.porcentaje_usado / 100)),2) AS ganancia_plataforma, "
                + "ROUND(SUM(c.precio_pagado * (1 - cc.porcentaje_usado / 100)),2) AS ganancia_empresas "
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

    public List<ReporteIngresosEmpresaDTO> obtenerIngresosPorEmpresa() throws Exception {

        String sql = "SELECT e.id_empresa, "
                + "e.nombre_empresa AS nombre_empresa, "
                + "ROUND(SUM(c.precio_pagado),2) AS total_ventas, "
                + "ROUND(SUM(c.precio_pagado * (cc.porcentaje_usado / 100)),2) AS comision_plataforma, "
                + "ROUND(SUM(c.precio_pagado * (1 - cc.porcentaje_usado / 100)),2) AS ingreso_empresa "
                + "FROM empresa e "
                + "JOIN videojuego v ON v.id_empresa = e.id_empresa "
                + "JOIN compras c ON c.id_videojuego = v.id_videojuego "
                + "JOIN compra_comision cc ON cc.id_compra = c.id_compra "
                + "GROUP BY e.id_empresa, e.nombre_empresa "
                + "ORDER BY total_ventas DESC";

        List<ReporteIngresosEmpresaDTO> lista = new ArrayList<>();

        Connection conn = new ConnectionManager().conectar();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ReporteIngresosEmpresaDTO dto = new ReporteIngresosEmpresaDTO();

            dto.setIdEmpresa(rs.getInt("id_empresa"));
            dto.setNombreEmpresa(rs.getString("nombre_empresa"));
            dto.setTotalVentas(rs.getBigDecimal("total_ventas"));
            dto.setComisionPlataforma(rs.getBigDecimal("comision_plataforma"));
            dto.setIngresoEmpresa(rs.getBigDecimal("ingreso_empresa"));

            lista.add(dto);
        }

        conn.close();
        return lista;
    }
}
