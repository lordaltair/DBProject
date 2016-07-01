package code.Server.Network;

import code.PrimitiveClasses.User;
import org.json.simple.JSONObject;

import java.io.*;

public class ClientTCPConnection implements Runnable
{
    String clientSentence;
    String capitalizedSentence;
    User user;


    public ClientTCPConnection(InputStream inputStream, OutputStream outputStream)
    {
        BufferedReader inFromClient =
                new BufferedReader(new InputStreamReader(inputStream));
        DataOutputStream outToClient = new DataOutputStream(outputStream);
        while (true)
        {
            try
            {
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run()
    {

    }

    public void send(JSONObject obj)
    {
    }
}
