package com.banco.clases;

import lombok.Getter;
import lombok.Setter;

public class Persona {
  @Getter @Setter public String nombre;
  @Getter @Setter public String apellido;
  @Getter @Setter public String email;
  @Getter @Setter public String telefono;

  public String contraseña;

  public Persona(String nombre, String apellido, String email, String telefono) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.telefono = telefono;
  }

  public Persona() {

  }
}
