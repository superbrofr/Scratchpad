import java.io.*;

class Dice
{
  public static void main(String[] args) throws Exception
  {
    BufferedReader in = new BufferedReader(new FileReader(new File("input.txt")));
    FileWriter out = new FileWriter(new File("output.txt"));
    
    String next = in.readLine();
    while(!next.equals("# # #")){
      //get details
      String[] participants = next.split(" ");
      String thrower = participants[0];
      String guesser = participants[1];
      String guess = participants[2];
      int noThrows = Integer.parseInt(in.readLine());
      int correctGuesses = 0;
      String[] rolls = in.readLine().split(" ");
      for(int i = 0; i < noThrows; i++){
        if(guess.equals("even") && (Integer.parseInt(rolls[i]) % 2 == 0))
          correctGuesses++;
        else if(guess.equals("odd") && (Integer.parseInt(rolls[i]) % 2 != 0))
          correctGuesses++;
      }
      //write output
      out.write(thrower + " " + String.valueOf(noThrows - correctGuesses) + " " + guesser + " " + correctGuesses + '\n');
      
      next = in.readLine();
    }
    in.close();
    out.close();
  }
}