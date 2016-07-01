package code;

public class COMMAND_CODES
{
    public static final int NUMBER_OF_COMMANDS;
    public static final int GET_FRIEND_LIST; // -
    public static final int START_CHAT; // GROUP OR USER OR ....
    public static final int UNFRIEND; // USER
    public static final int MORE_MESSAGE; // TIME, GROUP OR USER OR ....
    public static final int GET_PROFILE_DETAIL; // -
    public static final int REPORT; // USER : NAME
    public static final int CLIENT_SEND_NEW_MSG; // TO USER OR GROUP, TIME, MSG
    public static final int SERVER_BROADCAST_NEW_MSG; // FROM USER OR GROUP, TIME, MSG
    public static final int START_PV_CHAT; // USER, TIME, MSG
    public static final int GET_GROUP_MEMBERS; // GROUP
    public static final int CLIENT_MENTION; // USER
    public static final int SERVER_MENTION; // GROUP OR CHAT
    public static final int CLIENT_UNMENTION; // USER
    public static final int SEARCH; // SEARCH QUERY
    public static final int ADD_TO_FREIND_LIST; // USER
    public static final int CREATE_GROUP; // TITLE, MEMBERS[]
    public static final int LEAVE_GROUP; // GROUP
    public static final int CREATE_CHANEL; // TITLE, MEMBERS[]
    public static final int LEAVE_CHANEL; // CHANEL
    public static final int UPDATE_PROFILE; // PROFILE
    public static final int SIGN_UP; // PROFILE
    public static final int LOGIN; // USERNAME, PASS
    public static final int LOGOUT; // -

    static
    {
        int i = 0;
        GET_FRIEND_LIST = i++;
        START_CHAT = i++;
        UNFRIEND = i++;
        MORE_MESSAGE = i++;
        GET_PROFILE_DETAIL = i++;
        REPORT = i++;
        CLIENT_SEND_NEW_MSG = i++;
        SERVER_BROADCAST_NEW_MSG = i++;
        START_PV_CHAT = i++;
        GET_GROUP_MEMBERS = i++;
        CLIENT_MENTION = i++;
        SERVER_MENTION = i++;
        CLIENT_UNMENTION = i++;
        SEARCH = i++;
        ADD_TO_FREIND_LIST = i++;
        CREATE_GROUP = i++;
        LEAVE_GROUP = i++;
        CREATE_CHANEL = i++;
        LEAVE_CHANEL = i++;
        UPDATE_PROFILE = i++;
        SIGN_UP = i++;
        LOGIN = i++;
        LOGOUT = i++;
        NUMBER_OF_COMMANDS = i;
    }
}
