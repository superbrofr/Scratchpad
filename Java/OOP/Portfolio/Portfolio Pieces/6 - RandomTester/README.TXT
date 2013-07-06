Project: Random Tester
Author: Charlotte Pierce

This project allows the user to generate some random numbers. Each random
number generate must have a limit specified, which defines the range of numbers
that the generator could return (range = 1 -> limit). The user can then request
a certain number of random numbers. The application will then generate that number
of random digits, keeping track of how often each digit is returned. It then
prints the details of the number generation, showing what numbers were generated,
how many times each number was generated, and the percentage of all the numbers
generated that the frequency represents.
Note: if the number was not generated at all, it is not shown in the output, for
the purpose of brevity.

To use this application:
- create an instance of RandomTester with a certain limit
- use the generate() method, specifying the number of random digits to generate
Alternatively:
Run the main method in the Test class, which shows a number of possible uses for 
the random numeber generator.

This project demonstrates the use of the Random class. It is fully documented using
javadoc style. The project implements all of the required features. It also includes
a table showing the tests performed on the RandomTester class, to see how accurate its 
number generation is.