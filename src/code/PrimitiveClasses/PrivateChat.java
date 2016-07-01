package code.PrimitiveClasses;

public class PrivateChat
{
    int deletetime;
    User user;
    public int getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(int deletetime) {
        this.deletetime = deletetime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PrivateChat(User user , int deletetime)
    {
        this.deletetime = deletetime;
        this.user = user;
    }
}
