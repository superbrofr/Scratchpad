/**
 * Tests the random number generator by calling for
 * some outputs.

 * @author Charlotte Pierce
 * @version 4/9/2010
 */

public class Test
{    
    public static void main(String[] args)
    {
        System.out.println("10 DICE ROLLS:");
        RandomTester dice = new RandomTester(6);
        dice.generate(10);
        System.out.println("100 COIN TOSSES:");
        RandomTester coin = new RandomTester(2);
        coin.generate(100);
        System.out.println("100,000 COIN TOSSES:");
        RandomTester coin2 = new RandomTester(2);
        coin.generate(100000);
    }
}
