/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MantenedorEmpleado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class Conexion {
    public static Connection getConexion() {
        String conexionUrl = "jdbc:sqlserver://25.56.220.223:1433;"
                + "databaseName=Malee;" 
                + "user=sa;"
                + "password=danirl12;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30";

        try {
            Connection con = DriverManager.getConnection(conexionUrl);
            //System.out.println("Conexión exitosa a la base de datos.");
            return con;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.toString());
            return null;
        }
    }
}
