import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;

/**
* An inference engine. Takes a text file containing a TELL and a number of horn clauses, followed by an ASK and a symbol.
* Forms a knowledge base using the hornclauses after TELL, and can then use that knowledge base to determine whether
* the ASK'ed symbol is entailed in the knowledge base, using either truth table (TT) checking, forward chaining (FC)
* or backward chaining (BC), as specified by the user.
* @author Charlotte Pierce [7182139]
* @version 25/5/2011
*/
public class iengine
{
  private String fileName;
  private String method;
  private ArrayList<HornClause> knowledgeBase;
  private String askedSymbol;
  
  /**
  * Creates a new inference engine, using the data in aFileName and the method specified by aMethod.
  * @param aFileName The path of the file with the knowledge base and asked symbol.
  * @param aMethod The solving method to use (TT, FC or BC).
  */
  public iengine(String aFileName, String aMethod)
  {
    knowledgeBase = new ArrayList<HornClause>();
    fileName = aFileName;
    method = aMethod;
    initialise();
    if(!askedSymbolInKnowledgeBase()){
      System.out.println("NO");
      System.exit(1);
    }
  }
  
  /**
  * Checks the correct number of arguments have been received, and if so creates and runs the inference engine.
  */
  public static void main(String args[])
  {
    if(args.length != 2){
      System.out.println("Invalid number of arguments!");
      System.exit(1);
    }
    new iengine(args[1], args[0]).run();
  }
  
  /**
  * Checks which method is being used, and runs the approrpiate inference solver.
  */
  private void run()
  {
    if(method.toUpperCase().equals("TT"))
      new TT(knowledgeBase, askedSymbol).run();
    else if(method.toUpperCase().equals("FC"))
      new FC(knowledgeBase, askedSymbol).run();
    else if(method.toUpperCase().equals("BC"))
      new BC(knowledgeBase, askedSymbol).run();
  }
  
  /**
  * Opens the file containing the knowledge base, calls setKB on the line after "TELL" to create the knowledge base,
  * and stores the asked symbol occurring after the line "ASK".
  */
  private void initialise()
  {
    try
    {
      BufferedReader fileReader = new BufferedReader(new FileReader(new File(fileName)));
      String nextLine = fileReader.readLine();
      if(nextLine.equals("TELL")){
        setKB(fileReader.readLine());
      }
      while(!nextLine.equals("ASK")){
        nextLine = fileReader.readLine();
      }
      askedSymbol = fileReader.readLine();
    }
    catch(FileNotFoundException e)
    {
      System.out.println("No such file!");
      System.exit(1);
    }
    catch(IOException e)
    {
      System.out.println("Error reading file!");
      System.exit(1);
    }
  }
  
  /**
  * Takes a line containing a set of horn clauses separated by commas. Separates each hornclause, creates each hornclause
  * with the relevant data and adds the clause to the knowledge base.
  * @param aLine A line containing the knowledge base.
  */
  private void setKB(String aLine)
  {
    StringTokenizer tokenizer = new StringTokenizer(aLine, ";");
    while(tokenizer.hasMoreTokens()){
      String[] clause = tokenizer.nextToken().split("=>");
      if(clause.length == 2){
        knowledgeBase.add(new HornClause(clause[0].trim(), clause[1].trim()));
      }
      else if(clause.length == 1){
        knowledgeBase.add(new HornClause(clause[0].trim(), null));
      }
    }
  }

  /**
  * Checks if the specified asked symbol is actually in the knowledge base (i.e. is present in at least one horn clause).
  * @return True if the asked symbol is in the knowledge base, otherwise false.
  */
  private boolean askedSymbolInKnowledgeBase()
  {
    boolean returnBool = false;
    for(HornClause h: knowledgeBase){
      if(h.containsSymbol(askedSymbol)){
        returnBool = true;
      }
    }
    return returnBool;
  }
}