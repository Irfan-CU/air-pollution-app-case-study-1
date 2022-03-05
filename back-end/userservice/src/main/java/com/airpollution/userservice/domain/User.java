package com.airpollution.userservice.domain;

import javax.persistence.*;


@Entity
@Table(name = "User")
public class User {
    /**
     * @Id annotation to make id variable as Primary key
     */
    @Id
    @Column(name = "id", length = 50)
    private String id;

    //encryptedPassword
    @Column(name= "password")
    private String password;

    @Lob
    @Column(name="profilePic")
    private byte[] profilePic;

    public String getId()
    {
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

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", encryptedPassword='" + password + '\'' +
                '}';
    }
}
