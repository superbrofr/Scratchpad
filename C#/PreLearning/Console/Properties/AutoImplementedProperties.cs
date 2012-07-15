//NOTE: Only a feature of C# 3.0

using System;

public class Customer
{
  public int ID
  {
    get; //removes the need to manually code the assignment of the specified value
    set; //removes the need to manually code the display of the value
  }
  
  public string Name
  {
    get;
    set;
  }
}

public class AutoImplementedCustomerManager
{
  static void Main()
  {
    Customer cust = new Customer();

    cust.ID = 1;
    cust.Name = "Blah Blergh";
    Console.WriteLine("ID: {0}, Name: {1}", cust.ID, cust.Name);
  }
}