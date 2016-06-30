package code.PrimitiveClasses;

public class Conversation
{
    private Message[] messages;

    public Conversation(Message[] messages)
    {
        this.messages = messages;
    }

    public Message[] getMessages()
    {
        return messages;
    }

    public void setMessages(Message[] messages)
    {
        this.messages = messages;
    }
}
