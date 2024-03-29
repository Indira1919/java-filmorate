package ru.yandex.practicum.filmorate.storage;

public interface FriendsStorage {

    void addFriend(int id, int friendId);

    void removeFriend(int id, int friendId);

}
