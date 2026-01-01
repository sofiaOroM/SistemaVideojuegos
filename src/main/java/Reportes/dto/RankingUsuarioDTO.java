/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes.dto;

/**
 *
 * @author sofia
 */
public class RankingUsuarioDTO {

    private int posicion;
    private String correoUsuario;
    private int totalCompras;
    private int totalResenas;
    private int actividadTotal;

    public RankingUsuarioDTO() {}

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public int getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(int totalCompras) {
        this.totalCompras = totalCompras;
    }

    public int getTotalResenas() {
        return totalResenas;
    }

    public void setTotalResenas(int totalResenas) {
        this.totalResenas = totalResenas;
    }

    public int getActividadTotal() {
        return actividadTotal;
    }

    public void setActividadTotal(int actividadTotal) {
        this.actividadTotal = actividadTotal;
    }
    
}
