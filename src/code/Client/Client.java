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

        inFromServer.readUTF();

        friendlist = new FriendList();
        friendlist.parsJsonObj();

        //insert kardan friendlist be UI
        // va sepas kole ui ra inja neshan bedahim

    }

    private FriendList jsontonormalfriendlist(String str ,String username) {
//
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

        JSONObject object = new JSONObject();
        object.put("username", username);
        JSONArray array = new JSONArray();
        array.add(object);

        obj.put("command" , START_CHAT);
        obj.put("arg" , array);

        obj.writeJSONString(out);
        result = out.toString();
        return result;

        // safe kari badesh biad ke bere toye chat
    }

    private Time getmessage(String str) throws IOException {
        String result;
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
                User sender = new User(objmessage.get("name").toString(),null);
                String strtime = objmessage.get("timeSent").toString();
                Time time = Time.valueOf(strtime);
                message.setMsg(objmessage.get("msg").toString());
                message.setSender(sender);
                message.setTimeSent(time);
                messages[i] = message;
            }
            lasttime = messages[messages.length].getTimeSent();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // ferestadane messages be UI va neshan dadan payam ha
        return lasttime;
    }
    private String unfriend(String username) throws IOException {
        String result;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject object = new JSONObject();
        object.put("username", username);
        JSONArray array = new JSONArray();
        array.add(object);

        obj.put("command" , UNFRIEND);
        obj.put("arg" , array);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private void setfriendlist(String me, String str) throws IOException {
        String result = null;

        friendlist = new FriendList();
        friendlist = jsontonormalfriendlist(str , me);

        //insert kardan friendlist be UI
        // va sepas kole ui ra inja neshan bedahim

    }

    private String moremessage(String username , Time time) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("time" , time);
        JSONArray array = new JSONArray();
        array.add(object);

        obj.put("command" , MORE_MESSAGE);
        obj.put("arg" , array);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String getmyprofiledetail(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject object = new JSONObject();
        object.put("username", username);
        JSONArray array = new JSONArray();
        array.add(object);

        obj.put("command" , GET_PROFILE_DETAIL);
        obj.put("arg" , array);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String report(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject object = new JSONObject();
        object.put("username", username);
        JSONArray array = new JSONArray();
        array.add(object);

        obj.put("command" , REPORT);
        obj.put("arg" , array);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String clientsendnewmsg(String username , String str, Time time) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("msg",str);
        object.put("time",time);
        JSONArray array = new JSONArray();
        array.add(object);

        obj.put("command" , CLIENT_SEND_NEW_MSG);
        obj.put("arg" , array);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String startprivatechat(String username , int deletetime) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("time",deletetime);
        JSONArray array = new JSONArray();
        array.add(object);

        obj.put("command" , START_PV_CHAT);
        obj.put("arg" , array);

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
