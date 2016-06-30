package code.Server;

import code.Server.Network.ServerTCPListener;

public class ServerMain
{
    public static void main(String[] args)
    {
        ServerTCPListener serverTCPListener = new ServerTCPListener();
        new Thread(serverTCPListener).run();
    }
}
