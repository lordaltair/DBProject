package code.Server.DataBase;

import code.PrimitiveClasses.FriendList;
import code.PrimitiveClasses.Group;
import code.PrimitiveClasses.User;
import com.mongodb.*;

import java.net.UnknownHostException;

public class MongoDBJDBC
{
    DB db;
    MongoClient mongoClient = null;
    public static void main(String args[])
    {

        MongoDBJDBC myMongoDBJDBC = new MongoDBJDBC();

        myMongoDBJDBC.retriveAllDoc();
    }
    public MongoDBJDBC()
    {
        initialization();
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

    public void retriveAllDoc()
    {
        MongoClient mongoClient = null;
        try
        {
            mongoClient = new MongoClient("localhost", 27017);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
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
            }
            FriendList result = new FriendList();
            DBObject res = cursor.next();


            BasicDBList list = (BasicDBList) res.get("Friend IDs");
            int sizeOfList = list.size();
            User [] friends = new User[sizeOfList];
            int index=0;
            for (Object element : list)
            {
                User tmp= new User();
                tmp.setUsername((String)element);
                friends[index]=tmp;
                index++;
            }
            result.setFriends(friends);
            list = (BasicDBList) res.get("Group IDs");
            sizeOfList = list.size();
            Group[] groups = new Group[sizeOfList];
            index=0;
            for (Object element : list)
            {
                User tmp= new User();
                element.
                tmp.setUsername((String)element);
                friends[index]=tmp;
                index++;
            }
            result.setFriends(friends);



        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
    public void add_a_massage_to_chat(BasicDBObject a, BasicDBObject b, String message)
    {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("Test");
            System.out.println("Connect to database successfully");
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


    public String find_username_pass(String username)
    {
        MongoClient mongoClient = null;
        try
        {
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
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}