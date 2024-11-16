    package interfaceFX;
    import javafx.scene.media.Media;
    import javafx.scene.media.MediaPlayer;
    import java.io.File;

/**
 *
 * @author Josep
 */
public class Son implements Runnable {
        private static Son instance;
        private MediaPlayer mediaPlayer;

    /**
     *
     * @param fichierSon
     */
    public Son(String fichierSon) {
            Media media = new Media(new File(fichierSon).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            instance = this;
        }

    /**
     *
     * @return instance son
     */
    public static Son getInstance() {
            return instance;
        }

    /**
     *Méthode qui active le lancement du son
     */
    public void run() {
            mediaPlayer.play();
        }

    /**
     *Méthode qui désactive le son
     */
    public void mute() {
            mediaPlayer.setMute(true);
        }
    }
