package code.Client;

import code.PrimitiveClasses.*;
import com.google.gson.Gson;
import gui.First;
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

    public FriendList friendlist;
    String clientname;
    public Time lasttime;
    First ui;
    public DataOutputStream outToServer;
    public DataInputStream inFromServer;


    public Client(Socket clientSocket, String username, DataOutputStream outToServer, DataInputStream inFromServer, First ui) throws IOException
    {
        //initial variables
        this.outToServer = outToServer;
        this.inFromServer = inFromServer;
        this.ui = ui;
        String modifiedSentence;
        this.clientname = username;

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

        Gson gson = new Gson();
        jsonobj.writeJSONString(new StringWriter());
        this.friendlist = gson.fromJson(jsonobj.toString(), FriendList.class);
        ui.updateFriendList(friendlist);


    }

    private int getdeletetime(String str){
        JSONParser parser=new JSONParser();
        int deletetime;
        Object obj = null;
        try {
            obj = parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonobj = (JSONObject)obj;
        String timestr = jsonobj.get("time").toString();
        deletetime = Integer.parseInt(timestr);
        return deletetime;
    }
    public Time getmessage(String str) throws IOException {
        Message[] messages;
        Time lasttime = null;
        Conversation conversation = new Gson().fromJson(str, Conversation.class);
        messages = conversation.getMessages();
        lasttime = messages[messages.length].getTimeSent();
        ui.textArea1.setEditable(false);
        for(int i=0;i<messages.length;i++){
            String strtmp = messages[i].getSender() + " : " + messages[i].getMsg() + "      time : " + messages[i].getTimeSent() + "\n";
            ui.textArea1.append(strtmp);
        }

        return lasttime;
    }

    private boolean ackjsontonormal(String sendrecievestr) {
        String result = null;
        JSONParser parser = new JSONParser();

        try {
            System.out.println(sendrecievestr);
            Object obj = parser.parse(sendrecievestr);
            JSONObject obj2 = (JSONObject) obj;
            result = obj2.get("ack").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result.equals("true");
    }

    public String getfriendlist() throws IOException {
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

    public String startchat(String username) throws IOException {
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

    public String unfriend(String username) throws IOException {
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

        //insert kardan friendlist be UI
        // va sepas kole ui ra inja neshan bedahim

    }

    public String moremessage(String username , Time time) throws IOException {
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

    private String clientunmention(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        JSONObject args = new JSONObject();
        args.put("user", user);

        obj.put("command" , CLIENT_UNMENTION);
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

        obj.put("command" , SEARCH_USER);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String searchquery(String query) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        Query q = new Query(query);
        JSONObject args = new JSONObject();
        args.put("query", q);

        obj.put("command" , SEARCH_QUERY);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String addtofriend(String username) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        User user = new User();
        user.setUsername(username);
        JSONObject args = new JSONObject();
        args.put("user", user);

        obj.put("command" , ADD_TO_FRIEND_LIST);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String creategroup(Group group) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject args = new JSONObject();
        args.put("group", group);

        obj.put("command" , CREATE_GROUP);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String leavegroup(String title) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        Group group = new Group(title);
        JSONObject args = new JSONObject();
        args.put("group", group);

        obj.put("command" , LEAVE_GROUP);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String createchannel(Channel channel) throws IOException
    {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject args = new JSONObject();
        args.put("channel", channel);

        obj.put("command" , CREATE_CHANEL);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String leavechannel(String title) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        Channel channel = new Channel(title);
        JSONObject args = new JSONObject();
        args.put("channel", channel);

        obj.put("command" , LEAVE_CHANEL);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String updateprofile(Profile profile) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject args = new JSONObject();

        args.put("profile", profile.toJsonObj());

        obj.put("command" , UPDATE_PROFILE);
        obj.put("arg" , args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String logout() throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , LOGOUT);
        obj.put("arg" , null);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

}
