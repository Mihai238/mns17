package at.ac.tuwien.mns17.java8example.services;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jakob on 14.01.2017.
 */

public class WebsiteUptimeCheck implements ServiceCheck {

    private String url;

    public WebsiteUptimeCheck(String url) {
        this.url = url;
    }

    @Override
    public URL getEndpointURL() throws MalformedURLException {
        return new URL(this.url);
    }
}
