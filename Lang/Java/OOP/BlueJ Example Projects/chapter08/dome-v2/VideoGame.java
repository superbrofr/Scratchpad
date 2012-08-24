
/**
 * A class to describe a video game stored in the DoME
 * Objects first with Java, Exercise  8.8
 * 
 * @author Charlotte Pierce
 * @version 27/10/2010
 */
public class VideoGame extends Item
{
    // instance variables - replace the example below with your own
    private String platform;

    /**
     * Constructor for objects of class VideoGame
     */
    public VideoGame(String title, int time, String thePlatform)
    {
        super(title, time);
        platform = thePlatform;
    }
}
