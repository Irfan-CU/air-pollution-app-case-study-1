package com.airpollution.userservice.userdto;

import com.airpollution.userservice.domain.User;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Arrays;

public class UserDto implements Serializable {
    private String id;
    private String encryptedPassword;
    private String password;
    private byte[] profilePic;

    private static final long serialVersionUID = -953297098295050686L;

    public UserDto() {}

    public UserDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", password='" + password + '\'' +
                ", profilePic=" + Arrays.toString(profilePic) +
                '}';
    }
}
