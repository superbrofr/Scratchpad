import java.util.ArrayList;
import java.util.HashMap;

/**
* A truth table checker, calculates how many models of a knowledge base support a particular symbol being true.
* @author Charlotte Pierce [7182139]
* @version 25/11/2011
*/
public class TT
{
  private ArrayList<HornClause> knowledgeBase;
  private ArrayList<String> symbols; //list of unique symbols in the KB - i.e. 'a', 'b', 'c'
  private HashMap<String, Boolean> boolSymbols; //a mapping for each symbol to a boolean value for calcuations
  private int validModels; //the number of models in which the query is found to be true
  private String askedSymbol;

  /**
  * Creates a new truth table checker.
  * @param aKB The knowledge base to use in calculations.
  * @param theAskedSymbol The specific symbol used in the calculations.
  */
  public TT(ArrayList<HornClause> aKB, String theAskedSymbol)
  {
    knowledgeBase = aKB;
    askedSymbol = theAskedSymbol;
    symbols = new ArrayList<String>();
    boolSymbols = new HashMap<String, Boolean>();
    validModels = 0;
    initialiseSymbols();
  }

  /**
  * Runs the truth table checker.
  */
  public void run()
  {
    //there will be ((2 ^ symbols.size()) - 1) total possible worlds, and we must test them all
    int boolCount = 0;
    int endMarker = (int)Math.pow(2.0, symbols.size());
    while(boolCount != endMarker){
      updateSymbolBools(Integer.toBinaryString(boolCount));
      //process each of the clauses given the current values for each symbol (i.e. the current model) - takes care of incrementing validModels if needed
      checkModel();
      boolCount++;
    }
    if(validModels > 0){
      System.out.println("YES: " + validModels);
    }
    else{
      System.out.println("NO");
    }
  }

  /**
  * Checks if the current model (i.e. the boolean values for each symbol in the knowledge base) held in the truth table checker
  * is valid or not for the asked symbol. If it is, the count of valid models is increased by one.
  */
  private void checkModel()
  {
    if(!boolSymbols.get(askedSymbol)){
      //if the asked symbol is false from the outset, the model is already invalid - don't bother checking
      return;
    }
    else{
      //actually check the model
      for(HornClause hc: knowledgeBase){
        if(!checkClause(hc)){
          return;
        }
      }
      //all clauses hold - never satisfied condition for 'return'
      validModels++;
    }
  }

  /**
  * Checks if a hornclause evaluates to true, given the current boolean values stored in the truth table checker. Updates
  * the hornclause with the boolean values held in the truth table checker first.
  * @param aClause The hornclause to check.
  * @return True if the hornclause evaluates to true, otherwise false.
  */
  private boolean checkClause(HornClause aClause)
  {
    if(aClause.getConsequent() == null){
      //single argument, just need to know if antecedent is true in the model being tested
      return boolSymbols.get(aClause.getAntecedent());
    }
    else{
      //actual calculations to be done
      //update each symbol in the hornclause with the value currently being tested
      for(String s: symbols){
        if(aClause.containsSymbol(s)){
          aClause.setSymbolBool(s, boolSymbols.get(s));
        }
      }
      //return whether the horn clause evaluates to true or not given the values from the model being tested
      return aClause.isTrue();
    }
  }

  /**
  * Updates the boolean values in the truth table checker for each symbol in the knowledge base. Uses a string of 0/1 values
  * to the length of the string to represent true/false values for each symbol.
  * @param theBools The 1/0 string representing true/false values for each symbol
  */
  private void updateSymbolBools(String theBools)
  {
    //Pad the boolean string to number of symbols
    while(theBools.length() != symbols.size()){
      theBools = "0" + theBools;
    }
    //Change boolean values appropriately
    for(int i = 0; i < theBools.length(); i++){
      if(theBools.charAt(i) == '0'){
        boolSymbols.put(symbols.get(i), false);
      }
      else if(theBools.charAt(i) == '1'){
        boolSymbols.put(symbols.get(i), true);
      }
    }
  }

  /**
  * Gets the list of unique symbols in the knowledge base which will need to be tested and assigned to the master list of symbols.
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
      boolSymbols.put(c, false);
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
}