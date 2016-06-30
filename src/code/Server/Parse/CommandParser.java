package code.Server.Parse;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommandParser
{
    public static void parse(String command)
    {
        JSONParser parser = new JSONParser();
        try
        {
            Object parse = parser.parse(command);

        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

}
