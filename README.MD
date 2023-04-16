# An toruist application server

## Stack
  - Java
    - Hibernate
    - Sql Server
    - Springboot
    - Junit
  - Angular
    - Nginx
  - React
  - Docker

This application has an security layer based on JWT

## Install
### Requeriments
- Docker Compose
- Git

``` sh
git clone https://github.com/Ru-kko/tourist_test.git /app
cd /app
docker compose up
```

To make autostart server on linux distributions using system md

``` sh
cp /app/docker-compose-app.service /etc/systemd/system/docker-compose-app.service
sudo systemctl daemon-reload
sudo systemctl enable docker-compose-app.service
sudo systemctl start docker-compose-app.service

```