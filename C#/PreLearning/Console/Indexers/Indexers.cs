using System;

class IntIndexer
{
  private string[] myData;

  public IntIndexer(int size)
  {
    myData = new string[size];

    for (int i=0; i < size; i++)
    {
      myData[i] = "empty";
    }
  }

  //NOTE: Indexers are identifiable by their square brackets for parameters
  //this is the indexer - indexers are like properties, but used on arrays rather than individual variables
  public string this[int pos] //returns the data at the specified index
  {
    get
    {
      return myData[pos];
    }
    set
    {
      myData[pos] = value;
    }
  }

  static void Main(string[] args)
  {
    int size = 10;
    IntIndexer myInd = new IntIndexer(size);

    myInd[9] = "Some Value";
    myInd[3] = "Another Value";
    myInd[5] = "Any Value";

    Console.WriteLine("Indexer Output");

    for (int i=0; i < size; i++)
    {
      Console.WriteLine("myInd[{0}]: {1}", i, myInd[i]);
    }
  }
}