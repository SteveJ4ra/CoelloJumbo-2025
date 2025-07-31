package com.organizadorcaja.unl.edu.util;

import com.organizadorcaja.unl.edu.model.Objeto;

import java.util.Comparator;
import java.util.List;

public class OrdenadorObjetos {

    /**
     * Ordena los objetos por tipo (alfabéticamente).
     */
    public static void ordenarPorTipo(List<Objeto> objetos) {
        objetos.sort(Comparator.comparing(
                Objeto::getTipo,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));
    }

    /**
     * Ordena los objetos por tamaño (de menor a mayor).
     */
    public static void ordenarPorTamano(List<Objeto> objetos) {
        objetos.sort(Comparator.comparing(
                Objeto::getTamano,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));
    }

    /**
     * Ordena los objetos por nombre (alfabéticamente).
     */
    public static void ordenarPorNombre(List<Objeto> objetos) {
        objetos.sort(Comparator.comparing(
                Objeto::getNombre,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));
    }
}
