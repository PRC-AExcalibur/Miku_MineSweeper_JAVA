import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class BGMplayer {
    public void getBGMPlayer(){
        File bgm= new File("Audio/nyaxx.wav");
        AudioClip BGMa = null;
        try { BGMa = Applet.newAudioClip(bgm.toURI().toURL()); }
        catch (MalformedURLException ex) { ex.printStackTrace(); }
        BGMa.loop();
    }
}
