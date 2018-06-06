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
                        }
                    }
                    else if(arc.getDestination().equals(y)){
                        if(DSAT.containsKey(arc.getOrigine())){
                            DSAT.replace(arc.getOrigine(),couleursDiff(arc.getOrigine()).size());
                        }
                    }
                }
            }
            ArrayList<String> couleursVois = couleursDiff(y);
            String petiteCouleur = plusPetiteCouleur(couleursVois,couleurs);
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

    @Override
    public String toString() {
        return "Dsatur{" +
                "graphe=" + graphe.toStringConsole() +
                '}';
    }

}
