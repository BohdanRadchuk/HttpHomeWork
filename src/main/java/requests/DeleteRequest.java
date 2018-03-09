package requests;

import entity.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteRequest {

    private final String USER_AGENT = "Chrome/65.0.3325.146";

    public void sendDeleteUser(User user) throws IOException {      //перегруженый метод для приёма пользователя
        sendDeleteUser(user.getUsername());
    }

    public void sendDeleteUser(String userName) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        String url = "http://petstore.swagger.io/v2/user/" + userName;

        HttpDelete delete = new HttpDelete(url);

        delete.setHeader("User-Agent", USER_AGENT);
        delete.setHeader("accept", "application/xml");
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("api_key", "asdasd");

        HttpResponse response = client.execute(delete);
        System.out.println("\nSending 'DELETE' request to URL : " + url);
        System.out.println("Post parameters : " + delete.toString());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
    }
}