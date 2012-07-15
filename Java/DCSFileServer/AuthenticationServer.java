import java.util.HashMap;
import java.net.*;
import java.io.*;

/**
* An authentication server. Runs on the same host as the file server.
* Receives login details through the file server using a UDP connection, and validates them against the
* stored username/password combinations.
* Creates a new thread to deal with each packet of details to avoid missing any.
* @author Charlotte Pierce [7182139] and Callum Stewart [7182988]
* @version 20/5/2011
*/
class AuthenticationServer implements Runnable
{
  private static final int LISTENING_PORT = 3011;
  private static final int BUFFER_SIZE = 1024;
  
  private static DatagramSocket serverSocket;
  private HashMap<String, String> loginDetails; // <username, password>
  private DatagramPacket receivedPacket;
  
  /**
  * Creates a new authentication server, given the received packet.
  * @param aReceivedPacket The packet received by the server.
  */
  public AuthenticationServer(DatagramPacket aReceivedPacket)
  {
    receivedPacket = aReceivedPacket;
    //initialise login details
    loginDetails = new HashMap<String, String>();
    String username = "user";
    String password = "pass";
    loginDetails.put(username, password);
  }
  
  /**
  * Starts the server listening. Creates a new thread to deal with each datagram packet received.
  * @param args Any command line arguments passed to the software.
  * @throws IOException Error reading from listening socket.
  * @throws SocketException Error creating the socket.
  */
  public static void main(String[] args) throws IOException, SocketException
  {
    serverSocket = new DatagramSocket(LISTENING_PORT);
    System.out.println("Listening on port " + LISTENING_PORT);
    while(true){
      byte[] tempBuffer = new byte[BUFFER_SIZE];
      DatagramPacket newPacket = new DatagramPacket(tempBuffer, BUFFER_SIZE);
      serverSocket.receive(newPacket);
      new Thread(new AuthenticationServer(newPacket)).start();
    }
  }
  
  /**
  * Runs the authentication server; handles one packet of login details, authenticating them and returning the appropriate
  * response to the file server.
  */
  public void run()
  {
    //get return information
    InetAddress returnIP = receivedPacket.getAddress();
    int returnPort = receivedPacket.getPort();
    byte[] receivedData = receivedPacket.getData();
    byte[] returnMessage = new byte[BUFFER_SIZE];
    //test the login details
    String[] givenLoginDetails = new String(receivedData).trim().split(" ");
    if(givenLoginDetails.length != 2){
      returnMessage = new String("no").getBytes(); //invalid details, send 'no'
    }
    else if(!givenLoginDetails[1].equals(loginDetails.get(givenLoginDetails[0]))){
      returnMessage = new String("no").getBytes(); //invalid details, send 'no'
    }
    else if(givenLoginDetails[1].equals(loginDetails.get(givenLoginDetails[0]))){
      returnMessage = new String("yes").getBytes(); //valid details, send 'yes'
    }
    //send return message
    try{
      DatagramPacket returnPacket = new DatagramPacket(returnMessage, returnMessage.length, returnIP, returnPort);
      serverSocket.send(returnPacket);
    }
    catch(IOException e)
    {
      System.out.println("Error sending return message.");
    }
  }
}