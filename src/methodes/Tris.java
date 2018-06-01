package methodes;

import structure.Arc;
import structure.GrapheListe;
import structure.Sommet;

import java.util.*;

public class Tris {


    public static void triCroissant(LinkedHashMap<Sommet,LinkedList<Arc>> map) {
        // Ajout des entrées de la map à une liste
        final List<Map.Entry<Sommet, LinkedList<Arc>>> entries = new ArrayList<>(map.entrySet());

        // Tri de la liste sur la valeur de l'entrée
        entries.sort(new Comparator<Map.Entry<Sommet, LinkedList<Arc>>>() {
            @Override
            public int compare(final Map.Entry<Sommet, LinkedList<Arc>> e1, final Map.Entry<Sommet, LinkedList<Arc>> e2) {
                return -Integer.compare(e1.getValue().size(), e2.getValue().size());
            }
        });

        fillMap(map, entries);
    }

    public static void triDecroissant(LinkedHashMap<Sommet,LinkedList<Arc>> map) {
        // Ajout des entrées de la map à une liste
        final List<Map.Entry<Sommet, LinkedList<Arc>>> entries = new ArrayList<>(map.entrySet());

        // Tri de la liste sur la valeur de l'entrée
        entries.sort(new Comparator<Map.Entry<Sommet, LinkedList<Arc>>>() {
            @Override
            public int compare(final Map.Entry<Sommet, LinkedList<Arc>> e1, final Map.Entry<Sommet, LinkedList<Arc>> e2) {

                return Integer.compare(e1.getValue().size(), e2.getValue().size());
            }
        });

        fillMap(map, entries);
    }

    private static void fillMap(LinkedHashMap<Sommet, LinkedList<Arc>> map, List<Map.Entry<Sommet, LinkedList<Arc>>> entries) {
        map.clear();
        for (final Map.Entry<Sommet, LinkedList<Arc>> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    public static LinkedList<Sommet> listeOrdonee(GrapheListe graphe){
        LinkedList<Sommet> liste = new LinkedList<>();
        for (Map.Entry<Sommet,LinkedList<Arc>> e : graphe.getL().entrySet()){
            liste.add(e.getKey());
        }
        return liste;
    }

    public static LinkedList<Sommet> trier(int tri, GrapheListe graphe) {
        LinkedList<Sommet> listeOrdo;
        switch (tri){
            case 1:
                Tris.triDecroissant(graphe.getL());
                listeOrdo = listeOrdonee(graphe);
                break;
            case 2 :
                Tris.triCroissant(graphe.getL());
                listeOrdo = listeOrdonee(graphe);
                break;
            default :
                listeOrdo = listeOrdonee(graphe);
                Random r = new Random();
                listeOrdo.sort(new Comparator<Sommet>() {
                    @Override
                    public int compare(Sommet o1, Sommet o2) {
                        return r.nextInt(3)-1;
                    }
                });
                break;
        }
        return listeOrdo;
    }

}
