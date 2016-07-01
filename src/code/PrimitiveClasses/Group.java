package code.PrimitiveClasses;

import org.json.simple.JSONObject;

public class Group extends BasicChat
{
    private User[] memmbers;

    public Group()
    {
        super();
    }

    public Group(String title)
    {
        super(title);
    }

    public User[] getMemmbers() {
        return memmbers;
    }

    public void setMemmbers(User[] memmbers) {
        this.memmbers = memmbers;
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
