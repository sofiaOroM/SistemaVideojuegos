/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes.dto;

import java.math.BigDecimal;

/**
 *
 * @author sofia
 */
public class ReporteIngresosEmpresaDTO {
        private int idEmpresa;
    private String nombreEmpresa;

    private BigDecimal totalVentas;
    private BigDecimal comisionPlataforma;
    private BigDecimal ingresoEmpresa;

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public BigDecimal getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(BigDecimal totalVentas) {
        this.totalVentas = totalVentas;
    }

    public BigDecimal getComisionPlataforma() {
        return comisionPlataforma;
    }

    public void setComisionPlataforma(BigDecimal comisionPlataforma) {
        this.comisionPlataforma = comisionPlataforma;
    }

    public BigDecimal getIngresoEmpresa() {
        return ingresoEmpresa;
    }

    public void setIngresoEmpresa(BigDecimal ingresoEmpresa) {
        this.ingresoEmpresa = ingresoEmpresa;
    }
}
