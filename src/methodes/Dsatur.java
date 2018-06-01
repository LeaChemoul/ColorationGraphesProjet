package methodes;

import structure.Arc;
import structure.GrapheListe;
import structure.Sommet;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

public class Dsatur {
    private GrapheListe graphe;
    private LinkedHashMap<Sommet, Integer>  DSAT;

    public Dsatur(GrapheListe graphe) {
        this.graphe = graphe;
    }

    public GrapheListe getGraphe() {
        return graphe;
    }

    public String plusPetiteCouleur(ArrayList<String> couleursVoisins,Couleur[] couleurs ) throws NullPointerException{
        for (Couleur couleur : couleurs) {
            if (!couleursVoisins.contains(couleur.getC()))
                return couleur.getC();
        }
        throw new NullPointerException();
    }

    public Sommet maxDsat(LinkedHashMap<Sommet, Integer> DSAT, LinkedList<Sommet> sommets){
        int maxValue= 0;

        Sommet max = sommets.get(0); //valeur du premier ?

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
        ArrayList<String> couleursUtil = new ArrayList<>();
        //Tri décroissant
        LinkedList<Sommet> listeOrdo = Tris.trier(1, graphe);

        //Initialisation du DSAT
        DSAT = new LinkedHashMap<Sommet, Integer>();
        for(int i =0;i<listeOrdo.size();i++){
            DSAT.put(listeOrdo.get(i),0);
        }

        for (Sommet aListeOrdo : listeOrdo) {
            aListeOrdo.setCoul(-1);
            aListeOrdo.setCouleur("white");
        }

        Couleur[] couleurs = Couleur.values();

        do {
            Sommet y = maxDsat(DSAT, listeOrdo);
            //Mise a jour du dsat
            for (LinkedList<Arc> arcs: graphe.getL().values()) {
                for (Arc arc: arcs){
                    if(arc.getOrigine().equals(y)){
                        if(DSAT.containsKey(arc.getDestination())){
                            if(!arc.getDestination().getCouleursVoisins().contains(y.getCouleur())){
                                arc.getDestination().getCouleursVoisins().add(y.getCouleur());
                                DSAT.replace(arc.getDestination(),arc.getDestination().getCouleursVoisins().size());
                            }
                        }

                        if(!y.getCouleursVoisins().contains(arc.getDestination().getCouleur()))
                            y.getCouleursVoisins().add(arc.getDestination().getCouleur());
                    }
                    else if(arc.getDestination().equals(y)){
                        if(DSAT.containsKey(arc.getOrigine())){
                            if(!arc.getOrigine().getCouleursVoisins().contains(y.getCouleur())){
                                arc.getOrigine().getCouleursVoisins().add(y.getCouleur());
                                DSAT.put(arc.getOrigine(),arc.getOrigine().getCouleursVoisins().size());
                            }
                        }
                        if(!y.getCouleursVoisins().contains(arc.getOrigine().getCouleur()))
                            y.getCouleursVoisins().add(arc.getOrigine().getCouleur());
                    }
                }
            }
            String petiteCouleur = plusPetiteCouleur(y.getCouleursVoisins(),couleurs);
            y.setCouleur(petiteCouleur);

            if(!couleursUtil.contains(petiteCouleur))
                couleursUtil.add(petiteCouleur);

            DSAT.remove(y);
        }while(!DSAT.isEmpty());

        graphe.setNbrChromatique(couleursUtil.size());
        System.out.println("Coloration valide dsatur : " + graphe.colorationValide());

    }


    public ArrayList<String> couleursDiff(Sommet s){
        ArrayList<String> couleurs = new ArrayList<>();

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

}
