package code.Server.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCPListener implements Runnable
{
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
                new Thread(clientTCPConnection).run();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
