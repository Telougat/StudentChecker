package org.model;

import java.util.Date;

public class Presence extends DB {

    // ATTRIBUTS
    private int id;
    private Date date_debut;
    private Date date_fin;

    // CONSTRUCTEUR
    public Presence(int id, Date dateDebut, Date dateFin) {
        this.id = id;
        this.date_debut = dateDebut;
        this.date_fin = dateFin;
    }

    // GETTER ET SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return date_debut;
    }

    public void setDateDebut(Date dateDebut) {
        this.date_debut = dateDebut;
    }

    public Date getDateFin() {
        return date_fin;
    }

    public void setDateFin(Date dateFin) {
        this.date_fin = dateFin;
    }
}
