package org.model;

public class Groupe {

    // ATTRIBUTS
    private int id;
    private String label;

    // CONSTRUCTEUR
    public Groupe(int id, String label) {
        this.id = id;
        this.label = label;
    }

    // GETTER ET SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
