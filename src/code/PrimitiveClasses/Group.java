package code.PrimitiveClasses;

public class Group extends BasicChat
{
    private User[] members;

    public Group(User[] members)
    {
        this.members = members;
    }

    public User[] getMembers()
    {
        return members;
    }

    public void setMembers(User[] members)
    {
        this.members = members;
    }
}
