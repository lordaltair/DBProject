package code.PrimitiveClasses;

public class Chanel extends BasicChat
{
    private User admin;

    public Chanel(String title)
    {
        super(title);
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