package interfaceSWING;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Musique implements Runnable {
    protected Clip clip;
    private boolean Fonctionne = true;

    public Musique(String fichierSon) {
        try {
            File file = new File(fichierSon);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (Fonctionne) {
            clip.setFramePosition(0);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            Fonctionne = false;
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    System.out.println("fonctionne ?");
                    Fonctionne = true;
                }
            });
        }
    }

    public void pause() {
        clip.stop();
    }

    public void resume() {
        clip.start();
    }

    public void stop() {
        Fonctionne = false;
        clip.stop();
    }

    public void mute() {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(-80.0f);
    }

    public void unmute() {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(0.0f);
    }
}
