package ru.yandex.practicum.filmorate.storage;

public interface LikesStorage {
    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);
}
