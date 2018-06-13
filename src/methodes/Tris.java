package methodes;

import structure.Arc;
import structure.GrapheListe;
import structure.Sommet;

import java.util.*;

public class Tris {

    /**
     * Tri croissant des sommets par rapport à leur degré (taille de la liste de leurs liens)
     * @param map Sommets et leurs arcs.
     * @return Une liste triée.
     */
    private static LinkedHashMap<Sommet, LinkedList<Arc>> triCroissant(LinkedHashMap<Sommet, LinkedList<Arc>> map) {
        LinkedHashMap<Sommet,LinkedList<Arc>> res = new LinkedHashMap<>(map);
        // Ajout des entrées de la map à une liste
        final List<Map.Entry<Sommet, LinkedList<Arc>>> entries = new LinkedList<>(res.entrySet());

        // Tri de la liste sur la valeur de l'entrée
        entries.sort(new Comparator<Map.Entry<Sommet, LinkedList<Arc>>>() {
            @Override
            public int compare(final Map.Entry<Sommet, LinkedList<Arc>> e1, final Map.Entry<Sommet, LinkedList<Arc>> e2) {
                return Integer.compare(e1.getValue().size(), e2.getValue().size());
            }
        });

        fillMap(res, entries);
        return res;
    }

    /**
     * Tri décroissant des sommets par rapport à leur degré (taille de la liste de leurs liens)
     * @param map Sommets et leurs arcs.
     * @return Une liste triée.
     */
    private static LinkedHashMap<Sommet, LinkedList<Arc>> triDecroissant(LinkedHashMap<Sommet, LinkedList<Arc>> map) {
        LinkedHashMap<Sommet,LinkedList<Arc>> res = new LinkedHashMap<>(map);
        // Ajout des entrées de la map à une liste
        final List<Map.Entry<Sommet, LinkedList<Arc>>> entries = new LinkedList<>(res.entrySet());

        // Tri de la liste sur la valeur de l'entrée
        entries.sort(new Comparator<Map.Entry<Sommet, LinkedList<Arc>>>() {
            @Override
            public int compare(final Map.Entry<Sommet, LinkedList<Arc>> e1, final Map.Entry<Sommet, LinkedList<Arc>> e2) {

                return -Integer.compare(e1.getValue().size(), e2.getValue().size());
            }
        });

        fillMap(res, entries);
        return res;
    }

    /**
     * Tri aléatoire des sommets par rapport à leur degré (taille de la liste de leurs liens)
     * @param map Sommets et leurs arcs.
     * @return Une liste triée.
     */
    private static LinkedHashMap<Sommet, LinkedList<Arc>> triAleatoire(LinkedHashMap<Sommet, LinkedList<Arc>> map) {
        LinkedHashMap<Sommet,LinkedList<Arc>> temp = new LinkedHashMap<>();
        LinkedList<Sommet> amelanger= new LinkedList<>();
        for (HashMap.Entry<Sommet,  LinkedList<Arc>> entry : map.entrySet())
        {
            amelanger.add(entry.getKey());
        }
        LinkedList<Sommet> melangee = melanger(amelanger);
        for (Sommet aMelangee : melangee) {
            temp.put(aMelangee, map.get(aMelangee));
        }
        return temp;
    }

    private static void fillMap(LinkedHashMap<Sommet, LinkedList<Arc>> map, List<Map.Entry<Sommet, LinkedList<Arc>>> entries) {
        map.clear();
        for (final Map.Entry<Sommet, LinkedList<Arc>> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    private static LinkedList<Sommet> listeOrdonee(LinkedHashMap<Sommet, LinkedList<Arc>> map){
        LinkedList<Sommet> liste = new LinkedList<>();
        for (Map.Entry<Sommet,LinkedList<Arc>> e : map.entrySet()){
            liste.add(e.getKey());
        }
        return liste;
    }

    /**
     * Tri d'un graphe donné selon le tri choisie.
     * @param tri 1 pour décroissant, 2 pour croissant, 3 pour aléatoire
     * @param graphe Graphe à trier.
     * @return Liste des sommets triés
     */
    static LinkedList<Sommet> trier(int tri, GrapheListe graphe) {
        LinkedHashMap<Sommet, LinkedList<Arc>> res;
        LinkedList<Sommet> listeOrdo;
        switch (tri){
            case 1:
                res = Tris.triDecroissant(graphe.getL());
                listeOrdo = listeOrdonee(res);
                break;
            case 2 :
                res = Tris.triCroissant(graphe.getL());
                listeOrdo = listeOrdonee(res);
                break;
            case 3 :
               res =  Tris.triAleatoire((graphe.getL()));
                listeOrdo = listeOrdonee(res);
                break;
            case 4 :
                res =  Tris.triAleatoire((graphe.getL()));
                res = Tris.triDecroissant(res);
                listeOrdo = listeOrdonee(res);
                break;
                default:
                    res =  Tris.triAleatoire((graphe.getL()));
                    listeOrdo = listeOrdonee(res);
                    break;
        }
        return listeOrdo;
    }

    private static LinkedList<Sommet> melanger(LinkedList<Sommet> listeDepart){
        LinkedList<Sommet> nouvelle = new LinkedList<>(listeDepart);
        Collections.shuffle(nouvelle);
        return nouvelle;
    }
}
