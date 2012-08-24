using System;

public class Customer
{
  private int m_id = -1;
  private string m_name = string.Empty;

  public Customer(int id, string name) //properties are set by the constructer, when the class is initialised
                                       //this means that the values of the properties can only be set ONCE
  {
    m_id = id;
    m_name = name;
  }

  public int ID
  {
    get //a read-only property has only 'get' accessor - does not have 'set'
    {
      return m_id;
    }
  }

  public string Name
  {
    get //another read-only property
    {
      return m_name;
    }
  }
}

public class ReadOnlyCustomerManager
{
  public static void Main()
  {
    Customer cust = new Customer(1, "Bob Fletcher");
    Console.WriteLine("ID: {0}, Name: {1}", cust.ID, cust.Name);
  }
}