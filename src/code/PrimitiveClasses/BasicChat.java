package code.PrimitiveClasses;

public class BasicChat
{
    private String title;
    private User[] members;

    public BasicChat(String title, User[] members)
    {
        this.members = members;
        this.title = title;
    }

    public User[] getMembers()
    {
        return members;
    }

    public void setMembers(User[] members)
    {
        this.members = members;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
