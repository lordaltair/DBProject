package code.Server.Parse;

import code.COMMAND_CODES;
import code.PrimitiveClasses.*;
import code.Server.DataBase.MongoDBJDBC;
import code.Server.Network.ClientTCPConnection;
import code.Server.Network.ServerTCPListener;
import com.google.gson.Gson;
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
            methods[COMMAND_CODES.START_PV_CHAT] = CommandParser.class.getMethod("START_PV_CHAT", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.GET_GROUP_MEMBERS] = CommandParser.class.getMethod("GET_GROUP_MEMBERS", JSONObject.class, ClientTCPConnection.class);
            methods[COMMAND_CODES.CLIENT_MENTION] = CommandParser.class.getMethod("CLIENT_MENTION", JSONObject.class, ClientTCPConnection.class);
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

    private MongoDBJDBC mongoDb;

    public CommandParser()
    {
        mongoDb = new MongoDBJDBC();
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
        FriendList friendList = null;
        friendList = mongoDb.get_friend_list(clientTCPConnection.getUser().getUsername());
        clientTCPConnection.sendObject(friendList);
    }

    public void START_CHAT(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User chatUsername = new User();
        chatUsername.parsJsonObj((JSONObject) args.get("user"));
        // TODO: 7/1/16 search usernames
        // TODO: 7/2/16 search group and channel titles
        // TODO: 7/2/16 create conversation
        Conversation conversation = null;
        clientTCPConnection.sendObject(conversation);

    }

    public void UNFRIEND(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User friend = new Gson().fromJson(args.get("user").toString(), User.class);
        // TODO: 7/2/16 mdb unfriend
        FriendList friendList = mongoDb.get_friend_list(clientTCPConnection.getUser().getUsername());
        clientTCPConnection.sendObject(friendList);
    }

    public void MORE_MESSAGE(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        MoreMessage moreMessage = new Gson().fromJson(args.get("moremessage").toString(), MoreMessage.class);
        // TODO: 7/1/16 search usernames
        // TODO: 7/2/16 search group and channel titles
        // TODO: 7/2/16 create conversation
        Conversation conversation = null;
        clientTCPConnection.sendObject(conversation);
    }

    public void GET_PROFILE_DETAIL(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User user = new Gson().fromJson(args.get("user").toString(), User.class);
        // TODO: 7/2/16 get profile from mongo
        Profile profile = null;
        clientTCPConnection.sendObject(profile);
    }

    public void REPORT(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User user = new Gson().fromJson(args.get("user").toString(), User.class);
        // TODO: 7/2/16 add to reports
    }

    public void CLIENT_SEND_NEW_MSG(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Message message = new Gson().fromJson(args.get("message").toString(), Message.class);
        for (String token : message.getMsg().split(" "))
        {
            if (token.startsWith("@"))
            {
                // todo if(database has it username)
                // mentionUser(dstinatio user, group)
            }

        }
        // TODO: 7/2/16 add new msg to mongo 
        // TODO: 7/2/16 create new conversation
        Conversation conversation = null;
        // TODO: 7/2/16 get members
        User[] members = null;
        for (User member : members)
        {
            ClientTCPConnection memberTCPConnection = ServerTCPListener.clients.get(member.getUsername());
            JSONObject asyncConversatoin = new JSONObject();
            asyncConversatoin.put("conversation", new Gson().toJson(conversation));
            memberTCPConnection.sendJsonObj(asyncConversatoin);
        }
    }

    public void START_PV_CHAT(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User chatUsername = new User();
        chatUsername.parsJsonObj((JSONObject) args.get("user"));
        // TODO: 7/1/16 search usernames
        // TODO: 7/2/16 create conversation
        Conversation conversation = null;
        clientTCPConnection.sendObject(conversation);
    }

    public void GET_GROUP_MEMBERS(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User chatUsername = new Gson().fromJson(args.get("user").toString(), User.class);
        // TODO: 7/2/16 get group members from db
        Group group = null;
        clientTCPConnection.sendObject(group);
    }

    public void CLIENT_MENTION(JSONObject args, ClientTCPConnection clientTCPConnection)
    {

    }

    public void CLIENT_UNMENTION(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User chatUsername = new Gson().fromJson(args.get("mention").toString(), User.class);
        // TODO: 7/2/16 unmention chat for user

    }

    public void SEARCH_USER(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User username = new Gson().fromJson(args.get("user").toString(), User.class);
        // TODO: 7/2/16 find usernames contain username
        User[] result = null;
        clientTCPConnection.sendObject(result);

    }

    public void SEARCH_QUERY(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Query query = new Gson().fromJson(args.get("query").toString(), Query.class);
        // TODO: 7/2/16 find usernames with corresponding username
        User[] result = null;
        clientTCPConnection.sendObject(result);
    }

    public void ADD_TO_FRIEND_LIST(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User friend = new User();
        friend.parsJsonObj((JSONObject) args.get("user"));
        // TODO: 7/1/16 add to db
//        mongoDb.add_a_friend(clientTCPConnection.getUser().getUsername(), friend.getUsername());
        FriendList friendList = null;
        // TODO: 7/1/16 get friend list
        clientTCPConnection.sendObject(friendList);
    }

    public void CREATE_GROUP(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Group group = new Gson().fromJson(args.get("group").toString(), Group.class);
        // TODO: 7/2/16 add group to db
        sendFriendListToUsers(group.getMemmbers());
    }

    public void LEAVE_GROUP(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Group group = new Gson().fromJson(args.get("group").toString(), Group.class);
        // TODO: 7/2/16 remove user from group in db
        // TODO: 7/2/16 get new group members
        User[] members = null;
        sendFriendListToUsers(members);
    }

    private void sendFriendListToUsers(User[] members)
    {
        for (User member : members)
        {
            FriendList friendList = null;
            // TODO: 7/1/16 get friend list for member
            ClientTCPConnection memberTCPConnection = ServerTCPListener.clients.get(member.getUsername());
            JSONObject asyncConversatoin = new JSONObject();
            asyncConversatoin.put("friendlist", new Gson().toJson(friendList));
            memberTCPConnection.sendJsonObj(asyncConversatoin);
        }
    }

    public void CREATE_CHANEL(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Channel channel = new Gson().fromJson(args.get("channel").toString(), Channel.class);
        // TODO: 7/2/16 add channel to db
        sendFriendListToUsers(channel.getMemmbers());
    }

    public void LEAVE_CHANEL(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Channel channel = new Gson().fromJson(args.get("channel").toString(), Channel.class);
        // TODO: 7/2/16 remove user from channel in db
        // TODO: 7/2/16 get new channel members
        User[] members = null;
        sendFriendListToUsers(members);
    }

    public void UPDATE_PROFILE(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Profile profile = new Gson().fromJson(args.get("profile").toString(), Profile.class);
        // TODO: 7/2/16 update user profile on db
        JSONObject response = new JSONObject();
        response.put("ack", true);
        clientTCPConnection.sendJsonObj(response);
    }

    public void SIGN_UP(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        Profile profile = new Profile();
        profile.parsJsonObj((JSONObject) args.get("profile"));
        mongoDb.add_a_user(profile.getUser().getName(), profile.getLastName(),
                profile.getUser().getUsername(), profile.getPassword(),
                profile.getEmail(), profile.getPhoneNumber(), profile.getBioDescription());
        JSONObject response = new JSONObject();
        response.put("ack", true);
        clientTCPConnection.sendJsonObj(response);
    }

    public void LOGIN(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.parsJsonObj((JSONObject) args.get("logininfo"));
        String pass = null;
        try
        {
            pass = mongoDb.find_username_pass(loginInfo.getUsername());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        JSONObject response = new JSONObject();
        boolean accepted = false;
        if (pass != null && pass.equalsIgnoreCase(loginInfo.getPassword()))
        {
            clientTCPConnection.setUser(new User("", loginInfo.getUsername()));
            accepted = true;
        }
        response.put("ack", accepted);
        clientTCPConnection.sendJsonObj(response);

    }

    public void LOGOUT(JSONObject args, ClientTCPConnection clientTCPConnection)
    {
        User user = new Gson().fromJson(args.get("user").toString(), User.class);
        ClientTCPConnection removed = ServerTCPListener.clients.remove(user.getUsername());
        if (removed != null)
            removed.close();
    }

}
