package code.PrimitiveClasses;

/**
 * Created by danial on 6/30/16.
 */
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
