
import com.banco.clases.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
  private JTextField tfEmail;
  private JPasswordField pfContraseña;
  private JButton btnOK;
  private JButton btnCancel;
  private JPanel loginPanel;
  public LoginForm(JFrame parent) {
    super(parent);
    setTitle("Login");
    setContentPane(loginPanel);
    setMinimumSize(new Dimension(500, 550));
    setModal(true);
    setLocationRelativeTo(parent);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    btnOK.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String email = tfEmail.getText();
        String contraseña = String.valueOf(pfContraseña.getPassword());

        persona = iniciarSesion(email, contraseña);

        if (persona != null) {
          dispose();
        }
        else {
          JOptionPane.showMessageDialog(LoginForm.this, "Email o Contraseña no validas", "Pruebe de nuevo", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    btnCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        //show Registration form
        RegistrationForm registrationForm = new RegistrationForm(parent);
        Persona persona = registrationForm.persona;

        if (persona != null) {
          //lbAdmin.setText("Cliente: " + persona.nombre);
          setLocationRelativeTo(null);
          setVisible(true);
        }
        else {
          dispose();
        }
      }
    });

    setVisible(true);
  }

  public Persona persona;
  private Persona iniciarSesion(String email, String contraseña) {
    Persona persona = null;

    final String DB_URL = "jdbc:mysql://localhost/banco?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "";

    try{
      Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
      // Connected to database successfully...

      Statement stmt = conn.createStatement();
      String sql = "SELECT * FROM personas WHERE email=? AND contraseña=?";
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, contraseña);

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        persona = new Persona();
        persona.nombre = resultSet.getString("nombre");
        persona.email = resultSet.getString("email");
        persona.telefono = resultSet.getString("telefono");
        persona.apellido = resultSet.getString("apellido");
        persona.contraseña = resultSet.getString("contraseña");
      }

      stmt.close();
      conn.close();

    }catch(Exception e){
      e.printStackTrace();
    }


    return persona;
  }


  public static void main(String[] args) {
    LoginForm loginForm = new LoginForm(null);
    Persona persona = loginForm.persona;
    if (persona != null) {
      System.out.println("Inicio de sesion correcto de: " + persona.nombre);
      System.out.println("          Apellido: " + persona.apellido);
      System.out.println("          Email: " + persona.email);
      System.out.println("          Telefono: " + persona.telefono);
    }
    else {
      System.out.println("Inicio de sesion cancelado");
    }
  }
}
