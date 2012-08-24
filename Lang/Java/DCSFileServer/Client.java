import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
* A client object. The client can communicate with the server, and request files.
* The client software responds to the following commands: connect, login, get, list, help, disconnect, exit.
* @author Charlotte Pierce [7182139] and Callum Stewart [7182988]
* @version 20/5/2011
*/
public class Client
{
  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;

  private DataOutputStream outToServer;
  private BufferedInputStream inFromServer;
  private BufferedReader inFromUser;
  Socket clientSocket;
  private boolean loggedIn;
  private String serverID;
  private String username;
  private String password;
  
  private PrivateKey privateKey;
  private PublicKey publicKey;
  
  /**
  * Creates a client object, generating a key pair for the client to use in encryption.
  */
  public Client()
  {
    try
    {
      loggedIn = false;
      inFromUser = new BufferedReader(new InputStreamReader(System.in));
      //generate keys
      KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
      privateKey = keyPair.getPrivate();
      publicKey = keyPair.getPublic();
    }
    catch(NoSuchAlgorithmException e)
    {
      System.out.println("Invalid algorithm used.");
    }
  }

  /**
  * Creates and runs a new client.
  * @param args Any command line arguments passed to the software.
  */
  public static void main(String[] args) throws Exception
  {
    new Client().run();
  }
  
  /**
  * Runs the client. Initially displays the help dialog showing the possible commands.
  * Accepts and runs commands entered by the user until "exit" is run.
  * @throws IOException Error reading the user's entered command.
  */
  private void run() throws IOException
  {
    displayHelp();
    String[] userCommand;
    do{
      System.out.print("Enter command: ");
      userCommand = inFromUser.readLine().trim().split(" ");
      if(userCommand.length >= 1){
        if(userCommand[0].equals("connect")){
          if(userCommand.length >= 2){
            serverID = userCommand[1];
            connect(serverID);
          }
        }
        else if(userCommand[0].equals("list")){
          listFiles();
        }
        else if(userCommand[0].equals("get")){
          if(userCommand.length >= 2){
            getFile(userCommand[1]);
          }
        }
        else if(userCommand[0].equals("disconnect")){
          disconnect();
        }
        else if(userCommand[0].equals("help")){
          displayHelp();
        }
        else if(userCommand[0].equals("login")){
          startLogin();
        }
        else if(!(userCommand[0].equals("exit"))){
          System.out.println("Invalid command. Please type <help> for a lits of commands");
        }
      }
    }while(!( userCommand[0].equals("exit")));
    exit();
  }
  
  /**
  * Starts the login process, getting the users' desired username and password.
  * Login can only be executed if a connection has been made to the server.
  * Calls login() automatically once details have been found.
  * @throws IOException Error reading users' entered details.
  */
  private void startLogin() throws IOException
  {
    if(clientSocket == null || clientSocket.isClosed()){
      System.out.println("You must connect before you can log in.");
    }
    else{
      System.out.print("Username: ");
      username = inFromUser.readLine();
      Console console = System.console();
      char[] passwordArr = console.readPassword("Password: ");
      password = new String(passwordArr);
      login(username, password);
    }
  }

  /**
  * Attempts to log the user in. Sends the details provided to the server, and receives confirmation.
  * @param aUsername The username of the client.
  * @param aPassword Password for the user.
  * @throws IOException Error reading from server.
  */
  public void login(String aUsername, String aPassword) throws IOException
  {
		//make sure instance variables are set - mostly for the sake of the test harness
		if(username == null){
			username = aUsername;
		}
		if(password == null){
			password = aPassword;
		}
		
    outToServer.writeBytes("login \n");
    outToServer.writeBytes(username + '\n');
    outToServer.writeBytes(password + '\n');
    String logInConfirmation = readFromServer();
    if(logInConfirmation.equals("yes")){
      loggedIn = true;
    }
    else{
      System.out.println("Login failed.");
    }
  }
  
  /**
  * Retrieves and prints a list of files contained on the server's end.
  * @throws IOException Error reading from server.
  */
  private void listFiles() throws IOException
  {
    if(loggedIn){
        outToServer.writeBytes("list \n");
        String fileList = readFromServer();
        System.out.println(fileList);
    }
    else{
      System.out.println("You must log in first.");
    }
  }
  
  /**
  * Gets the specified file from the server. Sends the transfer message to the server, and sends the server public key with which to encrypt
	* the session key used for the transfer.
  * @param aFile The name or path of the file being requested.
  */
  public void getFile(String aFile) throws IOException
  {
    if(loggedIn){
      try
      {
        //Specify file to transfer - send transfer message to server
        outToServer.writeBytes("get " + aFile + "\n");
    
        //Get filename to save to
        System.out.print("Save as: ");
        String newFileName = inFromUser.readLine();
        
        //send public key to server
        ObjectOutputStream publicOut = new ObjectOutputStream(outToServer);
        publicOut.writeObject(publicKey);
        publicOut.flush();
				
				//get key string from server and decrypt with RSA (asymmetric)
				Cipher keyCipher = Cipher.getInstance("RSA");
				keyCipher.init(Cipher.DECRYPT_MODE, privateKey);
				ObjectInputStream keyStringIn = new ObjectInputStream(inFromServer);
				SealedObject sealedKeyString = (SealedObject)keyStringIn.readObject();
				String keyString = (String)sealedKeyString.getObject(keyCipher);

				//generate key spec from keyString
				SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(), "DES");
				
				//set up decryption
				Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, keySpec);
				CipherInputStream cipherIn = new CipherInputStream(inFromServer, cipher);
				
				//Open and write to file
				byte[] fileBuffer = new byte[BUFFER_SIZE];
				FileOutputStream fileWriter = new FileOutputStream(newFileName);
				int bytesRead;
				while((bytesRead = cipherIn.read(fileBuffer)) != EOF){
					fileWriter.write(fileBuffer, 0, bytesRead);
				}
				fileWriter.flush();
				fileWriter.close();
				disconnect();
				connect(serverID);
				login(username, password);
      }
			catch(IOException e)
			{
				System.out.println("Error writing to server.");
			}
			catch(NullPointerException npe)
			{
				System.out.println("New filename not specified!");
				return;
			}
			catch(NoSuchAlgorithmException nsae)
			{
				System.out.println("Invalid algorithm entered.");
			}
			catch(InvalidKeyException ike)
			{
				System.out.println("Invalid key - error creating decryptor.");
			}
			catch(NoSuchPaddingException nspe)
			{
				System.out.println("Invalid padding entered.");
			}
			catch(ClassNotFoundException nsce)
			{
				System.out.println("Invalid class used.");
			}
			catch(IllegalBlockSizeException ibse)
			{
				System.out.println("Invalid block size used in decryption.");
			}
			catch(BadPaddingException bpe)
			{
				System.out.println("Bad padding used in decryption.");
			}
    }
    else{
      System.out.println("You're not logged in yet.");
    }
  }
  
  /**
  * Disconnects the client from the server.
  * @throws IOException Error disconnection from server.
  */
  public void disconnect() throws IOException
  {
    if(clientSocket == null || clientSocket.isClosed()){
      System.out.println("You must connect first before you can disconnect.");
    }
    else{
      clientSocket.close();
    }
    loggedIn = false;
  }
  
  /**
  * Attempts to connect the client to the specified server.
  * @param aServer The IP address or hostname of the server to connect to.
  * @throws IOException Error creating output streams. Client will be unusable.
  */
  public void connect(String aServer) throws IOException
  {
    try
    {
      if(clientSocket == null || clientSocket.isClosed()){
        clientSocket = new Socket(aServer, 2011);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedInputStream(clientSocket.getInputStream());
      }
      else{
        System.out.println("You're aLREAdy connected you numpty :D");
      }
    }
    catch(UnknownHostException uhe)
    {
      System.out.println("Invalid server address.");
    }
  }
  
  /**
  * Closes the client software.
  * Closes the socket if not already done.
  * @throws IOException Error disconnecting from server.
  */
  private void exit() throws IOException
  {
    if(clientSocket != null){
      if(!clientSocket.isClosed()){
        clientSocket.close();
      }
    }
    System.out.println("Goodbye...");
    System.exit(1);
  }
  
  //Reads and returns message from server - message is equal to BUFFER_SIZE
  /**
  * Reads from the server and returns the message as a string. Message can be up to the length of BUFFER_SIZE.
  * @return A message received from the server.
  * @throws IOException Error reading message from the server.
  */
  private String readFromServer() throws IOException
  {
    byte[] temp = new byte[BUFFER_SIZE];
    inFromServer.read(temp);
    return (new String(temp).trim());
  }
  
  /**
  * Displays the help dialog. Includes a list of commands supported by the client software.
  */
  private void displayHelp()
  {
    System.out.println("Commands: \n");
    System.out.println("connect [IP address | Hostname]: Connects to server");
    System.out.println("login: Starts the login process");
    System.out.println("list: Lists files in current server directory");
    System.out.println("get [Filename]: Requests file");
    System.out.println("disconnect: Terminates the current connection");
    System.out.println("help: Displays this helpfile");
    System.out.println("exit: Exits program");
    System.out.println();
  }
}