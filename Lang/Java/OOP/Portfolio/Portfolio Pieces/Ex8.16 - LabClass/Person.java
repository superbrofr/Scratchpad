
/**
 * Abstract class Person - can be extended into an instructor or a student to be
 * included in a lab class.
 * 
 * @author Charlotte Pierce
 * @version 3/11/2010
 */
public abstract class Person
{
    protected String name;
    protected String address;
    protected String phone;
    protected String id;
    
    /**
     * Create the person.
     * @param aName The name of the person.
     * @param anAddress The address of the person.
     * @param aPhone The phone number of the person.
     * @param id The ID of the person.
     */
    public Person(String aName, String anAddress, String aPhone, String id)
    {
        name = aName;
        address = anAddress;
        phone = aPhone;
        this.id = id;
    }   
    
    /**
    * Return the name of this person.
    * @return The name of the person.
    */
    protected String getName()
    {
        return name;
    }
    
    /**
     * Set a new name for this person.
     * @param replacementName The new name for the person.
     */
    protected void changeName(String replacementName)
    {
        name = replacementName;
    }
    
    /**
     * Return the login name of this person.
     * @return The login name for the person.
     */
    public String getLoginName()
    {
        return name.substring(0,4) + id.substring(0,3);
    }
    
    /**
    * Print the person's name and ID number to the output terminal.
    */
    public void print()
    {
        System.out.println(name + " (" + id + ")");
    }
}
