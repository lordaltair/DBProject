package code.PrimitiveClasses;

import org.json.simple.JSONObject;

public class BasicChat implements JsonParsable
{

    private String title;

    public BasicChat(String title)
    {
        this.title = title;
    }

    public BasicChat()
    {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public JSONObject toJsonObj()
    {
        return null;
    }

    @Override
    public void parsJsonObj(JSONObject obj)
    {
        setTitle(obj.get("title").toString());
    }
}
