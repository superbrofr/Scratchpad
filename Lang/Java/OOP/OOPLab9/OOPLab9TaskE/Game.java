import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

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
    private RoomRandomiser randomiser;
    private JFrame frame;
    private JTextArea mainText; // the place to show all text to the user
    private Command nextCommand;
    
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
        setUpGUI();
    }

    /**
     * Create all the rooms and link their exits together.
     * Create items and monsters and place them in appropriate rooms.
     */
    private void initialiseGame()
    {
        randomiser = new RoomRandomiser();
        // initialise rooms - give them descriptions and add them to the randomiser
        Room outside = new Room("\n \n \n You are outside a huge lab facility. Inpenetrable jungle is all around, \n with the only exits being the trailing path you can from, and the entry to \n the facility your options. It looks like Zuul knew you were coming, and has \n abandoned the building. \n However, you know he will have left nasties within.", randomiser);
        TransporterRoom foyer = new TransporterRoom("\n \n \n You are in what appears to be a foyer. It looks like \n a pretty standard room. The exits look strange, almost as if they are portals \n somwhere. You think it's a trap, but you have no options. You know that \n trying to exit the room could leave you anywhere.", randomiser);
        Room waitingRoom1 = new Room("\n \n \n You walk slowly into what you decide must be a waiting room. Apart \n from the chairs and tables, you see nothing out of the ordinary.", randomiser);
        Room teaRoom = new Room("\n \n \n Success! You have found the tea room! Perhaps now you can get something \n to refresh you after your long journey...", randomiser);
        Room entertainmentRoom = new Room("\n \n \n Seems you have found a room where Zuul's scientists allow their \n test subjects to rest a while.", randomiser);
        Room waitingRoom2 = new Room("\n \n \n This room looks exactly the same as the waiting room you found eariler \n in the adventure. *Exactly* the same. Creepy...", randomiser);
        Room hiddenRoom = new Room("\n \n \n This does not look like a room people are supposed to find...", randomiser);
        Room controlRoom = new Room("\n \n \n This looks like the room you have been looking for.", randomiser);
        Room testRoom1 = new Room("\n \n \n Scientific things all around, looks like some sort of testing takes place \n here, a smear that looks suspiciously like blood is on the wall.", randomiser);
        Room testRoom2 = new Room("\n \n \n Scientific things all around, a testing room you think.", randomiser);
        Room storage1 = new Room("\n \n \n This room looks less organised than the others. It's filled with cleaning products.", randomiser);
        Room storage2 = new Room("\n \n \n This room seems out of character with the others in the facility, there's no \n scientific equipment to be seen, no hidden monitors, just endless \n rags and cleaning products.", randomiser);
        Room observationRoom1 = new Room("\n \n \n This room is filled with monitors looking into four smaller rooms. \n Possibly an observation room?", randomiser);
        Room observationRoom2 = new Room("\n \n \n You see lots of equipment in this room. Through the glass you can see \n a couple of smaller rooms. It looks like they are used for some \n form of testing.", randomiser);
        Room lab2A = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '2A'.", randomiser);
        Room lab2B = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '2B'.", randomiser);
        Room lab1A = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '1A'.", randomiser);
        Room lab1B = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '1B'.", randomiser);
        Room lab1C = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '1C'.", randomiser);
        Room lab1D = new Room("\n \n \n A small room, with a few pieces of equipment and a single chair. \n You see a big sign on the roof which reads '1D'.", randomiser);
        
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
        lab1D.setExit("north", lab1C);
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
     *  Main play routine. Executes one 'move' of play.
     */
    public void play() 
    {            
        boolean finished = false;
        if (!finished) {
            if(nextCommand != null){
                finished = processCommand(nextCommand);
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
        }
        if(finished){
            if(checkMoves()){
                mainText.setText("!!! Move limit reached. !!!");
            }
            else if(!checkAlive()){
                mainText.setText("!!! You died. !!!");
            }
            else if(reachedGoal()){
                mainText.setText("\n!!! You reached the control room with the master key!\n You destroy Zuul's work, and his evil plans. \nContratulations! !!!\n");
            }
            else{
                mainText.setText("");
            }
            mainText.append("\n Thank you for playing.  Goodbye. \n You may now click Exit to quit.");
        }
    }

    /**
     * Return a string of the opening message for the player.
     * @return The opening message for the player
     */
    private String printWelcome()
    {
        String returnString = "";
        returnString += "Welcome to the World of Zuul!";
        returnString += "\n Your entire town was abducted by a scientist known as 'Zuul'.";
        returnString += "\n You have followed Zuul and his team back to his secret laboratory";
        returnString += "\n in the jungle. You know there is little hope for you friends and family,";
        returnString += "\n but you know that if you can get to the control room in this research facility,";
        returnString += "\n with the key to the master files, you can thwart Zuul's evil plans and save";
        returnString += "\n other towns from going through what you had to endure.";
        returnString += "\n Your journey has been long and tough, and you are already feeling its effects \n on your health... \n \n";
        returnString += "\n Click the '" + CommandWord.HELP + "' button if you need help.";
        returnString += "\n" + currentRoom.getLongDescription() + "\n\n";
        return returnString;
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
            mainText.append("I don't know what you mean...");
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
            getItem();
        }
        else if (commandWord == CommandWord.DROP){
            getDropItem();
        }
        else if (commandWord == CommandWord.SHOW){
            showInventory();
        }
        else if (commandWord == CommandWord.ATTACK){
            getAttackMonster();
        }
        else if (commandWord == CommandWord.POTION){
            drinkPotion();
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
        mainText.setText("You are lost. You are alone. You wander");
        mainText.append("\n around at the university. \n");
        mainText.append("\n Your command words are: \n");
        mainText.append(parser.showCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            mainText.append("\n There is no door!");
        }
        else {
            currentRoom = nextRoom;
            player1.addMove();
            mainText.setText(currentRoom.getLongDescription());
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
            mainText.append("Quit what?");
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
        try
        {
            Item item = currentRoom.findItem(itemName);
            int itemWeight = currentRoom.findItem(itemName).getWeight();
            if(player1.canPickUp(itemWeight, maxWeight)){
                player1.addItem(item);
                currentRoom.removeItem(itemName);
                player1.addWeight(item.getWeight());
                mainText.append(itemName + " picked up!   ");
            }
            else{
                mainText.append("Item too heavy!");
            }
        }
        catch(NullPointerException npe)
        {
            mainText.append("No item selected!");
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
        try
        {   if(item != null){
                for(int i = 0; i < player1.getInventory().size(); i++){
                    currentRoom.addItem(item);
                    if(player1.getInventory().get(i) == item){
                        player1.removeItem(i);
                    }
                }
                player1.removeWeight(item.getWeight());
                mainText.append(itemName + " dropped!");
            }
            else{
                mainText.append("Item not in inventory!  ");
            }
        }
        catch(NullPointerException npe)
        {
            mainText.append("No item selected!");
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
        try
        {   
            if(m.getHealth() < 1){
            mainText.append("Monster is dead!");
            }
            else{
                if(playerAttack >= monstAttack){
                    m.getHit(playerAttack);
                    mainText.setText("You hit monster for " + playerAttack + " damage! \n");
                    mainText.append(m.getDescription());
                    
                    
                    if(m.getHealth() < 1){
                        mainText.append(m.getName() + " is dead!");
                        if(m.getName().equals("mutant")){
                            mainText.append("\n The mutant dropped a potion, \n and a mysterious looking key!");
                            Item pot = new Potion();
                            currentRoom.addItem(pot);
                            Item key = new Item("key", 1, 0, 0);
                            currentRoom.addItem(key);
                        }
                    }
                }
                else{
                    player1.getHit(monstAttack);
                    mainText.setText("Monster hit you for " + monstAttack + " damage!");
                    mainText.append("Your health: " + player1.getHealth());
                }
            }
        }
        catch(NullPointerException npe)
        {
            mainText.append("No monster selected!");
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
    
    /**
     * The player drinks a potion.
     */
    private void drinkPotion()
    {
        mainText.append(player1.drinkPotion());
    }
    
    /**
     * Shows the inventory of the player.
     */
    private void showInventory()
    {
        mainText.setText(player1.showInventory(maxWeight));
    }
    
    /**
     * Starts the process for a user to pick up an item in-game.
     * Opens a dialog and asks user which item, then filters that name through  to
     * the regular pickUpItem() method.
     * @param frame The frame to which to associate the dialog.
     */
    private void getItem()
    {
        final JDialog dialog = new JDialog(frame, "Choose Item", true);
        JPanel dialogPanel = new JPanel();
        
        dialog.setLayout(new BorderLayout());
        dialogPanel.setLayout(new GridLayout(1, 2));
        
        JLabel itemLabel = new JLabel("Name of item: ");
        dialogPanel.add(itemLabel);
        
        final JTextArea itemInput = new JTextArea(1, 10); //needs to be final so it can be accessed by the listener
        dialogPanel.add(itemInput);
        
        JButton submitButton = new JButton("Ok");
        submitButton.addActionListener(new ActionListener(){
                                            public void actionPerformed(ActionEvent e){
                                                pickUpItem(itemInput.getText());
                                                dialog.setVisible(false);
                                            }
                                        });
        dialog.add(submitButton, BorderLayout.SOUTH);
        
        dialog.add(dialogPanel, BorderLayout.CENTER);
        dialog.setSize(new Dimension(200, 80));
        
        dialog.setVisible(true);
    }
    
    /**
     * Asks the user which item they would like to drop, and then drops it from their inventory,
     * and adds it to the room the player is currently in.
     */
    private void getDropItem()
    {
        final JDialog dialog = new JDialog(frame, "Choose Item", true);
        JPanel dialogPanel = new JPanel();
        
        dialog.setLayout(new BorderLayout());
        dialogPanel.setLayout(new GridLayout(1, 2));
        
        JLabel itemLabel = new JLabel("Name of item: ");
        dialogPanel.add(itemLabel);
        
        final JTextArea itemInput = new JTextArea(1, 10); //needs to be final so it can be accessed by the listener
        dialogPanel.add(itemInput);
        
        JButton submitButton = new JButton("Ok");
        submitButton.addActionListener(new ActionListener(){
                                            public void actionPerformed(ActionEvent e){
                                                dropItem(itemInput.getText());
                                                dialog.setVisible(false);
                                            }
                                        });
        dialog.add(submitButton, BorderLayout.SOUTH);
        
        dialog.add(dialogPanel, BorderLayout.CENTER);
        dialog.setSize(new Dimension(200, 80));
        
        dialog.setVisible(true);
    }

    /**
     * Asks the user which monster they would like to attack, and then proceeds with the attack process.
     */
    private void getAttackMonster()
    {
        final JDialog dialog = new JDialog(frame, "Choose Monster", true);
        JPanel dialogPanel = new JPanel();
        
        dialog.setLayout(new BorderLayout());
        dialogPanel.setLayout(new GridLayout(1, 2));
        
        JLabel itemLabel = new JLabel("Name of monster: ");
        dialogPanel.add(itemLabel);
        
        final JTextArea monsterInput = new JTextArea(1, 10); //needs to be final so it can be accessed by the listener
        dialogPanel.add(monsterInput);
        
        JButton submitButton = new JButton("Ok");
        submitButton.addActionListener(new ActionListener(){
                                            public void actionPerformed(ActionEvent e){
                                                attackMonster(monsterInput.getText());
                                                dialog.setVisible(false);
                                            }
                                        });
        dialog.add(submitButton, BorderLayout.SOUTH);
        
        dialog.add(dialogPanel, BorderLayout.CENTER);
        dialog.setSize(new Dimension(250, 80));
        
        dialog.setVisible(true);
    }
    
    /**
     * Creates the GUI for the Zuul game.
     */
    private void setUpGUI()
    {
        mainText = new JTextArea(printWelcome());
        mainText.setEditable(false);
        frame = new JFrame("World of Zuul");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout()); // create the border layout
        contentPane.add(mainText, BorderLayout.CENTER); // add the main text label to the center area
        
        //create grid for the 6 buttons in the south of the screen
        JPanel southButtonGrid = new JPanel();
        southButtonGrid.setLayout(new GridLayout(2, 3));
        // create buttons for the south button grid
        JButton showInventory = new JButton("Show Inventory");
        JButton moveUp = new JButton("Up");
        JButton getItem = new JButton("Get...");
        JButton moveLeft = new JButton("Left");
        JButton moveDown = new JButton("Down");
        JButton moveRight = new JButton("Right");
        //add event listeners for each button in the south grid
        showInventory.addActionListener(new ActionListener(){
                                            public void actionPerformed(ActionEvent e){
                                                nextCommand = parser.getCommand("show", null);
                                                play();
                                            }
                                        });
        moveUp.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("go", "north");
                                        play();
                                    }
                                 });
        getItem.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("get", null);
                                        play();
                                    }
                                 });
        moveLeft.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("go", "west");
                                        play();
                                    }
                                 });
        moveDown.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("go", "south");
                                        play();
                                    }
                                 });
        moveRight.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("go", "east");
                                        play();
                                    }
                                 });
        //add buttons to the grid layout
        southButtonGrid.add(showInventory);
        southButtonGrid.add(moveUp);
        southButtonGrid.add(getItem);
        southButtonGrid.add(moveLeft);
        southButtonGrid.add(moveDown);
        southButtonGrid.add(moveRight);
        //add the gridpanel to the contentpane
        contentPane.add(southButtonGrid, BorderLayout.SOUTH);
        
        //create grid for 5 buttons on left-hand-side of the screen
        JPanel leftButtonGrid = new JPanel();
        leftButtonGrid.setLayout(new GridLayout(5, 1));
        //create buttons for the leftbuttongrid
        JButton attack = new JButton("Attack");
        JButton potion = new JButton("Drink Potion");
        JButton dropItem = new JButton("Drop...");
        JButton help = new JButton("Help");
        JButton quit = new JButton("Quit");
        //add event listeners for each button in the left grid
        attack.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("attack", null);
                                        play();
                                    }
                                 });
        potion.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("potion", null);
                                        play();
                                    }
                                 });
        dropItem.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("drop", null);
                                        play();
                                    }
                                 });
        help.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        nextCommand = parser.getCommand("help", null);
                                        play();
                                    }
                               });
        quit.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        System.exit(0);
                                    }
                               });
        //add buttons to the grid layout
        leftButtonGrid.add(attack);
        leftButtonGrid.add(potion);
        leftButtonGrid.add(dropItem);
        leftButtonGrid.add(help);
        leftButtonGrid.add(quit);
        //add the gridpanel to the contentpane - first add it to a container panel with flow layout
        //so that it does not make the buttons super tall to fill up the entire height of the window
        JPanel leftGridContainer = new JPanel();
        leftGridContainer.setLayout(new FlowLayout());
        leftGridContainer.add(leftButtonGrid);
        contentPane.add(leftGridContainer, BorderLayout.WEST);
        
        frame.pack();
        frame.setVisible(true);
    }
}