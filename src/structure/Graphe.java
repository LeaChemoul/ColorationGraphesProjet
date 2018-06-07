package structure;

public abstract class Graphe {
    public abstract int taille();
    public abstract Graphe copie();

    public abstract void ajouterSommet(Sommet s);
    public abstract boolean existeArcOriente(Sommet s, Sommet t);
    public abstract boolean existeSommet(Sommet s);
    public abstract void ajouterArc(Sommet s, Sommet t, int val);
    public abstract void ajouterArrete(Sommet s, Sommet t, int val);
    public abstract int valeurArc(Sommet s, Sommet t);
    public abstract void enleverArc(Sommet s, Sommet t);
}
