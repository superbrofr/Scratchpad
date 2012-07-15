import java.util.ArrayList;
import java.util.HashMap;

/**
* A backward chaining checker, calculates if a particular symbol is entailed in a knowledge base by using the backward
* chaining algorithm.
* @author Charlotte Pierce [7182139]
* @version 25/11/2011
*/
public class BC
{
  private ArrayList<HornClause> knowledgeBase;
  private ArrayList<String> symbols;
  private String askedSymbol;
  private ArrayList<String> entailedSymbols;
  private HashMap<String, Boolean> boolSymbols;
  private ArrayList<String> goals; //list of symbols we need to prove
  private boolean changeMade; //if no progress was made last iteration, don't loop any more
  
  private int iterations;

  /**
  * Creates a new backward chaining checker.
  * @param aKB The knowledge base to use in calculations.
  * @param theAskedSymbol The symbol to check for entailment.
  */
  public BC(ArrayList<HornClause> aKB, String theAskedSymbol)
  {
    knowledgeBase = aKB;
    askedSymbol = theAskedSymbol;
    symbols = new ArrayList<String>();
    boolSymbols = new HashMap<String, Boolean>();
    entailedSymbols = new ArrayList<String>();
    goals = new ArrayList<String>();
    initialiseSymbols();
    goals.add(askedSymbol);
    changeMade = true; //if false won't even try to look (won't enter loop)
    iterations = 0;
  }

  /**
  * Runs the checker. Iterates over the backward chaining algorithm whilst changes are being made (i.e. new information being found),
  * and while there are still goal symbols to check.
  */
  public void run()
  {
    while((goals.size() > 0) && changeMade){
      iterations++;
      update();
    }
    printResult();
  }

  /**
  * Runs one iteration of the backward chaining algorithm.
  * Uses the values found in the previous iteration to check if further entailments can be found.
  * Updates each horn clause with the boolean values stored for each symbol, then retrieves the next goal symbol.
  * Checks if the goal symbol is true - if so, adds it to the entailed list. If it is not true, looks for horn clauses
  * in which the goal is the consequent. Once found, checks if the antecedent in these clause are true (i.e. if the goal
  * can be inferred). If the goal can't be inferred, adds the symbols in the antecedents to the goal list, to start
  * checking next iteration.
  */
  private void update()
  {
    changeMade = false;
    //update the boolean values in each hornclause with the latest (i.e. those stored here)
    for(HornClause h: knowledgeBase){
      for(String s: symbols){
        if(h.containsSymbol(s)){
          h.setSymbolBool(s, boolSymbols.get(s)); //if the symbol is in the hornclause, then update it with the value stored here
        }
      }
    }

    String currentGoal = goals.get(goals.size() - 1);
    for(HornClause h: knowledgeBase){
      if(h.getConsequent() != null && h.getConsequent().equals(currentGoal)){
        if(h.antecedentTrue()){
          changeMade = true;
          boolSymbols.put(currentGoal, true);
          entailedSymbols.add(currentGoal);
          goals.remove(currentGoal);
          if(currentGoal.equals(askedSymbol)){
            printResult();
          }
        }
        else{
          ArrayList<String> hAnteSymbols = h.getAntecedentSymbols();
          for(String s: hAnteSymbols){
            if(!entailedSymbols.contains(s) && !goals.contains(s)){
              changeMade = true; //only change if new goals are found
              goals.add(s);
            }
          }
        }
      }
      else if(h.getConsequent() == null && h.getAntecedent().equals(currentGoal)){
        changeMade = true;
        boolSymbols.put(currentGoal, true);
        entailedSymbols.add(currentGoal);
        goals.remove(currentGoal);
        if(currentGoal.equals(askedSymbol)){
          printResult();
        }
      }
    }
    if(!changeMade){
      changeMade = true;
      goals.remove(currentGoal);
    }
  }

  /**
  * Initialises the symbols, by getting each unique symbol in the knowledge base and storing them in a list.
  * Sets each symbol to false initially, so make sure to call initialiseLiterals() if needed.
  */
  private void initialiseSymbols()
  {
    //find the symbols
    for(HornClause clause: knowledgeBase){
      processSymbolString(clause.getAntecedent());
      processSymbolString(clause.getConsequent());
    }
    //create boolean mappings for each of them - initialise each bool to false
    for(String c: symbols){
      boolSymbols.put(c, false); //initialise each symbol to false
    }
  }

  /**
  * Used to process the antecedent and consequent of each clause during initialiseSymbols(). 
  * Splits the string if it has an '&' symbol within it, otherwise just adds the symbol.
  * @param someSymbols A string of symbols.
  */
  private void processSymbolString(String someSymbols)
  {
    if(someSymbols != null){
      if(someSymbols.contains("&")){
        String[] someSymbolsSymbols = someSymbols.split("&");
        for(int i = 0; i < someSymbolsSymbols.length; i++){
          if(!symbols.contains(someSymbolsSymbols[i])){
            symbols.add(someSymbolsSymbols[i]);
          }
        }
      }
      else{
        if(!symbols.contains(someSymbols)){
          symbols.add(someSymbols);
        }
      }
    }
  }

  /**
  * Prints the result of the calculations and exists the application.
  */
  private void printResult()
  {
    System.out.println("Iterations: " + iterations);
    if(boolSymbols.get(askedSymbol)){
      System.out.print("YES: ");
    }
    else{
      System.out.print("NO: ");
    }
    for(String s: entailedSymbols){
      System.out.print(s + ", ");
    }
    System.out.print("\b\b "); //remove last comma
    System.exit(0);
  }
}