package code.PrimitiveClasses;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Group extends BasicChat implements JsonParsable
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

    private User[] memmbers;
    public Group(String title)
    {
        super(title);
    }

    @Override
    public JSONObject toJsonObj() {
        return null;
    }

    @Override
    public void parsJsonObj(JSONObject obj) {
        Group group = new Group(obj.get("title").toString());
            JSONArray membersarray = (JSONArray) obj.get("members");
        User[] thismembers = new User[membersarray.size()];
        for(int i=0; i<membersarray.size();i++){
            thismembers[i].setUsername(membersarray.get(i).toString());
        }
            group.setMemmbers(thismembers);

    }
}
