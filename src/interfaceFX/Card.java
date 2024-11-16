package interfaceFX;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author Josep
 */
public class Card extends ImageView {

    // Création d'attribut pour la class Card
    private static final String IMAGE_DIRECTORY = "images";
    private static final String IMAGE_PREFIX = "carte_";
    private static final String IMAGE_SUFFIX = ".png";
    private static final char SEPARATOR_CHAR = File.separatorChar;
    private static final Integer IMAGE_HEIGHT = 500;

    private final int numero;
    private double dragX, dragY;
    protected static int numeroCarte;
    protected final ArrayList<CercleCliquable> ListeCercleCliquable;
    protected boolean[] verif = new boolean[8];

    /**
     *
     * @param numero de la carte
     * @param a une liste de cercle cliquable
     */
    public Card(int numero, ArrayList<CercleCliquable> a) {
        this.numero = numero;
        this.ListeCercleCliquable = a;

        String chemin = IMAGE_DIRECTORY;
        chemin += SEPARATOR_CHAR;
        chemin += IMAGE_PREFIX;
        chemin += this.numero;
        chemin += IMAGE_SUFFIX;

        File file = new File(chemin);

        setImage(new Image(file.toURI().toString()));
        setPreserveRatio(true);
        setFitHeight(IMAGE_HEIGHT);

        setOnMousePressed(e -> handleMousePressed(e));
        setOnMouseDragged(e -> handleMouseDragged(e));
        setOnMouseClicked(e -> handleMouseClicked(e));
    }
    /**
     *
     * @return le numero de la carte
     */
    public int getNumero() {
        return numero;
    }

    /**
     *
     * @param e
     * Méthode appelée lorsque la souris est pressée sur la carte
     */
    protected void handleMousePressed(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            this.dragX = e.getScreenX() - getTranslateX();
            this.dragY = e.getScreenY() - getTranslateY();
            System.out.println(e.getX());
            System.out.println(e.getY());
        }
    }
    /**
     *
     * @param e
     * Méthode appelée lorsque la souris est déplacée sur la carte
     */
    protected void handleMouseDragged(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            translateXProperty().set(e.getScreenX() - dragX);
            translateYProperty().set(e.getScreenY() - dragY);
        }
    }
    /**
     *
     * @param e
     * Méthode appelée lorsque la souris est cliquée sur la carte
     */
    protected void handleMouseClicked(MouseEvent e) {
        this.toFront();

        if (e.getButton() == MouseButton.SECONDARY) {
            this.setRotate(this.getRotate() + 25);
        }

        if (ListeCercleCliquable != null && !ListeCercleCliquable.isEmpty()) {
            for (int i = 0; i < ListeCercleCliquable.size(); i++) {
                if (ListeCercleCliquable.get(i).isInside(e.getX(), e.getY())) {
                    verif[i] = true;
                    if (ListeCercleCliquable.get(i).getNumero().equals(10) && !Controller.verif48) {
                        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                        dialog.setTitle("Attention !");
                        dialog.setHeaderText("Un bandit vous bloque l'accès");
                        dialog.showAndWait();
                        verif[i] = false;
                        Controller.verif48 = false;
                    }
                    numeroCarte = ListeCercleCliquable.get(i).getNumero();
                } else {
                    verif[i] = false;
                }
            }
        }
    }
    /**
     *
     * @return la taille de la liste de cercles cliquables
     */
    public int getCercleCliquableSize() {
        return ListeCercleCliquable.size();
    }
}
