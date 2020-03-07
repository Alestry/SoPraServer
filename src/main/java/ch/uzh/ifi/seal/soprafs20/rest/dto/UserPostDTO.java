package ch.uzh.ifi.seal.soprafs20.rest.dto;

public class UserPostDTO {

    private String name;

    private String username;

    private String date;

    private String token;

    private String birthdate;

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

    public void setDate(String date){this.date = date;}

    public String getDate(){return date;}

    public void setToken(String token){this.token = token;}

    public String getToken(){return token;}

    public void setBirthdate(String date){this.birthdate = date;}

    public String getBirthdate(){return birthdate;}
}
