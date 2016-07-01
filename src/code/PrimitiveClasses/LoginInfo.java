package code.PrimitiveClasses;

import org.json.simple.JSONObject;

/**
 * Created by Ali on 7/1/2016.
 */
public class LoginInfo implements JsonParsable{
    private String username;
    private String password;

    public LoginInfo(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public JSONObject toJsonObj()
    {
        return null;
    }

    @Override
    public void parsJsonObj(JSONObject obj) {

    }
}
