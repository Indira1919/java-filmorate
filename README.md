# java-filmorate

![er-diagram.png](er-diagram.png)

**Получение всех фильмов:**

SELECT *   

FROM film;

**Получение всех пользователей:**

SELECT *  

FROM user;


**Получение топ 10 фильмов:**

SELECT f.name,  

COUNT(fl.user_id)  

FROM film AS f  

INNER JOIN film_likes AS fl ON fl.film_id = f.film_id  

GROUP BY f.film_id  

ORDER BY COUNT(fl.user_id) DESC  

LIMIT 10;

**Получение списка общих друзей с пользователем 1:**

SELECT uf1.friend_id  

FROM (SELECT *  

FROM user_friend  

WHERE user_id = 1) AS uf1  

INNER JOIN user_friend AS uf2 ON uf1.friend_id = uf2.friend_id  

WHERE user_id = 2;