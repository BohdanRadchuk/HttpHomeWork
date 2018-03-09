import entity.User;
import requests.DeleteRequest;
import requests.GetRequest;
import requests.PostRequest;
import requests.PutRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HttpMain {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        startMenu();
    }

    public static void startMenu() throws IOException {
        System.out.println("Введите номер типа запроса");
        System.out.println("1 - Post");
        System.out.println("2 - Get");
        System.out.println("3 - Put");
        System.out.println("4 - Delete");
        System.out.println("0 - Выход");
        int choice = scanner.nextInt();
        if (choice == 1) postMenu();

        if (choice == 2) getMenu();

        if (choice == 3) updateMenu();

        if (choice == 4) deleteMenu();

        if (choice == 0) exitMenu();
    }

    public static void postMenu() throws IOException {
        PostRequest postRequest = new PostRequest();
        System.out.println("Введите номер операции");
        System.out.println("1 - Создать нового пользователя");
        System.out.println("2 - Создать массив пользователей");
        System.out.println("3 - Создать лист пользователей");
        System.out.println("0 - Выход");
        int choice = scanner.nextInt();
        if (choice == 1) {
            postRequest.sendPostCreateUser(createUser());
            otherOperation();
        }

        if (choice == 2) {
            System.out.println("какого размера будет ваш массив ?");
            User[] users = new User[scanner.nextInt()];
            for (int i = 0; i < users.length; i++) {
                System.out.println("Создание нового пользователя");
                users[i] = createUser();
            }
            postRequest.sendPostArray(users);
            otherOperation();
        }

        if (choice == 3) {
            List<User> userList = new ArrayList<User>();
            String oneMore = "y";
            while (!oneMore.equals("n")) {
                userList.add(createUser());
                System.out.println("хотите ввести ещё 1 пользователя в список  ? \n y - да; \n n - нет ");
                scanner.nextLine();
                oneMore = scanner.nextLine();
            }
            postRequest.sendPostList(userList);
            otherOperation();
        }
        if (choice == 0) exitMenu();
    }

    public static void getMenu() throws IOException {
        GetRequest getRequest = new GetRequest();
        System.out.println("Введите номер операции");
        System.out.println("1 - Сделать запрос логина");
        System.out.println("2 - Сделать запрос логаута");
        System.out.println("3 - Сделать запрос получения пользователя по имени");
        System.out.println("0 - Выход");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("Введите login");
            scanner.nextLine();
            String login = scanner.nextLine();
            System.out.println("Введите password");
            String pass = scanner.nextLine();
            getRequest.sendGetLogin(login, pass);
            otherOperation();
        }

        if (choice == 2) {
            getRequest.sendGetLogout();
            otherOperation();
        }

        if (choice == 3) {
            System.out.println("Введите имя пользователя");
            scanner.nextLine();
            getRequest.sendGetUser(scanner.nextLine());
            otherOperation();
        }
        if (choice == 0) exitMenu();
    }

    public static void updateMenu() throws IOException {
        PutRequest putRequest = new PutRequest();
        System.out.println("Введите Username пользователя которого хотите изменить");
        scanner.nextLine();
        String username = scanner.nextLine();
        System.out.println("Введите параметры полей нового пользователя");
        User user = createUser();
        putRequest.sendPutUser(username, user);
        otherOperation();
    }

    public static void deleteMenu() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest();
        System.out.println("Введите Username пользователя которого хотите изменить");
        scanner.nextLine();
        String username = scanner.nextLine();
        deleteRequest.sendDeleteUser(username);
        otherOperation();
    }

    public static User createUser() {
        User user = new User();
        System.out.println("Введите Id");
        if (scanner.hasNextInt()) {
            user.setId(scanner.nextInt());
        } else {
            System.err.println("вы ввели не числовое значение. Ид присвоен по умолчанию (0)");
            scanner.next();
            user.setId(0);
        }
        System.out.println("Введите Username");
        scanner.nextLine();
        user.setUsername(scanner.nextLine());
        System.out.println("Введите FirstName");
        user.setFirstName(scanner.nextLine());
        System.out.println("Введите LastName");
        user.setLastName(scanner.nextLine());
        System.out.println("Введите Email");
        user.setEmail(scanner.nextLine());
        System.out.println("Введите Password");
        user.setPassword(scanner.nextLine());
        System.out.println("Введите Phone");
        user.setPhone(scanner.nextLine());
        System.out.println("Введите UserStatus (0 или 1)");
        if (scanner.hasNextByte()) {
            user.setUserStatus(scanner.nextByte());
        } else user.setUserStatus(new Byte("0"));

        scanner.nextLine();
        return user;
    }

    private static void otherOperation() throws IOException {      //меню возврата в главное меню или выхода (используется после операций)
        System.out.println("хотите выполнить другую операцию ?");
        System.out.println("y - Да, вернуться в главное меню");
        System.out.println("n - Нет, закрыть програму");
        String choice = scanner.nextLine();
        choice = choice.toLowerCase();
        if (choice.equals("y") || choice.equals("n")) {
            if (choice.equals("y")) {
                startMenu();
            }
            if (choice.equals("n")) {
                exitMenu();
            }
        } else {
            System.out.println("Введите один из предложеных вариантов");
            otherOperation();
        }
    }

    private static void exitMenu() {        //меню выхода для закрытия програмы
        System.out.println("Спасибо что пользовались нашей програмой");
        scanner.close();
    }
}
