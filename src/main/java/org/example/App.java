package org.example;

import org.example.configuration.Config;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = context.getBean("userService", UserService.class);

        List<User> users = userService.getUsers();

        User user = new User(3L, "James","Brown", (byte) 33);
        userService.saveUser(user);

        user.setName("Thomas");
        user.setLastName("Shelby");
        userService.updateUser(user);
        userService.deleteUser(user.getId());

        System.out.println("Result: " + userService.getResult());
    }
}
