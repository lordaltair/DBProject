package code.PrimitiveClasses;

import java.sql.Time;

public class Message
{
    User sender;
    Time timeSent;
    private String msg;

    public Message(String msg)
    {
        this.msg = msg;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public User getSender()
    {
        return sender;
    }

    public void setSender(User sender)
    {
        this.sender = sender;
    }

    public Time getTimeSent()
    {
        return timeSent;
    }

    public void setTimeSent(Time timeSent)
    {
        this.timeSent = timeSent;
    }
}
