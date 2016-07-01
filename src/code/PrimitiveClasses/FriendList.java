package code.PrimitiveClasses;

public class FriendList
{
    private User user;
    private User[] friends;
    private User[] unknownFriends;
    private Group[] groups;
    private Channel[] channels;
    private PrivateChat[] privateChats;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User[] getFriends()
    {
        return friends;
    }

    public void setFriends(User[] friends)
    {
        this.friends = friends;
    }

    public User[] getUnknownFriends()
    {
        return unknownFriends;
    }

    public void setUnknownFriends(User[] unknownFriends)
    {
        this.unknownFriends = unknownFriends;
    }

    public Group[] getGroups()
    {
        return groups;
    }

    public void setGroups(Group[] groups)
    {
        this.groups = groups;
    }

    public Channel[] getChannels()
    {
        return channels;
    }

    public void setChannels(Channel[] channels)
    {
        this.channels = channels;
    }

}