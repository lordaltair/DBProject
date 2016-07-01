package DataBase;

import com.mongodb.*;

import com.mongodb.DBCollection;

import java.net.UnknownHostException;
import java.util.Arrays;

public class MongoDBJDBC {
    DB db ;

    void connectToDataBase()
    {
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            db = mongoClient.getDB( "test" );
            System.out.println("Connect to database successfully");
//            boolean auth = db.authenticate("myUser", "myPassword".toCharArray());
//            System.out.println("Authentication: "+auth);
        }
        catch(Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    void retrieveAllDoc()
    {
        try{
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("test");

//            boolean auth = db
//                    .authenticate("myUserName", "myPassword".toCharArray());
            DBCollection coll = db.getCollection("mycol");
            DBCursor cursor = coll.find();
            int i = 1;
            while (cursor.hasNext()) {
                System.out.println("Inserted Document: " + i);
                System.out.println(cursor.next());
                i++;
            }
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

    }
    void insertADocument()
    {
        try {
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
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    void updateDocument ()
    {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB("test");
//            boolean auth = db
//                    .authenticate("myUserName", "myPassword".toCharArray());
//            System.out.println("Authentication: " + auth);
            DBCollection coll = db.getCollection("mycol");
            System.out.println("Collection mycol selected successfully");
            DBCursor cursor = coll.find();
            while (cursor.hasNext()) {
                DBObject updateDocument = cursor.next();
                updateDocument.put("likes", "300");
                coll.save(updateDocument);
            }
            System.out.println("Document updated successfully");
            cursor = coll.find();
            int i = 1;
            while (cursor.hasNext()) {
                System.out.println("Updated Document: " + i);
                System.out.println(cursor.next());
                i++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    void myInsertion_Users()
    {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("test");
            System.out.println("Connect to database successfully");
            DBCollection coll = db.getCollection("User");
            System.out.println("Collection User selected successfully");

            BasicDBObject doc = new BasicDBObject("ID", 1)
                    .append("First Name", "Ali")
                    .append("Last Name", "Morty")
                    .append("UserName", "AliMorty")
                    .append("Password", "123")
                    .append("Email", "mortyaut@gmail.com")
                    .append("Phone", 256)
                    .append("Biography", "A Person who is in searching of incredibles")
                   // .append("PICTURE!!!!",????)
                    .append("Friend IDs", new BasicDBList())
                    .append("Group IDs", new BasicDBList())
                    .append("Channel IDs", new BasicDBList());
            coll.insert(doc);
            System.out.println("Document inserted successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    void add_new_user_to_DB (String FirstName, String LastName, String UserName, String Password, String Email,
                             long phone, String Biography)
    {
            try
            {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("test");
            System.out.println("Connect to database successfully");
            DBCollection coll = db.getCollection("User");
            System.out.println("Collection User selected successfully");

            BasicDBObject doc = new BasicDBObject("ID", 1)
                    .append("First Name", FirstName)
                    .append("Last Name", LastName)
                    .append("UserName", UserName)
                    .append("Password", Password)
                    .append("Email", Email)
                    .append("Phone", phone)
                    .append("Biography", Biography)
                            // .append("PICTURE!!!!",????)
                    .append("Friend IDs", new BasicDBList())
                    .append("Group IDs", new BasicDBList())
                    .append("Channel IDs", new BasicDBList());
            coll.insert(doc);
            System.out.println("Document inserted successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    public static void main( String args[] ) {

        MongoDBJDBC myMongoDBJDBC = new MongoDBJDBC();
        //myMongoDBJDBC.insertADocument();

//        myMongoDBJDBC.updateDocument();
       myMongoDBJDBC.retrieveAllDoc();
    }

}