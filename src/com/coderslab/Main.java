package com.coderslab;

import com.coderslab.dao.UserDao;
import com.coderslab.domain.User;

public class Main {

    public static void main(String[] args) {
        User user1 = new User("lareth51",
                "lareth51@gmail.com", "Dupa123!");
        User user2 = new User("adam1",
                "adam1@gmail.com", "Dupa123!");
        User user3 = new User("lareth51",
                "adam21@gmail.com", "Dupa123!");

        UserDao.create(user1);
        UserDao.create(user2);
        UserDao.create(user3);

        System.out.println("Email pierwszego uzytkownika");
        System.out.println(UserDao.read(1).getEmail());

        System.out.println("Update pierwszego uzytkownika");
        user1.setEmail("zmienionyEmail@gmail.com");
        System.out.println(UserDao.update(user1));

        System.out.println("Uzytkownik po zmianach");
        System.out.println(UserDao.read(1).getEmail());

        System.out.println("Usuwamy uzytkownika 2");
        UserDao.delete(2);

        System.out.println("Lista uzytkownikow w bazie danych:");
        User[] users = UserDao.findAllUsers();
        for(int i = 0; i < users.length; i++) {
            User user = users[i];
            System.out.println("Uzytkownik: " + user.getId() + " email: " + user.getEmail());
        }
    }
}
