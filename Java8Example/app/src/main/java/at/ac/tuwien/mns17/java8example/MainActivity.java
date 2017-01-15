package at.ac.tuwien.mns17.java8example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import at.ac.tuwien.mns17.java8example.models.User;
import at.ac.tuwien.mns17.java8example.services.ServiceCheck;
import at.ac.tuwien.mns17.java8example.services.WebsiteUptimeCheck;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Static Interface Methods:
         * It makes a lot of sense putting util methods which do not have any state into an
         * interface.
         * The reason for this is that interfaces in Java are cheaper than classes in terms of
         * space required. Static methods in normal classes require a built class in the heap space,
         * an interface on the other hand is much cheaper.
         */

        List<User> sampleUsers = SampleData.getSampleUsers();

        /**
         * Stream API:
         * The Java8 Stream API makes manipulating lists much easier with less code required.
         * The following code samples takes a list of Users, extracts all users from Austria
         * with ages 18 and higher and extracts their usernames into a new list
         * Not that this sample already uses Lambda Expressions which will be explained further in a
         * following code sample
         */

        List<String> atUsersOver18 = sampleUsers.stream()
                                                .filter(u -> u.getAge() >= 18)
                                                .filter(u -> u.getCountry().equals("AT"))
                                                .map(user -> user.getUsername())
                                                .collect(Collectors.toList());

        /**
         * Lambda Expressions:
         * Now we do the same thing we did in the Stream API example again using a Lambda Expression.
         * We use the Predicate Interface which has exactly one abstract Method called test
         * Instead of creating an Anonymous class implementing the interface and method we use a Lambda expression
         * to implement the method.
         * This not only simplifies the code but also improves efficiency since lambdas are implemented
         * differently and more efficiently than Anonymous inner classes.
         */
        atUsersOver18 = getUsernamesOfSpecificUsers(sampleUsers, u -> u.getAge() >= 18 && u.getCountry().equals("AT"));

        /**
         * Method References:
         * Method Referneces are syntactic improvements to Lambda Expressions if in the Lambda Expressions only a method
         * is called
         */
        atUsersOver18 = getUsernamesOfSpecificUsersMethodReference(sampleUsers, u -> u.getAge() >= 18 && u.getCountry().equals("AT"));

        /**
         * Default Interface Methods:
         * Functional Interfaces should be the new goto method for realizing subtyping in Java. Using interfaces
         * instead of abstract classes again is more perfomant due to the fact that the abstract class doesn't need
         * to be created in the Java heap space.
         * The following code sample realizes an Uptime Check for websites using a ServiceCheck Interface as a superType.
         * The ServiceCheck Interface has a default method which uses another method of the interface which has to be implemented in the subtype
         */
        ServiceCheck uptimeCheck = new WebsiteUptimeCheck("http://koreanbuilds.net");
        boolean siteUp = uptimeCheck.testConnection();

    }

    private List<String> getUsernamesOfSpecificUsers(List<User> users, Predicate<User> tester) {
        return users.stream()
                .filter(u -> tester.test(u))
                .map(u -> u.getUsername())
                .collect(Collectors.toList());
    }

    private List<String> getUsernamesOfSpecificUsersMethodReference(List<User> users, Predicate<User> tester) {
        return users.stream()
                .filter(tester::test)
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
