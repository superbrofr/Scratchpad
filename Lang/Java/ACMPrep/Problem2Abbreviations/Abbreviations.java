import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

class Abbreviations
{
  ArrayList<String> scnWords;
  HashMap<String, String> wordAbbr;
  
  public static void main(String[] args) throws Exception
  {
    new Abbreviations().run();
  }
  
  public void run() throws Exception
  {
    scnWords = new ArrayList<String>();
    wordAbbr = new HashMap<String, String>();
    BufferedReader in = new BufferedReader(new FileReader(new File("input.txt")));
    FileWriter out = new FileWriter(new File("output.txt"));
    
    int scnNo = 1;
    int noWords = Integer.parseInt(in.readLine());
    while(noWords != 0){
      //get words
      for(int i = 0; i < noWords; i++){
        String next = in.readLine();
        scnWords.add(next);
        wordAbbr.put(next, "");
      }
      //create abbreviations
      abbreviate();
      //write to file
      out.write(String.valueOf(scnNo) + '\n');
      for(String s: scnWords)
        out.write(s + " " + wordAbbr.get(s) + '\n');
      //update for next round
      scnNo++;
      scnWords.clear();
      wordAbbr.clear();
      noWords = Integer.parseInt(in.readLine());
    }
    
    in.close();
    out.close();
  }
  
  private void abbreviate()
  {
    for(String s: scnWords){
      String currAbbr = "";
      boolean abbrFound = false;
      int charIndex = 0;
      while(!abbrFound){
        currAbbr += String.valueOf(s.charAt(charIndex));
        if(!otherWordContains(currAbbr, s)){
          abbrFound = true;
          wordAbbr.put(s, currAbbr);
        }
        else if(currAbbr.equals(s)){
          abbrFound = true;
          wordAbbr.put(s, currAbbr);
        }
        else
          charIndex++;
      }
    }
  }
  
  private boolean otherWordContains(String toFind, String exclude)
  {
    boolean ans = false;
    for(String s: scnWords){
      if(s.contains(toFind) && !s.equals(exclude))
        ans = true;
    }
    return ans;
  }
}