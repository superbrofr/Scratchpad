import java.io.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

class ProblemA
{
  public static void main(String[] args) throws Exception
  {
    ArrayList<String> inputWords = new ArrayList<String>();
    BufferedReader in = new BufferedReader(new FileReader(new File("input.txt")));
    while(in.ready()){
      String next = in.readLine();
      if(!next.equals("0") && !next.equals("")){
        String[] words = next.split(" ");
        for(int i = 0; i< words.length; i++){
          inputWords.add(words[i]);
        }
      }
    }
    
    int index = 0;
    FileWriter out = new FileWriter(new File("output.txt"));
    DecimalFormat format = new DecimalFormat("0.00");
    while(index < inputWords.size()){
      out.write("Year " + inputWords.get(index) + ": ");
      index++;
      int noRecords = Integer.parseInt(inputWords.get(index));
      index++;
      int total = 0;
      for(int i = index; i < index + noRecords; i++){
        total += Integer.parseInt(inputWords.get(i));
      }
      index += noRecords;
      double avg = (double)total / noRecords;
      out.write(String.valueOf(format.format(avg)));
      out.write('\n');
    }
    out.close();
  }
}