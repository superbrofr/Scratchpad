import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    private ArrayList<Monster> monsters;
    private RoomRandomiser roomRandomiser;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, RoomRandomiser rand) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        monsters = new ArrayList<Monster>();
        rand.addRoom(this);
        roomRandomiser = rand;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return  description + ".\n" + getExitString() + "\n" + getItemsDesc() + getMonstersDesc();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Finds and returns an item if it is in the room.
     * @param itemName Name of the item to find.
     * @return The item found.
     */
    public Item findItem(String itemName)
    {
        for(Item item: items){
            if(itemName.equals(item.getName())){
                return item;
            }
        }
        return null;
    }
    
    /**
     * Finds and returns a monster if it is in the room.
     * @param monsterName Name of the monster to find.
     * @return The monster found.
     */
    public Monster findMonster(String monstName)
    {
        for(Monster m: monsters){
            if(monstName.equals(m.getName())){
                return m;
            }
        }
        return null;
    }
    
    /**
     * Find the item with the specified name and removes it from the room.
     * @param itemName The name of the item to remove.
     */
    public void removeItem(String itemName)
    {
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getName().equals(itemName)){
                items.remove(i);
            }
        }
    }
    
    /**
     * Adds an item to the room.
     * @param item The item to add.
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Returns a String representing a description of all items in the room.
     * @return Description of all the items in the room.
     */
    private String getItemsDesc()
    {
        String returnString = " Items: ";
        if(items.size() == 0){
            returnString += "\n   No items!";
        }
        else{
            for(Item item: items){
                returnString += item.getDescription();
            }
        }
        return returnString;
    }
    
    /**
     * Returns a String representing a description of all monsters in the room.
     * @return Description of all the monsters in the room.
     */
    private String getMonstersDesc()
    {
        String returnString = "\n Monsters: ";
        if(monsters.size() == 0){
            returnString += "\n   No monsters!";
        }
        else{
            for(Monster m: monsters){
                returnString += m.getDescription();
            }
        }
        return returnString;
    }
    
    /**
     * Adds a monster to the room.
     * @param m The monster to add.
     */
    public void addMonster(Monster m)
    {
        monsters.add(m);
    }
    
    /**
     * Returns the room randomiser (so that TransporterRoom class can use it)
     * @return The RoomRandomiser obejct.
     */
    public RoomRandomiser getRandomiser()
    {
        return roomRandomiser;
    }
}

