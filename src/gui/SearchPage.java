package gui;

import code.Client.Client;
import code.PrimitiveClasses.FriendList;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.IOException;

/**
 * Created by Ali on 7/2/2016.
 */
public class SearchPage {
    public JPanel LoginPanel;
    private JTextField textField1;
    private JButton searchHastagButton;
    private JButton searchNameButton;
    private JTextField textField2;
    private JButton addButton;
    private JButton sendUnknownMessageButton;
    private JTree tree1;

    public SearchPage(MainFrame mainFrame)
    {

        searchNameButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String username = textField2.getText();
                Client client = Client.GetInstance(null, null, null, null, null);
                try
                {
                    String userJs = client.addtofriend(username);
                    client.outToServer.writeUTF(userJs);
                    client.outToServer.flush();

                    String std = client.inFromServer.readUTF();
                    FriendList friendList = new Gson().fromJson(std, FriendList.class);
                    mainFrame.getFirstPage().updateFriendList(friendList);
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
                mainFrame.gotoFrame(1);
            }
        });
    }
}
