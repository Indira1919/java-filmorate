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
        return userService.userStorage.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.userStorage.getUser(id);
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
        User userAdd = userService.userStorage.addUser(user);
        log.info("Сохраненный пользователь: {}", user);

        return userAdd;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        User userUpdate = userService.userStorage.updateUser(user);
        log.info("Данные обновлены: {}", user);

        return userUpdate;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        User userAdd = userService.addFriend(id, friendId);
        log.info("Пользователь добавлен: {}", userAdd);

        return userAdd;
    }

    @DeleteMapping("/users/{id}")
    public User removeUser(@PathVariable int id) {
        User userRemove = userService.userStorage.removeUser(id);
        log.info("Данные обновлены: {}", userRemove);

        return userRemove;
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable int id, @PathVariable int friendId) {
        User friendRemove = userService.removeFriend(id, friendId);
        log.info("Пользователь удален: {}", friendRemove);

        return friendRemove;
    }
}
