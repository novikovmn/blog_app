-- users

INSERT INTO users (username, password, firstname, lastname, role) VALUES ( 'ivan.ivanov@example.com', '$2a$12$aG3bIfmj.yvAIg9npL422.QL289b85WpbIqFbZcxnFkCQJhG71Coi', 'Ivan', 'Ivanov', 'USER');
INSERT INTO users (username, password, firstname, lastname, role) VALUES ( 'petr.petrov@example.com', '$2a$12$aG3bIfmj.yvAIg9npL422.QL289b85WpbIqFbZcxnFkCQJhG71Coi', 'Petr', 'Petrov', 'USER');
INSERT INTO users (username, password, firstname, lastname, role) VALUES ( 'olga.sidorova@example.com', '$2a$12$aG3bIfmj.yvAIg9npL422.QL289b85WpbIqFbZcxnFkCQJhG71Coi', 'Olga', 'Sidorova', 'USER');
INSERT INTO users (username, password, firstname, lastname, role) VALUES ( 'anna.kuznetsova@example.com', '$2a$12$aG3bIfmj.yvAIg9npL422.QL289b85WpbIqFbZcxnFkCQJhG71Coi', 'Anna', 'Kuznetsova', 'USER');
INSERT INTO users (username, password, firstname, lastname, role) VALUES ( 'alexey.smirnov@example.com','$2a$12$aG3bIfmj.yvAIg9npL422.QL289b85WpbIqFbZcxnFkCQJhG71Coi', 'Alexey', 'Smirnov', 'USER');
INSERT INTO users (username, password, firstname, lastname, role) VALUES ('novikovmn93@gmail.com', '$2a$12$aG3bIfmj.yvAIg9npL422.QL289b85WpbIqFbZcxnFkCQJhG71Coi', 'Maksim', 'Novikov', 'ADMIN');


-- posts

INSERT INTO posts (title, content, description, created_at, user_id) VALUES ( 'Первые шаги в PostgreSQL', 'Это руководство поможет начать работу с PostgreSQL...', 'Введение в основы PostgreSQL', '2025-07-28 09:15:00', 1);
INSERT INTO posts (title, content, description, created_at, user_id) VALUES ( 'Оптимизация запросов', 'Используйте индексы для ускорения выполнения сложных запросов...', 'Советы по оптимизации SQL', '2025-07-27 14:30:45', 1);
INSERT INTO posts (title, content, description, created_at, user_id) VALUES ( 'Транзакции и ACID', 'PostgreSQL строго соблюдает принципы ACID...', 'Основы транзакций в БД', '2025-07-25 18:00:22', 2);
INSERT INTO posts (title, content, description, created_at, user_id) VALUES ( 'Работа с JSONB', 'Хранение и индексация JSON-данных в PostgreSQL...', 'JSONB руководство', '2025-07-22 11:45:33', 5);
INSERT INTO posts (title, content, description, created_at, user_id) VALUES ('Репликация данных', 'Настройка репликации master-slave в PostgreSQL...', 'High Availability решения', '2025-07-20 16:20:10', 4);


-- comments

INSERT INTO comments (content, created_at, post_id, user_id) VALUES ('Отличная статья, спасибо!', '2025-07-28 10:30:00', 1, 3);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ('Можно подробнее про индексы?', '2025-07-27 15:45:00', 2, 1);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ('А есть примеры использования JSONB?', '2025-07-25 19:30:00', 4, 5);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ( 'Транзакции работают стабильно?', '2025-07-25 20:15:00', 3, 2);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ( 'Репликация настроена у меня через Patroni', '2025-07-22 13:20:00', 5, 4);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ( 'Для новичков самое то!', '2025-07-28 11:05:00', 1, 5);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ( 'Оптимизация помогла ускорить запросы в 10 раз', '2025-07-27 16:30:00', 2, 3);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ( 'ACID - основа надежности', '2025-07-26 09:00:00', 3, 1);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ( 'JSONB просто революция', '2025-07-23 12:15:00', 4, 2);
INSERT INTO comments (content, created_at, post_id, user_id) VALUES ( 'Master-slave репликация устарела?', '2025-07-21 17:40:00', 5, 4);

