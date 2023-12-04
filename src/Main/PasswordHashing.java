package Main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
/**
 *
 * @author Daniel
 */
public class PasswordHashing {
    
 private static Argon2 argon2 = Argon2Factory.create();

    public static String encriptarPassword(String password) {
        char[] passwordChars = password.toCharArray();
        String hash = argon2.hash(10, 65536, 1, passwordChars);
        return hash;
    }

    public static boolean verificarPassword(String password, String hash) {
        char[] passwordChars = password.toCharArray();
        return argon2.verify(hash, passwordChars);
    }

    public static void main(String[] args) {
        // Ejemplo de encriptar una contraseña
        String contraseñaOriginal = "miContraseñaSecreta";
        String contraseñaEncriptada = encriptarPassword(contraseñaOriginal);

        System.out.println("Contraseña Original: " + contraseñaOriginal);
        System.out.println("Contraseña Encriptada: " + contraseñaEncriptada);

        // Ejemplo de verificar una contraseña
        String contraseñaIngresada = "miContraseñaSecreta"; 
        boolean contraseñaCorrecta = verificarPassword(contraseñaIngresada, contraseñaEncriptada);

        if (contraseñaCorrecta) {
            System.out.println("Contraseña válida");
        } else {
            System.out.println("Contraseña incorrecta");
        }
    }
}