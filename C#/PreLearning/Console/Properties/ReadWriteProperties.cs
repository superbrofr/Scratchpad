using System;

//this class contains the properties which a 'customer' can have
public class Customer
{
  private int m_id = -1;

  public int ID
  {
    get //defines the rules for when this property is called upon
    {
      return m_id;
    }
    set //defines the rules for when this property is assigned a value
    {
      m_id = value;
    }
  }

  private string m_name = string.Empty;

  public string Name
  {
    get
    {
      return m_name;
    }
    set
    {
      m_name = value;
    }
  }
}

public class CustomerManagerWithProperties
{
  public static void Main()
  {
    Customer cust = new Customer();
    cust.ID = 1;
    cust.Name = "John Smith";
    Console.WriteLine("ID: {0}, Name: {1}", cust.ID, cust.Name);
  }
}