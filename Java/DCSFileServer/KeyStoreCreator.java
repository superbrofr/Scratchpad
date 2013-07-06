import java.security.*;
import java.io.*;
import javax.crypto.*;

class KeyStoreCreator
{
  public static void main(String args[]) throws Exception
  {
    KeyStore ks1 = KeyStore.getInstance("JCEKS");
    
    //initialise keystore
    ks1.load(null, null);
    
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    //get keystorename and password
    System.out.print("Keystore name (must end with .keystore): ");
    String keyStoreName = inFromUser.readLine();
    System.out.print("Keystore password: ");
    char[] password = inFromUser.readLine().toCharArray();
    
    //create output stream for the keystored
    FileOutputStream outToKS = new FileOutputStream(keyStoreName);
    //create the keystore
    ks1.store(outToKS, password);
    
    //create a SecretKey and store in the key store
    //load the keystore
    KeyStore ks2 = KeyStore.getInstance("JCEKS");
    FileInputStream inFromFile = new FileInputStream(keyStoreName);
    ks2.load(inFromFile, password);
    
    FileOutputStream outToKS2 = new FileOutputStream(keyStoreName);
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    Key key = keyGen.generateKey();
    ks2.setKeyEntry("Key1", key, password, null);
    //rewrite the new keystore with the new entry
    ks2.store(outToKS2, password);
  }
}