import java.io.*;
import java.util.ArrayList;

class ProblemC
{
  public static void main(String[] args) throws Exception
  {
    new ProblemC().run();
  }

  public void run() throws Exception
  {
    BufferedReader in = new BufferedReader(new FileReader(new File("input.txt")));
    FileWriter out = new FileWriter(new File("output.txt"));

    ArrayList<Integer> theNums = new ArrayList<Integer>();

    while(in.ready())
      theNums.add(Integer.parseInt(in.readLine()));

    while(theNums.size() > 0){
      int next = theNums.get(0);
      System.out.println("!!! WORKING ON : " + next + " !!!");
      int factorCount = 0;
      for(int i = 1; i <= next; i++){
        int processing = i;
        while((processing % 3 == 0) || (processing % 2 == 0) || (processing > 1)){
          while(processing % 2 == 0){
            processing = processing / 2;
            factorCount++;
            System.out.println("factors: " + factorCount);
          }
          while(processing % 3 == 0){
            processing = processing / 3;
            factorCount++;
            System.out.println("factors: " + factorCount);
          }
          if((processing > 1) && (processing % 2 != 0) && (processing % 3 != 0)){
            processing = processing / highestPrimeFactor(processing);
            factorCount++;
            System.out.println("factors: " + factorCount);
          }
        }
      }
      out.write(String.valueOf(factorCount) + '\n');
      theNums.remove(0);
    }

    in.close();
    out.close();
  }

  private int highestPrimeFactor(int aNum)
  {
    int highest = 0;
    for(int i = 1; i <= aNum; i++){
      if(aNum % i == 0)
        if(isPrime(i) && (i > highest))
          highest = i;
    }
    return highest;
  }

  private boolean isPrime(int toTest)
  {
    boolean ans = true;
    for(int i = 2; i * i <= toTest; i++){
      if(toTest % i == 0)
        ans = false;
    }
    return ans;
  }
}