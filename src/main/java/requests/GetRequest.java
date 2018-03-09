package requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetRequest {
    private final String USER_AGENT = "Chrome/65.0.3325.146";

    public void sendGetLogin(String username, String password) throws IOException {
        String url = "http://petstore.swagger.io/v2/user/login?username=" + username + "&password=" + password;
        int response = sendTotal(url);
        if (response == 200) {
            System.out.println("you successfully logged in");
        } else {
            if (response == 400) {
                System.err.println("Login failed: Invalid username/password supplied");
            }
        }
    }

    public void sendGetLogout() throws IOException {
        String url = "http://petstore.swagger.io/v2/user/logout";
        int response = sendTotal(url);
        if (response == 200) {
            System.out.println("you are successfully logged out");
        }
    }

    public void sendGetUser(String username) throws IOException {
        String url = "http://petstore.swagger.io/v2/user/" + username;
        int response = sendTotal(url);
        if (response == 200) {
            System.out.println("you successfully got user info");
        } else {
            if (response == 400) {
                System.err.println("Invalid username supplied");
            } else {
                if (response == 404) {
                    System.err.println("User not found");
                }
            }
        }
    }

    private int sendTotal(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = null;

        response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        int responseCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
        return responseCode;
    }

}
