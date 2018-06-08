package methodes;

import structure.Arc;
import structure.GrapheListe;
import structure.Sommet;

import java.util.*;

public class WelshPowell {

    private GrapheListe graphe;

    public WelshPowell(GrapheListe graphe) {
        this.graphe = graphe;
    }

    //tri : 1 pour décroissant, 2 pour croissant, 3 pour aléatoire
    public void algorithme(int tri){
        LinkedList<Sommet> listeOrdo;
        listeOrdo = Tris.trier(tri, graphe);
        int indexCouleur = 1;
        int k;

        //Initialisation
        for (Sommet aListeOrdo : listeOrdo) {
            aListeOrdo.setCoul(-1);
            aListeOrdo.setCouleur("white");
        }

        while(!listeOrdo.isEmpty()){
            Couleur couleur = Couleur.values()[indexCouleur];
            Sommet x = listeOrdo.get(0);
            x.setCouleur(couleur.getC());
            x.setCoul(indexCouleur);
            listeOrdo.remove(0);
            k = 0;

            LinkedList<Sommet> listeOrdoCopie = new LinkedList<>(listeOrdo) ;
            while(k < listeOrdoCopie.size()){
                Sommet y = listeOrdoCopie.get(k);
                boolean memeCouleur = false;

                //Si y est adjacent à un sommet de couleur : couleur
                for (LinkedList<Arc> arcs: graphe.getL().values()) {
                    for (Arc arc: arcs){
                        if(arc.getOrigine().equals(y) && arc.getDestination().getCouleur().equals(couleur.getC()))
                            memeCouleur = true;
                    }
                }
                if(!memeCouleur){
                    y.setCouleur(couleur.getC());
                    y.setCoul(indexCouleur);
                    listeOrdo.remove(y);
                }
                k++;
            }
            indexCouleur++;
        }
        graphe.setNbrChromatique(indexCouleur-1); //-1 car on a incrementé une fois de trop a la fin de la boucle xhile
        //System.out.println("Coloration valide WP : " + graphe.colorationValide());
        //System.out.println(this.toString()); //pour afficher les couleurs assignées
    }

    @Override
    public String toString() {
        return "WelshPowell{" +
                "graphe=" + graphe.toStringConsole() +
                '}';
    }
}
