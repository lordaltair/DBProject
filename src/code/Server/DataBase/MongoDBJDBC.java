package code.Server.DataBase;

import code.PrimitiveClasses.*;
import com.mongodb.*;

import java.net.UnknownHostException;

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
            db = mongoClient.getDB("test");
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
        DB db = mongoClient.getDB("test");

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
            DB db = mongoClient.getDB("test");
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

            DB db = mongoClient.getDB("test");
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
            DB db = mongoClient.getDB("test");
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
    public void add_a_friend(String  aUserName, String bUserName)//b = a's friend
    {

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("test");
            System.out.println("Connect to database successfully");
            DBCollection coll = db.getCollection("User");
            DBObject find = new BasicDBObject("UserName", aUserName);
            DBObject listItem = new BasicDBObject("Friend IDs", new BasicDBObject("UserName",bUserName));
            DBObject updateQuery = new BasicDBObject("$push", listItem);
            coll.update(find, updateQuery);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    public void add_a_friend(BasicDBObject a, BasicDBObject b)//b = a's friend
    {

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("test");
            System.out.println("Connect to database successfully");
            DBCollection coll = db.getCollection("User");
            DBObject find = new BasicDBObject("UserName", a.get("UserName"));
            DBObject listItem = new BasicDBObject("Friend IDs", new BasicDBObject("UserName",b.get("UserName")));
            DBObject updateQuery = new BasicDBObject("$push", listItem);
            coll.update(find, updateQuery);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public FriendList get_friend_list(String UserName)
    {
        try
        {
            DBCollection coll = db.getCollection("User");

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("UserName", UserName);
            // BasicDBObject fields = new BasicDBObject("UserName", "1");
            DBCursor cursor = coll.find(whereQuery);
            if (!cursor.hasNext())
            {
                System.err.println("This user can not be found");
                return null;

            }
            FriendList result = new FriendList();
            DBObject res = cursor.next();

            //set user
            User user = new User();
            user.setUsername(UserName);
            result.setUser(user);
            //set friends
            BasicDBList list = (BasicDBList) res.get("Friend IDs");
            int sizeOfList = list.size();
            User[] friends = new User[sizeOfList];
            int index=0;
            for (Object element : list)
            {
                User tmp= new User();
                tmp.setUsername((String)element);
                tmp.setName(null);
                friends[index]=tmp;
                index++;
            }
            result.setFriends(friends);

            //set groups
            list = (BasicDBList) res.get("Group IDs");
            sizeOfList = list.size();
            Group[] groups = new Group[sizeOfList];
            index=0;
            for (Object element : list)
            {
                Group tmp = null;
                tmp = new Group((String)element);
                tmp.setMemmbers(null);
                groups[index]=tmp;
                index++;
            }
            result.setGroups(groups);
            //set channels
            list = (BasicDBList) res.get("Channel IDs");
            sizeOfList = list.size();
            Channel[] channels = new Channel[sizeOfList];
            index=0;
            for (Object element : list)
            {
                Channel tmp = null;
                tmp = new Channel((String)element);
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
        return null;
    }
    public Message[] get_chat_messages (String UserName1, String UserName2)
    {
        //TODO
        return null;
    }
    public void add_a_massage_to_chat(BasicDBObject a, BasicDBObject b, String message)
    {
        try {



            DBCollection coll = db.getCollection("Chat");
//            BasicDBObject fields = new BasicDBObject();
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("A",a.get("UserName"));
            whereQuery.put("B",b.get("UserName"));
            DBCursor cursor = coll.find(whereQuery);
            if (cursor.hasNext())//I think it means we found a true document
            {
                DBObject tmp = cursor.next();
                DBObject listItem = new BasicDBObject("Messages", new BasicDBObject("Message",message)
                        .append("$currentDate", new BasicDBObject("Time", true)));
                DBObject updateQuery = new BasicDBObject("$push", listItem);
                coll.update(tmp, updateQuery);
                return;
            }
            whereQuery = new BasicDBObject();
            whereQuery.put("A",b.get("UserName"));
            whereQuery.put("B",a.get("UserName"));
            cursor= coll.find(whereQuery);
            if (cursor.hasNext())
            {
                DBObject tmp = cursor.next();
                DBObject listItem = new BasicDBObject("Messages", new BasicDBObject("Message",message)
                        .append("$currentDate", new BasicDBObject("Time", true)));
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
        DB db = mongoClient.getDB("test");
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
}