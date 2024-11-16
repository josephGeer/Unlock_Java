/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceSWING;

import java.util.Arrays;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Font;
import java.io.Serializable;
import java.io.IOException;
import Serialization.SerializableValue;
import Launcher.Launcher;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Josep
 */
public class IHM extends JFrame implements Runnable, Serializable {
    //déclaration des attributs
    private Thread cmpt;
    private int a = 0;
    SerializableValue value;
    SerializableValue valueCarteSupr;
    JTextField NumCarte_field;
    interfaceFX.Controller controller;
    
    private Thread musicThread; 
    private Thread effectMalus;
    private Thread musiqueVictoire;
    
    private String musiqueJeu = "sons/Scarface.WAV";
    private String MalusSon = "sons/Malus.WAV";
    
    private Musique musiquePrincipal = new Musique(musiqueJeu);
    
    protected JButton code;
    protected JButton indice;
    
    private String texteCode;
    
    private int tmpScore = 0;
    private int tmpTemps;
    private boolean verifPerdu = false;
    

    /**
     *création du constructeur
     */
    public IHM () {
    
     //Texte d'annonce pour le jeu
    JOptionPane.showMessageDialog(this, "Bonjour, vous voici dans l'univers d'Unlock Cinéma.\n" +"Vous regardez tranquillement Retour vers le futur lorsque soudain, un symbole apparaît sur l'écran.\n" +
            "Légèrement inquiets, vous commencez à enquêter sur cet étrange symbole. De plus, vous vous apercevez que la porte est bloquée.\n"+""
            + "Vous dites à tout le monde de rester calme, mais les gens sont paniqués.\n" + 
            "Vous allez devoir élucider ce mystère et sortir de ce cinéma au plus vite, avant que le pire n'arrive...");
    
    Font font = loadFontFromFile("Font/Marons-Regular.otf");
    Font font1 = loadFontFromFile("Font/Beckan-Personal_Use.otf");
    Font font2 = loadFontFromFile("Font/Medyson.otf").deriveFont(17f);
    Font font3 = loadFontFromFile("Font/10_Bucks.ttf");
    Font font4 = loadFontFromFile("Font/JMH_Typewriter.ttf").deriveFont(18f);
    value = new SerializableValue(0);
    value.saveValue(Launcher.getSerialFileFullPath());
        
    //création des parties de l'interface
    JPanel panneau = new JPanel(new GridLayout(4, 1));
    JPanel hautAvant = new JPanel(new GridLayout(1, 2));
    JPanel hautAvDroite = new JPanel(new GridLayout(2, 1));
    JPanel haut = new JPanel(new GridLayout(1, 2));
    JPanel bas = new JPanel(new GridLayout(2, 2));
    
    Color jaune = new Color(243, 232, 188);
    Color rouge = new Color(100, 14, 27); 
    Color noir = new Color(15, 7, 4);
    
    Border bordure = BorderFactory.createLineBorder(jaune, 2);
    
    JLabel label = new JLabel("Unlock Cinema");
    label.setFont(font);
    label.setForeground(noir);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setBackground(rouge);
    label.setOpaque(true);
    label.setBorder(bordure);
    
    panneau.add(label);
    //ajout du TextField qui seras utile pour la sérialisation
    NumCarte_field = new JTextField("?");
    NumCarte_field.setForeground(jaune);
    NumCarte_field.setBackground(noir);
    NumCarte_field.setFont(font);
    NumCarte_field.setHorizontalAlignment(JTextField.CENTER);
    NumCarte_field.setPreferredSize(new java.awt.Dimension(418,68));
    NumCarte_field.setBorder(bordure);
    
    controller = new interfaceFX.Controller();
        
    //Mise en place du bouton de supression de carte
        JButton SuprCarte = new JButton("Supression Carte ");
        SuprCarte.setFont(font2);
        SuprCarte.setBackground(noir);
        SuprCarte.setForeground(jaune);
        SuprCarte.setBorder(bordure);
        SuprCarte.addActionListener(ev -> {
        String texte = NumCarte_field.getText();
        if(!texte.isEmpty()){
            SerializableValue valueCarteSupr = new SerializableValue(Integer.parseInt(NumCarte_field.getText()));
            valueCarteSupr.saveValue(Launcher.getSerialFileFullPath());
            NumCarte_field.setText("");
            }
                });
    //Mise en place du bouton d'ajout de carte
    JButton boutonNum = new JButton("Apparition Carte");
    boutonNum.setBackground(noir);
    boutonNum.setFont(font2);
    boutonNum.setForeground(jaune);
    boutonNum.setBorder(bordure);
    boutonNum.addActionListener(ev -> {
        String texte = NumCarte_field.getText();
        if(!texte.isEmpty()){
            SerializableValue value = new SerializableValue(Integer.parseInt(NumCarte_field.getText()));
            value.saveValue(Launcher.getSerialFileFullPath());
            
            NumCarte_field.setText("");
        }
            });
    //MIse en place du bouton Malus
    JButton Malus = new JButton(" Malus ");
    Malus.setBackground(noir);
    Malus.setFont(font2);
    Malus.setForeground(jaune);
    Malus.setBorder(bordure);
    Malus.addActionListener(ev -> {
    Compteur.score -= 500;
    if(effectMalus == null || !effectMalus.isAlive()){
    effectMalus = new Thread(new Musique(musiqueJeu));
    effectMalus.start();
    }
});
    JPanel basPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Créer un nouveau panel pour le bouton Malus
    basPanel.add(Malus); // Ajouter le bouton Malus au nouveau panel
    Malus.setPreferredSize(new Dimension(150, 25)); // Définir la taille préférée du bouton Malus
    basPanel.setBackground(noir);
    basPanel.setBorder(bordure);
    
    //Mise en place du bouton de changement de musique
    JButton Musique = new JButton("Musique");
    Musique.setFont(font2);
    Musique.setBackground(noir);
    Musique.setForeground(jaune);
    Musique.setBorder(bordure);
    Musique.addActionListener(ev ->{
    
        JFrame musique = new JFrame("Musique");
        musique.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        musique.setPreferredSize(new Dimension(400, 125));
        JPanel selection = new JPanel(new GridLayout(2, 2));
        musique.add(selection);
        
        JButton m1 = new JButton("Scarface");
        m1.setFont(font2);
        m1.setForeground(jaune);
        m1.setBackground(noir);
        m1.setBorder(bordure);
        JButton m2 = new JButton("Pirate des Caraibes");
        m2.setFont(font2);
        m2.setForeground(jaune);
        m2.setBackground(noir);
        m2.setBorder(bordure);
        JButton m3 = new JButton("Pulp Fiction");
        m3.setFont(font2);
        m3.setForeground(jaune);
        m3.setBackground(noir);
        m3.setBorder(bordure);
        JButton m4 = new JButton("Chat");
        m4.setFont(font2);
        m4.setForeground(jaune);
        m4.setBackground(noir);
        m4.setBorder(bordure);
        
        selection.add(m1);selection.add(m2);selection.add(m3);selection.add(m4);
        
        m1.addActionListener( ev1 ->{
            musiqueJeu = "sons/Scarface.WAV" ;
        });
        m2.addActionListener( ev1 ->{
            musiqueJeu = "sons/Pirate_des_Caraibes.WAV" ;
        });
        m3.addActionListener( ev1 ->{
            musiqueJeu = "sons/Pulp_fiction.WAV" ;
        });
        m4.addActionListener( ev1 ->{
            musiqueJeu = "sons/Cat.WAV" ;
        });
        
        musique.pack();
        musique.setVisible(true);
    });
    basPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Ajouter un espace entre les boutons Malus et Musique
    basPanel.add(Musique); // Ajouter le bouton Musique à basPanel
    Musique.setPreferredSize(new Dimension(150, 25)); // Définir la taille préférée du bouton Musique
    
    getContentPane().add(panneau, BorderLayout.CENTER); // Ajouter le panneau principal au centre de la JFrame
    getContentPane().add(basPanel, BorderLayout.SOUTH); // Ajouter le nouveau panel en bas de la JFrame
    
    //ajout des bordures sur une autre parties de l'interface
    hautAvant.setBorder(bordure);
    panneau.setBorder(bordure);
    
    //ajout des différentes parties de l'interface au panneau principal, et ajout des boutons aux Panels secondaires
    panneau.add(hautAvant);
    hautAvant.add(NumCarte_field);
    hautAvant.add(hautAvDroite);
    hautAvDroite.add(SuprCarte);
    hautAvDroite.add(boutonNum);
    panneau.add(haut);
    panneau.add(bas);
    
    //création d'un affichage pour le décompte, seconde et minute
    JLabel Seconde = new JLabel("8 :");
    Seconde.setHorizontalAlignment(SwingConstants.CENTER);
    Seconde.setFont(font3);
    haut.add(Seconde);
    Seconde.setForeground(rouge);
    Seconde.setBackground(noir);
    Seconde.setBorder(bordure);
    Seconde.setOpaque(true); 
    
    JLabel labelM = new JLabel("0");
    labelM.setHorizontalAlignment(SwingConstants.CENTER);
    labelM.setFont(font3);
    haut.add(labelM);
    labelM.setForeground(rouge);
    labelM.setBackground(noir);
    labelM.setBorder(bordure);
    labelM.setOpaque(true); 
    
    // création du bouton pour lancé la parti, et lancer le compteur
    JButton play;
    try{
        //ajout d'une image au bouton play pour stylisé l'interface
        BufferedImage jouer = ImageIO.read(new File("images/play.png"));
            int newWidth = 150;  
            int newHeight = 50; 
            Image scaledImg = jouer.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon ImgPlay = new ImageIcon(scaledImg);
            
            play = new JButton(ImgPlay);
            play.setBorder(bordure);
            play.setBackground(noir);
            bas.add(play);
            play.addActionListener(ev -> {
            if(cmpt == null || !cmpt.isAlive() && a <= 0){
            a++;
            cmpt = new Thread(new Compteur(Seconde, labelM));
            cmpt.start();
            
            if (musicThread == null || !musicThread.isAlive()) {  // Vérification si la musique est en cours de lecture
                musiquePrincipal = new Musique(musiqueJeu); // créer une nouvelle instance de Musique
                musicThread = new Thread(musiquePrincipal);
                musicThread.start();
    }
            }
            });
                
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }
    
    //création du bouton pause, met en pause le compteur
    JButton stop = new JButton("0");
     try {
            BufferedImage pause = ImageIO.read(new File("images/pause.png"));
            int newWidth = 150; 
            int newHeight = 50; 
            Image scaledImg = pause.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
             ImageIcon ImgStop = new ImageIcon(scaledImg);

            stop = new JButton(ImgStop);
            stop.setBackground(noir);
            stop.setBorder(bordure);
            bas.add(stop);
           
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }
     stop.addActionListener(ev -> {
    if(Compteur.tempsMax != 0){
        tmpTemps = Compteur.tempsMax;
        Compteur.tempsMax = 1 ;
        tmpScore = Compteur.score;
        Compteur.score = 100000;
        if (musiquePrincipal != null && musiquePrincipal.clip.isRunning()) { // vérifier si la musique est en cours de lecture
            musiquePrincipal.pause(); // appeler la méthode pause() sur l'instance de Musique en cours de lecture
        }
    }
    else {
        Compteur.tempsMax = tmpTemps;
        Compteur.score = tmpScore;
        cmpt = new Thread(new Compteur(Seconde, labelM));
        cmpt.start();
        if (musiquePrincipal != null && !musiquePrincipal.clip.isRunning()) { // vérifier si la musique est en pause
            musiquePrincipal.resume(); // appeler la méthode resume() sur l'instance de Musique en cours de lecture
        }
    }
});
     
     //création du bouton pour avoir des indices
     JButton indice = new JButton("0");
try {
    BufferedImage loupe = ImageIO.read(new File("images/ampoule.png"));
    int newWidth = 50;  
    int newHeight = 50; 
    Image scaledImg = loupe.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    ImageIcon ImgIndice = new ImageIcon(scaledImg);

    indice = new JButton(ImgIndice);
    indice.setBackground(noir);
    indice.setBorder(bordure);
    bas.add(indice);
} catch (IOException e) {
    System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
}

indice.addActionListener(ev-> {
    
    //création de deux listes, une avec le numéro de la carte, l'autre avec l'indice associé, à l'index i, 
    //nous retrouvons la carte i qui va de paire avec l'indice i
    ArrayList<Integer> listeNum = new ArrayList<>(20);
    ArrayList<String> listeIndice = new ArrayList<>(20);
    
    //gestion du contenus des indices
    String s,s1,s2,s3,s4,s5,s6,s7,s8,s10,s11,s12,s14,s15,s16,s17,s18;
 s="Retourner la carte"; s1="Sympa le film de gangster"; s2= "Ont-ils dégradé les sièges ?";
s3="La dame a soif, il vaut mieux répondre à ses exigences"; s4= "Il faut assembler le mot de deux en deux, et décaler de deux aussi";
s5= "C'est l'énigme, va voir l'homme mystérieux"; s6="Il vaudrait mieux voir si j'ai fait le tri dans mes documents !";
s7="Fais-en ce que tu veux mais ne me tire pas dessus...";
s8="Je ne vais pas te donner le code"; s10="Sérieusement, vous vous en êtes servis !";
s11="Vous êtes un meurtrier messieurs, pas d'aide pour vous"; s12="L'inflation ça ne rigole pas";
s14="Si vraiment tu n'y arrives pas je te passe la solution, écris 98"; s15="Non je rigole, tu n'auras pas d'indice, relis l'énigme";
s16="L'argent c'est le pouvoir, mais aussi l'information"; s17="Oh un petit chat, non mais sérieusement, il y a une affiche dans le fond";
s18="Juste une armada de chats, ils sont beaucoup non ?";
    
    //ajout des chaines de caractères à la listeIndice, et ajout du numéro de carte à la listeNum
    listeNum.addAll(Arrays.asList(1, 6, 9, 12, 13, 15, 27, 28, 30, 40, 41, 42, 67, 98, 54, 10, 32));
    listeIndice.addAll(Arrays.asList(s, s1,s2,s3,s4,s5,s6,s7,s8,s10,s11,s12,s14,s15,s16,s17,s18));
    
    //création d'une nouvelle fenêtre qui s'ouvre lorsque l'on appuie sur le bouton indice
    JFrame frame = new JFrame("Indice");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setPreferredSize(new Dimension(500, 250));
    JPanel panelIndice = new JPanel(new GridLayout(3, 1));
    panelIndice.setBorder(bordure);
    panelIndice.setBackground(noir);
    
    JPanel hautInd = new JPanel();
    hautInd.setBackground(noir);
    hautInd.setBorder(bordure);
    panelIndice.add(hautInd);
    
    JPanel centralInd = new JPanel(new GridLayout(1,2));
    centralInd.setBackground(noir);
    centralInd.setBorder(bordure);
    panelIndice.add(centralInd);
    
    JPanel basInd = new JPanel();
    basInd.setBackground(noir);
    basInd.setBorder(bordure);
    panelIndice.add(basInd);

    JLabel IndiceText = new JLabel("Entrez la carte dont vous voulez l'indice");
    IndiceText.setForeground(jaune);
    IndiceText.setBackground(noir);
    IndiceText.setFont(font2);
    hautInd.add(IndiceText);
    
    //JtextField qui va servir à enregistrer une valeur pour plus tard afficher l'indice
    JTextField Indice = new JTextField("?");
    Indice.setHorizontalAlignment(JTextField.CENTER); // centre le texte
    Indice.setBorder(bordure);
    Indice.setBackground(noir);
    Indice.setForeground(rouge);
    Indice.setFont(font4);
    
    //bouton permettant d'enregistrer la valeur présente dans le JTextField
    JButton valider = new JButton("Valider");
    valider.setBorder(bordure);
    valider.setBackground(noir);
    valider.setForeground(rouge);
    valider.setFont(font4);
    
    centralInd.add(Indice);
    centralInd.add(valider);
    
    //JTextArea servant à afficher l'indice
    JTextArea rep = new JTextArea("");
    rep.setPreferredSize(new Dimension(450, 100));
    rep.setForeground(jaune);
    rep.setFont(font4);
    rep.setBackground(noir);
    rep.setLineWrap(true); // active le retour à la ligne automatique
    rep.setWrapStyleWord(true); // active le retour à la ligne au niveau des mots

    
    basInd.add(rep);

    valider.addActionListener(ev1->{
            for(int i = 0; i < listeIndice.size(); i++){
                String numInd = Integer.toString(listeNum.get(i));
                if(numInd.equals(Indice.getText())){
                   rep.setText(listeIndice.get(i));
                }
            }
    });

    frame.add(panelIndice);
    frame.pack();
    frame.setVisible(true);
});
     //Création du bouton code qui afficheras une fenêtre dans laquelle on pourras entrer un code à 5 chiffres
     JButton code = new JButton("0");
     try {
            BufferedImage chiffre = ImageIO.read(new File("images/code.png"));
            int newWidth = 50; 
            int newHeight = 50; 
            Image scaledImg = chiffre.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon ImgCode = new ImageIcon(scaledImg);

            code = new JButton(ImgCode);
            code.setBackground(noir);
            bas.add(code);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }
     
    code.addActionListener(ev -> {
    // Créez une nouvelle fenêtre Swing
    if(controller.cartesNum.contains(30)){
    JFrame frame = new JFrame("Code");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setPreferredSize(new Dimension(400, 200));
    

    JPanel panelCode = new JPanel(new GridLayout(3, 1));
    panelCode.setBackground(noir);
    panelCode.setBorder(bordure);
    
    JPanel hautCode = new JPanel(new GridLayout(1,1));
    hautCode.setBackground(noir);
    hautCode.setBorder(bordure);
    
    JPanel midCode = new JPanel(new GridLayout(3, 3));
    midCode.setBackground(noir);
    midCode.setBorder(bordure);
    
    JPanel basCode = new JPanel();
    basCode.setBackground(noir);
    basCode.setBorder(bordure);
    
    panelCode.add(hautCode);
    panelCode.add(midCode);
    panelCode.add(basCode);

    JLabel codeRep = new JLabel("");
    codeRep.setForeground(jaune);
    codeRep.setFont(font2);
    codeRep.setHorizontalAlignment(SwingConstants.CENTER);
    
    hautCode.add(codeRep);
   
    texteCode = ""; // Variable pour stocker le texte actuel du JLabel
    //création d'une boucle pour créer et initialisé tous les boutons
    for (int i = 1; i <= 9; i++) {
    String a = Integer.toString(i);
    JButton bouton = new JButton(String.valueOf(i));
    bouton.setForeground(jaune);
    bouton.setBackground(noir);
    bouton.setBorder(bordure);
    midCode.add(bouton);
    
    bouton.addActionListener(evi -> {
        texteCode += a; // Ajouter la valeur du bouton à la variable texteCode
        codeRep.setText(texteCode); // Définir le texte du JLabel avec la nouvelle valeur
        System.out.println(texteCode);
    });
}
    JButton boutonValider = new JButton("Valider");
    boutonValider.setForeground(jaune);
    boutonValider.setBackground(noir);
    boutonValider.setBorder(bordure);
    basCode.add(boutonValider);
    
    //création d'un JTextArea pour afficher la victoire du joueur
    JTextArea gagne = new JTextArea("Victoire score : " + Compteur.score );
    gagne.setPreferredSize(new Dimension(250, 60));
    gagne.setForeground(rouge);
    gagne.setFont(font2);
    gagne.setBackground(noir);
    gagne.setLineWrap(true); 
    gagne.setWrapStyleWord(true); 
    
    boutonValider.addActionListener(e -> {
        // Compars les valeurs saisies avec les valeurs attendues
        if (codeRep.getText().equals("59283")) {
            Compteur.tempsMax = 1 ;
            basCode.remove(boutonValider);
            basCode.add(gagne);
            frame.pack();
            musiquePrincipal.stop();
            musiqueVictoire = new Thread(new Musique("sons/Victoir.WAV"));
            musiqueVictoire.start();
            
        } else {
            JOptionPane.showMessageDialog(frame, "Code incorrect !");
          }
    });
    frame.add(panelCode);
    frame.pack();
    frame.setVisible(true);
    }
});
    
        getContentPane().add(panneau);
        setTitle("Unlock Jeu");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
	setLocationRelativeTo(null);
        Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setLocation(screenBounds.x, screenBounds.y);
        
        setVisible(true);
        //ajout d'un timer afin de vérifier si le joueur a perdu
        Timer perdu = new Timer(1000, ev-> {
            if (Compteur.score <= 5300 && Compteur.tempsMax == 0 && verifPerdu == false || Compteur.score <= 0) {
                verifPerdu= true;
                musiquePrincipal.stop();
                Compteur.tempsMax = 1 ;
                Musique perduSon = new Musique("sons/Perdu.WAV");
                Thread perduThread = new Thread(perduSon);
                perduThread.start();
                
                JFrame perduFrame= new JFrame("Perdu");
                perduFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                perduFrame.setPreferredSize(new Dimension(800, 400));
                
                JPanel panelPerdu = new JPanel(new GridLayout(1, 2));
                panelPerdu.setBackground(noir);
                
                JTextArea Perdu = new JTextArea("VOUS AVEZ PERDU ! vous êtes enfermé dans ce cinéma pour toujours, vous ne reverrez jamais la lumière du jour, votre score : "+ Compteur.score);
                Perdu.setPreferredSize(new Dimension(600, 400));
                Perdu.setFont(font4);
                Perdu.setForeground(jaune);
                Perdu.setBackground(noir);
                Perdu.setBorder(bordure);
                Perdu.setLineWrap(true); 
                Perdu.setWrapStyleWord(true); 
                panelPerdu.add(Perdu);
                
                JButton reessayer = new JButton("retry");
                reessayer.setBackground(noir);
                reessayer.setForeground(jaune);
                reessayer.setFont(font2);
                reessayer.addActionListener(ev1->{
                   Launcher.relaunch(); 
                });
                panelPerdu.add(reessayer);
                
                perduFrame.add(panelPerdu);
                perduFrame.pack();
                perduFrame.setVisible(true);
            }
        });
        perdu.start(); // Démarrez le Timer
    }

    /**
     * run
     */
    public void run() {
        setVisible(true);
    }
    //fonction pour charger une police depuis le fichier Font
     private static Font loadFontFromFile(String fontPath) {
        Font customFont = null;
        try {
            // Charger la police depuis le fichier
            customFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(fontPath)).deriveFont(70f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customFont;
    }
}