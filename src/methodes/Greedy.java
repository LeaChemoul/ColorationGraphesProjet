package methodes;

import structure.Arc;
import structure.GrapheListe;
import structure.Sommet;

import java.util.*;

public class Greedy {


    private GrapheListe graphe;

    public Greedy(GrapheListe graphe) {
        this.graphe = graphe;
    }

    /**
     * Algorithme de Greedy.
     * @param tri 1 pour décroissant, 2 pour croissant, 3 pour aléatoire
     */
    public void algorithme(int tri){
        LinkedList<String> couleursUtil = new LinkedList<>();
        LinkedList<Sommet> listeOrdo = Tris.trier(tri, graphe);

        for (Sommet aListeOrdo : listeOrdo) {
            aListeOrdo.setCoul(-1);
            aListeOrdo.setCouleur("white");
        }

        Couleur[] couleurs = Couleur.values();

        do {
            Sommet x = listeOrdo.get(0);
            LinkedList<String> couleursVoisins = new LinkedList<>();

            //On cherche la couleur de tous les sommets adjacents
            for (LinkedList<Arc> arcs: graphe.getL().values()) {
                for (Arc arc: arcs){
                    if(arc.getOrigine().equals(x) && !arc.getDestination().getCouleur().equals("white"))
                        couleursVoisins.add(arc.getDestination().getCouleur());
                }
            }
            String petiteCouleur = plusPetiteCouleur(couleursVoisins,couleurs);
            x.setCouleur(petiteCouleur);
            if(!couleursUtil.contains(petiteCouleur))
                couleursUtil.add(petiteCouleur);
            listeOrdo.remove(0);
            x.setCoul(couleursUtil.indexOf(petiteCouleur));
        }while(!listeOrdo.isEmpty());


        graphe.setNbrChromatique(couleursUtil.size());
        //System.out.println("Coloration valide greeedy : " + graphe.colorationValide());
        //System.out.println(this.toString()); //pour afficher les couleurs assignées
    }

    /**
     * Cherche la plus petit couleur disponible pour un sommet.
     * @param couleursVoisins Couleurs déjà utilisées apr els voisins du sommet.
     * @param couleurs Couleurs disponibles.
     * @return String de la couleur
     * @throws NullPointerException si aucune couleur est disponible
     */
    private String plusPetiteCouleur(LinkedList<String> couleursVoisins, Couleur[] couleurs) throws NullPointerException{
        for (Couleur couleur : couleurs) {
            if (!couleursVoisins.contains(couleur.getC()))
                return couleur.getC();
        }
        throw new NullPointerException();
    }

    @Override
    public String toString() {
        return "Greedy{" +
                "graphe=" + graphe.toStringConsole() +
                '}';
    }
}
