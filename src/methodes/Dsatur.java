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

    private String plusPetiteCouleur(LinkedList<String> couleursVoisins, Couleur[] couleurs) throws NullPointerException{
        for (Couleur couleur : couleurs) {
            if (!couleursVoisins.contains(couleur.getC()))
                return couleur.getC();
        }
        throw new NullPointerException();
    }

    private Sommet maxDsat(LinkedHashMap<Sommet, Integer> DSAT, LinkedList<Sommet> sommets){
        int maxValue= 0;

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

    public void algorithme(){
        LinkedList<String> couleursUtil = new LinkedList<>();
        //Tri décroissant
        LinkedList<Sommet> listeOrdo = Tris.trier(4, graphe); //tri décroissant

        //Initialisation du DSAT
        LinkedHashMap<Sommet, Integer> DSAT = new LinkedHashMap<>();
        for (Sommet anElement : listeOrdo) {
            DSAT.put(anElement, 0);
        }

        for (Sommet aListeOrdo : listeOrdo) {
            aListeOrdo.setCoul(-1);
            aListeOrdo.setCouleur("white");
        }

        //Couleurs disponibles
        Couleur[] couleurs = Couleur.values();

        do {
            Sommet y = maxDsat(DSAT, listeOrdo);
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

            //Mise à jour de la couleur
            String petiteCouleur = plusPetiteCouleur(couleursDiff(y),couleurs);
            y.setCouleur(petiteCouleur);
            if(!couleursUtil.contains(petiteCouleur))
                couleursUtil.add(petiteCouleur);
            y.setCoul(couleursUtil.indexOf(petiteCouleur));

            DSAT.remove(y);
            listeOrdo.remove(y);
        }while(!DSAT.isEmpty());

        graphe.setNbrChromatique(couleursUtil.size());
        System.out.println("Coloration valide dsatur : " + graphe.colorationValide());
        //System.out.println(this.toString()); //pour afficher les couleurs assignées
    }


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
