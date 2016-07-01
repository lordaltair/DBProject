package code.Server.Network;

import code.PrimitiveClasses.User;
import code.Server.Parse.CommandParser;
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
                commandParser.parse(msg, this);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    synchronized public void send(JSONObject obj)
    {
        try
        {
            obj.writeJSONString(new StringWriter());
            outToClient.writeUTF(obj.toString());
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
        // TODO: 7/1/16 add it to logins
    }
}
