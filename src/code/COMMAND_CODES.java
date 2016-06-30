package code;

public class COMMAND_CODES
{
    private static int i = 0;
    public static final int GET_FRIEND_LIST = i++; // -
    public static final int START_CHAT = i++; // GROUP OR USER OR ....
    public static final int UNFRIEND = i++; // USER
    public static final int MORE_MESSAGE = i++; // TIME, GROUP OR USER OR ....
    public static final int GET_PROFILE_DETAIL = i++; // -
    public static final int REPORT = i++; // USER : NAME
    public static final int CLIENT_SEND_NEW_MSG = i++; // TO USER OR GROUP, TIME, MSG
    public static final int SERVER_SEND_NEW_MSG = i++; // FROM USER OR GROUP, TIME, MSG
    public static final int START_PV_CHAT = i++; // USER, TIME, MSG
    public static final int GET_GROUP_LIST = i++; // GROUP
    public static final int CLIENT_MENTION = i++; // USER
    public static final int SERVER_MENTION = i++; // GROUP OR CHAT
    public static final int SEARCH = i++; // SEARCH QUERY
    public static final int ADD_TO_FREIND_LIST = i++; // USER
    public static final int CREATE_GROUP = i++; // TITLE, MEMBERS[]
    public static final int LEAVE_GROUP = i++; // GROUP
    public static final int CREATE_CHANEL = i++; // TITLE, MEMBERS[]
    public static final int LEAVE_CHANEL = i++; // CHANEL
    public static final int UPDATE_PROFILE = i++; // PROFILE
    public static final int SIGN_UP = i++; // PROFILE
    public static final int LOGIN = i++; // USERNAME, PASS
    public static final int LOGOUT = i++; // -
}
