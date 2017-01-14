package at.ac.tuwien.mns17.java8example.models;

/**
 * Created by Jakob on 14.01.2017.
 */

public class User {
    private String username;
    private int age;
    private String country;

    public User(String username, int age, String country) {
        this.username = username;
        this.age = age;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
