import java.util.*;

/**
 * The LabClass class represents an enrolment list for one lab class. It stores
 * the time, room and participants of the lab, as well as the instructor's name.
 * 
 * @author Charlotte Pierce
 * @version 3/11/2010
 */
public class LabClass
{
    private String instructor;
    private String room;
    private String timeAndDay;
    private List<Student> students;
    private Instructor teacher; // can only have one instructor per lab class
    private int capacity;
    
    /**
     * Create a LabClass with a maximum number of enrolments. All other details
     * are set to default values.
     * @param maxNumberOfStudents The maximum number of students.
     * @param teacher The instructor for the class.
     */
    public LabClass(int maxNumberOfStudents, Instructor teacher)
    {
        instructor = "unknown";
        room = "unknown";
        timeAndDay = "unknown";
        students = new ArrayList<Student>();
        this.teacher = teacher;
        capacity = maxNumberOfStudents;
    }

    /**
     * Add a student to this LabClass.
     * @param newStudent The student to add.
     */
    public void enrollStudent(Student newStudent)
    {
        if(students.size() == capacity) {
            System.out.println("The class is full, you cannot enrol.");
        }
        else {
            students.add(newStudent);
        }
    }
    
    /**
     * Return the number of students currently enrolled in this LabClass.
     * @return The number of students in the class.
     */
    public int numberOfStudents()
    {
        return students.size();
    }
    
    /**
     * Set the room number for this LabClass.
     * @param roomNumber The room number for the class.
     */
    public void setRoom(String roomNumber)
    {
        room = roomNumber;
    }
    
    /**
     * Set the time for this LabClass. The parameter should define the day
     * and the time of day, such as "Friday, 10am".
     * @param timeAndDayString The time for the lab class.
     */
    public void setTime(String timeAndDayString)
    {
        timeAndDay = timeAndDayString;
    }
    
    /**
     * Set the name of the instructor for this LabClass.
     * @param instructorName The name of the instructor for the class.
     */
    public void setInstructor(String instructorName)
    {
        instructor = instructorName;
    }
    
    /**
     * Print out a class list with other LabClass details to the standard
     * terminal.
     */
    public void printList()
    {
        System.out.println("Lab class " + timeAndDay);
        System.out.println("Instructor: " + instructor + "   room: " + room);
        System.out.println("Class list:");
        for(Student student : students) {
            student.print();
        }
        System.out.println("Number of students: " + numberOfStudents());
    }
}
