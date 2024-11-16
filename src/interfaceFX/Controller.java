package interfaceFX;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import Serialization.SerializableValue;
import Launcher.Launcher;

/**
 *
 * @author Josep
 */
public class Controller implements Serializable {

    private String FXML_DIR = "interfaceFX";
    private String FXML_FILE = "UnlockScene.fxml";
    protected ArrayList<CercleCliquable> ListeCercle0;
    protected ArrayList<CercleCliquable> ListeCercle1;

    private Thread pistolet;
    private Thread chat;
    
    private Timeline timeline;
    private Timeline bandit;

    protected static Card carte;
    @FXML
    private Pane panneau;

    protected int numeroC;

    public static ArrayList<Integer> cartesNum;

    protected ArrayList<Card> ListeCarte;

    private StringBuffer carteN = new StringBuffer("carteN");

    private SerializableValue valueCarteSupr;
    private SerializableValue ValueReset;
    
    protected static boolean verif48 = false;
    private boolean verif32 = false;
    private boolean verif40 = false;
    private boolean verif41 = false;

    /**
     *Création du controller, on crée et initialise 4 ArrayList
     */
    public Controller() {
        cartesNum = new ArrayList<>(20);
        ListeCercle0 = new ArrayList<>(20);
        ListeCercle1 = new ArrayList<>(20);
        ListeCarte = new ArrayList<>(20);
    }

    /**
     *Initialisation du controller
     */
    public void initialize() {
        // Initialisation des cercles cliquables de la première carte
        ListeCercle0.add(new CercleCliquable(15, 212, 136));
        ListeCercle0.add(new CercleCliquable(12, 70, 201));
        ListeCercle0.add(new CercleCliquable(13, 259, 219));

        // Création de la première carte
        carte = new Card(1, ListeCercle0);

        // Ajout du numéro de la carte à la liste des cartes
        cartesNum.add(carte.getNumero());
        ListeCarte.add(carte);
        panneau.getChildren().add(carte);

        // Initialisation de la timeline
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                //première méthode pour vérifier si des cartes sont cliqués
                carteClique();
                // deuxième méthode pour vérifier si un numéro de carte a été entré dans le JTextField de l'IHM et doit être ajouté
                load();
                //troisième méthode pour vérifier si un numéro de carte a été rentré et doit être supprimé
                carteSupr();
                
                if(cartesNum.contains(32) && verif32 == false){
                    verif32 = true;
                    Thread chat = new Thread(new Son("sons/Miaou.mp3"));
                    chat.start();
                }
            }
        ));
        //lancement de la première timeline
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        //création d'un compteur qui lorsque la carte 9 sera sur le terrain, commencera son décompte
        Timeline bandit = new Timeline(
                //au bout de 60 secondes, la carte 20 est créer et affiché
            new KeyFrame(Duration.seconds(80), event -> {
                if(!cartesNum.contains(20) && cartesNum.contains(9)){
                    Card carte20= new Card(20, null);
                    cartesNum.add(carte20.getNumero());
                    ListeCarte.add(carte20);
                    panneau.getChildren().add(carte20);
                }
            }
        ));
        bandit.setCycleCount(Animation.INDEFINITE);
        bandit.play();
    }
    
    //Récolte de la SerializableValue et vérification de si elle existe déja dans la liste de numéro
    private void load() {
        SerializableValue value = SerializableValue.loadValue(Launcher.getSerialFileFullPath());
        int intValue = value.getValue();

        if(!cartesNum.contains(intValue) && intValue != 0 ){
            numeroC = intValue;
            char a = (char)intValue;
            carteN.setCharAt(5, a);
            ArrayList<CercleCliquable> ListeCercle1 = new ArrayList<>(6);

            ListeCercle1.add(new CercleCliquable(28, 86, 264));
            ListeCercle1.add(new CercleCliquable(10, 271, 191));
            ListeCercle1.add(new CercleCliquable(30, 25, 102));
            ListeCercle1.add(new CercleCliquable(42, 215, 313));

            if(intValue == 9){
                Card carteN = new Card(value.getValue(), ListeCercle1);
                System.out.println(carteN.toString());
                carte = carteN;
               cartesNum.add(carteN.getNumero());
                ListeCarte.add(carteN);
                panneau.getChildren().add(carteN);
            }
            else{
                Card carteN = new Card(value.getValue(), null);
                System.out.println(carteN.toString());
                cartesNum.add(carteN.getNumero());
                ListeCarte.add(carteN);
                panneau.getChildren().add(carteN);
            }

            value = new SerializableValue(0);
            value.saveValue(Launcher.getSerialFileFullPath());
        }
    }
    /**
     *méthode pour vérifier si un cercle est cliqué
     */
    public void carteClique(){
        //petite vérification pour avoir accès ou non à la carte 10
        if(cartesNum.contains(48) && !verif48){
                   verif48 = true;
                   Thread pistolet = new Thread(new Son("sons/pistolet.WAV"));
                   pistolet.start();
        }
         // Détection des cartes à faire apparaitre lorsque l'on clique sur un numéro
                numeroC = Card.numeroCarte; //numeroCarte correspond au numéro du cercle cliqué, je stock la valeur dans une autre variable
                for(int i = 0; i< carte.getCercleCliquableSize(); i++){
                    //verification du clic dans le cercle ou non, et vérification si la carte est déja créer ou non.
                    if(carte.verif[i] == true && !cartesNum.contains(numeroC)){
                        carte.verif[i] = false; // on repasse la verif à faux, satatic car on veux aussi modifie l'attribut dans Card
                        //création d'un nom unique pour chaque carte créer
                        char a = (char)numeroC;
                        carteN.setCharAt(5, a);
                        //Ajout d'une liste de cercle à la carte numero 10
                        if(numeroC == 10 && verif48 == true){
                            ArrayList<CercleCliquable> ListeCercle10 = new ArrayList<>(2);
                            ListeCercle10.add(new CercleCliquable(32, 39, 169));
                            Card carteN = new Card(numeroC, ListeCercle10);
                            cartesNum.add(carteN.getNumero());
                            ListeCarte.add(carteN);
                            panneau.getChildren().add(carteN);
                            carte = carteN;
                        }
                        if(numeroC !=10){
                            Card carteN = new Card(numeroC, null);
                            cartesNum.add(carteN.getNumero());
                            ListeCarte.add(carteN);
                            panneau.getChildren().add(carteN);
                        }
                    }
                }
    }
    /**
     *Suppression des cartes
     */
    public void carteSupr(){
        //récupération de la valeur dans le JTextField
                valueCarteSupr = SerializableValue.loadValue(Launcher.getSerialFileFullPath());
                int valeurSupr = valueCarteSupr.getValue();
                if(valeurSupr > 0){
                    //on parcours la liste de numéro, et on vérifie si la valeur valeurSupr est présente, si oui, on rend la carte associé au numéro invisible
                    for (int i = 0; i < ListeCarte.size(); i ++){
                        if(cartesNum.get(i) == valeurSupr ){
                            if (ListeCarte.get(i).getNumero() > 0) {
                                ListeCarte.get(i).setVisible(false);
                            }
                            //on remet la valeur à 0
                            valueCarteSupr.setValue(0);
                            valueCarteSupr.saveValue(Launcher.getSerialFileFullPath());
                        }
                    }
                }
    }
}
