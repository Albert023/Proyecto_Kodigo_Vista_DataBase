import com.banco.clases.Cliente;
import com.banco.clases.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog {
  private JTextField tfNombre;
  private JTextField tfEmail;

  private JTextField tfTelefono;
  private JTextField tfApellido;
  private JPasswordField pfContraseña;
  private JPasswordField pfConfirmarContraseña;
  private JButton btnRegistrar;
  private JButton btnCancelar;
  private JPanel registerPanel;

  public RegistrationForm(JFrame parent) {
    super(parent);
    setTitle("Crear una nueva cuenta");
    setContentPane(registerPanel);
    setMinimumSize(new Dimension(500, 550));
    setModal(true);
    setLocationRelativeTo(parent);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    btnRegistrar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        registrarCliente();

      }
    });
    btnCancelar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    
    setVisible(true);
  }

  private void registrarCliente() {
    String nombre = tfNombre.getText();
    String email = tfEmail.getText();
    String telefono = tfTelefono.getText();
    String apellido = tfApellido.getText();
    String contraseña = String.valueOf(pfContraseña.getPassword());
    String confirmarContraseña = String.valueOf(pfConfirmarContraseña.getPassword());

    if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || apellido.isEmpty() || contraseña.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Por favor ingrese todos los datos", "Prueba de nuevo", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (!contraseña.equals(confirmarContraseña)) {
      JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Prueba de nuevo", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String codigo ="12345";
    String codigoEmail = JOptionPane.showInputDialog("Digite el codigo que se envio a su correo");
    if(codigo.equals(codigoEmail))
    persona = AgregarClienteBaseDeDatos(nombre,apellido,email,telefono,contraseña);

    if (persona != null) {
      dispose();
    }

    else {
      JOptionPane.showMessageDialog(this,
              "Fallo al registrar el cliente", "Prueba de nuevo", JOptionPane.ERROR_MESSAGE);
    }

  }

  public Persona persona;

  private Persona AgregarClienteBaseDeDatos(String nombre, String apellido, String email, String telefono, String contraseña) {

    Persona persona = null;
    final String DB_URL = "jdbc:mysql://localhost/banco?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "";

    try{
      Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
      // Connected to database successfully...

      Statement stmt = conn.createStatement();
      String sql = "INSERT INTO personas (nombre, apellido, telefono, email, contraseña) " + "VALUES (?, ?, ?, ?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setString(1, nombre);
      preparedStatement.setString(2, apellido);
      preparedStatement.setString(3, telefono);
      preparedStatement.setString(4, email);
      preparedStatement.setString(5, contraseña);

      //Insert row into the table
      int addedRows = preparedStatement.executeUpdate();
      if (addedRows > 0) {
        persona = new Persona();
        persona.nombre = nombre;
        persona.apellido = apellido;
        persona.email = email;
        persona.telefono = telefono;
        persona.contraseña = contraseña;
      }

      stmt.close();
      conn.close();
    }catch(Exception e){
      e.printStackTrace();
    }


    return persona;
  }

  public static void main(String[] args) {

    RegistrationForm form = new RegistrationForm(null);
    Persona persona = form.persona;
    if (persona != null) {
      System.out.println("Successful registration of: " + persona.nombre);
    }
    else {
      System.out.println("Registration canceled");
  }
  }
}