/**
 * Defines a club. Every club can have a number of members.
 * 
 * @author Charlotte Pierce
 * @version 29/8/2010
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Club
{
    ArrayList<Membership> members;
    
    public Club()
    {
        members = new ArrayList<Membership>();
    }

   //Add a new member to the club's list of members.
    public void join(Membership member)
    {
        members.add(member);
    }

    //The number of members (Membership objects) in the club.
    public int numberOfMembers()
    {
        return members.size();
    }
    
    /*
     *Determine the number of members who joined in the
     *given month.
     *@param month The month we are interested in.
     *@return The number of members joinng in month
    */
    public int joinedInMonth(int month)
    {
        int count = 0;
        Iterator<Membership> it = members.iterator();
        if(month < 1 || month > 12){
            System.out.println("Invalid month");
            return 0;
        }
        else{
            while(it.hasNext()){
                Membership mem = it.next();
                if(mem.getMonth() == month){
                    count += 1;
                }
            }
            return count;
        }
    }
    
    /*
     * Remove from the club's collection all members who
     * joined in the given month, and return them stored 
     * in a  separate collection object.
     * @param month The month of the Membership.
     * @param year The year of the Membership.
     * @return The members who joined in the given month and year.
    */
   public ArrayList<Membership> purge(int month, int year)
   {
       ArrayList<Membership> purged = new ArrayList<Membership>();
       Iterator<Membership> it = members.iterator();
       if(month < 1 || month > 12){
           System.out.println("Invalid month entered!");
           return null;
       }
       else{
           while(it.hasNext()){
               Membership mem = it.next();
               if(mem.getMonth() == month && mem.getYear() == year){
                   purged.add(mem);
                   it.remove();
               }
           }
           return purged;
       }
   }
}
