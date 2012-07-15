import java.util.Random;
import java.util.ArrayList;

public class RandomTester
{
    // instance variables - replace the example below with your own
    private Random randomTest;

    /**
     * Constructor for objects of class RandomTester
     */
    public RandomTester()
    {
        randomTest = new Random();
    }
    
    public void printOneRandom()
    {
        System.out.println(randomTest.nextInt());
    }
    
    public void printMultiRandom(int howMany)
    {
        for(int i = 0; i < howMany; i++){
            System.out.println(randomTest.nextInt());
        }
    }
    
    public void throwDice()
    {
        System.out.println(randomTest.nextInt(6) + 1);
    }
    
    public String getResponse()
    {
        ArrayList<String> responses = new ArrayList<String>();
        responses.add("Yes");
        responses.add("No");
        responses.add("Maybe");
        int temp = randomTest.nextInt(3);
        return responses.get(temp);
    }
    
    public int returnToMax(int max)
    {
        return randomTest.nextInt(max + 1);
    }
}
