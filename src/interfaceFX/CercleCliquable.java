/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package interfaceFX;
import java.io.Serializable;

/**
 *
 * @author Josep
 */
public class CercleCliquable implements Serializable {
    
    private static final Integer RADIUS=18;
    
    private final int numero;
    private final int x_center_location;
    private final int y_center_location;
    
    /**
     *
     * @param numero
     * @param x_center_location
     * @param y_center_location
     */
    public CercleCliquable(int numero, int x_center_location, int y_center_location) {
        this.numero=numero;
        this.x_center_location=x_center_location;
        this.y_center_location=y_center_location;
    }
    
    //retourn le numero de la carte associé au cercle cliquable

    /**
     *
     * @return le numero de la carte associé au cercle
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     *méthode permettant de savoir si on clique dans l'endroit ou nous avons déclaré un cercle
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(double x,double y) {
        
       return Math.sqrt(Math.pow((x-x_center_location), 2)+
                        Math.pow((y-y_center_location), 2))
                        <= RADIUS;
                       
    }
    /**
     *
     * @return x
     */
    public int getX(){return x_center_location;}

    /**
     *
     * @return y
     */
    public int getY(){return y_center_location;}
    
}
