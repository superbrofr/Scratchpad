//different types of parameters:
//value [no modifier] - value of the parameter is copied on the stack, local changes only
//reference ['ref' modifier] - method is given a pointer to the original - global changes
//out ['out' modifier] - original value of the variable is disregarded and overwritten - global changes
//parameters ['params' modifier] - parameter accepting a single-dimension or jagged array of values, works like value,
//- local changes only

using System;

class Address
{
    public string name;
    public string address;
}

class Parameters
{
    public static void Main()
    {
        string myChoice;
        Parameters p = new Parameters();
        do
       {
            myChoice = p.getChoice();
            p.makeDecision(myChoice);

            Console.Write("press Enter key to continue...");
            Console.ReadLine();
        } while (myChoice != "Q" && myChoice != "q"); // Keep going until the user wants to quit
    }

    // show menu and get user's choice
    string getChoice()
    {
        string myChoice;

        Console.WriteLine("A - Add New Address");
        Console.WriteLine("D - Delete Address");
        Console.WriteLine("M - Modify Address");
        Console.WriteLine("V - View Addresses");
        Console.WriteLine("Q - Quit\n");
        Console.WriteLine("Choice: ");

        myChoice = Console.ReadLine();

        return myChoice; 
    }

    void makeDecision(string myChoice)
    {
        Address addr = new Address();

        switch(myChoice)
        {
            case "a":
                addr.name = "Joe";
                addr.address = "Fake St";
                this.addAddress(ref addr); //'this' means that the program can jump to a block of code in THE SAME
                                          //INSTANCE of the class, and continue from the specified block as normal
                                          //'this' is used to refer to its containing objects members, including the
                                          //methods located within
                break;
            case "d":
                addr.name = "Robert";
                this.deleteAddress(addr.name);
                break;
            case "m":
                addr.name = "Matt";
                this.modifyAddress(out addr);
                Console.WriteLine("Name is now {0}.", addr.name);
                break;
            case "v":
                this.viewAddresses("Cheryl", "Joe", "Matt", "Robert");
                break;
            case "q":
                Console.WriteLine("Bye.");
                break;
        }
    }

    void addAddress(ref Address addr)
    {
        Console.WriteLine("Name: {0}, Address: {1} added.", addr.name, addr.address); 
    }

    void deleteAddress(string name)
    {
        Console.WriteLine("You wish to delete {0}'s address.", name); 
    }

    void modifyAddress(out Address addr)
    {
        addr = new Address();
        addr.name = "Joe";
        addr.address = "Fake St.";
    }

    void viewAddresses(params string[] names)
    {
        foreach (string name in names)
        {
            Console.WriteLine("Name: {0}", name);
        }
    }
}