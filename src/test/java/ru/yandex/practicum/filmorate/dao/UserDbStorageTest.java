package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDbStorageTest {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;
    private User newUser;

    @BeforeEach
    public void createUser() {
        newUser = new User(1, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        userStorage.addUser(newUser);
        newUser.setId(1);
    }

    @Test
    public void testAddAndFindUserById() {
        // вызываем тестируемый метод
        User savedUser = userStorage.getUser(1);

        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newUser);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testRemoveUserById() {
        User savedUser = userStorage.getUser(1);

        assertThat(savedUser)
                .isNotNull();

        userStorage.removeUser(newUser.getId());

        assertThat(userStorage.getUser(1))
                .isNull();
    }

    @Test
    public void testGetUserFriends() {
        User newUser2 = new User(2, "user@mail.ru", "test", "test test,",
                LocalDate.of(1990, 1, 1));
        userStorage.addUser(newUser2);
        newUser2.setId(2);
        List<User> friends = new ArrayList<>();
        friends.add(newUser2);
        friendsStorage.addFriend(1, 2);
        assertThat(friends)
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(userStorage.getAllFriends(1));
    }

    @Test
    public void testGetUsers() {
        User newUser2 = new User(2, "user@mail.ru", "test", "test test,",
                LocalDate.of(1990, 1, 1));
        userStorage.addUser(newUser2);
        newUser2.setId(2);
        List<User> users = new ArrayList<>();
        users.add(newUser);
        users.add(newUser2);

        assertThat(users)
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(userStorage.getUsers());
    }
}