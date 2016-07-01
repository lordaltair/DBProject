package code.PrimitiveClasses;

public class PrivateChat extends BasicChat
{
    int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public PrivateChat(String title , int time)
    {
        super(title);
        this.time  = time;
    }
}
