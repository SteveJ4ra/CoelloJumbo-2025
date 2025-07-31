package com.organizadorcaja.unl.edu.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "objetos")
public class Objeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String tipo;

    @Enumerated(EnumType.STRING)
    private Tamano tamano;  // Enum: PEQUEÑO, MEDIANO, GRANDE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cajon_id")
    private Cajon cajon;

    // equals y hashCode para detectar duplicados (basado en nombre, tipo y tamaño)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Objeto)) return false;
        Objeto obj = (Objeto) o;
        return Objects.equals(nombre, obj.nombre) &&
                Objects.equals(tipo, obj.tipo) &&
                tamano == obj.tamano;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, tamano);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }

    public Cajon getCajon() {
        return cajon;
    }

    public void setCajon(Cajon cajon) {
        this.cajon = cajon;
    }
}
