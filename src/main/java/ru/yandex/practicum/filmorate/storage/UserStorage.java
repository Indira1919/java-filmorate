package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {

    Collection<User> getUsers();

    User getUser(int id);

    User addUser(User user);

    User updateUser(User user);

    void removeUser(int id);

    List<User> getAllFriends(int id);

    List<User> getListOfCommonFriends(int id, int otherId);


}
