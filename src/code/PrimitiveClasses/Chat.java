package code.PrimitiveClasses;

import org.json.simple.JSONObject;

public class Chat extends BasicChat
{
    public Chat(String title)
    {
        super(title);
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
