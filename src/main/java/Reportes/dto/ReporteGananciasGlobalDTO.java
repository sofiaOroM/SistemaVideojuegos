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
public class ReporteGananciasGlobalDTO {
    
    private BigDecimal totalIngresos;
    private BigDecimal gananciaEmpresas;
    private BigDecimal gananciaPlataforma;

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public BigDecimal getGananciaEmpresas() {
        return gananciaEmpresas;
    }

    public void setGananciaEmpresas(BigDecimal gananciaEmpresas) {
        this.gananciaEmpresas = gananciaEmpresas;
    }

    public BigDecimal getGananciaPlataforma() {
        return gananciaPlataforma;
    }

    public void setGananciaPlataforma(BigDecimal gananciaPlataforma) {
        this.gananciaPlataforma = gananciaPlataforma;
    }
    
    
}