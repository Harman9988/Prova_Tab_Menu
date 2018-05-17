package com.example.alber.prova_tab_menu;

public class Alumnes {

    private int id;
    private String nom;
    private String cognom;
    private String image;

    public Alumnes(int id, String nom, String cognom, String image) {
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
        this.image = image;
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


}
