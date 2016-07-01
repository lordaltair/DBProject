package code.Client;

import code.PrimitiveClasses.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.Socket;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        obj.put("command" , 1);
        obj.put("arg1" , null);
        obj.put("arg2" , null);

        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        modifiedSentence = out.toString();

        outToServer.writeUTF(modifiedSentence);
        outToServer.flush();

        inFromServer.readUTF();

        friendlist = new FriendList();
        friendlist = normaltojsonfriendlist(modifiedSentence , username);

        //insert kardan friendlist be UI
        // va sepas kole ui ra inja neshan bedahim

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

    private String startchat(String me, String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 2);
        obj.put("arg1" , me);
        obj.put("arg2" , username);

        obj.writeJSONString(out);
        result = out.toString();
        return result;

        // safe kari badesh biad ke bere toye chat
    }

    private void getmessage(String str) throws IOException {
        String result = null;
        Message[] messages;
        JSONParser parser=new JSONParser();
        try {
            Object obj = parser.parse(str);
            JSONArray array = (JSONArray) obj;
            messages= new Message[array.size()];
            for(int i=0;i<array.size();i++) {
                Message message = new Message(null);
                JSONObject objmessage = (JSONObject) array.get(i);
                User sender = new User(objmessage.get("name").toString(),null);
                String strtime = objmessage.get("timeSent").toString();
                Time time = Time.valueOf(strtime);
                message.setMsg(objmessage.get("msg").toString());
                message.setSender(sender);
                message.setTimeSent(time);
                messages[i] = message;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // ferestadane messages be UI va neshan dadan payam ha
    }
    private String unfriend(String me, String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 3);
        obj.put("arg1" , me);
        obj.put("arg2" , username);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private void setfriendlist(String me, String str) throws IOException {
        String result = null;

        friendlist = new FriendList();
        friendlist = normaltojsonfriendlist(str , me);

        //insert kardan friendlist be UI
        // va sepas kole ui ra inja neshan bedahim

    }

    private String moremessage(String me, String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Time time = Time.valueOf(sdf.format(cal.getTime()));

        obj.put("command" , 4);
        obj.put("arg1" , me);
        obj.put("arg2" , username);
        obj.put("arg3" , time);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String getmyprofiledetail(String me) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 5);
        obj.put("arg1" , me);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String getprofiledetail(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 6);
        obj.put("arg1" , username);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String report(String me,String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 7);
        obj.put("arg1" , me);
        obj.put("arg2" , username);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String clientsendnewmsg(String me,String username , String str, Time time) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 8);
        obj.put("arg1" , me);
        obj.put("arg2" , username);
        obj.put("arg3" , str);
        obj.put("arg4" , time);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String startprivatechat(String me,String username , int deletetime) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 10);
        obj.put("arg1" , me);
        obj.put("arg2" , username);
        obj.put("arg3" , deletetime);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

//    private String getgrouplist(String me,String username , int deletetime) throws IOException {
//        String result = null;
//        JSONObject obj = new JSONObject();
//        StringWriter out = new StringWriter();
//
//        obj.put("command" , 10);
//        obj.put("arg1" , me);
//        obj.put("arg2" , username);
//        obj.put("arg3" , deletetime);
//
//        obj.writeJSONString(out);
//        result = out.toString();
//        return result;
//    }

    private String clientmention(String me,String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 12);
        obj.put("arg1" , me);
        obj.put("arg2" , username);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String searchuser(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 14);
        obj.put("arg1" , username);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String searchquery(String query) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 15);
        obj.put("arg1" , query);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String addtofriend(String me,String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 16);
        obj.put("arg1" , me);
        obj.put("arg1" , username);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String creategroup(Group group) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 17);
        obj.put("arg1" , group);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String leavegroup(String me,String title) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 18);
        obj.put("arg1" , me);
        obj.put("arg2" , title);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String createchannel(Chanel chanel) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 19);
        obj.put("arg1" , chanel);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String leavechannel(String me,String title) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 20);
        obj.put("arg1" , me);
        obj.put("arg2" , title);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String updateprofile(String me,Profile profile) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 21);
        obj.put("arg1" , me);
        obj.put("arg2" , profile);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String logout() throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 24);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

}
