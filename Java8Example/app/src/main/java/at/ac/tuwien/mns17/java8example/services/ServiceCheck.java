package at.ac.tuwien.mns17.java8example.services;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jakob on 14.01.2017.
 */

public interface ServiceCheck {

    default boolean testConnection() {
        URL endpoint = null;
        try {
            endpoint = getEndpointURL();

            HttpURLConnection urlConnection = (HttpURLConnection) endpoint.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            int responseCode = urlConnection.getResponseCode();

            return responseCode == 200;
        } catch (IOException e) {
            System.err.println("Website check failed with Exception " + e.getMessage());
            return false;
        }
    }

    URL getEndpointURL() throws MalformedURLException;
}
