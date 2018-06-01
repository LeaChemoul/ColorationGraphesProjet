package structure;

public class Arc {

    private Sommet origine, destination;
    private int poids;

    public Arc(Sommet origine, Sommet destination, int poids) {
        this.origine = origine;
        this.destination = destination;
        this.poids = poids;
    }

    //GETTER AND SETTER
    public Sommet getOrigine() {
        return origine;
    }

    public void setOrigine(Sommet origine) {
        this.origine = origine;
    }

    public Sommet getDestination() {
        return destination;
    }

    public void setDestination(Sommet destination) {
        this.destination = destination;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "origine=" + origine.toString() +
                ", destination=" + destination.toString() +
                ", poids=" + poids +
                '}';
    }

    public String toStringGraphVIZ(){
        return origine.toString() +
                " -> " + destination.toString() + "\n"
                + origine.getNom() + "[color="+ origine.getCouleur() +"]\n"
                + destination.getNom() + "[color="+ destination.getCouleur() +"]\n";
    }
}
