package code.Client;

import gui.First;
import gui.LoginPage;
import gui.MainFrame;

import javax.swing.*;
import java.io.IOException;

public class ClientMain
{

    public static void main(String[] args) throws IOException {
//        new LoginPage();
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new MainFrame().MainPanel);
//        frame.setContentPane(new LoginPage(frame).LoginPanel);
//        frame.add(new First().firstpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
