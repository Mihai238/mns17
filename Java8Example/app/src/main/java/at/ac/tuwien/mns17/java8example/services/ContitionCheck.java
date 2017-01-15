package at.ac.tuwien.mns17.java8example.services;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.tuwien.mns17.java8example.models.User;

/**
 * Created by Jakob on 14.01.2017.
 */

public interface ContitionCheck {

    default List<String> filterCondition(List<User> users) {
        return users.stream()
              .filter(this::fulfillContition)
              .map(User::getUsername)
              .collect(Collectors.toList());

    }

    boolean fulfillContition(User user);
}
