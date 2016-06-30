package code.PrimitiveClasses;

public class Profile
{
    private User user;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String bioDescription;

    public Profile(User user, String lastName, String password, String email, String phoneNumber, String bioDescription)
    {
        this.user = user;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bioDescription = bioDescription;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getBioDescription()
    {
        return bioDescription;
    }

    public void setBioDescription(String bioDescription)
    {
        this.bioDescription = bioDescription;
    }
}
