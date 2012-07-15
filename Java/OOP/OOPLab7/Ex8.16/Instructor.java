
/**
 * An instructor who takes a lab class. Extends from Person.
 * 
 * @author Charlotte Pierce
 * @version 3/11/2010
 */
public class Instructor extends Person
{
    private int salary;
    
    public Instructor(String name, String address, String phone, int salary, String id)
    {
        super(name, address, phone, id);
        this.salary = salary;
    }
}
