package com.banco.clases.clasesImpresion;

import com.banco.clases.Banco;
import com.banco.clases.Cuenta;
import com.banco.interfaces.Imprimir;

public class imprimirConsola implements Imprimir {
  @Override
  public void imprimir(Cuenta cuenta, Banco banco, double deposito, double total, String transaccion) {
    // agregar codigo

    System.out.println("Recibo");
  }
}
