/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegosbackend.services;

import com.videojuegosbackend.conexionDB.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author sofia
 */
public class App {

    public static void main(String[] args) {
        ConnectionManager mysqlManager = new ConnectionManager();
        Connection conn = mysqlManager.conectar();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                String tableSql = "SELECT * FROM cliente";
                ResultSet rs = stmt.executeQuery(tableSql);
                while (rs.next()) {
                    System.out.println("ID Cliente: " + rs.getInt("id_cliente") + ", Nombre Cliente:" + rs.getString("nombre_cliente") + ", Cidudad: " + rs.getString("ciudad"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mysqlManager.desconectar(conn);
            }
        }
        
        InnerJoin();
    }

    public static void InnerJoin() {
        ConnectionManager mysqlManager = new ConnectionManager();
        Connection conn = mysqlManager.conectar();

        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                String tableSql = "SELECT cliente.id_cliente,\n"
                        + "	cliente.nombre_cliente,\n"
                        + "    ordenes.id_orden,\n"
                        + "    ordenes.fecha,\n"
                        + "    articulos.nombre_articulo\n"
                        + "FROM ordenes\n"
                        + "INNER JOIN articulos\n"
                        + "	on ordenes.id_articulo = articulos.id_articulo\n"
                        + "INNER JOIN ordenes_clientes oc\n"
                        + "	ON ordenes.id_orden = oc.id_orden\n"
                        + "INNER JOIN cliente\n"
                        + "	ON oc.id_cliente = cliente.id_cliente\n";
                ResultSet rs = stmt.executeQuery(tableSql);
                while (rs.next()) {
                    System.out.println("ID Cliente: " + rs.getInt("id_cliente") + ", Nombre Cliente: " + rs.getString("nombre_cliente")
                            + ", Id Orden: " + rs.getInt("id_orden") + ", Fecha: " + rs.getDate("fecha")
                            + ", Articulo: " + rs.getString("nombre_articulo"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mysqlManager.desconectar(conn);
            }
        }
    }
}
