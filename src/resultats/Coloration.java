package resultats;

import javax.swing.*;
import java.awt.*;

public class Coloration {

    public static void main(String[] args){
        JFrame fenetre = new JFrame();
        fenetre.setTitle("Coloration");
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.setVisible(true);

        Icon graphe = new ImageIcon("graph/queen5_5_wp_decroiss.png");
        JLabel grapheLabel = new JLabel();
        grapheLabel.setIcon(graphe);

        fenetre.setSize(900, 900);
        fenetre.setLocationRelativeTo(null);

        //fenetre.add(grapheLabel);

        JScrollPane scroller = new JScrollPane(grapheLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVisible(true);
        fenetre.add(scroller);


        fenetre.setVisible(true);
    }
}
