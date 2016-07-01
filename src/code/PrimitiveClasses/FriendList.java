package code.PrimitiveClasses;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FriendList implements JsonParsable
{
    private User user;
    private User[] friends;
    private User[] unknownFriends;
    private Group[] groups;
    private Chanel[] chanels;
    private PrivateChat[] privateChats;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User[] getFriends()
    {
        return friends;
    }

    public void setFriends(User[] friends)
    {
        this.friends = friends;
    }

    public User[] getUnknownFriends()
    {
        return unknownFriends;
    }

    public void setUnknownFriends(User[] unknownFriends)
    {
        this.unknownFriends = unknownFriends;
    }

    public Group[] getGroups()
    {
        return groups;
    }

    public void setGroups(Group[] groups)
    {
        this.groups = groups;
    }

    public Chanel[] getChanels()
    {
        return chanels;
    }

    public void setChanels(Chanel[] chanels)
    {
        this.chanels = chanels;
    }

    @Override
    public JSONObject toJsonObj()
    {
        return null;
    }

    @Override
    public void parsJsonObj(JSONObject obj)
    {
        JSONParser parser=new JSONParser();
            JSONObject obj1 = (JSONObject) obj;

        user = new User();
        user.parsJsonObj((JSONObject) obj1.get("user"));

        JSONArray arrayfriends = (JSONArray) obj1.get("friends");
        User[] friends = new User[arrayfriends.size()];
        for(int i=0;i < arrayfriends.size();i++) {
            JSONObject objuser = (JSONObject)arrayfriends.get(i);
            friends[i].setUsername(objuser.get("user").toString());
            friends[i].setName(objuser.get("name").toString());
        }
       setFriends(friends);

        JSONArray arrayunknown = (JSONArray) obj1.get("unknown");
        User[] unknown = new User[arrayunknown.size()];
        for(int i=0;i < arrayunknown.size();i++) {
            JSONObject objuser = (JSONObject)arrayunknown.get(i);
            unknown[i].setUsername(objuser.get("user").toString());
            unknown[i].setName(objuser.get("name").toString());
        }
        setUnknownFriends(unknown);

        JSONArray arraygroup = (JSONArray) obj1.get("group");
        Group[] groups = new Group[arraygroup.size()];
        for(int i=0;i < arraygroup.size();i++) {
            JSONObject objuser = (JSONObject) arraygroup.get(i);
            groups[i].setTitle(objuser.get("title").toString());
        }
        setGroups(groups);

        JSONArray arraychannel = (JSONArray) obj1.get("channel");
        Chanel[] channels = new Chanel[arraychannel.size()];
        for(int i=0;i < arraychannel.size();i++) {
            JSONObject objuser = (JSONObject)arraychannel.get(i);
            channels[i].setTitle(objuser.get("title").toString());
        }
        setChanels(channels);

        }
    }

//{
//    "user":"Danial",
//    "friends":
//    [
//        {"user":"alim","name":"ali"},
//        {"user":"alim2","name":"ali2"}
//    ],
//    "unknownFriends" : null,
//    "groups" :
//    [
//        {"title" : "ceit", "members" :
//            [
//                {"user":"alim","name":"ali"},
//                {"user":"alim2","name":"ali2"}
//            ]
//        }
//    ],
//    "chanels" : null
//}