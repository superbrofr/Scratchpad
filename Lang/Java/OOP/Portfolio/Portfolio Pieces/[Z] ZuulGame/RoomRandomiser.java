import java.util.Random;
import java.util.ArrayList;

/**
 * A class which stores all the rooms in the game, and can return a random one if called for.
 * 
 * @author Charlotte Pierce
 * @version 6/11/2010
 */
public class RoomRandomiser
{
    private Random rand;
    private ArrayList<Room> rooms;
    
    public RoomRandomiser()
    {
        rand = new Random();
        rooms = new ArrayList<Room>();
    }
    
    /**
     * Returns a random room from the list of rooms.
     * Makes sure a viable room is chosen, by limiting the scope of the output
     * to any number between 0 and rooms.size()
     * @return Room A random room from within the game.
     */
    public Room getRandomRoom()
    {
        return (rooms.get(rand.nextInt(rooms.size())));
    }
    
    /**
     * Adds a room to the randomisers' list.
     */
    public void addRoom(Room toAdd)
    {
        rooms.add(toAdd);
    }
    
    /**
     * Finds the room in the list of rooms with the specified string.
     */
    public Room findRoom(String find)
    {
        for(Room r: rooms){
            if(r.getName().equals(find)){
                return r;
            }
        }
        return null;
    }
}
