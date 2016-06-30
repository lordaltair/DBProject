package code.Client;

import gui.LoginPage;

import javax.swing.*;
import java.io.IOException;

public class ClientMain
{

    public static void main(String[] args) throws IOException {
//        new LoginPage();
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new LoginPage().LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
