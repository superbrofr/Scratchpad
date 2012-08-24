import java.util.ArrayList;

/**
 * Player class for the text game. This class holds information about the player, such as the number
 * of moves they have made, the items they are currently holding, and the weight of those items.
 * 
 * @author Charlotte Pierce
 * @version 1/10/2010
 */
public class Player extends IntelligentBeing
{
    private int currMoves;
    private int currWeight;
    private ArrayList<Item> inventory;
    
    /**
     * Initialises the player. Health is set to 5, and strength is 10. 
     * The number of moves taken, and the weight of items the player holds is
     * set to zero (just to make sure).
     * The ArrayList for the inventory is also initialised.
     */
    public Player()
    {
        super(10, 5);
        currMoves = 0;
        currWeight = 0;
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
        double attackStr = getStrength();
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
     * Player drinks a potion to restore some of their health. Prints a message if player
     * is not currently holding a potion, otherwise removes potion from inventory, and
     * adds to players' health.
     * @return The status of the player after drinking, or a message saying there are no
     * potions in the inventory.
     */
    public String drinkPotion()
    {
        Item item = findItem("potion");
        Potion pot = (Potion) item;
        String returnString = "";
        if(pot != null){
            addHealth(pot.getRestoreVal());
            for(int i = 0; i < inventory.size(); i++){
                if(inventory.get(i) == pot){
                    removeItem(i);
                }
            }
            returnString += "Drank a potion! \n Health is now: " + getHealth();
        }
        else{
            returnString += "No potions in inventory! \n Health is: " + getHealth();
        }
        return returnString;
    }
    
    /**
     * Returns the number of moves the player has made.
     * @return The number of moves the player has made.
     */
    public int getCurrMoves()
    {
        return currMoves;
    }
    
    /**
     * Returns the current weight held by the player.
     * @return The current weight held by the player.
     */
    public int getCurrWeight()
    {
        return currWeight;
    }
    
    /**
     * Sets the value for currMoves - used for loading
     * @param curr The new value for currMoves.
     */
    public void setCurrMoves(int curr)
    {
        currMoves = curr;
    }
    
    /**
     * Sets the value for currWeight - used for loading
     * @param newWeight The new value for currWeight.
     */
    public void setWeight(int newWeight)
    {
        currWeight = newWeight;
    }
}
