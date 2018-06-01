package structure;

import java.awt.*;
import java.util.ArrayList;

public class Sommet {

    private String nom;
    private int marque;
    private String couleur;
    private int coul;
    private int valeur;
    private ArrayList<String> couleursVoisins = new ArrayList<>();

    public Sommet(String nom, int marque, String couleur, int valeur) {
        this.nom = nom;
        this.marque = marque;
        this.couleur = couleur;
        this.valeur = valeur;
        this.coul = -1;
    }

    //COMPARAISON
    public boolean equals(Object o){
        return nom.equals(((Sommet)o).nom);
    }

    public int compareTo(Object o){
        Sommet s = (Sommet)o;
        return nom.compareTo(s.nom);
    }

    //GETTER AND SETTER

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMarque() {
        return marque;
    }

    public void setMarque(int marque) {
        this.marque = marque;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public ArrayList<String> getCouleursVoisins() {
        return couleursVoisins;
    }

    public void setCoul(int coul) {
        this.coul = coul;
    }

    public int getCoul() {
        return coul;
    }

    @Override
    public String toString() {
        return "Sommet{" +
                "nom='" + nom + '\'' +
                ", marque=" + marque +
                ", couleur=" + couleur +
                '}';
    }

    public String toStringColor() {
        return "Sommet{" +
                "nom='" + nom + '\'' +
                ", couleur=" + coul +
                '}';
    }
}
