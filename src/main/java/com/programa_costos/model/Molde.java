package com.programa_costos.model;

public class Molde {
    public int largo;
    public int ancho;
    public int grueso;
    public int numPiezas;
    public int gruesoTotal;
    public int numCapas;
    public int profundidad;

    public Molde(int largo, int ancho, int grueso, int numPiezas, int gruesoTotal, int numCapas, int profundidad) {
        this.largo = largo;
        this.ancho = ancho;
        this.grueso = grueso;
        this.numPiezas = numPiezas;
        this.gruesoTotal = gruesoTotal;
        this.numCapas = numCapas;
        this.profundidad = profundidad;
    }
}