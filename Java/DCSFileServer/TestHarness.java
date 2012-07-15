//	Test Harness for File Server

import java.util.*;
import java.io.*;
import java.net.*;

public class TestHarness
{
  private BufferedReader inFromUser;
	private String username;
  private String password;
	private int clientsToTest;
	private String fileToGet;
	private String serverAdd;

	private ArrayList<Client> clientList;
	
	//Constructor
	public TestHarness()
	{
		try
		{
			//set up variables
			inFromUser = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter number of clients to create: ");
			clientsToTest = Integer.parseInt(inFromUser.readLine());
			System.out.print("Enter username to use: ");
			username = inFromUser.readLine();
			System.out.print("Enter password to use: ");
			password = inFromUser.readLine();
			System.out.print("Enter file to retrieve: ");
			fileToGet = inFromUser.readLine();
			System.out.print("Enter hostname or address of server: ");
			serverAdd = inFromUser.readLine();
			
			createClients();
			connectClients(); //connect each client and attempt to get a file
			disconnectClients();
		}
		catch(IOException e)
		{
			System.out.println("Error reading from user.");
		}
	}
	
	public static void main(String args[])
	{
		new TestHarness();
	}
	
	//Creates a number of clients and stores them in an arraylist
	public void createClients()
	{
		System.out.println("Creating clients...");
		clientList = new ArrayList<Client>();
		
		for(int i = 0; i < clientsToTest; i++)
		{
			Client temp = new Client();
			clientList.add(temp);
			System.out.println("Client " + i + " created!");
		}
		
		System.out.println("Done!");
		
	}
	
	//Goes through the arraylist of clients and connects each of them,
	//also requesting a file from the server
	public void connectClients()
	{
		System.out.println("Connecting clients...");
		for(int i = 0; i < clientsToTest; i++)
		{
			try
			{
				clientList.get(i).connect(serverAdd);
				System.out.println("Client " + i + " connected! Logging in...");
				clientList.get(i).login(username, password);
				clientList.get(i).getFile(fileToGet);
				System.out.println(fileToGet + " retrieved.");
			}
			catch(IOException e)
			{
				System.out.println("Connection failed");
			}
		}
	}
	
	//disconnects every client created
	private void disconnectClients()
	{
		System.out.println("Disconnecting clients...");
		for(int i = 0; i < clientsToTest; i++)
		{
			Client temp = new Client();
			clientList.add(temp);
			System.out.println("Client " + i + " disconnected!");
		}
	}
}