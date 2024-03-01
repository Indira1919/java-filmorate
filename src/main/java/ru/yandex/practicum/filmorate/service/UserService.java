package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    public UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(int id, int friendId) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (userStorage.getUser(friendId) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        userStorage.getUser(id).getFriends().add(friendId);
        userStorage.getUser(friendId).getFriends().add(id);

        userStorage.updateUser(userStorage.getUser(friendId));
        return userStorage.updateUser(userStorage.getUser(id));
    }

    public User removeFriend(int id, int friendId) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (userStorage.getUser(friendId) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        userStorage.getUser(id).getFriends().remove(friendId);
        userStorage.getUser(friendId).getFriends().remove(id);

        userStorage.updateUser(userStorage.getUser(friendId));
        return userStorage.updateUser(userStorage.getUser(id));
    }

    public List<User> getAllFriends(int id) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        return userStorage.getUser(id).getFriends().stream().map(userStorage::getUser).collect(Collectors.toList());
    }

    public List<User> getListOfCommonFriends(int id, int otherId) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (userStorage.getUser(otherId) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        return userStorage.getUser(id).getFriends().stream()
                .filter(userStorage.getUser(otherId).getFriends()::contains)
                .map(userStorage::getUser).collect(Collectors.toList());
    }
}
