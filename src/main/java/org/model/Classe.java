package org.model;

public class Classe extends DB {
    private int id;
    private String nom;
    private int ecole;

    public Classe(Integer id) {
        super();
        this.getConn();
    }

    public static void main(String[] args) {
        Classe classe = new Classe(1);
    }

}
