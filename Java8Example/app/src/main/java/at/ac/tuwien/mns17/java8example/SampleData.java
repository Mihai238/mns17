package at.ac.tuwien.mns17.java8example;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.mns17.java8example.models.User;

/**
 * Created by Jakob on 14.01.2017.
 */

public interface SampleData {

    public static List<User> getSampleUsers() {
        List<User> samples = new ArrayList<>();
        samples.add(new User("jakob", 24, "AT"));
        samples.add(new User("georg", 25, "AT"));
        samples.add(new User("michal", 13, "DE"));
        samples.add(new User("simon", 29, "AT"));
        samples.add(new User("budi", 40, "AT"));
        samples.add(new User("etienne", 39, "AT"));
        samples.add(new User("florentin", 33, "UK"));
        samples.add(new User("lars", 11, "UK"));
        samples.add(new User("nils", 15, "DE"));
        samples.add(new User("sophia", 18, "AT"));
        samples.add(new User("anabell", 19, "FR"));
        samples.add(new User("thomas", 17, "NL"));
        samples.add(new User("manuel", 60, "HU"));
        samples.add(new User("lisa", 54, "DE"));
        samples.add(new User("carina", 44, "US"));
        samples.add(new User("tanja", 22, "NO"));
        samples.add(new User("sonja", 21, "NO"));

        return samples;
    }
}
