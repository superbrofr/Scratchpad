import java.io.*;

class ProblemB
{
  public static void main(String[] args) throws Exception
  {
    new ProblemB().run();
  }
  
  public void run() throws Exception
  {
    BufferedReader in = new BufferedReader(new FileReader(new File("input.txt")));
    FileWriter out = new FileWriter(new File("output.txt"));
    while(in.ready()){
      String next = in.readLine();
      if(!next.equals("0") && !next.equals("")){
        //student details
        String[] studentDeets = next.split(" ");
        out.write("Student ID: " + studentDeets[0] + '\n');
        //read appropriate number of events
        int noEvents = Integer.parseInt(studentDeets[1]);
        Item[] events = new Item[noEvents];
        for(int i = 0; i < noEvents; i++){
          events[i] = new Item(in.readLine());
        }
        //sort events int ascending order
        while(!inOrder(events))
          sort(events);
        //write events to output file
        for(int i = 0; i < events.length; i++)
          out.write(events[i].getItemDeets() + '\n');
        out.write('\n');
      }
    }
    
    in.close();
    out.close();
  }
  
  private boolean inOrder(Item[] anEventList)
  {
    boolean ans = true;
    for(int i = 1; i < anEventList.length; i++){
      if(anEventList[i].getMonth() < anEventList[i - 1].getMonth())
        ans = false;
      else if(anEventList[i].getMonth() == anEventList[i - 1].getMonth())
        if(anEventList[i].getDay() < anEventList[i - 1].getDay())
          ans = false;
        else if(anEventList[i].getDay() == anEventList[i - 1].getDay())
          if(anEventList[i].getTimePeriod().equals("AM") && anEventList[i - 1].getTimePeriod().equals("PM"))
            ans = false;
          else if(anEventList[i].getTimePeriod().equals(anEventList[i - 1].getTimePeriod()))
            if(anEventList[i].getHours() < anEventList[i - 1].getHours())
              ans = false;
            else if(anEventList[i].getHours() == anEventList[i - 1].getHours())
              if(anEventList[i].getMins() < anEventList[i - 1].getMins())
                ans = false;
    }
    return ans;
  }
  
  private void sort(Item[] anEventList)
  {
    for(int i = 1; i < anEventList.length; i++){
      if(anEventList[i].getMonth() < anEventList[i - 1].getMonth()){
        Item temp = anEventList[i];
        anEventList[i] = anEventList[i - 1];
        anEventList[i - 1] = temp;
      }
      else if(anEventList[i].getMonth() == anEventList[i - 1].getMonth())
        if(anEventList[i].getDay() < anEventList[i - 1].getDay()){
          Item temp = anEventList[i];
          anEventList[i] = anEventList[i - 1];
          anEventList[i - 1] = temp;
        }
        else if(anEventList[i].getDay() == anEventList[i - 1].getDay())
          if(anEventList[i].getTimePeriod().equals("AM") && anEventList[i - 1].getTimePeriod().equals("PM")){
            Item temp = anEventList[i];
            anEventList[i] = anEventList[i - 1];
            anEventList[i - 1] = temp;
          }
          else if(anEventList[i].getTimePeriod().equals(anEventList[i - 1].getTimePeriod()))
            if(anEventList[i].getHours() < anEventList[i - 1].getHours()){
              Item temp = anEventList[i];
              anEventList[i] = anEventList[i - 1];
              anEventList[i - 1] = temp;
            }
            else if(anEventList[i].getHours() == anEventList[i - 1].getHours())
              if(anEventList[i].getMins() < anEventList[i - 1].getMins()){
                Item temp = anEventList[i];
                anEventList[i] = anEventList[i - 1];
                anEventList[i - 1] = temp;
              }
    }
  }
}