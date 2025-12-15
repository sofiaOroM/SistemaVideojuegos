/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author sofia
 */

public class PasswordUtil {

    public static String encriptar(String passwordPlano) {
        return BCrypt.hashpw(passwordPlano, BCrypt.gensalt());
    }

    public static boolean verificar(String passwordPlano, String hash) {
        return BCrypt.checkpw(passwordPlano, hash);
    }
}

