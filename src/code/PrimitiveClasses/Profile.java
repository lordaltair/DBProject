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
}
