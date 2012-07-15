import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Random;

/**
* File server. Clients can connect to this server and request transmission of files.
* Connection to a client is maintained using TCP until the client specifies a disconnection should occur.
* Creates a new thread for each client which connects.
* @author Charlotte Pierce [7182139] and Callum Stewart [7182988]
* @version 20/5/2011
*/
public class FileServer implements Runnable
{
  private static final int LISTENING_PORT = 2011;
  private static final int AUTHENTICATION_PORT = 3011;
  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;
  
  private Socket connectedSocket;
  private BufferedReader inFromClient;
  private DataOutputStream outToClient;
  
  private PrivateKey privateKey;
  private PublicKey publicKey;

  /**
  * Creates a file server.
  * Uses aConnectedSocket to create input/output stream readers to communicate with a client.
  * @param aConnectedSocket Socket through which to communicated with a client.
  */
  public FileServer(Socket aConnectedSocket) throws IOException, NoSuchAlgorithmException
  {
    connectedSocket = aConnectedSocket;
    //Set up input/output streams
    inFromClient = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
    outToClient = new DataOutputStream(connectedSocket.getOutputStream());
    //generate keys
    KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
    privateKey = keyPair.getPrivate();
    publicKey = keyPair.getPublic();
  }

  /**
  * Starts the server listening.
  * Creates a new server thread for each connecting client.
  * @param args Any command line arguments passed to the software.
  */
  public static void main(String[] args) throws Exception
  {
    ServerSocket listenSocket = new ServerSocket(LISTENING_PORT);
    System.out.println("Super mega-awesome file server of awesome. Awaiting patrons...");
    System.out.println("Listening on port " + LISTENING_PORT);

    while(true){
      new Thread(new FileServer(listenSocket.accept())).start();
    }
  }
  
  /**
  * Runs the server. Waits for client to send a message, and then executes the command matching that message.
  */
  public void run()
  {
    try
    {
      String[] clientMessage;
      //loop for commands
      do
      {
        clientMessage = inFromClient.readLine().trim().split(" ");
        if(clientMessage.length >= 1){
          if(clientMessage[0].equals("get")){
            if(clientMessage.length >= 2){
              sendFile(clientMessage[1]);
            }
          }
          else if(clientMessage[0].equals("list")){
            listFiles();
          }
          else if(clientMessage[0].equals("disconnect")){
            disconnect();
          }
          else if(clientMessage[0].equals("login")){
            login();
          }
        }
      }while(!clientMessage.equals("disconnect"));
    }
    catch(IOException e)
    {
      //System.out.println("Error reading from client.");
    }
    catch(NullPointerException npe)
    {
      disconnect();
    }
  }
  
  /**
  * Attempts to log a client in using the authentication server.
  * Authentication server needs to run on the same host as the file server.
  * @throws IOException Error reading from socket.
  */
  private void login() throws IOException
  {
    //set up required variables
    DatagramSocket clientSocket = new DatagramSocket();
    InetAddress authServerIP = InetAddress.getLocalHost(); //because authentication server runs on same host as file server
    byte[] dataToSend;
    byte[] receivedData = new byte[BUFFER_SIZE];

    //get username and password
    String userName = inFromClient.readLine().trim(); //get username
    String password = inFromClient.readLine().trim(); //get password
    dataToSend =  new String(userName + " " + password).getBytes();
		
    //send the username and password for processing by authentication server
    DatagramPacket packetToSend = new DatagramPacket(dataToSend, dataToSend.length, authServerIP, AUTHENTICATION_PORT);
    clientSocket.send(packetToSend);

    //receive the response from the authentication server
    DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
    clientSocket.receive(receivedPacket);
    String receivedString = new String(receivedPacket.getData()).trim();
    receivedData = receivedString.getBytes();
    if(receivedString.equals("yes")){
      outToClient.writeBytes(receivedString); //successful login
    }
    else{
      outToClient.writeBytes("no"); //unsuccessful login
    }
  }
  
  /**
  * Disconnect server from client.
  */
  private void disconnect()
  {
    try
    {
      connectedSocket.close();
    }
    catch (IOException e)
    {
      System.out.println("Error closing socket.");
    }
  }
  
  /**
  * Sends the specified file to the client.
  * File must exist or client and server threads will hang indefinitely.
  * Generates a session key to encrypt the file over transfer; session key is encrypted using
  * the client's public (asymmetric) key.
  * @param aFile The name or path of the file to send.
  * @throws IOException Error reading from socket.
  */
  private void sendFile(String aFile) throws IOException
  {
    try
    {
      //get client public key
      ObjectInputStream clientPubIn = new ObjectInputStream(connectedSocket.getInputStream());
      PublicKey clientPublicKey = (PublicKey)clientPubIn.readObject();

      //generate key string and send to client using their public key encrypted with RSA (asymmetric)
      String keyString = generateKeyString();
      Cipher keyCipher = Cipher.getInstance("RSA");
      keyCipher.init(Cipher.ENCRYPT_MODE, clientPublicKey);
      SealedObject sealedKeyString = new SealedObject(keyString, keyCipher);
      ObjectOutputStream testOut = new ObjectOutputStream(outToClient);
      testOut.writeObject(sealedKeyString);
      testOut.flush();

      //generate key spec from keyString
      SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(), "DES");

      //set up encryption
      Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, keySpec);
      CipherOutputStream cipherOut = new CipherOutputStream(outToClient, cipher);

      //send file
      byte[] fileBuffer = new byte[BUFFER_SIZE];
      InputStream fileReader = new BufferedInputStream(new FileInputStream(aFile));
      int bytesRead;
      while((bytesRead = fileReader.read(fileBuffer)) != EOF){
        cipherOut.write(fileBuffer, 0, bytesRead);
      }
      cipherOut.flush();
      cipherOut.close();
      disconnect();
    }
    catch(NoSuchPaddingException nspe)
    {
      System.out.println("No such padding.");
    }
    catch(NoSuchAlgorithmException nsae)
    {
      System.out.println("Invalid algorithm entered");
    }
    catch(ClassNotFoundException cnfe)
    {
      System.out.println("Class not found.");
    }
    catch(InvalidKeyException ike)
    {
      System.out.println("Invalid key used for file encryption.");
    }
    catch(FileNotFoundException fnfe)
    {
      System.out.println("Invalid file entered.");
      return;
    }
    catch(IllegalBlockSizeException ibse)
    {
      System.out.println("Illegal block size used for encryption.");
    }
  }
  
  /**
  * Sends the user a list of the files contained in the same folder as the server.
  */
  private void listFiles() throws IOException
  {
    File folder = new File(".");
    File[] listOfFiles = folder.listFiles();
    for(File f: listOfFiles){
      outToClient.writeBytes(f.getName() + '\n');
    }
  }

  /**
  * Generates a random 8-character string. Eight characters exactly as that is the length required to create
  * a key spec object for DES encryption.
  * @return An 8-character random string.
  */
  private String generateKeyString()
  {
    Random random = new Random();
    String returnString = "";
    for(int i = 0; i < 8; i++){
      returnString += (char)random.nextInt(255); //255 because it is the max integer for 8 bits - and there are 8 bits per char
    }
    return returnString;
  }
}