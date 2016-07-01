package code.Server.Parse;

import code.COMMAND_CODES;
import code.PrimitiveClasses.FriendList;
import code.Server.Network.ClientTCPConnection;
import org.json.simple.JSONArray;
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
            methods[COMMAND_CODES.SEARCH] = CommandParser.class.getMethod("SEARCH", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.ADD_TO_FREIND_LIST] = CommandParser.class.getMethod("ADD_TO_FREIND_LIST", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.CREATE_GROUP] = CommandParser.class.getMethod("CREATE_GROUP", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.LEAVE_GROUP] = CommandParser.class.getMethod("LEAVE_GROUP", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.CREATE_CHANEL] = CommandParser.class.getMethod("CREATE_CHANEL", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.LEAVE_CHANEL] = CommandParser.class.getMethod("LEAVE_CHANEL", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.UPDATE_PROFILE] = CommandParser.class.getMethod("UPDATE_PROFILE", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.SIGN_UP] = CommandParser.class.getMethod("SIGN_UP", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.LOGIN] = CommandParser.class.getMethod("LOGIN", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.LOGOUT] = CommandParser.class.getMethod("LOGOUT", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.NUMBER_OF_COMMANDS] = CommandParser.class.getMethod("NUMBER_OF_COMMANDS", JSONObject.class, ClientTCPConnection.class);
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    public void parse(String command, ClientTCPConnection clientTCPConnection)
    {
        JSONParser parser = new JSONParser();
        try
        {
            JSONObject obj = (JSONObject) parser.parse(command);
            int commCode = Integer.parseInt(String.valueOf(obj.get("command")));
            methods[commCode].invoke(this, obj.get("args"), clientTCPConnection);
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


    public void GET_FRIEND_LIST(JSONArray args, ClientTCPConnection clientTCPConnection)
    {
        //todo get friend list from mongodb
        FriendList friendList = new FriendList();
        clientTCPConnection.send(friendList.toJsonObj());
    }

    public void START_CHAT(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void UNFRIEND(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void MORE_MESSAGE(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void GET_PROFILE_DETAIL(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void REPORT(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CLIENT_SEND_NEW_MSG(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SERVER_BROADCAST_NEW_MSG(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void START_PV_CHAT(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void GET_GROUP_MEMBERS(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CLIENT_MENTION(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SERVER_MENTION(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CLIENT_UNMENTION(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SEARCH(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void ADD_TO_FREIND_LIST(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CREATE_GROUP(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void LEAVE_GROUP(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CREATE_CHANEL(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void LEAVE_CHANEL(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void UPDATE_PROFILE(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void SIGN_UP(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void LOGIN(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void LOGOUT(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void NUMBER_OF_COMMANDS(JSONArray args, ClientTCPConnection clientTCPConnection)
    {

    }


}
