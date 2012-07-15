using System;

class OvrIndexer
{
  private string[] myData;
  private int arrSize;

  public OvrIndexer(int size)
  {
    arrSize = size;
    myData = new string[size];

    for (int i=0; i < size; i++)
    {
      myData[i] = "empty";
    }
  }

  //the two indexers are overloaded - the compiler will choose the most appropriate to use, given the
  //number and type of parameter(s)
  public string this[int pos] //this index returns and sets values based on a numerical parameter
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

  public string this[string data] //this index returns and sets values based on a alphanumerical parameter
  {
    get
    {
      int count = 0;

      for (int i=0; i < arrSize; i++)
      {
        if (myData[i] == data)
        {
          count++;
        }
      }
      return count.ToString();
    }
    set
    {
      for (int i=0; i < arrSize; i++)
      {
        if (myData[i] == data)
        {
          myData[i] = value;
        }
      }
    }
  }

  static void Main(string[] args)
  {
    int size = 10;
    OvrIndexer myInd = new OvrIndexer(size);

    myInd[9] = "Some Value";
    myInd[3] = "Another Value";
    myInd[5] = "Any Value";

    myInd["empty"] = "no value";

    Console.WriteLine("Indexer Output");

    for (int i=0; i < size; i++)
    {
      Console.WriteLine("myInd[{0}]: {1}", i, myInd[i]);
    }

    Console.WriteLine("Number of \"no value\" entries: {0}", myInd["no value"]);
  }
}