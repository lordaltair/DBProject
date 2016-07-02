package code.PrimitiveClasses;

import org.json.simple.JSONObject;

public class User implements JsonParsable
{
    private String name;
    private String username;

    public User(String name, String username)
    {
        this.name = name;
        this.username = username;
    }

    public User() {
        name=null;
        username=null;

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public JSONObject toJsonObj()
    {
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("username", username);
        return result;
    }

    @Override
    public void parsJsonObj(JSONObject obj)
    {
        name = obj.get("name").toString();
        username = obj.get("username").toString();
    }
}
