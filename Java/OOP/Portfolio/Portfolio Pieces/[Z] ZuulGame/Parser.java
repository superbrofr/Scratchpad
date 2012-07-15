/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two-word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Charlotte Pierce
 * @version 6/11/2010
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
    }

    /**
     * Creates and returns a command, as if the command words 'word1' and 'word2' were typed into the system
     * @return The next command from the user.
     */
    public Command getCommand(String aWord1, String aWord2) 
    {
        String word1 = aWord1;
        String word2 = aWord2;
        return new Command(commands.getCommandWord(word1), word2);
    }

    /**
     * Return out a list of valid command words.
     * @return A list of all valid command words.
     */
    public String showCommands()
    {
        return commands.showAll();
    }
}
