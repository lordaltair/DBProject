package code.Server.Parse;

import code.COMMAND_CODES;
import code.PrimitiveClasses.FriendList;
import code.PrimitiveClasses.LoginInfo;
import code.PrimitiveClasses.Profile;
import code.PrimitiveClasses.User;
import code.Server.DataBase.MongoDBJDBC;
import code.Server.Network.ClientTCPConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandParser
{
    private static final Method[] methods = new Method[COMMAND_CODES.NUMBER_OF_COMMANDS];

    static
    {
        try
        {
            methods[COMMAND_CODES.GET_FRIEND_LIST] = CommandParser.class.getMethod("GET_FRIEND_LIST", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.START_CHAT] = CommandParser.class.getMethod("START_CHAT", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.UNFRIEND] = CommandParser.class.getMethod("UNFRIEND", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.MORE_MESSAGE] = CommandParser.class.getMethod("MORE_MESSAGE", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.GET_PROFILE_DETAIL] = CommandParser.class.getMethod("GET_PROFILE_DETAIL", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.REPORT] = CommandParser.class.getMethod("REPORT", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.CLIENT_SEND_NEW_MSG] = CommandParser.class.getMethod("CLIENT_SEND_NEW_MSG", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.SERVER_BROADCAST_NEW_MSG] = CommandParser.class.getMethod("SERVER_BROADCAST_NEW_MSG", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.START_PV_CHAT] = CommandParser.class.getMethod("START_PV_CHAT", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.GET_GROUP_MEMBERS] = CommandParser.class.getMethod("GET_GROUP_MEMBERS", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.CLIENT_MENTION] = CommandParser.class.getMethod("CLIENT_MENTION", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.SERVER_MENTION] = CommandParser.class.getMethod("SERVER_MENTION", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.CLIENT_UNMENTION] = CommandParser.class.getMethod("CLIENT_UNMENTION", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.SEARCH_QUERY] = CommandParser.class.getMethod("SEARCH_QUERY", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.SEARCH_USER] = CommandParser.class.getMethod("SEARCH_USER", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.ADD_TO_FRIEND_LIST] = CommandParser.class.getMethod("ADD_TO_FRIEND_LIST", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.CREATE_GROUP] = CommandParser.class.getMethod("CREATE_GROUP", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.LEAVE_GROUP] = CommandParser.class.getMethod("LEAVE_GROUP", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.CREATE_CHANEL] = CommandParser.class.getMethod("CREATE_CHANEL", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.LEAVE_CHANEL] = CommandParser.class.getMethod("LEAVE_CHANEL", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.UPDATE_PROFILE] = CommandParser.class.getMethod("UPDATE_PROFILE", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.SIGN_UP] = CommandParser.class.getMethod("SIGN_UP", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.LOGIN] = CommandParser.class.getMethod("LOGIN", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.LOGOUT] = CommandParser.class.getMethod("LOGOUT", JSONObject.class, ClientTCPConnection.class);
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    private MongoDBJDBC dbManager;

    public CommandParser()
    {
        dbManager = new MongoDBJDBC();
    }

    public void parse(String command, ClientTCPConnection clientTCPConnection)
    {
        JSONParser parser = new JSONParser();
        try
        {
            JSONObject obj = (JSONObject) parser.parse(command);
            int commCode = Integer.parseInt(String.valueOf(obj.get("command")));
            methods[commCode].invoke(this, obj.get("arg"), clientTCPConnection);
        } catch (ParseException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }


    public void GET_FRIEND_LIST(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        FriendList friendList = new FriendList();
        //todo get friend list from mongodb
        clientTCPConnection.send(friendList.toJsonObj());
    }

    public void START_CHAT(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User user = new User();
        user.parsJsonObj((JSONObject) args.get("user"));
        // TODO: 7/1/16 to be done
    }

    public void UNFRIEND(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void MORE_MESSAGE(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void GET_PROFILE_DETAIL(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void REPORT(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CLIENT_SEND_NEW_MSG(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SERVER_BROADCAST_NEW_MSG(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void START_PV_CHAT(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void GET_GROUP_MEMBERS(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CLIENT_MENTION(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SERVER_MENTION(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CLIENT_UNMENTION(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SEARCH_USER(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SEARCH_QUERY(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void ADD_TO_FRIEND_LIST(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User friend = new User();
        friend.parsJsonObj((JSONObject) args.get("user"));
        // TODO: 7/1/16 add to db
//        dbManager.add_a_friend(clientTCPConnection.getUser().getUsername(), friend.getUsername());
        FriendList friendList = null;
        // TODO: 7/1/16 get friend list
        clientTCPConnection.send(friendList.toJsonObj());
    }

    public void CREATE_GROUP(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void LEAVE_GROUP(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CREATE_CHANEL(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void LEAVE_CHANEL(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void UPDATE_PROFILE(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SIGN_UP(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Profile profile = new Profile();
        profile.parsJsonObj((JSONObject) args.get("profile"));
        dbManager.add_a_user(profile.getUser().getName(), profile.getLastName(),
                profile.getUser().getUsername(), profile.getPassword(),
                profile.getEmail(), profile.getPhoneNumber(), profile.getBioDescription());
        JSONObject response = new JSONObject();
        response.put("ack", true);
        clientTCPConnection.send(response);
    }

    public void LOGIN(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.parsJsonObj((JSONObject) args.get("logininfo"));
        String pass = null;
        pass = dbManager.find_username_pass(loginInfo.getUsername());
        JSONObject response = new JSONObject();
        boolean accepted = false;
        if (pass != null && pass.equalsIgnoreCase(loginInfo.getPassword()))
        {
            clientTCPConnection.setUser(new User("", loginInfo.getUsername()));
            accepted = true;
        }
        response.put("ack", accepted);
        clientTCPConnection.send(response);

    }

    public void LOGOUT(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

}
