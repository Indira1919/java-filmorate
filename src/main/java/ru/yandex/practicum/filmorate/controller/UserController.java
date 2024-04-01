package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getAllFriends(@PathVariable int id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getListOfCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getListOfCommonFriends(id, otherId);
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        User userAdd = userService.addUser(user);
        log.info("Сохраненный пользователь: {}", user);

        return userAdd;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        User userUpdate = userService.updateUser(user);
        log.info("Данные обновлены: {}", user);

        return userUpdate;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
        log.info("Пользователь добавлен: {}", friendId);
    }

    @DeleteMapping("/users/{id}")
    public void removeUser(@PathVariable int id) {
        userService.removeUser(id);
        log.info("Пользователь с Id {} удален", id);

    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId);
        log.info("Пользователь удален: {}", friendId);
    }
}
