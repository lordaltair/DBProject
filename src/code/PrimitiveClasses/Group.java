package code.PrimitiveClasses;

public class Group extends BasicChat
{
    private User[] memmbers;

    public Group(String title)
    {
        super(title);
    }

    public User[] getMemmbers() {
        return memmbers;
    }

    public void setMemmbers(User[] memmbers) {
        this.memmbers = memmbers;
    }
}
