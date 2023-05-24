import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
/**
 * Write a description of class AudioWAV here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AudioWAV
{
    Clip clip;
    AudioInputStream audioStream;
    public AudioWAV()
    {

    }

    public AudioWAV(String name)
    {
        try{
            File file = new File(name);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch(UnsupportedAudioFileException e) {
            System.out.println("Error: auidiofile in "+name+" not supported");
        } catch(LineUnavailableException e) {
            System.out.println("Error: file "+name+" not found");
        } catch(IOException e) {
            System.out.println("Error: end of stream encountered when reading "+name);
        }
    }

    public void play()
    {
        clip.setMicrosecondPosition(0);
        clip.start();
    }
}
