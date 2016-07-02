package code.Server.DataBase;

import code.PrimitiveClasses.*;
import com.mongodb.*;

import java.sql.Time;
import java.util.Vector;

public class MongoDBJDBC
{
    DB db;
    MongoClient mongoClient = null;

    public MongoDBJDBC()
    {
        initialization();
    }

    public static void main(String args[])
    {

        MongoDBJDBC myMongoDBJDBC = new MongoDBJDBC();


    }

    public void connectToDataBase()
    {
        try
        {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB("Test");
            System.out.println("Connect to database successfully");
//            boolean auth = db.authenticate("myUser", "myPassword".toCharArray());
//            System.out.println("Authentication: "+auth);
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void retriveAllDoc() throws Exception
    {
        MongoClient mongoClient = null;
        mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("Test");

//            boolean auth = db
//                    .authenticate("myUserName", "myPassword".toCharArray());
        DBCollection coll = db.getCollection("mycol");
        DBCursor cursor = coll.find();
        int i = 1;
        while (cursor.hasNext())
        {
            System.out.println("Inserted Document: " + i);
            System.out.println(cursor.next());
            i++;
        }

    }

    public void insertADocument()
    {
        try
        {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("Test");
            System.out.println("Connect to database successfully");
//            boolean auth = db.authenticate("myUserName",
//                    "myPassword".toCharArray());
//            System.out.println("Authentication: " + auth);
            DBCollection coll = db.getCollection("mycol");
            System.out.println("Collection mycol selected successfully");
            BasicDBObject doc = new BasicDBObject("title", "MongoDB")
                    .append("description", "database")
                    .append("likes", 100)
                    .append("url", "http://www.java2s.com");
            coll.insert(doc);
            System.out.println("Document inserted successfully");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void updateDocument()
    {
        try
        {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB("Test");
//            boolean auth = db
//                    .authenticate("myUserName", "myPassword".toCharArray());
//            System.out.println("Authentication: " + auth);
            DBCollection coll = db.getCollection("mycol");
            System.out.println("Collection mycol selected successfully");
            DBCursor cursor = coll.find();
            while (cursor.hasNext())
            {
                DBObject updateDocument = cursor.next();
                updateDocument.put("likes", "300");
                coll.save(updateDocument);
            }
            System.out.println("Document updated successfully");
            cursor = coll.find();
            int i = 1;
            while (cursor.hasNext())
            {
                System.out.println("Updated Document: " + i);
                System.out.println(cursor.next());
                i++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void add_a_user(String FirstName, String LastName, String UserName,
                           String Password, String Email, String phone, String biography)
    {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("Test");
            System.out.println("Connect to database successfully");
            DBCollection coll = db.getCollection("User");
            System.out.println("Collection User selected successfully");
            BasicDBObject doc = new BasicDBObject("UserName", UserName)
                    .append("Password", Password)
                    .append("FirstName", FirstName)
                    .append("LastName", LastName)
                    .append("Email", Email)
                    .append("Phone", phone)
                    .append("biography", biography)
                    .append("Friend IDs", new BasicDBList())
                    .append("Group IDs", new BasicDBList())
                    .append("Channel IDs", new BasicDBList());
            coll.insert(doc);
            System.out.println("Document inserted successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public FriendList get_friend_list(String UserName)
    {
        FriendList result = null;
        try
        {
            DBCollection coll = db.getCollection("User");

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("UserName", UserName);
            DBCursor cursor = coll.find(whereQuery);
            if (!cursor.hasNext())
            {
                System.err.println("This user can not be found");
                return null;

            }
            result = new FriendList();
            DBObject res = cursor.next();

            //set user
            User user = new User();
            user.setUsername(UserName);
            result.setUser(user);
            //set friends
            BasicDBList list = (BasicDBList) res.get("Friend IDs");
            int sizeOfList = list.size();
            User[] friends = new User[sizeOfList];
            for (int index=0; index < list.size(); index++)
            {
                BasicDBObject element = (BasicDBObject) list.get(index);
                User tmp= new User();
                tmp.setUsername((String) element.get("UserName"));
                tmp.setName(null);
                friends[index]=tmp;
                index++;
            }
            result.setFriends(friends);

            //set groups
            list = (BasicDBList) res.get("Group IDs");
            sizeOfList = list.size();
            Group[] groups = new Group[sizeOfList];
            for (int index=0; index < list.size(); index++)
            {
                BasicDBObject element = (BasicDBObject) list.get(index);
                Group tmp = null;
                tmp = new Group((String) element.get("Group ID"));
                tmp.setMemmbers(null);
                groups[index]=tmp;
                index++;
            }
            result.setGroups(groups);
            //set channels
            list = (BasicDBList) res.get("Channel IDs");
            sizeOfList = list.size();
            Channel[] channels = new Channel[sizeOfList];
            for (int index=0; index < list.size(); index++)
            {
                BasicDBObject element = (BasicDBObject) list.get(index);
                Channel tmp = null;
                tmp = new Channel((String) element.get("Channel ID"));
                tmp.setAdmin(null);
                channels[index]=tmp;
                index++;
            }
            result.setGroups(groups);

            //Unknown Chats
            DBCollection chatCollection=db.getCollection("Chat");
            BasicDBObject whereQuery_chat = new BasicDBObject();
            whereQuery_chat.put("A",UserName);
            DBCursor cursor_chat = coll.find(whereQuery_chat);
            while (cursor_chat.hasNext())
            {
                DBObject tmp = cursor_chat.next();
                //TODO
            }



        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return result;
    }
    public User[] search_userName (String UserName)
    {
        DBCollection coll = db.getCollection("User");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("UserName", UserName);
        DBCursor cursor = coll.find(whereQuery);
        if (!cursor.hasNext())
        {
            return null;
        }
        Vector<User> res= new Vector<User>();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            User tmp = new User();
            tmp.setUsername(obj.get("UserName").toString());
            res.add(tmp);
        }
        User[] result= res.toArray(new User[res.size()]);
        return result;
    }
    public Message[] get_chat_messages(String UserName1, String UserName2, Time fromDate)
    {
        DBCollection coll = db.getCollection("Chat");
        BasicDBList or = new BasicDBList();
        BasicDBList and = new BasicDBList();
        BasicDBObject whereQuery1 = new BasicDBObject();

        whereQuery1.put("A", UserName1);
        whereQuery1.put("B", UserName2);
        //
        or.add(new BasicDBObject("$and", whereQuery1));

        BasicDBObject whereQuery2 = new BasicDBObject();
        whereQuery2.put("B", UserName1);
        whereQuery2.put("A", UserName2);
        or.add(new BasicDBObject("$and", whereQuery2));

        DBObject ors = new BasicDBObject("$or", or);
        and.add(ors);

        and.put("Time", BasicDBObjectBuilder.start("$gte", fromDate));
        DBObject query = new BasicDBObject("$and", and);
        DBCursor cursor = coll.find(query).sort(new BasicDBObject("Time ", -1)).limit(20);
        Message[] messages = new Message[cursor.size()];
        int i = 0;
        while (cursor.hasNext())
        {
            DBObject next = cursor.next();
            messages[i] = new Message(new User(null, (String) next.get("Sender")), (Time) next.get("Time"), (String) next.get("message"));
            i++;
        }
        return messages;
    }
    public void add_friend (String aUserName, String bUserName)//b will add to a's friend
    {
        DBCollection coll = db.getCollection("User");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("UserName",aUserName);
        DBCursor cursor = coll.find(whereQuery);
        if (!cursor.hasNext())
        {
            System.out.println("UserName "+aUserName+" is not existed");
            return;
        }
        DBObject obj = cursor.next();
        BasicDBList fr = (BasicDBList) obj.get("Friend IDs");
        for (Object a: fr)
        {
            if (a.toString().equals(bUserName))
            {
                return;
            }
        }
        DBObject listItem = new BasicDBObject("Friend IDs", new BasicDBObject("UserName",bUserName));
        DBObject updateQuery = new BasicDBObject("$push", listItem);
        coll.update(obj, updateQuery);
    }
    public void add_a_massage_to_chat(String aUserName, String bUserName, String message)//a is sender
    {
        try {



            DBCollection coll = db.getCollection("Chat");
//            BasicDBObject fields = new BasicDBObject();
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("A",aUserName);
            whereQuery.put("B",bUserName);
            DBCursor cursor = coll.find(whereQuery);
            if (cursor.hasNext())//I think it means we found a true document
            {
                DBObject tmp = cursor.next();
                DBObject listItem = new BasicDBObject("Messages", new BasicDBObject("Message",message)
                        .append("$currentDate", new BasicDBObject("Time", true)).append("Sender", aUserName));
                DBObject updateQuery = new BasicDBObject("$push", listItem);
                coll.update(tmp, updateQuery);
                return;
            }
            whereQuery = new BasicDBObject();
            whereQuery.put("A",bUserName);
            whereQuery.put("B",aUserName);
            cursor= coll.find(whereQuery);
            if (cursor.hasNext())
            {
                DBObject tmp = cursor.next();
                DBObject listItem = new BasicDBObject("Messages", new BasicDBObject("Message",message)
                        .append("$currentDate", new BasicDBObject("Time", true)).append("Sender", aUserName));
                DBObject updateQuery = new BasicDBObject("$push", listItem);
                coll.update(tmp, updateQuery);
                return;
            }




        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void initialization()
    {
        try {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB("Test");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public User searchUserName(String UserName)
    {
        DBCollection coll = db.getCollection("User");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("UserName",UserName);
        DBCursor cursor = coll.find(whereQuery);
        if (!cursor.hasNext())
        {
            return null;
        }
        User usr= new User();
        usr.setUsername(UserName);
        return usr;
    }
    public String find_username_pass(String username) throws Exception
    {
        MongoClient mongoClient = null;
        mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("Test");
        System.out.println("Connect to database successfully");
        DBCollection coll = db.getCollection("User");
        System.out.println("Collection User selected successfully");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("UserName", username);
        DBCursor dbObjects = coll.find(whereQuery);
        if (dbObjects.size() == 0)
            return null;
        DBObject next = dbObjects.next();
        return (String) next.get("Password");
    }

    public Group get_group(String title)
    {
        DBCollection coll = db.getCollection("Group");
        System.out.println("Collection Group selected successfully");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("Title", title);
        DBCursor dbObjects = coll.find(whereQuery);
        if (!dbObjects.hasNext())
            return null;
        return new Group(title);
    }

    public Channel get_channel(String title)
    {
        DBCollection coll = db.getCollection("Channel");
        System.out.println("Collection Channel selected successfully");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("Title", title);
        DBCursor dbObjects = coll.find(whereQuery);
        if (!dbObjects.hasNext())
            return null;
        return new Channel(title);
    }

    public Profile get_user_profile(String username)
    {

        DBCollection coll = db.getCollection("User");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("UserName", username);
        DBCursor cursor = coll.find(whereQuery);
        if (!cursor.hasNext())
        {
            return null;
        }
        DBObject next = cursor.next();
        Profile profile = new Profile(new User((String) next.get("Name"), (String) next.get("UserName")),
                (String) next.get("LastName"), (String) next.get("Password"),
                (String) next.get("Email"), (String) next.get("Phone"),
                (String) next.get("biography"));
        return profile;
    }
}