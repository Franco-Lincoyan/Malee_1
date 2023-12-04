package MantenedorEmpleado;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author vexer
 */
public class frmEliminarE extends javax.swing.JFrame {

    /**
     * Creates new form frmEliminarE
     */
    public frmEliminarE() {
        initComponents();
        llenarComboEliminar();
        this.setLocationRelativeTo(null);
    }

    //LLENAR COMBOBOX
    public void llenarComboEliminar() {
        try {
            cmbNombre.removeAllItems();

            String consulta = "SELECT Nombre FROM Empleado";
            Connection con = Conexion.getConexion();
            PreparedStatement pstmt = con.prepareStatement(consulta);
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                String Nombre = resultado.getString("Nombre");
                cmbNombre.addItem(Nombre);
            }

            resultado.close();
            pstmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
     private void cargarDatosEmpleado() {
        String nombreEmpleado = (String) cmbNombre.getSelectedItem();

        try {
            Connection con = Conexion.getConexion();
            String sql = "SELECT RutUsuario, NumEmpleado, Descripcion, Telefono, estado, Domicilio, F_nacimiento, F_contratacion, Sexo FROM empleado WHERE Nombre = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nombreEmpleado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtRut.setText(rs.getString("RutUsuario"));
                txtNumero.setText(rs.getString("NumEmpleado"));
                txtDescripcion.setText(rs.getString("Descripcion"));
                txtTelefono.setText(rs.getString("Telefono"));
                txtContratacion.setText(rs.getString("F_contratacion"));
                txtNacimiento.setText(rs.getString("F_nacimiento"));
                txtDomicilio.setText(rs.getString("Domicilio"));
                txtSexo.setText(rs.getString("Sexo"));

                String estado = rs.getString("estado");
                if ("A".equals(estado)) {
                    txtEstado.setText("A");
                } else if ("I".equals(estado)) {
                    txtEstado.setText("I");
                } else {
                    txtEstado.setText("Estado desconocido");
                }
            } else {
                txtRut.setText("");
                txtNumero.setText("");
                txtDescripcion.setText("");
                txtTelefono.setText("");
                txtEstado.setText("");
                txtContratacion.setText("");
                txtNacimiento.setText("");
                txtDomicilio.setText("");
                txtSexo.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos.");
        }
    }

    private boolean verificarEmpleadoEnPlanificacion(String numEmpleado) {
        try {
            Connection con = Conexion.getConexion();
            String sql = "SELECT COUNT(*) FROM Planificacion WHERE NumEmpleado = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, numEmpleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                rs.close();
                stmt.close();
                con.close();
                return true;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al verificar empleado en Planificacion.");
        }
        return false;
    }

    private boolean verificarEmpleadoEnUsuario(String rutEmpleado) {
        try {
            Connection con = Conexion.getConexion();
            String sql = "SELECT COUNT(*) FROM usuario WHERE Rut_Usuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rutEmpleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                rs.close();
                stmt.close();
                con.close();
                return true;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al verificar empleado en Usuario.");
        }
        return false;
    }
    private void eliminarEmpleado(String rutEmpleado) {
        try {
            Connection con = Conexion.getConexion();
            String sql = "DELETE FROM empleado WHERE RutUsuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rutEmpleado);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
                // Limpiar campos y actualizar el combo
                txtRut.setText("");
                txtNumero.setText("");
                txtDescripcion.setText("");
                txtTelefono.setText("");
                txtEstado.setText("");
                txtContratacion.setText("");
                txtNacimiento.setText("");
                txtDomicilio.setText("");
                txtSexo.setText("");
                llenarComboEliminar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el empleado.");
            }

            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar empleado: " + ex.getMessage());
        }
    }
    
    private void cambiarEstadoEmpleado(String rutEmpleado) {
        try {
            Connection con = Conexion.getConexion();
            String sql = "UPDATE empleado SET estado = 'I' WHERE RutUsuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rutEmpleado);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Estado del empleado cambiado a 'Inactivo'.");
                // Limpiar campo de estado y actualizar el combo
                txtEstado.setText("I");
                llenarComboEliminar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al cambiar el estado del empleado.");
            }

            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cambiar el estado del empleado: " + ex.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmbNombre = new javax.swing.JComboBox<>();
        txtNumero = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtRut = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblRut1 = new javax.swing.JLabel();
        lblEstado1 = new javax.swing.JLabel();
        lblNumero = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        lblContratacion = new javax.swing.JLabel();
        lblNacimiento = new javax.swing.JLabel();
        txtNacimiento = new javax.swing.JTextField();
        txtContratacion = new javax.swing.JTextField();
        lblSexo = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        lblDomicilio = new javax.swing.JLabel();
        txtSexo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cmbNombre.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        cmbNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNombreActionPerformed(evt);
            }
        });

        txtNumero.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtNumero.setEnabled(false);

        txtEstado.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtEstado.setEnabled(false);
        txtEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoActionPerformed(evt);
            }
        });

        txtTelefono.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtTelefono.setEnabled(false);

        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtDescripcion.setRows(5);
        txtDescripcion.setEnabled(false);
        jScrollPane1.setViewportView(txtDescripcion);

        txtRut.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtRut.setEnabled(false);

        lblNombre.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");

        lblTelefono.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblTelefono.setText("Telefono");

        lblRut1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblRut1.setText("Rut");

        lblEstado1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblEstado1.setText("Estado");

        lblNumero.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblNumero.setText("Numero");

        lblDescripcion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblDescripcion.setText("Descripcion");

        btnEliminar.setBackground(new java.awt.Color(255, 51, 0));
        btnEliminar.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(0, 0, 0));
        btnVolver.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.setBorder(null);
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lblContratacion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblContratacion.setText("Fecha de contratación");

        lblNacimiento.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblNacimiento.setText("Fecha de nacimiento");

        txtNacimiento.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtNacimiento.setEnabled(false);

        txtContratacion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtContratacion.setEnabled(false);

        lblSexo.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblSexo.setText("Sexo");

        txtDomicilio.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtDomicilio.setEnabled(false);

        lblDomicilio.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lblDomicilio.setText("Domicilio");

        txtSexo.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtSexo.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setText("ELIMINAR EMPLEADO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblContratacion)
                            .addComponent(lblNombre)
                            .addComponent(lblTelefono)
                            .addComponent(lblRut1)
                            .addComponent(lblEstado1)
                            .addComponent(lblNumero)
                            .addComponent(lblDescripcion)
                            .addComponent(lblNacimiento)
                            .addComponent(lblSexo)
                            .addComponent(lblDomicilio))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbNombre, javax.swing.GroupLayout.Alignment.LEADING, 0, 150, Short.MAX_VALUE)
                                .addComponent(txtEstado, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDomicilio, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSexo, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtContratacion, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNacimiento, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtRut, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNumero, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRut1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNacimiento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContratacion)
                    .addComponent(txtContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSexo)
                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDomicilio)
                    .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstado1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescripcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstadoActionPerformed

    private void cmbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNombreActionPerformed
    cargarDatosEmpleado();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbNombreActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
       this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        String rutEmpleado = txtRut.getText();

    if (verificarEmpleadoEnPlanificacion(txtNumero.getText())) {
        JOptionPane.showMessageDialog(this, "No se puede eliminar. El empleado está asignado a una planificación.");
    } else if (verificarEmpleadoEnUsuario(rutEmpleado)) {
        int confirmacion = JOptionPane.showConfirmDialog(this, "El empleado tiene un usuario asociado. ¿Deseas cambiar su estado a 'Inactivo'?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            cambiarEstadoEmpleado(rutEmpleado);
        }
    } else {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar al empleado?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            eliminarEmpleado(rutEmpleado);
        }
    }
    
       //
    }//GEN-LAST:event_btnEliminarActionPerformed


private void limpiarCampos() {
    txtRut.setText("");
    txtNumero.setText("");
    txtDescripcion.setText("");
    txtTelefono.setText("");
    txtEstado.setText("");
    txtContratacion.setText("");
    txtNacimiento.setText("");
    txtDomicilio.setText("");
    txtSexo.setText("");
    
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmEliminarE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEliminarE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEliminarE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEliminarE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmEliminarE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cmbNombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContratacion;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblDomicilio;
    private javax.swing.JLabel lblEstado1;
    private javax.swing.JLabel lblNacimiento;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblRut1;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JTextField txtContratacion;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtNacimiento;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtRut;
    private javax.swing.JTextField txtSexo;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

private boolean eliminarEmpleadoFisico(String nombreEmpleado) {
    try {
        Connection con = Conexion.getConexion();
        String sql = "DELETE FROM empleado WHERE Nombre = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nombreEmpleado);

        int filasAfectadas = stmt.executeUpdate();

        stmt.close();
        con.close();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(this, "Empleado eliminado físicamente.");
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar al empleado.");
            return false;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al eliminar al empleado.");
        return false;
    }
}}
