package Launcher;

import interfaceFX.Main;
import interfaceSWING.IHM;  
import javafx.application.Application;
import java.io.IOException;
/**
 *
 * @author Josep
 */
public class Launcher {
    
    private static final String SERIAL_FILE_DIRECTORY="serial";

    private static final String SERIAL_FILE_NAME = "file.ser";

    private static String serial_file_full_path;
   
    private Launcher() {}

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
        serial_file_full_path=SERIAL_FILE_DIRECTORY+java.io.File.separator+SERIAL_FILE_NAME;
             
        Thread swing_thread = new Thread(new IHM());
        swing_thread.start();
        Application.launch(Main.class,new String[1]);

    }

    /**
     *
     * @return chemin de dossier
     */
    public static String getSerialFileFullPath(){
        return serial_file_full_path;
    }

    /**
     *Méthode servant à relancé le jeu
     */
    public static void relaunch() {
    try {
        // Créer un nouveau processus pour lancer le Launcher
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Launcher.Launcher");
        processBuilder.start();

        // Fermer le processus actuel
        System.exit(0);
    } catch (IOException ex) {
        // Gérer les éventuelles erreurs d'entrée/sortie
        ex.printStackTrace();
    }
}

}
