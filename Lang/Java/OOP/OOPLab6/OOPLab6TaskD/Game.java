/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a text based adventure game.  Users 
 *  can walk around some scenery, pick up items and attack monsters.
 *  The goal for the user is to find the master key, and take it to the control room.
 *  There is a limit on the number of moves a player can make to reach this goal.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Charlotte Pierce
 * @version 1/10/2010
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room goalRoom;
    private int maxMoves;
    private int maxWeight;
    private Player player1;
    
    public static void main(String args[]){
        Game g = new Game();
        g.play();
    }
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        initialiseGame();
        parser = new Parser();
        player1 = new Player();
        maxMoves = 25;
        maxWeight = 100;
    }

    /**
     * Create all the rooms and link their exits together.
     * Create items and monsters and place them in appropriate rooms.
     */
    private void initialiseGame()
    {      
        // initialise rooms
        Room outside = new Room("\n \n \n You are outside a huge lab facility. Inpenetrable jungle is all around, \n with the only exits being the trailing path you can from, and the entry to \n the facility your options. It looks like Zuul knew you were coming, and has \n abandoned the building. \n However, you know he will have left nasties within.");
        Room foyer = new Room("\n \n \n You are in what appears to be a foyer. It looks like a pretty standard room.");
        Room waitingRoom1 = new Room("\n \n \n You walk slowly into what you decide must be a waiting room. Apart \n from the chairs and tables, you see nothing out of the ordinary.");
        Room teaRoom = new Room("\n \n \n Success! You have found the tea room! Perhaps now you can get something \n to refresh you after your long journey...");
        Room entertainmentRoom = new Room("\n \n \n Seems you have found a room where Zuul's scientists allow their \n test subjects to rest a while.");
        Room waitingRoom2 = new Room("\n \n \n This room looks exactly the same as the waiting room you found eariler \n in the adventure. *Exactly* the same. Creepy...");
        Room hiddenRoom = new Room("\n \n \n This does not look like a room people are supposed to find...");
        Room controlRoom = new Room("\n \n \n This looks like the room you have been looking for.");
        Room testRoom1 = new Room("\n \n \n Scientific things all around, looks like some sort of testing takes place \n here, a smear that looks suspiciously like blood is on the wall.");
        Room testRoom2 = new Room("\n \n \n Scientific things all around, a testing room you think.");
        Room storage1 = new Room("\n \n \n This room looks less organised than the others. It's filled with cleaning products.");
        Room storage2 = new Room("\n \n \n This room seems out of character with the others in the facility, there's no \n scientific equipment to be seen, no hidden monitors, just endless \n rags and cleaning products.");
        Room observationRoom1 = new Room("\n \n \n This room is filled with monitors looking into four smaller rooms. \n Possibly an observation room?");
        Room observationRoom2 = new Room("\n \n \n You see lots of equipment in this room. Through the glass you can see \n a couple of smaller rooms. It looks like they are used for some \n form of testing.");
        Room lab2A = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '2A'.");
        Room lab2B = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '2B'.");
        Room lab1A = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '1A'.");
        Room lab1B = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '1B'.");
        Room lab1C = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '1C'.");
        Room lab1D = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '1D'.");
        
        // initialise room exits
        outside.setExit("north", foyer);
        foyer.setExit("south", outside);
        foyer.setExit("north", waitingRoom1);
        waitingRoom1.setExit("south", foyer);
        waitingRoom1.setExit("north", teaRoom);
        waitingRoom1.setExit("east", storage2);
        waitingRoom1.setExit("west", lab1C);
        teaRoom.setExit("south", waitingRoom1);
        teaRoom.setExit("north", entertainmentRoom);
        teaRoom.setExit("east", storage1);
        entertainmentRoom.setExit("south", teaRoom);
        entertainmentRoom.setExit("north", waitingRoom2);
        entertainmentRoom.setExit("east", testRoom2);
        waitingRoom2.setExit("south", entertainmentRoom);
        waitingRoom2.setExit("north", hiddenRoom);
        waitingRoom2.setExit("east", testRoom1);
        waitingRoom2.setExit("west", lab2B);
        hiddenRoom.setExit("south", waitingRoom2);
        controlRoom.setExit("west", lab2B);
        testRoom1.setExit("west", waitingRoom2);
        testRoom1.setExit("south", testRoom2);
        testRoom2.setExit("west", entertainmentRoom);
        testRoom2.setExit("north", testRoom1);
        storage1.setExit("west", teaRoom);
        storage1.setExit("south", storage2);
        storage2.setExit("north", storage1);
        storage2.setExit("west", waitingRoom1);
        lab2A.setExit("east", lab2B);
        lab2A.setExit("south", observationRoom2);
        lab2B.setExit("north", controlRoom);
        lab2B.setExit("east", waitingRoom2);
        lab2B.setExit("west", lab2A);
        lab2B.setExit("south", observationRoom2);
        observationRoom2.setExit("north", lab2A);
        observationRoom2.setExit("south", lab1B);
        lab1A.setExit("north", observationRoom2);
        lab1A.setExit("south", observationRoom1);
        lab1A.setExit("east", lab1B);
        lab1B.setExit("west", lab1A);
        lab1B.setExit("south", lab1C);
        lab1C.setExit("north", lab1B);
        lab1C.setExit("south", lab1D);
        lab1D.setExit("north", lab1D);
        observationRoom1.setExit("north", lab1A);
        
        //create items
        Item tree = new Item("tree", 200, 50, 5);
        Item flower = new Item("flower", 1, 5, 1);
        Item chair = new Item("chair", 25, 10, 10);
        Item table = new Item("table", 100, 15, 5);
        Item potion = new Potion();
        Item television = new Item("television", 110, 250, 8);
        Item bat = new Item("bat", 30, 5, 20);
        Item speaker = new Item("speaker", 5, 10, 10);
        Item laptop = new Item("laptop", 50, 50, 15);
        Item acid = new Item("acid", 5, 10, 60);
        Item broom = new Item("broom", 5, 5, 5);
        
        //add items to rooms
        outside.addItem(tree);
        outside.addItem(flower);
        waitingRoom1.addItem(chair);
        waitingRoom1.addItem(table);
        teaRoom.addItem(potion);
        entertainmentRoom.addItem(television);
        waitingRoom2.addItem(bat);
        observationRoom2.addItem(speaker);
        observationRoom1.addItem(laptop);
        testRoom2.addItem(acid);
        storage1.addItem(broom);
        
        //create monsters
        Monster rat1 = new Monster("rat", 5, 10);
        Monster rat2 = new Monster("rat", 5, 10);
        Monster drone = new Monster("drone", 20, 10);
        Monster mutantB = new Monster("mutantB", 15, 10);
        Monster mutantL = new Monster("mutantL", 25, 50);
        Monster mutant3 = new Monster("mutant", 100, 100);
        Monster spider = new Monster("spider", 5, 15);
        Monster skeleton = new Monster("skeleton", 20, 5);
        
        //add monsters to rooms
        foyer.addMonster(rat1);
        storage2.addMonster(rat2);
        teaRoom.addMonster(drone);
        lab1D.addMonster(mutantB);
        lab2A.addMonster(mutantL);
        hiddenRoom.addMonster(mutant3);
        lab1A.addMonster(spider);
        observationRoom2.addMonster(skeleton);
        
        //assign current room and goal room
        currentRoom = outside;
        goalRoom = controlRoom;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            //only check if move limit reached if user hasn't already quit
            //nested if's because only want to check once (otherwise finished gets re-written to true by reachedGoa())
            if(!finished){
                finished = checkMoves();
                if(!finished){
                    finished = !checkAlive();
                }
                if(!finished){
                    finished = reachedGoal();
                }
            }
        }
        if(checkMoves()){
            System.out.println("!!! Move limit reached. !!!");
        }
        else if(!checkAlive()){
            System.out.println("!!! You died. !!!");
        }
        else if(reachedGoal()){
            System.out.println("!!! You reached the control room with the master key! You destroy Zuul's work, and his evil plans. Contratulations! !!!");
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("Your entire town was abducted by a scientist known as 'Zuul'.");
        System.out.println("You have followed Zuul and his team back to his secred laboratory");
        System.out.println("in the jungle. You know there is little hope for you friends and family,");
        System.out.println("but you know that if you can get to the control room in this research facility,");
        System.out.println("with the key to the master files, you can thwart Zuul's evil plans and save");
        System.out.println("other towns from going through what you had to endure.");
        System.out.println("Your journey has been long and tough, and you are already feeling its effects on your health \n \n");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.GET){
            pickUpItem(command.getSecondWord());
        }
        else if (commandWord == CommandWord.DROP){
            dropItem(command.getSecondWord());
        }
        else if (commandWord == CommandWord.SHOW){
            System.out.println(player1.showInventory(maxWeight));
        }
        else if (commandWord == CommandWord.ATTACK){
            attackMonster(command.getSecondWord());
        }
        else if (commandWord == CommandWord.POTION){
            player1.drinkPotion();
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            player1.addMove();
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Checks if user has reached the move limit.
     * @return True if user has reached limit, otherwise false.
     */
    private boolean checkMoves()
    {
        if(player1.getMoves() == maxMoves){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Try to pick up an item. Will print a message if the item cannot be picked up.
     * @param itemName The name of the item to try to pick up.
     */
    private void pickUpItem(String itemName)
    {
        Item item = currentRoom.findItem(itemName);
        int itemWeight = currentRoom.findItem(itemName).getWeight();
        if(player1.canPickUp(itemWeight, maxWeight)){
            player1.addItem(item);
            currentRoom.removeItem(itemName);
            player1.addWeight(item.getWeight());
            System.out.println(itemName + " picked up!");
        }
        else{
            System.out.println("Item too heavy!");
        }
    }
    
            /**
     * Drops the specified item from inventory. Checks if item is in inventory,
     * and if so adds the item to the current room, then removes it from the inventory.
     * @param itemName Name of item to drop.
     */
    private void dropItem(String itemName)
    {
        Item item = player1.findItem(itemName);
        if(player1.checkInventory(itemName)){
            for(int i = 0; i < player1.getInventory().size(); i++){
                currentRoom.addItem(item);
                if(player1.getInventory().get(i) == item){
                    player1.removeItem(i);
                }
            }
            player1.removeWeight(item.getWeight());
            System.out.println(itemName + " dropped!");
        }
        else{
            System.out.println("Item not in inventory!");
        }
    }
    
    /**
     * Sets player to attack the specified monster. Player automatically uses
     * heaviest item in their inventory, for maximum damage.
     */
    private void attackMonster(String monstName)
    {
        Monster m = currentRoom.findMonster(monstName);
        double playerAttack = player1.getAttackStr();
        double monstAttack = m.getStrength();
        if(m.getHealth() < 1){
            System.out.println("Monster is dead!");
        }
        else{
            if(playerAttack >= monstAttack){
                m.getHit(playerAttack);
                System.out.println("You hit monster for " + playerAttack + " damage!");
                System.out.println(m.getDescription());
                
                        
                if(m.getHealth() < 1){
                    System.out.println(m.getName() + " is dead!");
                    if(m.getName().equals("mutant")){
                        System.out.println("The mutant dropped a potion, and a mysterious looking key!");
                        Item pot = new Potion();
                        currentRoom.addItem(pot);
                        Item key = new Item("key", 1, 0, 0);
                        currentRoom.addItem(key);
                    }
                }
            }
            else{
                player1.getHit(monstAttack);
                System.out.println("Monster hit you for " + monstAttack + " damage!");
                System.out.println("Your health: " + player1.getHealth());
            }
        }
    }
    
    /**
     * Checks if the player is still alive.
     * @return False if player is dead, else true.
     */
    private boolean checkAlive()
    {
        if(player1.getHealth() < 1){
            return false;
        }
        else{
            return true;
        }
    }
    
    /**
     * Checks if player has reached the goal room.
     * @return True if in the goal room, with the key, else false.
     */
    private boolean reachedGoal()
    {
        if((currentRoom == goalRoom) && (player1.checkInventory("key"))){
            return true;
        }
        else{
            return false;
        }
    }
}
