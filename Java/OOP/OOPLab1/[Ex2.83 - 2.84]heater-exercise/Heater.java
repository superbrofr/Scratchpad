public class Heater
{
    private int temperature;
    private int min; //minimum temperature
    private int max; //maximum temperature
    private int increment; //amount to change temperature by
    
    public Heater(int minSet, int maxSet)
    {
        temperature = 15;
        min = minSet;
        max = maxSet;
        increment = 5;
    }
    
    public void warmer()
    {
        if((temperature + increment) > max){
            //do nothing...
        }
        else
        {
            temperature = temperature + increment;
        }
    }
    
    public void cooler()
    {
        if((temperature - increment) < min){
            //do nothing...
        }
        else{
            temperature = temperature - increment;
        }
    }
    
    public void setIncrement(int inc)
    {
        if(inc < 0){
            System.out.println("Invalid value to increment.");
        }
        else{
            increment = inc;
        }
    }
    
    public int getTemp()
    {
        return temperature;
    }
}
