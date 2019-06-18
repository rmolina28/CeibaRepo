package co.com.ceiba.mobile.pruebadeingreso.rest;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Endpoints {
    public static final String URL_BASE = "https://jsonplaceholder.typicode.com";
    public static final String GET_USERS = "/users";
    public static final String GET_POST_USER = "/posts?";

    private static HttpURLConnection connection;

    public static String getData(String urlString) {
        StringBuilder result = new StringBuilder();
        try{
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            while((inputLine = in.readLine()) != null){
                result.append(inputLine);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            connection.disconnect();
        }

        return result.toString();
    }
}
