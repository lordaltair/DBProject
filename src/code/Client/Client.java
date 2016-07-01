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

import static code.COMMAND_CODES.*;

public class Client {
    FriendList friendlist;
    String clientname;
    Time lassttime;
    public Client(Socket clientSocket , String username) throws IOException {
        //initial variables
        String modifiedSentence;
        this.clientname = username;
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        modifiedSentence=getfriendlist();
        outToServer.writeUTF(modifiedSentence);
        outToServer.flush();

        modifiedSentence = inFromServer.readUTF();

        JSONParser parser=new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(modifiedSentence);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonobj = (JSONObject) obj;

        friendlist = new FriendList();
        friendlist.parsJsonObj(jsonobj);

        //insert kardan friendlist be UI
        // va sepas kole ui ra inja neshan bedahim

    }

    private Time getmessage(String str) throws IOException {
        Message[] messages;
        JSONParser parser=new JSONParser();
        Time lasttime = null;
        try {
            Object obj = parser.parse(str);
            JSONArray array = (JSONArray) obj;
            messages= new Message[array.size()];
            for(int i=0;i<array.size();i++) {
                Message message = new Message(null);
                JSONObject objmessage = (JSONObject) array.get(i);
                message.parsJsonObj(objmessage);
                messages[i] = message;
            }
            lasttime = messages[messages.length].getTimeSent();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // ferestadane messages be UI va neshan dadan payam ha
        return lasttime;
    }

    private String getfriendlist() throws IOException {
        String result;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , GET_FRIEND_LIST);
        obj.put("arg" , null);

        obj.writeJSONString(out);
        result = out.toString();
        return result;

        // safe kari badesh biad ke bere toye chat
    }

    private String startchat(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        JSONObject args = new JSONObject();
        args.put("user", user);

        obj.put("command" , START_CHAT);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;

        // safe kari badesh biad ke bere toye chat
    }

    private String unfriend(String username) throws IOException {
        String result;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        JSONObject args = new JSONObject();
        args.put("user", user);

        obj.put("command" , UNFRIEND);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private void setfriendlist(JSONObject obj) throws IOException {
        String result = null;

        friendlist = new FriendList();
        friendlist.parsJsonObj(obj);

        //insert kardan friendlist be UI
        // va sepas kole ui ra inja neshan bedahim

    }

    private String moremessage(String username , Time time) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);

        MoreMessage mm = new MoreMessage();
        mm.setUser(user);
        mm.setLasttime(time);
        JSONObject args = new JSONObject();
        args.put("user", user);
        args.put("time", time);

        obj.put("command" , MORE_MESSAGE);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String getmyprofiledetail(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        JSONObject args = new JSONObject();
        args.put("user", user);

        obj.put("command" , GET_PROFILE_DETAIL);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String report(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        JSONObject args = new JSONObject();
        args.put("user", user);

        obj.put("command" , REPORT);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String clientsendnewmsg(String username , String str, Time time) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        Message m = new Message(str);
        m.setSender(user);
        m.setTimeSent(time);
        JSONObject args = new JSONObject();
        args.put("message", m);

        obj.put("command" , CLIENT_SEND_NEW_MSG);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String startprivatechat(String username , int deletetime) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        PrivateChat pv = new PrivateChat(user,deletetime);
        JSONObject args = new JSONObject();
        args.put("privatechat", pv);

        obj.put("command" , START_PV_CHAT);
        obj.put("arg" , args);

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

    private String clientmention(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        JSONObject args = new JSONObject();
        args.put("user", user);

        obj.put("command" , CLIENT_MENTION);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String searchuser(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        JSONObject args = new JSONObject();
        args.put("user", user);

        obj.put("command" , CLIENT_MENTION);
        obj.put("arg" , args);obj.put("command" , 14);
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

        JSONObject object = new JSONObject();
        object.put("me", me);
        object.put("title", title);
        JSONArray array = new JSONArray();
        array.add(object);

        obj.put("command" , 18);
        obj.put("arg" , array);

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
