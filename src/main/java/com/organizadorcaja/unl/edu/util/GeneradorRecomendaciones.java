package com.organizadorcaja.unl.edu.util;

import com.organizadorcaja.unl.edu.model.Cajon;
import com.organizadorcaja.unl.edu.model.Objeto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeneradorRecomendaciones {

    public List<String> generarRecomendaciones(Cajon cajon) {
        List<String> recomendaciones = new ArrayList<>();

        // Regla 1: Cajón casi lleno
        if (cajon.porcentajeOcupado() > 80) {
            recomendaciones.add("⚠ Este cajón está casi lleno. Considera no agregar más objetos.");
        }

        // Regla 2: Cajón vacío
        if (cajon.getObjetos().isEmpty()) {
            recomendaciones.add("ℹ Este cajón está vacío. Podrías usarlo para organizar objetos pequeños.");
        }

        // Regla 3: Detectar duplicados
        Set<String> nombres = new HashSet<>();
        Set<String> duplicados = new HashSet<>();
        for (Objeto obj : cajon.getObjetos()) {
            if (!nombres.add(obj.getNombre())) {
                duplicados.add(obj.getNombre());
            }
        }
        if (!duplicados.isEmpty()) {
            recomendaciones.add("🔄 Se encontraron objetos duplicados: " + String.join(", ", duplicados) + ".");
        }

        // Regla 4: Cajón subutilizado
        if (cajon.porcentajeOcupado() < 30 && !cajon.getObjetos().isEmpty()) {
            recomendaciones.add("📦 Este cajón está casi vacío. Podrías optimizar el espacio.");
        }

        // Regla 5: Sugerencia por tipos de objetos
        long ropaCount = cajon.getObjetos().stream()
                .filter(o -> "Ropa".equalsIgnoreCase(o.getTipo()))
                .count();
        if (ropaCount > 0 && ropaCount == cajon.getObjetos().size()) {
            recomendaciones.add("👕 Este cajón contiene solo ropa. ¡Buena organización!");
        }

        return recomendaciones;
    }
}
