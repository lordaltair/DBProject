package code.Server.DataBase;

import com.mongodb.*;

import java.net.UnknownHostException;

public class MongoDBJDBC
{
    DB db;

    public static void main(String args[])
    {

        MongoDBJDBC myMongoDBJDBC = new MongoDBJDBC();
        //myMongoDBJDBC.insertADocument();

//        myMongoDBJDBC.updateDocument();
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

}