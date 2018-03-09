package requests;

import entity.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PutRequest {
    private final String USER_AGENT = "Chrome/65.0.3325.146";

    public void sendPutUser(String userName, User user) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        String url = "http://petstore.swagger.io/v2/user/" + userName;

        HttpPut put = new HttpPut(url);
        put.setHeader("User-Agent", USER_AGENT);
        put.setHeader("accept", "application/json");
        put.setHeader("Content-Type", "application/json");

        String jsonUser = PostRequest.userToString(user);       //используем статический метод из PostRequest для
        // преобразования пользователя в json формат для отправки на сайт. Тот метод можно было бы вынести в отдельный кдасс,
        // насколько это рационально - мне тяжело сказать

        put.setEntity(new StringEntity(jsonUser));              //в этом месте мы добавляем значение нового пользователя

        HttpResponse response = client.execute(put);            //выполняем пут запрос и получаем ответ
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("PUT parameters : " + put.getEntity());
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

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
