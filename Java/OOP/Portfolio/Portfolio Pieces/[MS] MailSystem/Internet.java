/**
 * The internet object. The internet object accepts MailItems, searches
 * for the server to which they belong and forwards the MailItem to
 * that server.
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Internet
{
    //Calling server
    private ArrayList<MailServer> servers;
    
    public Internet()
    {
        servers = new ArrayList<MailServer>();
    }
    
    /**
     * Adds a server to the Internet's list of servers
     * @param addserv The server to add.
     * @return Whether the add was successful or not.
     */
    public boolean addServer(MailServer addserv)
    {
        servers.add(addserv);
        return true;
    }
    
    /**
     * Sends a mail item to the specified server.
     * @param email The mail item to send.
     * @param server The name of the server to send it to.
     * @return True if the sending was successful, else false.
     */
    public boolean sendMail(MailItem email, String server)
    {
        try
        {
            MailServer sendTo = findServer(server);
            sendTo.post(email);
            return true;
        }
        catch(NullPointerException npe)
        {
            System.out.println("Server does not exist!");
            return false;
        }
    }
    
    /**
     * Finds the server with the given name in the Internet's server list.
     * @param server The name of the server to find.
     * @return The found server.
     */
    private MailServer findServer(String server)
    {
        Iterator<MailServer> it = servers.iterator();
        while(it.hasNext()){
            MailServer serv = it.next();
            if(serv.getName().equals(server)){
                return serv;
            }
        }
        return null;
    }
}
