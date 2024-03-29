package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;

public class UserGetDTO {

    private Long id;
    private String name;
    private String username;
    private UserStatus status;
    private String date;
    private String token;
    private String birthdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setDate(String date){this.date = date;}

    public String getDate(){return date;}

    public void setToken(String token){this.token = token;}

    public String getToken(){return token;}

    public void setBirthdate(String date){this.birthdate = date;}

    public String getBirthdate(){return birthdate;}
}
