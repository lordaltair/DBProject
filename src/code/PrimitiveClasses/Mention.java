package code.PrimitiveClasses;

public class Mention
{
    private BasicChat location;

    public Mention(BasicChat location)
    {
        this.location = location;
    }

    public BasicChat getLocation()
    {
        return location;
    }

    public void setLocation(BasicChat location)
    {
        this.location = location;
    }
}
