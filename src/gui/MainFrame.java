package gui;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Ali on 7/1/2016.
 */
public class MainFrame
{
    private final LoginPage loginPage;
    private final First firstPage;
    private final ProfilePage profilepage;
    private final SearchPage searchpage;
    private final Signup signup;
    public JPanel MainPanel;

    public MainFrame()
    {
        loginPage = new LoginPage(this);
        firstPage = new First();
        profilepage = new ProfilePage();
        searchpage = new SearchPage();
        signup = new Signup();
        MainPanel.add(loginPage.LoginPanel, "0");
        MainPanel.add(firstPage.firstpanel, "1");
        MainPanel.add(searchpage.LoginPanel, "2");
        MainPanel.add(profilepage.ProfPanel , "3");
        signup.JP1.add(signup.JP2,"0");
        MainPanel.add(signup.JP1 , "4");
        ((CardLayout) MainPanel.getLayout()).show(MainPanel, "0");

    }

    public void gotoNextFrame(String username, Socket finalClientSocket, DataOutputStream outToServer, DataInputStream inFromServer)
    {
        MainPanel.setVisible(false);
        ((CardLayout) MainPanel.getLayout()).next(MainPanel);
        firstPage.startClient(username, finalClientSocket, outToServer, inFromServer);
        MainPanel.revalidate();
        MainPanel.repaint();
        MainPanel.setVisible(true);
    }
}
