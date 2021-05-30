import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class AudioPlayer {
    private AudioClip f1a;
    private AudioClip f2a;
    private AudioClip f3a;
    private AudioClip f4a;
    private AudioClip bgm;
    public void getAudioPlayer(){
        File f1= new File("Audio/F1.wav");
        File f2= new File("Audio/F2.wav");
        File f3= new File("Audio/F3G.wav");
        File f4= new File("Audio/1745.wav");
        File f5= new File("Audio/nyaxx.wav");
        try {
            f1a = Applet.newAudioClip(f1.toURI().toURL());
            f2a = Applet.newAudioClip(f2.toURI().toURL());
            f3a = Applet.newAudioClip(f3.toURI().toURL());
            f4a = Applet.newAudioClip(f4.toURI().toURL());
            bgm = Applet.newAudioClip(f5.toURI().toURL());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
    public void getLeftPlayer(int state){
        if (state==-1)f4a.play();
        else f1a.play();
    }
    public void getRightPlayer(int state){
        if (state==-1)f3a.play();
        else f2a.play();
    }
    public void getBGMPlayer(){
        bgm.loop();;
    }
}
