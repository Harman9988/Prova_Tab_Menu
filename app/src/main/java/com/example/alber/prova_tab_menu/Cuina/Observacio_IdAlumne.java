package com.example.alber.prova_tab_menu.Cuina;

public class Observacio_IdAlumne {
    private String observacio;
    private int id_alumne;
    private int contador_alumnes;

    public Observacio_IdAlumne(String observacio, int id_alumne, int contador_alumnes) {
        this.observacio = observacio;
        this.id_alumne = id_alumne;
        this.contador_alumnes = contador_alumnes;
    }

    public int getContador_alumnes() {
        return contador_alumnes;
    }

    public void setContador_alumnes(int contador_alumnes) {
        this.contador_alumnes = contador_alumnes;
    }

    public String getObservacio() {
        return observacio;
    }

    public void setObservacio(String observacio) {
        this.observacio = observacio;
    }


    public void setId_alumne(int id_alumne) {
        this.id_alumne = id_alumne;
    }

    public int getId_alumne() {
        return id_alumne;
    }
    public void augmentarAlumneContador(){ contador_alumnes++;
    }

}