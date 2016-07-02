package code.PrimitiveClasses;

public class Mention
{
    private User chat;

    public Mention(User chat)
    {
        this.chat = chat;
    }

    public User getChat()
    {
        return chat;
    }

    public void setChat(User chat)
    {
        this.chat = chat;
    }
}
