Project: Lab Classes
Author: Charlotte Pierce

This project simluates a lab class. Each lab class can have one instructor, and any 
number of students. The number of students allowed in a class is specified by the user.

To use this application:
- create an Instructor
- create a LabClass (specifying the maximum number of students and the instructor for the class)
- create as many Student objects as desired
- add students to the LabClass
- use the methods in LabClass to set the room and time for the class, and return the number of students enrolled in that class
- use the method printList() in LabClass to print all the details of that class

This project demonstrates the use of collections. It also demonstrates the use of an
abstract class (Person), which Instructor and Student classes extend. This stops the class Person
from being instantiated directly, allowing only its subclasses to be created. This was done
in order to allow both the Instructor and Student classes, which share similar code, to be able
to access their shared code from one common class, decreasing the need for code duplication, and
making it easier for extensions or changes to the project. The project is fully documented using
javadoc style.