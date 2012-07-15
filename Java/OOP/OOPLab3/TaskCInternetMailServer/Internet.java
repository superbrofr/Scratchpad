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
    
    public void addServer(MailServer addserv)
    {
        servers.add(addserv);
    }
    
    public void sendMail(MailItem email, String server)
    {
        MailServer sendTo = findServer(server);
        if(sendTo != null){
            sendTo.post(email);
        }
        else{
            System.out.println("Server does not exist!");
        }
    }
    
    //Finds the server and sends mail onwards
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
