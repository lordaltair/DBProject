package code.PrimitiveClasses;

public class FriendList
{
    private User user;
    private User[] friends;
    private User[] unknownFriends;
    private Group[] groups;
    private Chanel[] chanels;

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

    public Chanel[] getChanels()
    {
        return chanels;
    }

    public void setChanels(Chanel[] chanels)
    {
        this.chanels = chanels;
    }
}

//{
//    "user":"Danial",
//    "friends":
//    [
//        {"username":"alim","name":"ali"},
//        {"username":"alim2","name":"ali2"}
//    ],
//    "unknownFriends" : null,
//    "groups" :
//    [
//        {"title" : "ceit", "members" :
//            [
//                {"username":"alim","name":"ali"},
//                {"username":"alim2","name":"ali2"}
//            ]
//        }
//    ],
//    "chanels" : null
//}