package at.ac.tuwien.mns17.java8example.services;

import java.net.MalformedURLException;
import java.net.URL;

import at.ac.tuwien.mns17.java8example.models.User;

/**
 * Created by Jakob on 14.01.2017.
 */

public class Greater15andDECheck implements ContitionCheck {

    @Override
    public boolean fulfillContition(User user) {
        return user.getAge() >= 15 && user.getCountry().equals("DE");
    }
}
