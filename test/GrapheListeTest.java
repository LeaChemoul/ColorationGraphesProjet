import methodes.Dsatur;
import methodes.Greedy;
import methodes.Tris;
import methodes.WelshPowell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structure.GrapheListe;
import structure.Sommet;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GrapheListeTest {

    private GrapheListe grapheListe;
    private WelshPowell welshPowell;
    private Greedy greedy;
    private Dsatur dsatur;

    @BeforeEach
    void setup(){
        grapheListe = new GrapheListe();
        grapheListe.setNom("Test");
        grapheListe.setNbSommets(3);

        Sommet a = new Sommet("a",0,"white",0);
        Sommet b = new Sommet("b",0,"white",0);
        Sommet c = new Sommet("c",0,"white",0);
        Sommet d = new Sommet("d",0,"white",0);

        grapheListe.ajouterSommet(a);
        grapheListe.ajouterSommet(b);
        grapheListe.ajouterSommet(c);
        grapheListe.ajouterSommet(d);

        /*grapheListe.ajouterArc(a,b,0);
        grapheListe.ajouterArc(a,c,0);
        grapheListe.ajouterArc(a,d,0);
        grapheListe.ajouterArc(b,a,0);
        grapheListe.ajouterArc(b,d,0);
        grapheListe.ajouterArc(c,b,0);*/

        grapheListe.ajouterArc(c,d,0);
        grapheListe.ajouterArc(d,c,0);
        grapheListe.ajouterArc(a,c,0);
        grapheListe.ajouterArc(c,a,0);


        /*greedy = new Greedy(grapheListe);
        greedy.algorithme(3);
        grapheListe.generateFile("graph/test.dot");
        System.out.println("\n");*/

        /*try
        {
            Runtime rtime = Runtime.getRuntime();
            Process child = rtime.exec("dot -Tpng graph/test.dot -o graph/test.png");

        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //CONTRE EXEMPLE WP
        /*try {
            grapheListe = grapheListe.deFichier("files/contre_exempleWP.txt");
            welshPowell = new WelshPowell(grapheListe);
            welshPowell.algorithme(3);
            System.out.println("\n");

            //greedy = new Greedy(grapheListe);
            //greedy.algorithme(3);
            grapheListe.generateFile("graph/contre_exemple.dot");

            //Runtime rtime = Runtime.getRuntime();
            //Process child = rtime.exec("dot -Tpng graph/contre_exemple.dot -o graph/contre_exemple.png");
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

    @Test
    void testDeFichier() {
        String name = "queen5_5";
        String path = "files/"+ name + ".txt";
        String returnPath;

        try{
            grapheListe = new GrapheListe();
            grapheListe = grapheListe.deFichier(path);

            //WELSHPOWELL
            welshPowell = new WelshPowell(grapheListe);
            //Temps d'execution
            //----------
            long debut = System.nanoTime();
            welshPowell.algorithme(1);
            System.out.println("Temps d'execution welshpowell décroissant : " + (System.nanoTime() - debut)/1000 + " microsec");
            System.out.println("Nbr chromatique :" + grapheListe.getNbrChromatique());
            returnPath = "graph/graph_wp_decroiss.dot";
            grapheListe.generateFile(returnPath);
            System.out.println("\n");

            debut = System.nanoTime();
            welshPowell.algorithme(2);
            System.out.println("Temps d'execution welshpowell croissant : " + (System.nanoTime() - debut)/1000 + " microsec");
            System.out.println("Nbr chromatique :" + grapheListe.getNbrChromatique());
            returnPath = "graph/graph_wp_croiss.dot";
            grapheListe.generateFile(returnPath);
            System.out.println("\n");


            debut = System.nanoTime();
            welshPowell.algorithme(3);
            System.out.println("Temps d'execution welshpowell aleatoire : " + (System.nanoTime() - debut)/1000 + " microsec");
            System.out.println("Nbr chromatique :" + grapheListe.getNbrChromatique());
            returnPath = "graph/graph_wp_aleat.dot";
            grapheListe.generateFile(returnPath);
            System.out.println("\n");

            //----------


            //GREEDY
            greedy = new Greedy(grapheListe);
            //Temps d'execution
            //----------
            debut = System.nanoTime();
            greedy.algorithme(1);
            System.out.println("Temps d'execution greedy décroissant : " + (System.nanoTime() - debut)/1000 + " microsec");
            System.out.println("Nbr chromatique :" + grapheListe.getNbrChromatique());
            returnPath = "graph/graph_gr_decroiss.dot";
            grapheListe.generateFile(returnPath);
            System.out.println("\n");


            greedy = new Greedy(grapheListe);
            debut = System.nanoTime();
            greedy.algorithme(2);
            System.out.println("Temps d'execution greedy croissant : " + (System.nanoTime() - debut)/1000 + " microsec");
            System.out.println("Nbr chromatique :" + grapheListe.getNbrChromatique());
            returnPath = "graph/graph_gr_croiss.dot";
            grapheListe.generateFile(returnPath);
            System.out.println("\n");


            debut = System.nanoTime();
            grapheListe = grapheListe.deFichier(path);
            greedy = new Greedy(grapheListe);
            greedy.algorithme(3);
            System.out.println("Temps d'execution greedy aléatoire : " + (System.nanoTime() - debut)/1000 + " microsec");
            System.out.println("Nbr chromatique :" + grapheListe.getNbrChromatique());
            returnPath = "graph/graph_gr_aleat.dot";
            grapheListe.generateFile(returnPath);
            System.out.println("\n");
            //----------

            //DSATUR
            dsatur = new Dsatur(grapheListe);
            //Temps d'execution
            //----------
            debut = System.nanoTime();
            dsatur.algorithme();
            System.out.println("Temps d'execution dsatur : " + (System.nanoTime() - debut)/1000 + " microsec");
            System.out.println("Nbr chromatique :" + grapheListe.getNbrChromatique());
            returnPath = "graph/graph_dsatur.dot";
            grapheListe.generateFile(returnPath);
            System.out.println("\n");
            //----------

            //Génération des images : ne pas decommenter
            /*try
            {
                Runtime rtime = Runtime.getRuntime();
                Process child = rtime.exec("dot -Tpng graph/graph_gr_croiss.dot -o graph/" + name + "_gr_croiss.png");
                Process child2 = rtime.exec("dot -Tpng graph/graph_gr_decroiss.dot -o graph/" + name + "_gr_decroiss.png");
                Process child3 = rtime.exec("dot -Tpng graph/graph_gr_aleat.dot -o graph/" + name + "_gr_aleat.png");
                Process child4 = rtime.exec("dot -Tpng graph/graph_wp_croiss.dot -o graph/" + name + "_wp_croiss.png");
                Process child5 = rtime.exec("dot -Tpng graph/graph_wp_decroiss.dot -o graph/" + name + "_wp_decroiss.png");
                Process child6 = rtime.exec("dot -Tpng graph/graph_wp_aleat.dot -o graph/" + name + "_wp_aleat.png");
                Process child7 = rtime.exec("dot -Tpng graph/graph_dsatur.dot -o graph/" + name + "_dsatur.png");

            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }catch(NullPointerException e){
            System.out.println("Votre fichier est vide");
        }catch (IOException e){
            System.out.println("Erreur d'ouverture de fichier");
            e.printStackTrace();
        }
    }
}