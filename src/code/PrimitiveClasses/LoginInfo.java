package code.PrimitiveClasses;

import org.json.simple.JSONObject;

public class LoginInfo implements JsonParsable{
    private String username;
    private String password;

    public LoginInfo()
    {

    }

    public LoginInfo(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public JSONObject toJsonObj()
    {
        JSONObject result = new JSONObject();
        result.put("username", username);
        result.put("password", password);
        return result;
    }

    @Override
    public void parsJsonObj(JSONObject obj)
    {
        username = obj.get("username").toString();
        password = obj.get("password").toString();
    }
}
