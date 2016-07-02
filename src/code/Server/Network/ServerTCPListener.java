package code.Server.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerTCPListener implements Runnable
{
    public static HashMap<String, ClientTCPConnection> clients = new HashMap<>();
    ServerSocket welcomeSocket;

    public ServerTCPListener()
    {
    }

    @Override
    public void run()
    {

        try
        {
            welcomeSocket = new ServerSocket(6789);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        while (true)
        {
            Socket connectionSocket = null;
            try
            {
                connectionSocket = welcomeSocket.accept();
                ClientTCPConnection clientTCPConnection =
                        new ClientTCPConnection(connectionSocket.getInputStream(),
                                connectionSocket.getOutputStream());
                new Thread(clientTCPConnection).start();
            } catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
        }
    }
}
