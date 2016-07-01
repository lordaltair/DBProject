package code.Server.DataBase;

import com.mongodb.*;
import org.bson.types.ObjectId;
import sun.net.www.content.audio.basic;

import java.net.UnknownHostException;

public class MongoDBJDBC
{
    DB db;

    public static void main(String args[])
    {

        MongoDBJDBC myMongoDBJDBC = new MongoDBJDBC();

        myMongoDBJDBC.retriveAllDoc();
    }

    void connectToDataBase()
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

    void retriveAllDoc()
    {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
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

    void insertADocument()
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

    void updateDocument()
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
    void add_a_user(String FirstName, String LastName, String UserName,
                    String Password, String Email, long phone, String biography)
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
    void add_a_friend(BasicDBObject a, BasicDBObject b)//b = a's friend
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
    void add_a_massage_to_chat (BasicDBObject a, BasicDBObject b, String message)
    {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("Test");
            System.out.println("Connect to database successfully");
            DBCollection coll = db.getCollection("Chat");
            BasicDBObject fields = new BasicDBObject();
            BasicDBObject whereQuery = new BasicDBObject();
            fields.put("A",a.get("UserName"));
            fields.put("B",b.get("UserName"));
            DBCursor cursor = coll.find(whereQuery, fields);






        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    void initialization ()
    {


    }



}