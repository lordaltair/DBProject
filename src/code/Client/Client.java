package code.Client;

import code.PrimitiveClasses.Chanel;
import code.PrimitiveClasses.FriendList;
import code.PrimitiveClasses.Group;
import code.PrimitiveClasses.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.Socket;

public class Client {
    FriendList friendlist;
    String clientname;
    public Client(Socket clientSocket , String username) throws IOException {
        //initial variables
        String modifiedSentence = null;
        this.clientname = username;
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        JSONObject obj = new JSONObject();
        obj.put("command" , "1");
        obj.put("args" , null);

        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        modifiedSentence = out.toString();

        outToServer.writeUTF(modifiedSentence);
        outToServer.flush();

        FriendList fl = new FriendList();
        inFromServer.readUTF();

        friendlist = new FriendList();
        friendlist = normaltojsonfriendlist(modifiedSentence , username);

        //insert kardan friendlist be UI


    }

    private FriendList normaltojsonfriendlist(String str ,String username) {
        String result = null;
        FriendList friendlist = new FriendList();
        JSONParser parser=new JSONParser();
        try {
            Object obj = parser.parse(str);
            JSONArray array = (JSONArray)obj;

            JSONObject objme = (JSONObject)array.get(0);
            User me = new User(objme.get("user").toString() , username);

            Object objfriends = array.get(1);
            JSONArray arrayfriends = (JSONArray)objfriends;
            User[] friends = new User[arrayfriends.size()];
            for(int i=0;i < arrayfriends.size();i++) {
                JSONObject objuser = (JSONObject)arrayfriends.get(i);
                friends[i].setUsername(objuser.get("username").toString());
                friends[i].setName(objuser.get("name").toString());
            }
            friendlist.setFriends(friends);

            Object objunknown = array.get(2);
            JSONArray arrayunknown = (JSONArray)objunknown;
            User[] unknown = new User[arrayunknown.size()];
            for(int i=0;i < arrayunknown.size();i++) {
                JSONObject objuser = (JSONObject)arrayunknown.get(i);
                unknown[i].setUsername(objuser.get("username").toString());
                unknown[i].setName(objuser.get("name").toString());
            }
            friendlist.setUnknownFriends(unknown);

            Object objgroup = array.get(3);
            JSONArray arraygroup = (JSONArray)objgroup;
            Group[] groups = new Group[arraygroup.size()];
            for(int i=0;i < arraygroup.size();i++) {
                JSONObject objuser = (JSONObject)arraygroup.get(i);
                groups[i].setTitle(objuser.get("title").toString());

                Object memberss = objuser.get("members");
                JSONArray arraymembers = (JSONArray)memberss;
                User[] members = new User[arraymembers.size()];
                for(int j=0;j < arraymembers.size();j++) {
                    JSONObject objuserss = (JSONObject)arrayunknown.get(j);
                    members[i].setUsername(objuserss.get("username").toString());
                    members[i].setName(objuserss.get("name").toString());
                }
                groups[i].setMembers(members);
            }
            friendlist.setGroups(groups);

            Object objchannel = array.get(4);
            JSONArray arraychannel = (JSONArray)objchannel;
            Chanel[] channels = new Chanel[arraychannel.size()];
            for(int i=0;i < arraychannel.size();i++) {
                JSONObject objuser = (JSONObject)arraychannel.get(i);
                channels[i].setTitle(objuser.get("title").toString());
            }
            friendlist.setChanels(channels);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return friendlist;
    }
}
