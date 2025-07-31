package com.organizadorcaja.unl.edu.exception;

/**
 * Excepción personalizada para indicar que se ha excedido
 * la capacidad máxima permitida de un cajón.
 */
public class CapacidadExcedidaException extends Exception {

    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}
