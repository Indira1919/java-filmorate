CREATE TABLE IF NOT EXISTS mpa (
    mpa_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
);
CREATE TABLE IF NOT EXISTS genre (
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS film (
    film_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_name VARCHAR NOT NULL,
    description VARCHAR(200),
    release_date DATE,
    duration INTEGER,
    mpa_id INTEGER REFERENCES mpa (mpa_id)
);

CREATE TABLE IF NOT EXISTS film_genres (
    film_id INTEGER REFERENCES film (film_id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genre (genre_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name VARCHAR(100),
    email VARCHAR(100),
    login VARCHAR(100) NOT NULL,
    birthday DATE
);

CREATE TABLE IF NOT EXISTS film_likes (
    film_id INTEGER REFERENCES film (film_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users (user_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS user_friends (
    user_id INTEGER REFERENCES users (user_id) ON DELETE CASCADE,
    friend_id INTEGER REFERENCES users (user_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, friend_id)
);