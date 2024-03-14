package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;


public class Genre {
    int id;
    @NotBlank
    String name;
}
