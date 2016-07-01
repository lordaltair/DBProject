package code.PrimitiveClasses;

import org.json.simple.JSONObject;

public class Channel extends BasicChat
{
    private User admin;

    public Channel(String title)
    {
        super(title);
    }

    public Channel()
    {
        super();
    }

    public User getAdmin()
    {
        return admin;
    }

    public void setAdmin(User admin)
    {
        this.admin = admin;
    }

    @Override
    public JSONObject toJsonObj()
    {
        return null;
    }

    @Override
    public void parsJsonObj(JSONObject obj)
    {
        super.parsJsonObj(obj);
    }
}