package structure;


import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.*;

public class GrapheListe extends Graphe {
    private String nom;
    private int nbSommets = 0;
    private int nbValSommets = 0;
    private int nbArcs = 0;
    private int nbValArc = 0;
    private boolean oriente;
    private LinkedHashMap<Sommet,LinkedList<Arc>> L;
    private LinkedList<Sommet> sommets;
    private int nbrChromatique;

    @Override
    public int taille() {
        return L.size();
    }

    public GrapheListe() {
        L = new LinkedHashMap<Sommet,LinkedList<Arc>>();
        sommets = new LinkedList<Sommet>();

    }

    @Override
    public void ajouterSommet(Sommet s) {
        L.put(s, new LinkedList<Arc>());
        sommets.add(s);
    }

    @Override
    public boolean existeArcOriente(Sommet s, Sommet t) {
        if(L.get(s) != null)
            for (Arc a : L.get(s))
            if ((a.getDestination()).equals(t))
                return true;
        return false;

    }

    public boolean existeArcNonOriente(Sommet s, Sommet t) {

        if(L != null){
            if(L.get(s) != null)
                for (Arc a : L.get(s)){
                    if ((a.getDestination()).equals(t))
                        return true;
                }
            if(L.get(t) != null)
                for (Arc a : L.get(t)){
                    if ((a.getDestination()).equals(s))
                        return true;
                }
                return false;

        }else{
            return false;
        }

    }

    @Override
    public boolean existeSommet(Sommet s) {
        for (Sommet sommet : this.sommets) {
            if (sommet.getNom().equals(s.getNom()))
                return true;
        }
        return false;
    }

    @Override
    public void ajouterArc(Sommet s, Sommet t, int val) {
        if(!existeSommet(s))
            ajouterSommet(s);
        if(!existeSommet(t))
            ajouterSommet(t);
        L.get(s).addLast(new Arc(s, t, val));
    }

    @Override
    public void ajouterArrete(Sommet s, Sommet t, int val) {
        if(!existeSommet(s))
            ajouterSommet(s);
        if(!existeSommet(t))
            ajouterSommet(t);
        L.get(s).addLast(new Arc(s, t, val));
        L.get(t).addLast(new Arc(t, s, val));
    }

    @Override
    public int valeurArc(Sommet s, Sommet t) {
        for (Arc a : L.get(s))
            if (a.getDestination().equals(t))
                return a.getPoids();
        return -1; // convention
    }

    @Override
    public void enleverArc(Sommet s, Sommet t) {
        Arc a = null;
        for (Arc aa : L.get(s))
            if (aa.getDestination().equals(t)) {
                a = aa;
                break;
            }
        if (a != null)
            L.get(s).remove(a);

    }

    public void modifierValeur(Sommet s, Sommet t, int val) {
        for (Arc a : L.get(s))
            if (a.getDestination().equals(t)) {
                a.setPoids(val);
                return;
            }
    }

    public LinkedList<Arc> voisins(Sommet s) {
        return L.get(s);
    }

    private Sommet findSommet(String nom){
        for (Sommet sommet : this.sommets) {
            if (sommet.getNom().equals(nom))
                return sommet;
        }
        return null;
    }

    public GrapheListe copie() {
        int n = taille();
        GrapheListe G = new GrapheListe();

        G.setL(new LinkedHashMap<Sommet,LinkedList<Arc>>(this.getL()));
        //etc...
        return G;
    }

    public boolean colorationValide(){
        for (Map.Entry<Sommet,LinkedList<Arc>> e : this.getL().entrySet()){
            String coul = e.getKey().getCouleur();
            for(Arc arc : e.getValue())
                if(arc.getDestination().getCouleur().equals(coul))
                    return false;

        }
        return true;
    }


    private boolean containsNonOriente(Arc arc, ArrayList<Arc> arcs){
        for(Arc unArc : arcs){
            if(compareNonOriente(arc,unArc))
                return true;
        }
        return false;
    }

    private boolean compareNonOriente(Arc arc1, Arc arc2){
        if(arc1.getOrigine() == arc2.getOrigine() && arc1.getDestination() == arc2.getDestination())
            return true;
        else return arc1.getOrigine() == arc2.getDestination() && arc1.getDestination() == arc2.getOrigine();
    }

    //----------------------------------------------
    //GESTION DES FICHIERS
    //----------------------------------------------

    public GrapheListe deFichier(String path) throws IOException {
        String contenu = lireContenu(path);
        GrapheListe grapheListe =  new GrapheListe();
        if (contenu.isEmpty())
            return null;

        String[] lines = contenu.split("\n");
        boolean sommet = false;
        boolean arc = false;
        boolean arete = false;

        for (String line : lines) {

            if (line.toLowerCase().startsWith("nom:")) {
                line = line.replaceAll(" ", "");
                line = line.replaceAll("Nom:", "");
                line = line.replaceAll("\t", "");

                grapheListe.setNom(line);
            } else if (line.startsWith("nbsommets:")) {

            } else if (line.toLowerCase().startsWith("nvvalsommets:")) {

            } else if (line.toLowerCase().startsWith("nbarcs:")) {

            } else if (line.toLowerCase().startsWith("nbvalarcs:")) {

            } else if(line.toLowerCase().startsWith("oriente(non/oui):")){
                line = line.replaceAll(" ", "");
                line = line.replaceAll("oriente(non/oui):", "");
                this.oriente = !line.equals("non");
            }
            else if (!sommet && line.toLowerCase().startsWith("--- liste des sommets")) {
                sommet = true;
                arete = false;
                arc = false;
            } else if (!arete && line.toLowerCase().startsWith("--- liste des aretes")) {
                arete = true;
                sommet = false;
                arc = false;
            } else if (!arc && line.toLowerCase().startsWith("--- liste des arcs")) {
                arc = true;
                sommet = false;
                arete = false;
            } else if (sommet) {
                String[] unSommet = line.toLowerCase().split(" ");
                grapheListe.ajouterSommet(new Sommet(unSommet[0],0 , "white", 0));
            } else if (arete) {
                String[] uneArete = line.toLowerCase().split(" ");
                Sommet s = grapheListe.findSommet(uneArete[0]);
                Sommet t = grapheListe.findSommet(uneArete[1]);
                if(s == null)
                    grapheListe.ajouterSommet(new Sommet(uneArete[0],0 , "white", 0));
                if(t == null)
                    grapheListe.ajouterSommet(new Sommet(uneArete[1],0 , "white", 0));
                if(!grapheListe.existeArcNonOriente(s, t)){
                    grapheListe.ajouterArrete(s,t, 0);
                }
            } else if(arc){
                String[] uneArete = line.toLowerCase().split(" ");
                Sommet s = grapheListe.findSommet(uneArete[0]);
                Sommet t = grapheListe.findSommet(uneArete[1]);
                if(s == null)
                    grapheListe.ajouterSommet(new Sommet(uneArete[0],0 , "white", 0));
                if(t == null)
                    grapheListe.ajouterSommet(new Sommet(uneArete[1],0 , "white", 0));
                if(!grapheListe.existeArcOriente(s, t)){
                    grapheListe.ajouterArc(s,t, 0);
                }
            }
        }
        return grapheListe;
    }

    private static @NotNull
    String lireContenu(String path) throws IOException{
        StringBuilder content = new StringBuilder();

        try (FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null)
                content.append(line)
                        .append("\n");
        }

        return content.toString();
    }

    @SuppressWarnings("Duplicates")
    public boolean generateFile(String path) {
        FileWriter fw = null;
        BufferedWriter bw = null;

        boolean problem = false;

        try {
            fw = new FileWriter(path);
            bw = new BufferedWriter(fw);

            bw.write(this.toStringGraphViz());
        } catch (IOException ex) {
            ex.printStackTrace();
            problem = true;
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                problem = true;
            }

            try {
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                problem = true;
            }
        }

        return !problem;
    }

    //----------------------------------------------
    //GETTERS SETTERS
    //----------------------------------------------

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbSommets(int nbSommets) {
        this.nbSommets = nbSommets;
    }

    public void setSommets(LinkedList<Sommet> sommets) {
        this.sommets = sommets;
    }

    public void setNbrChromatique(int nbrChromatique) {
        this.nbrChromatique = nbrChromatique;
    }

    public void setL(LinkedHashMap<Sommet, LinkedList<Arc>> l) {
        L = l;
    }

    public int getNbrChromatique() {
        return nbrChromatique;
    }

    public String getNom() {
        return nom;
    }

    public boolean isOriente() {
        return oriente;
    }

    public LinkedList<Sommet> getSommets() {
        return sommets;
    }

    public LinkedHashMap<Sommet, LinkedList<Arc>> getL() {
        return L;
    }

    //----------------------------------------------
    //TO STRING
    //----------------------------------------------

    @Override
    public String toString() {
        return "GrapheListe{" +
                "nom='" + nom + '\'' +
                ", nbSommets=" + nbSommets +
                ", nbValSommets=" + nbValSommets +
                ", nbArcs=" + nbArcs +
                ", nbValArc=" + nbValArc +
                ", L=" + L +
                '}';
    }

    public String toStringConsole(){
        String lien = (this.oriente)? " -> ":" -- ";
        String debut = (this.oriente)? "digraph G {\n":"graph G {\n";
        StringBuilder chaine = new StringBuilder(debut);
        for (Sommet sommet : this.sommets) {
            chaine.append(sommet.getNom()).append(" [color= ")
                    .append(sommet.getCoul())
                    .append("];\n");
        }
        chaine.append("}");
        return chaine.toString();
    }

    private String toStringGraphViz(){
        String lien = (this.oriente)? " -> ":" -- ";
        String debut = (this.oriente)? "digraph G {\n":"graph G {\n";
        StringBuilder chaine = new StringBuilder(debut);
        ArrayList<Arc> parcourus = new ArrayList<>();
        for (Sommet sommet : this.sommets) {
            chaine.append(sommet.getNom()).append(" [shape=circle, style=filled, color= ")
                    .append(sommet.getCouleur())
                    .append("];\n");
            for (Arc arc :
                    L.get(sommet)) {
                if(!containsNonOriente(arc,parcourus)){
                    chaine.append(arc.getOrigine().getNom()).append(lien)
                            .append(arc.getDestination().getNom())
                            .append(";\n");
                    parcourus.add(arc);
                }
            }
        }
        chaine.append("}");
        return chaine.toString();
    }
}
