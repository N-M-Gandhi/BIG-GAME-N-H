import java.util.ArrayList;
/**
 * Write a description of class AudioManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AudioManager
{
    ArrayList<AudioWAV> audioList;

    /**
     * Constructor for objects of class AudioManager
     */
    public AudioManager()
    {
        audioList = new ArrayList<AudioWAV>();
    }

    public void cacheAudio()
    {
        for(int i = 0; i < 32; i++){audioList.add(new AudioWAV());}//fills ArrayList with empt AudioWAV objects to later be set to real images
        audioList.set(1, new AudioWAV("test.wav"));
        audioList.set(2, new AudioWAV("simeinator_blast.wav"));
        audioList.set(3, new AudioWAV("slimeinator_recharge.wav"));
    }
    
    public void play(int audioNumber)
    {
        audioList.get(audioNumber).play();
    }
}