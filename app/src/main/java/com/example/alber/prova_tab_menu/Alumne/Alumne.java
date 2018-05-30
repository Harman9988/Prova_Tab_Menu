package com.example.alber.prova_tab_menu.Alumne;

public class Alumne {

    private int id;
    private String nom;
    private String cognom;
    private String image;
    private boolean checked;

    public Alumne(int id, String nom, String cognom, String image) {
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
        this.image = image;
        this.checked=false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isChecked() { return checked; }

    public void setChecked(boolean checked) { this.checked = checked; }

}
