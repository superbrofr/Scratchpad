
/**
 * A transporter room which can be included in the game. When a player tries to leave a
 * transporter room, they arrive in a random room from the game.
 * 
 * @author Charlotte Pierce
 * @version 6/11/2010
 */
public class TransporterRoom extends Room
{
    
    public TransporterRoom(String description, RoomRandomiser rand)
    {
        super(description, rand);
    }
    
    /**
     * Uses the Randomiser to return a random room to which the player is directed.
     */
    public Room getExit(String direction) 
    {
        return (getRandomiser()).getRandomRoom();
    }
}