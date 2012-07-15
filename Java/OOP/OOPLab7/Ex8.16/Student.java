
/**
 * The Student class represents a student in a student administration system.
 * It holds the student details relevant in our context.
 * Students inherit the fields 'name', 'address' and 'phone' from class Person.
 * 
 * @author Michael Kolling and David Barnes
 * @version 2008.03.30
 */
public class Student extends Person
{
    // the amount of credits for study taken so far
    private int credits;

    /**
     * Create a new student with a given name and ID number.
     */
    public Student(String fullName, String address, String phone, String studentID)
    {
        super(fullName, address, phone, studentID);
        credits = 0;
    }

    /**
     * Return the student ID of this student.
     */
    public String getStudentID()
    {
        return id;
    }

    /**
     * Add some credit points to the student's accumulated credits.
     */
    public void addCredits(int additionalPoints)
    {
        credits += additionalPoints;
    }

    /**
     * Return the number of credit points this student has accumulated.
     */
    public int getCredits()
    {
        return credits;
    }
}
