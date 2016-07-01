package code.PrimitiveClasses;

public class Chanel extends BasicChat
{
    private User admin;

    public Chanel(String title, User[] members)
    {
        super(title, members);
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