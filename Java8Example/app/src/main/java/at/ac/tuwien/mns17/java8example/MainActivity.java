package at.ac.tuwien.mns17.java8example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import at.ac.tuwien.mns17.java8example.models.User;
import at.ac.tuwien.mns17.java8example.services.ContitionCheck;
import at.ac.tuwien.mns17.java8example.services.Greater15andDECheck;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sample_a)
    Button button_a;
    @BindView(R.id.sample_b)
    Button button_b;
    @BindView(R.id.sample_c)
    Button button_c;
    @BindView(R.id.sample_d)
    Button button_d;
    @BindView(R.id.sample_e)
    Button button_e;
    @BindView(R.id.resultText)
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        button_a.setOnClickListener(view -> codeSampleA());
        button_b.setOnClickListener(view -> codeSampleB());
        button_c.setOnClickListener(view -> codeSampleC());
        button_d.setOnClickListener(view -> codeSampleD());
        button_e.setOnClickListener(view -> codeSampleE());
    }

    /**
     * Static Interface Methods:
     * It makes a lot of sense putting util methods which do not have any state into an
     * interface.
     * The reason for this is that interfaces in Java are cheaper than classes in terms of
     * space required. Static methods in normal classes require a built class in the heap space,
     * an interface on the other hand is much cheaper.
     *
     * In this Codesample we call a method of a static interface which provides us with a List of
     * Sampleusers
     */
    public void codeSampleA(){
        List<User> sampleUsers = SampleData.getSampleUsers();

        result.setText("");
        result.append("Sampleusers:\r\n");
        for( User u : sampleUsers ) {
            result.append(u.getUsername() + " - " + u.getCountry() + " - " + u.getAge() + "\r\n");
        }
    }

    /**
     * Stream API:
     * The Java8 Stream API makes manipulating lists much easier with less code required.
     * Note that this sample already uses Lambda Expressions which will be explained further in a
     * following code sample
     *
     * In the following code sample we take a list of users, extract all users from Austria
     * with ages 18 and higher and extracts their usernames into a new list
     */
    public void codeSampleB() {
        List<User> sampleUsers = SampleData.getSampleUsers();

        List<String> usernamesAdultAustria = sampleUsers.stream()
                .filter(u -> u.getAge() >= 18)
                .filter(u -> u.getCountry().equals("AT"))
                .map(user -> user.getUsername())
                .collect(Collectors.toList());

        result.setText("");
        result.append("Usernames of austrian adult users:\r\n");
        for( String username : usernamesAdultAustria ) {
            result.append(username + "\r\n");
        }
    }

    /**
     * Lambda Expressions:
     * Lambda Expressions not only simplify code but also improves efficiency since lambdas are implemented
     * differently and more efficiently than Anonymous inner classes.
     *
     * In this sample we do the same thing we did in the Stream API example again using a Lambda Expression.
     * We use the Predicate Interface which has exactly one abstract Method called test
     * Instead of creating an Anonymous class implementing the interface and method we use a Lambda expression
     * to implement the method. This time we will extract all usernames younger than 18
     */
    public void codeSampleC() {
        List<User> sampleUsers = SampleData.getSampleUsers();
        List<String> usernamesMinors = getUsernamesOfSpecificUsers(sampleUsers, u -> u.getAge() < 18);

        result.setText("");
        result.append("Usernames of minor users:\r\n");
        for( String username : usernamesMinors ) {
            result.append(username + "\r\n");
        }
    }

    /**
     * Method References:
     * Method Referneces are syntactic improvements to Lambda Expressions if in the Lambda Expressions only a method
     * is called.
     *
     * This time we extract the usernames of all users from Norway
     */
    public void codeSampleD() {
        List<User> sampleUsers = SampleData.getSampleUsers();
        List<String> usernamesNorway = getUsernamesOfSpecificUsersMethodReference(sampleUsers, u -> u.getCountry().equals("NO"));

        result.setText("");
        result.append("Usernames of norwegian users:\r\n");
        for( String username : usernamesNorway ) {
            result.append(username + "\r\n");
        }
    }

    /**
     * Default Interface Methods:
     * Functional Interfaces should be the new goto method for realizing subtyping in Java. Using interfaces
     * instead of abstract classes again is more perfomant due to the fact that the abstract class doesn't need
     * to be created in the Java heap space.
     *
     * The following CodeSample is again realizing a Filter for a List of Users. The SuperType is a functional interface
     * which has a default method filterCondition which calls an abstract method fullfillCondition which has to be implemented
     * in a SubType
     */
    public void codeSampleE() {
        ContitionCheck contitionCheck = new Greater15andDECheck();
        List<User> sampleUsers = SampleData.getSampleUsers();
        List<String> usernamesGermanyOlder15 = contitionCheck.filterCondition(sampleUsers);

        result.setText("");
        result.append("Usernames of german users older than or exactly 15:\r\n");
        for( String username : usernamesGermanyOlder15 ) {
            result.append(username + "\r\n");
        }
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
