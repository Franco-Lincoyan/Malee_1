/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

/**
 *
 * @author Daniel
 */
public class UsuarioInfo {
    private String rutUsuario;
    private String id;

    public UsuarioInfo(String rutUsuario, String id) {
        this.rutUsuario = rutUsuario;
        this.id = id;
    }

    public String getRutUsuario() {
        return rutUsuario;
    }

    public String getId() {
        return id;
    }
    
    public String toString() {
        return "UsuarioInfo{" +
                "rutUsuario='" + rutUsuario + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}