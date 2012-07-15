
/**
 * The Student class represents a student in a student administration system.
 * It holds the student details relevant in our context.
 * Students inherit the fields 'name', 'address' and 'phone' from class Person.
 * 
 * @author Charlotte Pierce
 * @version 3/11/2010
 */
public class Student extends Person
{
    // the amount of credits for study taken so far
    private int credits;

    /**
     * Create a new student.
     * @param fullName The name of the student.
     * @param address The address of the student.
     * @param phone The phone of the student.
     * @param studentID The id of the student.
     */
    public Student(String fullName, String address, String phone, String studentID)
    {
        super(fullName, address, phone, studentID);
        credits = 0;
    }

    /**
     * Return the student ID of this student.
     * @return The student's ID.
     */
    public String getStudentID()
    {
        return id;
    }

    /**
     * Add some credit points to the student's accumulated credits.
     * @param additionalPoints The number of credit points to add.
     */
    public void addCredits(int additionalPoints)
    {
        credits += additionalPoints;
    }

    /**
     * Return the number of credit points this student has accumulated.
     * @return The number of credit points the student has.
     */
    public int getCredits()
    {
        return credits;
    }
}
