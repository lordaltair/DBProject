package code.PrimitiveClasses;

import org.json.simple.JSONObject;

import java.sql.Time;

public class Message implements JsonParsable
{
    User sender;
    Time timeSent;
    User chatUsername;
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

    public User getChatUsername()
    {
        return chatUsername;
    }

    public void setChatUsername(User chatUsername)
    {
        this.chatUsername = chatUsername;
    }

    @Override
    public JSONObject toJsonObj() {
        return null;
    }

    @Override
    public void parsJsonObj(JSONObject obj) {
        User sender = new User(obj.get("name").toString(),null);
        String strtime = obj.get("timeSent").toString();
        Time time = Time.valueOf(strtime);
        setMsg(obj.get("msg").toString());
        setSender(sender);
        setTimeSent(time);
    }
}
