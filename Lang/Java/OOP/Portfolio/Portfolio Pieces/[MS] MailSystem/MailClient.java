/**
 * A class to model a simple email client. The client is run by a
 * particular user, and sends and retrieves mail via a particular server.
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;
    //Name of the client
    private String name;
    //e-mail of the user
    private String email;

    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user, String name)
    {
        this.server = server;
        this.user = user;
        this.name = name;
        email = user + "@" + server.getName();
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        return server.getNextMailItem(user);
    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public boolean printNextMailItem()
    {
        try
        {
            MailItem item = server.getNextMailItem(email);
            System.out.println("From: " + item.getFrom());
            System.out.println("To: " + item.getTo());
            System.out.println("Message: " + item.getMessage());
            return true;
        }
        catch(NullPointerException npe)
        {
            System.out.println("No new mail.");
            return false;
        }
    }

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient. [their email address]
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to, String message)
    {
        MailItem item = new MailItem(user, to, message);
        server.post(item);
    }
}
