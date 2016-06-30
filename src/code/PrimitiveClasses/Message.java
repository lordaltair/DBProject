package code.PrimitiveClasses;

import java.sql.Time;

public class Message
{
    private String msg;
    User sender;
    Time timeSent;

    public Message(String msg)
    {
        this.msg = msg;
    }
}
