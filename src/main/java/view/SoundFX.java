package view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;

public class SoundFX {
    private String soundName;
    private static MediaPlayer mediaPlayer;
    private Media sound;
    private static AudioClip background;

    public SoundFX(String noise) {
        this.soundName = noise;
        sound = new Media(new File(soundName).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.9);
    }

    public void playSound() {
        mediaPlayer.play();
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    public void setTime(double sec) {
        mediaPlayer.setStopTime(new Duration(sec));
    }

    public static void setBackground() {
        String back = "src/main/java/view/sounds/civmain.mp3";
        background = new AudioClip(new File(back).toURI().toString());
        background.setVolume(0.1);
        background.setCycleCount(9999999);
        background.play();
    }


}
