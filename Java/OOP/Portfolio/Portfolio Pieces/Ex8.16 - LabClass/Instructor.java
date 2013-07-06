
/**
 * An instructor who takes a lab class. Extends from Person.
 * 
 * @author Charlotte Pierce
 * @version 3/11/2010
 */
public class Instructor extends Person
{
    private int salary;
    
    /**
     * Creates an instructor.
     * @param name The name of the instructor.
     * @param address The address of the instructor.
     * @param phone The phone of the instructor.
     * @param salary The salary of the instructor.
     * @param id The id of the instructor.
     */
    public Instructor(String name, String address, String phone, int salary, String id)
    {
        super(name, address, phone, id);
        this.salary = salary;
    }
}
