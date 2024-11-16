/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceSWING;
import javax.swing.Timer;
import javax.swing.JLabel;
import java.io.Serializable;
/**
 *
 * @author Josep
 */
public class Compteur implements Runnable, Serializable {
    //on crée nos attributs, ajout de minute, de seconde, et d'un score
    
    private int delais;
    protected static int tempsMax = 900;
    protected static int seconde =0;
    protected static int minute = 15;
    private JLabel labelM;  
    private JLabel labelS;
    protected Timer t1;
    /**
     *initialisation du score à 8000, décrémentation du score de 10 toute les seconde
     */
    protected static int score = 8000;

    /**
     *
     * @param label
     * @param label1
     */
    public Compteur(JLabel label, JLabel label1) {  
        this.labelM = label;
        this.labelS = label1;
        delais = 1000;
    }

    /**
     * run
     */
    @Override
    public void run() {
        tempsMax ++;
        t1 = new Timer(delais, event -> {
            tempsMax--;
            seconde --;
            score = score -1;
            
            //Lorsque les secondes sont à 0 ont enlève 1 au minute et on remet 60 au seconde
            if(seconde <= 0 && tempsMax >= 0){
                seconde = 59;
                minute --;
            }
            if (labelM != null) { 
                labelM.setText(Integer.toString(minute) + " :");
                labelS.setText(Integer.toString(seconde));
                
            //Lorsque le temps est écoulé ont stop le timer
            if(tempsMax == 0){
                t1.stop();
            }
            } else {
                System.out.println("Label is null");
            }
        });
        t1.start();
    }
    /**
     *méthode permettan de stoper le timer
     */
    public void stop(){
        t1.stop();
    }
    
}

