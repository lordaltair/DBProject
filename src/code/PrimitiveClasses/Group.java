package code.PrimitiveClasses;

public class Group extends BasicChat
{
    public User[] getMemmbers() {
        return memmbers;
    }

    public void setMemmbers(User[] memmbers) {
        this.memmbers = memmbers;
    }

    private User[] memmbers;
    public Group(String title)
    {
        super(title);
    }
}
