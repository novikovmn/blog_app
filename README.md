# BlogApp

### Инструкция по запуску:

1. Установите Docker</br>
https://docs.docker.com/engine/install/ - для Linux
https://docs.docker.com/desktop/setup/install/windows-install/ - для Windows</br>
2. Склонируйте репозиторий к себе на компьютер.</br>
3. Откройте папку с проектом в cmd/Power Shell для Windows,
либо в терминале для Unix-подобных систем.</br>
4. **Для запуска** приложения выполните команду `docker compose up`
5. В браузере приложение будет доступно по адресу `localhost:8080/`</br>
6. База данных приложения будет доступна по адресу `localhost:5432`</br> 
под именем `blogapp_db` (на случай необходимости работы через сторонние клиенты)
7. **Для остановки** приложения выполните команду `docker compose stop`
   