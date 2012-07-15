import java.util.ArrayList;

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
}
