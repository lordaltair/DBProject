package gui;

import code.Client.Client;
import code.PrimitiveClasses.Channel;
import code.PrimitiveClasses.FriendList;
import code.PrimitiveClasses.Group;
import code.PrimitiveClasses.User;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class First
{
    public JPanel firstpanel;
    private JButton searchButton;
    private JButton privateChatButton;
    private JTree chatTree;
    private JButton unFriendButton;
    private JButton loadProfileButton;
    private JButton reportButton;
    private JTextArea textArea1;
    private JButton profileButton;
    private JButton moreMessageButton;
    private JButton submitButton;
    private JTextField textField1;
    private Client client;

    public First()
    {

    }

    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }

    public void updateFriendList(FriendList friendList)
    {
        DefaultMutableTreeNode root
                = new DefaultMutableTreeNode("Chats");

        DefaultMutableTreeNode friends
                = new DefaultMutableTreeNode("Friends");
        for (User friend : friendList.getFriends())
        {
            DefaultMutableTreeNode temp
                    = new DefaultMutableTreeNode(friend.getUsername());
            friends.add(temp);
        }
        root.add(friends);

        DefaultMutableTreeNode uFriends
                = new DefaultMutableTreeNode("Unknown Friends");
        for (User ufriend : friendList.getUnknownFriends())
        {
            DefaultMutableTreeNode temp
                    = new DefaultMutableTreeNode(ufriend.getUsername());
            uFriends.add(temp);
        }
        root.add(uFriends);

        DefaultMutableTreeNode groups
                = new DefaultMutableTreeNode("Groups");
        for (Group group : friendList.getGroups())
        {
            DefaultMutableTreeNode temp
                    = new DefaultMutableTreeNode(group.getTitle());
            groups.add(temp);
        }
        root.add(groups);

        DefaultMutableTreeNode channels
                = new DefaultMutableTreeNode("Channels");
        for (Channel channel : friendList.getChannels())
        {
            DefaultMutableTreeNode temp
                    = new DefaultMutableTreeNode(channel.getTitle());
            channels.add(temp);
        }
        root.add(channels);

        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        chatTree.setModel(treeModel);


    }

    public void startClient(String username, Socket finalClientSocket, DataOutputStream outToServer, DataInputStream inFromServer)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    client = new Client(finalClientSocket, username, outToServer, inFromServer, First.this);
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }).start();
    }
}
