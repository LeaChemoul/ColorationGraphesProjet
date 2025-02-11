package methodes;

import structure.Arc;
import structure.GrapheListe;
import structure.Sommet;

import java.util.*;

public class Dsatur {
    private GrapheListe graphe;

    public Dsatur(GrapheListe graphe) {
        this.graphe = graphe;
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

    /**
     * Recherche le sommet de DSAT maximum.
     * @param DSAT Liste des sommets et de leur DSAT.
     * @param sommets Sommets du graphe
     * @return Le sommet choisie.
     */
    private Sommet maxDsat(LinkedHashMap<Sommet, Integer> DSAT, LinkedList<Sommet> sommets){
        int maxValue= -1;

        Sommet max = sommets.get(0);

        for (Map.Entry<Sommet,Integer> e : DSAT.entrySet()){
            if(e.getValue()> maxValue){
                maxValue = e.getValue();
                max = e.getKey();
            }else if(e.getValue() == maxValue){
                if(graphe.getL().get(e.getKey()).size() > graphe.getL().get(max).size()){
                    maxValue = e.getValue();
                    max = e.getKey();
                }
            }
        }
        return max;
    }

    /**
     * Algorithme de DSATUR
     */
    public void algorithme(){
        LinkedList<String> couleursUtil = new LinkedList<>();
        //Tri décroissant
        LinkedList<Sommet> listeOrdo = Tris.trier(1, graphe); //tri aléatoire temporaire

        //Initialisation du DSAT
        LinkedHashMap<Sommet, Integer> DSAT = new LinkedHashMap<>();
        for (Sommet anElement : listeOrdo) {
            DSAT.put(anElement, 0);
        }

        //Initialisation des couleurs
        for (Sommet sommet : listeOrdo) {
            sommet.setCoul(-1); //numerotation
            sommet.setCouleur("white"); //couleur pour la géneration graphique
        }

        //Couleurs disponibles en graphique
        Couleur[] couleurs = Couleur.values();

        do {
            Sommet y = maxDsat(DSAT, listeOrdo);
            //Mise à jour de la couleur
            String petiteCouleur = plusPetiteCouleur(couleursDiff(y),couleurs);
            y.setCouleur(petiteCouleur);
            if(!couleursUtil.contains(petiteCouleur))
                couleursUtil.add(petiteCouleur);
            y.setCoul(couleursUtil.indexOf(petiteCouleur));

            //Mise a jour du dsat
            for (LinkedList<Arc> arcs: graphe.getL().values()) {
                for (Arc arc: arcs){
                    if(arc.getOrigine().equals(y)){
                        if(DSAT.containsKey(arc.getDestination())){
                            DSAT.replace(arc.getDestination(),couleursDiff(arc.getDestination()).size());
                        } //non oriente donc on peut regarder que l'un des 2 arcs
                    }
                }
            }



            DSAT.remove(y);
            listeOrdo.remove(y);
        }while(!DSAT.isEmpty());

        graphe.setNbrChromatique(couleursUtil.size());
        //System.out.println("Coloration valide dsatur : " + graphe.colorationValide());
        //System.out.println(this.toString()); //pour afficher les couleurs assignées
    }


    /**
     * Determine les couleurs des voisins à un sommet donné.
     * @param s Sommet étudié.
     * @return Liste des couleurs des voisins.
     */
    private LinkedList<String> couleursDiff(Sommet s){
        LinkedList<String> couleurs = new LinkedList<>();

        for (Map.Entry<Sommet,LinkedList<Arc>> e : graphe.getL().entrySet()){
            if(e.getKey().equals(s))
                for (int i = 0; i < e.getValue().size() ; i++) {
                    String coul = e.getValue().get(i).getDestination().getCouleur();
                    if(!couleurs.contains(coul))
                        couleurs.add(coul);
                }
        }
        return couleurs;
    }

    @Override
    public String toString() {
        return "Dsatur{" +
                "graphe=" + graphe.toStringConsole() +
                '}';
    }

}
