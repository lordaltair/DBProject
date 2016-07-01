package code.PrimitiveClasses;

import org.json.simple.JSONObject;

/**
 * Created by danial on 7/1/16.
 */
public interface JsonParsable
{
    JSONObject toJsonObj();

    void parsJsonObj(JSONObject obj);

}
