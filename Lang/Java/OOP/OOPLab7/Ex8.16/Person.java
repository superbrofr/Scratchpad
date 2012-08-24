
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
    
    public Person(String aName, String anAddress, String aPhone, String id)
    {
        name = aName;
        address = anAddress;
        phone = aPhone;
        this.id = id;
    }   
    
    /**
    * Return the full name of this person.
    */
    protected String getName()
    {
        return name;
    }
    
    /**
     * Set a new name for this student.
     */
    protected void changeName(String replacementName)
    {
        name = replacementName;
    }
    
    /**
     * Return the login name of this person.
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
