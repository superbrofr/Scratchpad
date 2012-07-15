import java.util.HashMap;

public class MapTester
{
    HashMap<String, String> phoneBook;
    
    public MapTester()
    {
        phoneBook = new HashMap<String, String>();
    }
    
    public void addNumber(String name, String number)
    {
        phoneBook.put(name, number);
    }
    
    public String lookupNumber(String name)
    {
        return phoneBook.get(name);
    }
}
