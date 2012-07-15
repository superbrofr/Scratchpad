import java.util.ArrayList;
import java.util.HashMap;

/**
* An individual horn clause used in the knowledge base. Consists of an antecedent and a consequent, separated by an inference
* (=>) symbol. Antecedent consists of one or more symbols - if there is more than one symbol, they are joined by a conjunction.
* The consequent consists of a single symbol.
* @author Charlotte Pierce [7182139]
* @version 25/5/2011
*/
class HornClause
{
  private String antecedent, consequent;
  private ArrayList<String> antecedentSymbols; //stores the list of symbols in the horn clause
  private ArrayList<String> consequentSymbols; //stores the list of symbols in the horn clause
  private HashMap<String, Boolean> boolSymbols; //stores list of symbols with a boolean value - used for TT

  /**
  * Creates a hornclause.
  * @param anAntecedent The antecedent.
  * @param aConsequent The consequent.
  */
  public HornClause(String anAntecedent, String aConsequent)
  {
    antecedent = anAntecedent;
    consequent = aConsequent;
    antecedentSymbols = new ArrayList<String>();
    consequentSymbols = new ArrayList<String>();
    boolSymbols = new HashMap<String, Boolean>();
    initialiseSymbols(antecedent, antecedentSymbols);
    initialiseSymbols(consequent, consequentSymbols);
  }

  /**
  * Only used in truth table checking (which is concerned with the entire hornclause - FC and BC are only really concerned with
  * whether the consequent is true). Returns true if the entire hornclause evaluates to true, otherwise returns false.
  * Truth is based on the boolean values stored in the hornclause for each symbol - make sure to update each symbol with
  * the appripriate values first.
  */
  public boolean isTrue()
  {
    if(!antecedentTrue()){
      return true; //antecedent isn't true - implication will be true regardless of consequent
    }
    else{
      return boolSymbols.get(consequent); //otherwise implication is only true if consequent is also true
    }
  }

  /**
  * Returns the list of symbols used in the antecedent of the clause.
  * @return The symbols used in the antecedent of the clause.
  */
  public ArrayList<String> getAntecedentSymbols()
  {
    return antecedentSymbols;
  }

  /**
  * Returns whether the antecedent is true, based on the boolean values stored in the hornclause for each symbol in the
  * antecedent - make sure to update each symbols with the appropriate values first.
  * @return True if the antecedent is true, otherwise false.
  */
  public boolean antecedentTrue()
  {
    for(String s: antecedentSymbols){
      if(!boolSymbols.get(s)){
        return false;
      }
    }
    return true;
  }

  /**
  * Changes the boolean value stored for a symbol within the hornclause.
  * @param aSymbol The symbol to change.
  * @param aBool The boolean value to store for the symbol.
  */
  public void setSymbolBool(String aSymbol, Boolean aBool)
  {
    boolSymbols.put(aSymbol, aBool);
  }

  /**
  * Checks whether a specified symbol is used within the hornclause.
  * @param aSymbol The symbol to check.
  * @return True if the symbol is used in the hornclause, otherwise false.
  */
  public boolean containsSymbol(String aSymbol)
  {
    if(antecedentSymbols.contains(aSymbol) || consequentSymbols.contains(aSymbol)){
      return true;
    }
    return false;
  }

  /**
  * Returns the antecedent of the clause.
  * @return The antecedent of the clause.
  */
  public String getAntecedent()
  {
    return antecedent;
  }

  /**
  * Returns the consequent of the clause.
  * @return The consequent of the clause.
  */
  public String getConsequent()
  {
    return consequent;
  }
  
  /**
  * Returns a string representation of the clause, including the implication (=>) symbol if there is a consequent to the clause.
  * @return A string representation of the clause.
  */
  public String getString()
  {
    if(consequent == null)
      return antecedent;
    else
      return (antecedent + " => " + consequent);
  }

  /**
  * Extracts the symbols in a string, and adds them to a list of symbols. Uses the and (&&) symbols to separate symbols
  * if they are present in the string.
  * Used to initialise lists of consequent symbols, and lists of antecedent symbols in the hornclause.
  * @param someSymbols A string containing symbols to be found.
  * @param symbolList The symbol list to add the symbols found.
  */
  private void initialiseSymbols(String someSymbols, ArrayList<String> symbolList)
  {
    if(someSymbols != null){
      if(someSymbols.contains("&")){
        String[] someSymbolsSymbols = someSymbols.split("&");
        for(int i = 0; i < someSymbolsSymbols.length; i++){
          if(!symbolList.contains(someSymbolsSymbols[i])){
            symbolList.add(someSymbolsSymbols[i]);
          }
        }
      }
      else{
        if(!symbolList.contains(someSymbols)){
          symbolList.add(someSymbols);
        }
      }
    }
  }
}