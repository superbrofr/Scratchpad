import java.util.ArrayList;

/**
 * Player class for the text game. This class holds information about the player, such as the number
 * of moves they have made, the items they are currently holding, and the weight of those items.
 * 
 * @author Charlotte Pierce
 * @version 1/10/2010
 */
public class Player
{
    private int currMoves;
    private int currWeight;
    private int strength;
    private double health;
    private ArrayList<Item> inventory;
    
    public Player()
    {
        currMoves = 0;
        currWeight = 0;
        strength = 10;
        health = 5;
        inventory = new ArrayList<Item>();
    }
    
    /**
     * Prints a string describing the players current inventory.
     * @return A string describing the players inventory.
     */
    public String showInventory(int maxWeight)
    {
        String printString = "Current Items:";
        if(inventory.size() == 0){
            printString += " no items!";
        }
        else{
            for(Item item: inventory){
                printString += item.getDescription();
            }
        }
        printString += "\n Weight: " + currWeight + "/" + maxWeight;
        return printString;
    }
    
     /**
     * Checks if an item is in the inventory.
     * @param itemName Item to check for.
     * @return True if item is held, else false.
     */
    public boolean checkInventory(String itemName)
    {
        for(Item item: inventory){
            if(itemName.equals(item.getName())){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Adds one to the number of moves taken by the player.
     */
    public void addMove()
    {
        currMoves += 1;
    }
    
    /**
     * Adds the specified weight to the player.
     * @param weight The weight to add.
     */
    public void addWeight(int weight)
    {
        currWeight += weight;
    }
    
    /**
     * Removes the specified weight to the player.
     * @param weight The weight to remove.
     */
    public void removeWeight(int weight)
    {
        currWeight -= weight;
    }
    
    /**
     * Get the number of moves the player has made.
     * @return Number of moves made by the player.
     */
    public int getMoves()
    {
        return currMoves;
    }
    
    /**
     * Get the current total weight the player is carrying.
     * @return Total weight player is carrying.
     */
    public int getWeight()
    {
        return currWeight;
    }
    
    /**
     * Adds item to the players' inventory.
     * @param item The item to add to the inventory.
     */
    public void addItem(Item item)
    {
        inventory.add(item);
    }
    
    /**
     * Removes item from player's inventory.
     * @param item The index of the item to remove.
     */
    public void removeItem(int index)
    {
        inventory.remove(index);
    }
    
    /**
     * Returns the player's inventory.
     * @return The player's inventory.
     */
    public ArrayList<Item> getInventory()
    {
        return inventory;
    }
    
        /**
     * Checks whether the item can be picked up or not.
     * @param item Item to try to pick up.
     * @return True if item can be picked up, otherwise false.
     */
    public boolean canPickUp(int itemWeight, int maxWeight)
    {
        if((currWeight + itemWeight) <= maxWeight){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Finds and returns an item if it is in the player's inventory.
     * @param itemName Name of the item to find.
     * @return The item found.
     */
    public Item findItem(String itemName)
    {
        for(Item item: inventory){
            if(itemName.equals(item.getName())){
                return item;
            }
        }
        return null;
    }
    
    /**
     * Calculates and returns the strength of the player's attack. This is based on the
     * player's strength, and the most damaging item they are carrying.
     * @return The players attack strength.
     */
    public double getAttackStr()
    {
        Item best = findMostDamaging();
        double attackStr = strength;
        if(best != null){
            attackStr += best.getDamage() * 1.5;
        }
        return attackStr;
    }
    
    /**
     * Finds the most damaging item in the character's inventory and returns it.
     * @return The most damaging item in the inventory.
     */
    private Item findMostDamaging()
    {
        if(inventory.size() > 0){
            Item best = inventory.get(0);
            for(Item item: inventory){
                if(item.getDamage() > best.getDamage()){
                    best = item;
                }
            }
            return best;
        }
        else{
            return null;
        }
    }
    
    /**
     * Player gets 'hit' with the specified strength. Health goes down by that strength value.
     * @param strength Strength hit with.
     */
    public void getHit(double strength)
    {
        health -= strength;
    }
    
    /**
     * Returns the player's health.
     * @return The player's health.
     */
    public double getHealth()
    {
        return health;
    }
    
    /**
     * Player drinks a potion to restore some of their health. Prints a message if player
     * is not currently holding a potion, otherwise removes potion from inventory, and
     * adds to players' health.
     */
    public void drinkPotion()
    {
        Item item = findItem("potion");
        Potion pot = (Potion) item;
        if(pot != null){
            health += pot.getRestoreVal();
            for(int i = 0; i < inventory.size(); i++){
                if(inventory.get(i) == pot){
                    removeItem(i);
                }
            }
            System.out.println("Drank a potion! \n Health is now: " + health);
        }
        else{
            System.out.println("No potions in inventory! \n Health is: " + health);
        }
    }
}
