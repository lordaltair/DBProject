package code.PrimitiveClasses;

import java.sql.Time;

/**
 * Created by Ali on 7/1/2016.
 */
public class MoreMessage {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Time getLasttime() {
        return lasttime;
    }

    public void setLasttime(Time lasttime) {
        this.lasttime = lasttime;
    }

    User user;
    Time lasttime;
    public MoreMessage(){
        
    }
}
