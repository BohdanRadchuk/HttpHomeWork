package requests;

import entity.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class PostRequest {
    private final String USER_AGENT = "Chrome/65.0.3325.146";

    //создание одного пользователя
    public void sendPostCreateUser(User user) throws IOException {
        String url = "http://petstore.swagger.io/v2/user";
        String jsonUser = userToString(user);
        System.out.println("json user looks like this : \n" + jsonUser);
        postRequestTotal(url, jsonUser);
    }

    //создание групы пользователей с помощью массива
    public void sendPostArray(User[] users) throws IOException {
        String url = "http://petstore.swagger.io/v2/user/createWithArray";

        String allUsersString = "";
        for (int i = 0; i < users.length; i++) {                        //создаем стринг всех юзеров из масива в виде json
            allUsersString += userToString(users[i]);
            if (i < users.length - 1) {
                allUsersString += ",\n";                                //добавляем запятую и перено строки если есть ещё пользователи
            }
        }
        String jsonArrayOfUsers = "[\n" + allUsersString + "\n]";
        System.out.println("Так выглядит окончательный вариант масива пользователей: \n" + jsonArrayOfUsers);
        postRequestTotal(url, jsonArrayOfUsers);
    }

    //создание групы пользователей с помощью листа
    public void sendPostList(List<User> users) throws IOException {
        String url = "http://petstore.swagger.io/v2/user/createWithList";

        String allUsersString = "";
        for (int i = 0; i < users.size(); i++) {                        //создаем стринг всех юзеров из масива в виде json
            allUsersString += userToString(users.get(i));
            if (i < users.size() - 1) {
                allUsersString += ",\n";                                //добавляем запятую и перено строки если есть ещё пользователи
            }
        }
        String jsonArrayOfUsers = "[\n" + allUsersString + "\n]";
        System.out.println("Так выглядит окончательный вариант листа пользователей: \n" + jsonArrayOfUsers);
        postRequestTotal(url, jsonArrayOfUsers);
    }

    //общий метод для отправки пост-запроса, принимает URL куда отправлять и String в Json виде который будет поститься
    private void postRequestTotal(String url, String jsonUser) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("accept", "application/json");
        post.setHeader("Content-Type", "application/json");

        post.setEntity(new StringEntity(jsonUser));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
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

    public static String userToString(User user) {            //метод для конвертации пользователя в Стринг для отправки (в формате json)
        String jsonUser = "{\n" +
                "  \"id\": " + user.getId() + ",\n" +
                "  \"username\": \"" + user.getUsername() + "\",\n" +
                "  \"firstName\": \"" + user.getFirstName() + "\",\n" +
                "  \"lastName\": \"" + user.getLastName() + "\",\n" +
                "  \"email\": \"" + user.getEmail() + "\",\n" +
                "  \"password\": \"" + user.getPassword() + "\",\n" +
                "  \"phone\": \"" + user.getPhone() + "\",\n" +
                "  \"userStatus\": " + user.getUserStatus() + "\n" +
                "}";
        return jsonUser;
    }
}
