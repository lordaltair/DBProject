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
        firstPage = new First(this);
        profilepage = new ProfilePage(this);
        searchpage = new SearchPage(this);
        signup = new Signup(this, loginPage);
        MainPanel.add(loginPage.LoginPanel, "0");
        MainPanel.add(firstPage.firstpanel, "1");
        MainPanel.add(searchpage.LoginPanel, "2");
        MainPanel.add(profilepage.ProfPanel , "3");
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

    public void gotoFrame(int number)
    {
        MainPanel.setVisible(false);
        ((CardLayout) MainPanel.getLayout()).show(MainPanel, String.valueOf(number));
        MainPanel.revalidate();
        MainPanel.repaint();
        MainPanel.setVisible(true);
    }

    public LoginPage getLoginPage()
    {
        return loginPage;
    }

    public First getFirstPage()
    {
        return firstPage;
    }

    public ProfilePage getProfilepage()
    {
        return profilepage;
    }

    public SearchPage getSearchpage()
    {
        return searchpage;
    }

    public Signup getSignup()
    {
        return signup;
    }
}
