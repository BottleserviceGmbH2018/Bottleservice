package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 06/04/2018.
 */

public class UsersChat {

    public String name;
    public String email;
    public String profileImageUrl;

    public UsersChat(){}

    public UsersChat(String name, String email, String profileImageUrl) {
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl(String profileImageUrl) {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}
