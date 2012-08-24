import java.util.ArrayList;
import java.util.HashMap;

/**
* A foward chaining checker, calculates if a particular symbol is entailed in a knowledge base by using the forward
* chaining algorithm.
* @author Charlotte Pierce [7182139]
* @version 25/11/2011
*/
public class FC
{
  private ArrayList<HornClause> knowledgeBase;
  private ArrayList<String> symbols;
  private String askedSymbol;
  private ArrayList<String> entailedSymbols;
  private HashMap<String, Boolean> boolSymbols;

  private boolean changeMade; //checks if a change was made in the last iteration - if so, do another iteration, if not, stop trying to do forward chaining and display result
  
  /**
  * Creates a new forward chaining checker.
  * @param aKB The knowledge base to use in calculations.
  * @param theAskedSymbol The symbol to check for entailment.
  */
  public FC(ArrayList<HornClause> aKB, String theAskedSymbol)
  {
    knowledgeBase = aKB;
    askedSymbol = theAskedSymbol;
    symbols = new ArrayList<String>();
    boolSymbols = new HashMap<String, Boolean>();
    entailedSymbols = new ArrayList<String>();
    changeMade = false;
    initialiseSymbols();
    initialiseLiterals();
  }
  
  /**
  * Runs the checker. Iterates over the forward chaining algorithm whilst changes are being made (i.e. new information being found).
  */
  public void run()
  {
    while(changeMade){
      update();
    }
    printResult();
  }

  /**
  * Runs one iteration of the forward chaining algorithm.
  * Updates the hornclauses with the boolean symbols stored in the forward chaining checker (so any changes found last
  * iteration propagate into the relevant horn clause(s)), then checks if any new inferences can be made with
  * the information found in the last iteration.
  */
  private void update()
  {
    //update the boolean values in each hornclause with the latest (i.e. those stored here)
    changeMade = false; //will be made true again if necessary
    for(HornClause h: knowledgeBase){
      for(String s: symbols){
        if(h.containsSymbol(s)){
          h.setSymbolBool(s, boolSymbols.get(s)); //if the symbol is in the hornclause, then update it with the value stored here
        }
      }
    }
    //check if any new symbols can be entailed
    for(HornClause h: knowledgeBase){
      if(h.antecedentTrue()){
        String consequent = h.getConsequent();
        //consequent can be inferred to be true - only add to entailed/note change if it's not already entailed
        if((!entailedSymbols.contains(consequent) && (consequent != null))){
          entailedSymbols.add(consequent);
          h.setSymbolBool(consequent, true);
          boolSymbols.put(consequent, true);
          changeMade = true;
          if(consequent.equals(askedSymbol)){
            //entailed found, break and print result
            printResult();
          }
        }
      }
    }
  }

  /**
  * Initialises the literal values (i.e. singular symbols stored as hornclauses with null consequents).
  * Finds these literal values in the hornclauses and makes sure they are stored as being true.
  */
  private void initialiseLiterals()
  {
    for(HornClause h: knowledgeBase){
      String consequent = h.getConsequent();
      if(consequent == null){
        entailedSymbols.add(h.getAntecedent());
        h.setSymbolBool(h.getAntecedent(), true); //set the hornclause to true
        boolSymbols.put(h.getAntecedent(), true);
        changeMade = true;
        if(h.getAntecedent().equals(askedSymbol)){
          //entailed found, break and print result
          printResult();
        }
      }
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