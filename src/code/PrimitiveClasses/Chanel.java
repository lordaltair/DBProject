package code.PrimitiveClasses;

public class Chanel extends BasicChat
{
    private User members[];
    private User admin;

    public User[] getMembers()
    {
        return members;
    }

    public void setMembers(User[] members)
    {
        this.members = members;
    }

    public User getAdmin()
    {
        return admin;
    }

    public void setAdmin(User admin)
    {
        this.admin = admin;
    }
}
