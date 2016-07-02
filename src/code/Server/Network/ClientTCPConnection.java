package code.Server.Network;

import code.PrimitiveClasses.User;
import code.Server.Parse.CommandParser;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.*;

public class ClientTCPConnection implements Runnable
{
    private final DataInputStream inFromClient;
    private final DataOutputStream outToClient;
    User user;
    private CommandParser commandParser;


    public ClientTCPConnection(InputStream inputStream, OutputStream outputStream)
    {
        inFromClient = new DataInputStream(inputStream);
        outToClient = new DataOutputStream(outputStream);
        commandParser = new CommandParser();
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                String msg = inFromClient.readUTF();
                System.out.println("received:\t" + msg);
                commandParser.parse(msg, this);
            } catch (IOException e)
            {
                e.printStackTrace();
                try
                {
                    inFromClient.close();
                    return;
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }


    synchronized public void sendJsonObj(JSONObject obj)
    {
        try
        {
            if (obj == null)
                return;
            obj.writeJSONString(new StringWriter());
            System.out.println("to send:\t" + obj.toString());
            outToClient.writeUTF(obj.toString());
        } catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                inFromClient.close();
                return;
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }

    }

    synchronized public void sendObject(Object obj)
    {
        try
        {
            if (obj == null)
                return;
            Gson gson = new Gson();
            String toJson = gson.toJson(obj);
//            obj.writeJSONString(new StringWriter());
//            System.out.println("to sendObject:\t" + obj.toString());
            System.out.println("to send:\t" + toJson);
            outToClient.writeUTF(toJson);
        } catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                inFromClient.close();
                return;
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }

    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
        ServerTCPListener.clients.put(user.getUsername(), this);
    }

    public void close()
    {
        try
        {
            new Thread(this).stop();
            inFromClient.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
