import java.util.Random;

/**
 * Write a description of class RandomTester here.
 * 
 * @author Charlotte Pierce
 * @version 4/9/2010
 */
public class RandomTester
{
    private int limit; //the range of random numbers that will be generated
    private Random generator;
    private int[] frequencies;
    private int requested; //number of random values requested to be generated
    
    /**
     * Constructor for objects of class RandomTester
     */
    public RandomTester(int lim)
    {
        limit = lim;
        generator = new Random();
        frequencies = new int[limit];
    }
    
    /**
     * Generates the specified number of random numbers, from 1 to limit (defined in constructor)
     * Increments appropriate value in frequencies as numbers are generated.
     * @param amount The number of numbers to generate.
     */
    public void generate(int amount)
    {
        requested = amount;
        for(int i = 0; i < (requested + 1); i++){
            int tempInt = generator.nextInt(limit) + 1; //get the 'right' number
            frequencies[tempInt - 1]++; //increase frequencies
        }
        printDetails();
    }
    
    /**
     * Displays in the console the number of values requested, and the number of times each value from
     * 1 to limit occurred, as well as the percentage of total numbers generated this represents.
     */
    private void printDetails()
    {
        System.out.println("FORMAT - <Number>: <Frequency> [<Percentage>]");
        for(int i = 0; i < limit; i++){
            double percent = (double)frequencies[i] / (double)requested; //get percentage.
            int number = i + 1;
            System.out.println(number + ": " + frequencies[i] + "  [" + percent + "%]");
        }
    }
}